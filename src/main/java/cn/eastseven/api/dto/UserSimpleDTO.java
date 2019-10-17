package cn.eastseven.api.dto;

import cn.eastseven.security.model.RoleEntity;
import cn.eastseven.security.model.UserEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * @author eastseven
 */
@Data
public class UserSimpleDTO implements Serializable {

    public UserSimpleDTO(UserEntity entity) {
        BeanUtils.copyProperties(entity, this, "roles");
        if (!isEmpty(entity.getRoles())) {
            this.roles = entity.getRoles().stream().map(RoleEntity::getId).collect(toSet());
        }
    }

    protected String id;

    protected String username;

    protected String firstname;

    protected String lastname;

    protected String email;

    protected Boolean enabled;

    protected Date lastPasswordResetDate;

    protected Set<String> roles;
}
