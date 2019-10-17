package cn.eastseven.api.dto;

import cn.eastseven.security.model.PermissionEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author eastseven
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionSimpleDTO extends PermissionEntity {

    public PermissionSimpleDTO(PermissionEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }

}
