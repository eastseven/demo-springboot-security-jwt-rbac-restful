package cn.eastseven;

import cn.eastseven.security.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author d7
 * 入口
 */
@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private ApplicationContext ctx;

    @Override
    public void run(String... args) throws Exception {

        // init permission
        ctx.getBean(PermissionRepository.class).deleteAll();
        List<HttpMethod> httpMethodList = Lists.newArrayList(HttpMethod.DELETE, HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT);
        for (HttpMethod httpMethod : httpMethodList) {
            String method = httpMethod.name().toLowerCase();
            String url = "/users";
            String name = "PRIVILEGE_" + url.replaceFirst("/", "") + "_" + method;
            PermissionEntity permission = new PermissionEntity();
            permission.setId(name.toUpperCase());
            permission.setName(name.toUpperCase());
            permission.setUrl(url);
            permission.setMethod(method);
            ctx.getBean(PermissionRepository.class).save(permission);
            log.debug(">>> {}", permission);
        }

        ctx.getBean(RoleRepository.class).deleteAll();
        long roleCount = ctx.getBean(RoleRepository.class).count();
        if (roleCount == 0L) {
            // user, admin
            List<RoleEntity> roles = Lists.newArrayList("ROLE_USER", "ROLE_ADMIN")
                    .stream()
                    .map(RoleEntity::new)
                    .collect(Collectors.toList());

            ctx.getBean(RoleRepository.class).saveAll(roles);
        }

        // init role permission
        ctx.getBean(RoleRepository.class).findAll().forEach(role -> {
            List<PermissionEntity> permissionList = ctx.getBean(PermissionRepository.class).findAll();
            if ("ROLE_ADMIN".equals(role.getId())) {
                role.setPermissions(permissionList);
            } else if ("ROLE_USER".equals(role.getId())) {
                List<PermissionEntity> list = permissionList.stream()
                        .filter(p -> "POST".equalsIgnoreCase(p.getMethod()))
                        .collect(Collectors.toList());
                log.debug(">>> ROLE_USER permissions={}", list);
                role.setPermissions(list);
            }
            ctx.getBean(RoleRepository.class).save(role);
        });

        ctx.getBean(UserRepository.class).deleteAll();
        long userCount = ctx.getBean(UserRepository.class).count();
        if (userCount == 0L) {
            roleCount = ctx.getBean(RoleRepository.class).count();
            if (roleCount > 0) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                ctx.getBean(RoleRepository.class).findAll().forEach(role -> {
                    String username = role.getName().toLowerCase().replaceFirst("role_", "");
                    UserEntity user = new UserEntity(username, passwordEncoder.encode("123456"));
                    user.getRoles().add(role);
                    ctx.getBean(UserRepository.class).save(user);
                    log.debug(">>> {}", user);
                });
            }
        }
    }
}

