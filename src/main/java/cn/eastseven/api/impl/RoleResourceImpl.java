package cn.eastseven.api.impl;

import cn.eastseven.api.RoleResource;
import cn.eastseven.api.dto.RoleSimpleDTO;
import cn.eastseven.model.ApiResponse;
import cn.eastseven.security.model.RoleEntity;
import cn.eastseven.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eastseven
 */
@RestController
@RequestMapping(value = "role", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RoleResourceImpl implements RoleResource {

    @Autowired
    private RoleService roleService;

    @GetMapping("list")
    @Override
    public ApiResponse list(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(name = "limit", defaultValue = "10") int size) {
        Page<RoleEntity> pageResult = roleService.pageBy(PageRequest.of(page - 1, size));
        return ApiResponse.of(pageResult.map(RoleSimpleDTO::new));
    }
}
