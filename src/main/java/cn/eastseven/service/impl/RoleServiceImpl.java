package cn.eastseven.service.impl;

import cn.eastseven.security.model.RoleEntity;
import cn.eastseven.security.repository.RoleRepository;
import cn.eastseven.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author eastseven
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<RoleEntity> pageBy(PageRequest of) {
        return roleRepository.findAll(of);
    }
}
