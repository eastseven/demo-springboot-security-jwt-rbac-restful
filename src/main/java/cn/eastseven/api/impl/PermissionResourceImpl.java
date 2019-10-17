package cn.eastseven.api.impl;

import cn.eastseven.api.PermissionResource;
import cn.eastseven.api.dto.PermissionSimpleDTO;
import cn.eastseven.model.ApiResponse;
import cn.eastseven.security.model.PermissionEntity;
import cn.eastseven.security.repository.PermissionRepository;
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
@RequestMapping(value = "permission", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PermissionResourceImpl implements PermissionResource {

    @Autowired
    private PermissionRepository permissionRepository;

    @GetMapping("list")
    @Override
    public ApiResponse list(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(name = "limit", defaultValue = "10") int size) {
        Page<PermissionEntity> pageResult = permissionRepository.findAll(PageRequest.of(page - 1, size));
        return ApiResponse.of(pageResult.map(PermissionSimpleDTO::new));
    }
}
