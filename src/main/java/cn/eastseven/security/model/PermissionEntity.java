package cn.eastseven.security.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author d7
 */
@Data
@Document(collection = "t_permission")
public class PermissionEntity implements Serializable {

    public PermissionEntity() {}

    public PermissionEntity(String id, String name, String url, String method) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.method = method;
    }

    @Id
    protected String id;

    protected String name;

    protected String description;

    protected String url;

    protected String method;
}
