package cn.eastseven.security;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

/**
 * @author d7
 */
@Slf4j
@Component
public class JwtFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) o;
        final String url = filterInvocation.getRequestUrl();
        final String method = filterInvocation.getRequest().getMethod();
        String id = "PRIVILEGE_" + url.replaceFirst("/", "") + "_" + method;
        log.debug(">>> getAttributes id={}", id);

        List<PermissionEntity> permissionList = permissionRepository.findAll();
        for (PermissionEntity permission : permissionList) {
            String permissionId = id.toUpperCase();
            if (StringUtils.equalsAnyIgnoreCase(permissionId, permission.getId())) {
                Set<ConfigAttribute> result = Sets.newHashSet(new SecurityConfig(permission.getName()));
                log.debug(">>> getAttributes return {}", result);
                return result;
            }
        }

        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        // TODO 加载所有Permission
        // return Sets.newHashSet(load());
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    private Collection<SecurityConfig> load() {
        List<PermissionEntity> permissionList = permissionRepository.findAll();
        if (isNotEmpty(permissionList)) {
            return permissionList.stream().map(p -> new SecurityConfig(p.getName())).collect(Collectors.toSet());
        }
        return Sets.newHashSet();
    }
}
