package cn.eastseven;

import cn.eastseven.config.WebAppConfig;
import cn.eastseven.security.model.MenuEntity;
import cn.eastseven.security.model.PermissionEntity;
import cn.eastseven.security.model.RoleEntity;
import cn.eastseven.security.model.UserEntity;
import cn.eastseven.security.repository.MenuRepository;
import cn.eastseven.security.repository.PermissionRepository;
import cn.eastseven.security.repository.RoleRepository;
import cn.eastseven.security.repository.UserRepository;
import cn.eastseven.service.MenuService;
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
        MenuRepository menuRepository = ctx.getBean(MenuRepository.class);
        PermissionRepository permissionRepository = ctx.getBean(PermissionRepository.class);
        RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
        UserRepository userRepository = ctx.getBean(UserRepository.class);
        // init menu
        ctx.getBean(MenuService.class).init();
        // init permission
        permissionRepository.deleteAll();

        Map<RequestMappingInfo, HandlerMethod> map = ctx.getBean(WebAppConfig.class)
                .getRequestMappingHandlerMapping().getHandlerMethods();
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

                            permissionRepository.save(permission);
                        }
                    default:
                }
            }
        }

        roleRepository.deleteAll();
        long roleCount = roleRepository.count();
        if (roleCount == 0L) {
            // user, admin
            List<RoleEntity> roles = Lists.newArrayList("ROLE_USER", "ROLE_ADMIN")
                    .stream()
                    .map(RoleEntity::new)
                    .collect(Collectors.toList());

            roleRepository.saveAll(roles);
        }

        // init role permission
        roleRepository.findAll().forEach(role -> {
            List<PermissionEntity> permissionList = permissionRepository.findAll();
            if ("ROLE_ADMIN".equals(role.getId())) {
                // 管理员拥有所有权限及菜单
                role.setPermissions(permissionList);
                role.setMenus(menuRepository.findAll());
            } else if ("ROLE_USER".equals(role.getId())) {
                List<PermissionEntity> list = permissionList.stream()
                        .filter(p -> "POST".equalsIgnoreCase(p.getMethod()))
                        .collect(Collectors.toList());
                role.setPermissions(list);
            }
            roleRepository.save(role);
        });

        userRepository.deleteAll();
        long userCount = userRepository.count();
        if (userCount == 0L) {
            roleCount = roleRepository.count();
            Faker faker = new Faker();
            if (roleCount > 0) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                roleRepository.findAll().forEach(role -> {
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

        log.info(">>> 初始化 Menu 数据[{}]条", menuRepository.count());
        log.info(">>> 初始化 Permission 数据[{}]条", permissionRepository.count());
        log.info(">>> 初始化 Role 数据[{}]条", roleRepository.count());
        log.info(">>> 初始化 User 数据[{}]条", userRepository.count());
    }
}

