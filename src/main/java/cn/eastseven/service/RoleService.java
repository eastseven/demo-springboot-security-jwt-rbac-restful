package cn.eastseven.service;

import cn.eastseven.security.model.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @author eastseven
 */
public interface RoleService {

    Page<RoleEntity> pageBy(PageRequest of);
}
