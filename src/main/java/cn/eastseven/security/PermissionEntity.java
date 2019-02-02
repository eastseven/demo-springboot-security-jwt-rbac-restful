package cn.eastseven.security;

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

    @Id
    private String id;

    private String name;

    private String description;
}
