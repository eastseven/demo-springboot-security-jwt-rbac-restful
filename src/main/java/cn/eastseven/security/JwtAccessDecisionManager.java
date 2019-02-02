package cn.eastseven.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author d7
 */
@Slf4j
@Component
public class JwtAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        log.debug(">>> decide, authentication={}, object={}, ConfigAttributeCollection={}", authentication, o, collection);
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
