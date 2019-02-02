package cn.eastseven;

import cn.eastseven.security.RoleEntity;
import cn.eastseven.security.RoleRepository;
import cn.eastseven.security.UserEntity;
import cn.eastseven.security.UserRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
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

