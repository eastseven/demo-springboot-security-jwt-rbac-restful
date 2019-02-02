package cn.eastseven.security;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @author d7
 */
@Slf4j
@Component
public class JwtFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) o;
        final String url = filterInvocation.getRequestUrl();
        final String method = filterInvocation.getRequest().getMethod();
        UrlConfigAttribute attribute = new UrlConfigAttribute(url, method);
        log.debug(">>> getAttributes {}", attribute);

        if (StringUtils.containsNone(url, "/auth")) {
            return Lists.newArrayList(attribute);
        }

        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        List<UrlConfigAttribute> allConfigAttributes = Lists.newArrayList();
        for (HttpMethod method : HttpMethod.values()) {
            allConfigAttributes.add(new UrlConfigAttribute("/users", method.name()));
        }

        log.debug(">>> getAllConfigAttributes {}", allConfigAttributes);
        return null;//Sets.newHashSet(allConfigAttributes);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
