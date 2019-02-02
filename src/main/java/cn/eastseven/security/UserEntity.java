package cn.eastseven.security;

import com.google.common.collect.Sets;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Document(collection = "t_user")
public class UserEntity implements Serializable {

    public UserEntity() {
        lastPasswordResetDate = new Date();
        enabled = Boolean.TRUE;
        roles = Sets.newHashSet();
    }

    public UserEntity(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    @Id
    private String id;

    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @NotNull
    @Size(min = 4, max = 50)
    private String firstname;

    @NotNull
    @Size(min = 4, max = 50)
    private String lastname;

    @NotNull
    @Size(min = 4, max = 50)
    private String email;

    @NotNull
    private Boolean enabled;

    @NotNull
    private Date lastPasswordResetDate;

    private List<Authority> authorities;

    @DBRef(lazy = true)
    private Set<RoleEntity> roles;

}