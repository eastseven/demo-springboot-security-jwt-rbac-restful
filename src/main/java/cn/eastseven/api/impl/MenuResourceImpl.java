package cn.eastseven.api.impl;

import cn.eastseven.api.MenuResource;
import cn.eastseven.api.dto.MenuDTO;
import cn.eastseven.api.dto.MenuSimpleDTO;
import cn.eastseven.model.ApiResponse;
import cn.eastseven.security.JwtUser;
import cn.eastseven.security.annotation.CurrentUser;
import cn.eastseven.security.model.MenuEntity;
import cn.eastseven.security.repository.MenuRepository;
import cn.eastseven.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author eastseven
 */
@Slf4j
@RestController
@RequestMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MenuResourceImpl implements MenuResource {

    @Autowired
    private MenuRepository menuRepository;

    private final MenuService menuService;

    public MenuResourceImpl(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("list")
    @Override
    public ApiResponse list(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(name = "limit", defaultValue = "10") int size) {
        Page<MenuEntity> pageResult = menuRepository.findAll(PageRequest.of(page - 1, size));
        return ApiResponse.of(pageResult.map(MenuSimpleDTO::new));
    }

    @GetMapping("/all")
    @Override
    public ApiResponse all() {
        return ApiResponse.of(menuService.all().stream().map(MenuDTO::new).collect(toList()));
    }

    @GetMapping("/tree")
    @Override
    public ApiResponse menuTree(@CurrentUser JwtUser user) {
        log.debug(">>> 当前登录用户=[username={}, id={}]", user.getUsername(), user.getId());
        // TODO 先获取用户角色一级菜单数据
        List<MenuEntity> menuList = menuService.tree(user.getId());
        return ApiResponse.of(menuList.stream().map(MenuDTO::new).collect(toList()));
    }
}