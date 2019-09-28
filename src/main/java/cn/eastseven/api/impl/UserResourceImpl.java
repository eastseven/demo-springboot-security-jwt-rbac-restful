package cn.eastseven.api.impl;

import cn.eastseven.api.UserResource;
import cn.eastseven.api.dto.TokenResponse;
import cn.eastseven.api.dto.UserInfoResponse;
import cn.eastseven.api.dto.UsernamePasswordRequest;
import cn.eastseven.model.ApiResponse;
import cn.eastseven.security.JwtUser;
import cn.eastseven.security.RoleEntity;
import cn.eastseven.security.UserEntity;
import cn.eastseven.security.annotation.CurrentUser;
import cn.eastseven.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * @author eastseven
 */
@RestController
@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserResourceImpl implements UserResource {

    private final UserService userService;

    public UserResourceImpl(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @Override
    public ApiResponse login(@RequestBody UsernamePasswordRequest request) {
        final String token = userService.login(request.getUsername(), request.getPassword());
        return ApiResponse.of(TokenResponse.builder().token(token).build());
    }

    @PostMapping("/logout")
    @Override
    public ApiResponse logout() {
        SecurityContextHolder.clearContext();
        return ApiResponse.of();
    }

    @GetMapping("/info")
    @Override
    public ApiResponse info(@CurrentUser JwtUser user) {
        // https://image.eastseven.cn/heads/01.jpg
        UserEntity currentUser = userService.get(user.getId());
        return ApiResponse.of(UserInfoResponse.builder()
                .name(currentUser.getFirstname().concat(" ").concat(currentUser.getLastname()))
                .introduction("Hello World")
                .avatar("http://image.eastseven.cn/head.jpg?x-oss-process=style/avatar")
                .roles(currentUser.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet()))
                .build());
    }

    @GetMapping
    @Override
    public Object list() {
        return null;
    }

    @GetMapping("/{id}")
    @Override
    public Object get(@PathVariable String id) {
        return null;
    }

    @PostMapping
    @Override
    public Object add(@RequestBody Object request) {
        return null;
    }

    @PutMapping
    @Override
    public Object modify(@RequestBody Object request) {
        return null;
    }

    @DeleteMapping("/{id}")
    @Override
    public Object remove(@PathVariable String id) {
        return null;
    }
}
