package cn.eastseven.security;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * @author d7
 * 角色
 */
@Data
@Document(collection = "t_role")
public class RoleEntity implements Serializable {

    public RoleEntity() {
    }

    public RoleEntity(String role) {
        this.id = role;
        this.name = role;
    }

    public RoleEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    private String id;

    private String name;

    @DBRef(lazy = true)
    private List<PermissionEntity> permissions;
}
