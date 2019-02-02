package cn.eastseven.security;

import lombok.Data;
import org.springframework.security.access.ConfigAttribute;

/**
 * @author d7
 */
@Data
public class UrlConfigAttribute implements ConfigAttribute {

    private String url;

    private String method;

    public UrlConfigAttribute(String url, String method) {
        this.url = url;
        this.method = method;
    }

    @Override
    public String getAttribute() {
        return url + "#" + method;
    }
}
