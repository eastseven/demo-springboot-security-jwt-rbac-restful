package cn.eastseven.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.apache.commons.collections4.IterableUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author d7
 */
@Slf4j
@Component
public class JwtAccessDecisionManager implements AccessDecisionManager {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        log.debug(">>> collection={}, {}", isEmpty(collection), collection);

        if (isEmpty(collection)) {
            return;
        }

        for (ConfigAttribute configAttribute : collection) {
            if (isBlank(configAttribute.getAttribute())) {
                log.debug(">>> configAttribute={}, {}", configAttribute.getClass(), configAttribute.getAttribute());
                return;
            }
        }

        JwtUser user = null;
        FilterInvocation filterInvocation = null;

        if (authentication.getPrincipal() instanceof JwtUser) {
            user = (JwtUser) authentication.getPrincipal();
        }

        if (o instanceof FilterInvocation) {
            filterInvocation = (FilterInvocation) o;
        }

        if (Objects.nonNull(user) && Objects.nonNull(filterInvocation)) {
            String url = filterInvocation.getRequestUrl();
            String method = filterInvocation.getRequest().getMethod();

            for (ConfigAttribute configAttribute : collection) {
                // 当前请求
                String attribute = configAttribute.getAttribute();
                for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                    // 用户角色
                    Optional<RoleEntity> role = roleRepository.findById(grantedAuthority.getAuthority());
                    if (role.isPresent()) {
                        List<PermissionEntity> permissions = role.get().getPermissions();
                        for (int index = 0; index < permissions.size(); index++) {
                            PermissionEntity permission = permissions.get(index);
                            log.debug(">>> attribute={}, {}", attribute, permission);
                            // authenticated
                        }
                        long count = role.get().getPermissions().stream().filter(p -> p.getName().equalsIgnoreCase(attribute)).count();
                        boolean bln = count > 0L;
                        log.debug(">>> decide=[{}, {}], method={}, url={}, attribute={}, role={}", count, bln, method, url, attribute, role.get());
                        if (bln) {
                            return;
                        }
                    }
                }
            }
        }

        throw new AccessDeniedException("no right");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        log.debug(">>> supports ConfigAttribute {}", configAttribute);
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        log.debug(">>> supports Class {}", aClass);
        return true;
    }
}
