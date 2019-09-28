package cn.eastseven;

import cn.eastseven.config.WebAppConfig;
import cn.eastseven.security.*;
import com.github.javafaker.Faker;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RegExUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
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
        initData();
    }

    private void initData() {
        // init permission
        ctx.getBean(PermissionRepository.class).deleteAll();

        Map<RequestMappingInfo, HandlerMethod> map = ctx.getBean(WebAppConfig.class).getRequestMappingHandlerMapping().getHandlerMethods();
        map.forEach((k, v) -> {
            log.debug(">>> url, Patterns={}", k.getPatternsCondition().getPatterns());
            log.debug(">>> url, Methods={}\n", k.getMethodsCondition().getMethods());
        });

        for (RequestMappingInfo requestMappingInfo : map.keySet()) {
            Set<RequestMethod> requestMethods = requestMappingInfo.getMethodsCondition().getMethods();
            if (requestMethods.isEmpty()) {
                continue;
            }

            Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
            if (patterns.isEmpty()) {
                continue;
            }

            for (RequestMethod requestMethod : requestMethods) {
                switch (requestMethod) {
                    case GET:
                    case PUT:
                    case POST:
                    case DELETE:
                    case OPTIONS:
                        for (String pattern : patterns) {
                            String url = RegExUtils.replaceAll(pattern, "\\{.+}", "*").toLowerCase();
                            String id = "privilege_" + pattern + "_" + requestMethod.name().toLowerCase();
                            String method = requestMethod.name().toLowerCase();
                            PermissionEntity permission = new PermissionEntity();
                            permission.setId(id);
                            permission.setUrl(url);
                            permission.setName(id);
                            permission.setMethod(method);

                            ctx.getBean(PermissionRepository.class).save(permission);
                            log.debug(">>> {}", permission);
                        }
                    default:
                }
            }
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
                role.setPermissions(list);
            }
            ctx.getBean(RoleRepository.class).save(role);
        });

        ctx.getBean(UserRepository.class).deleteAll();
        long userCount = ctx.getBean(UserRepository.class).count();
        if (userCount == 0L) {
            roleCount = ctx.getBean(RoleRepository.class).count();
            Faker faker = new Faker();
            if (roleCount > 0) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                ctx.getBean(RoleRepository.class).findAll().forEach(role -> {
                    String username = role.getName().toLowerCase().replaceFirst("role_", "");
                    UserEntity user = new UserEntity(username, passwordEncoder.encode("123456"));
                    user.setFirstname(faker.name().firstName());
                    user.setLastname(faker.name().lastName());
                    user.getRoles().add(role);
                    ctx.getBean(UserRepository.class).save(user);
                    log.info("系统用户：username=[{}], password=[123456]", user.getUsername());
                });
            }
        }
    }
}

