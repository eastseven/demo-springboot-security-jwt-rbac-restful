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
import java.util.regex.Pattern;
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
        final String requestUrl = filterInvocation.getRequestUrl();
        final String requestMethod = filterInvocation.getRequest().getMethod();
        String id = "privilege_" + requestUrl + "_" + requestMethod.toLowerCase();
        log.debug(">>> getAttributes id={}, url={}, method={}", id, requestUrl, requestMethod);

        if ("/auth".equalsIgnoreCase(requestUrl)) {
            return null;
        }

        List<PermissionEntity> permissionList = permissionRepository.findAll();
        for (PermissionEntity permission : permissionList) {
            String url = permission.getUrl();
            String method = permission.getMethod();
            boolean match = Pattern.compile(url).matcher(requestUrl).find();

            if (StringUtils.equalsIgnoreCase(method, requestMethod) && match) {
                // AntPathMatcher matcher = new AntPathMatcher(url);
                return Sets.newHashSet(new SecurityConfig(permission.getName()));
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
