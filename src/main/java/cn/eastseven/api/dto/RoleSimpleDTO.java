package cn.eastseven.api.dto;

import cn.eastseven.security.model.RoleEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author eastseven
 */
@Data
public class RoleSimpleDTO implements Serializable {

    public RoleSimpleDTO(RoleEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }

    protected String id;

    protected String name;
}
