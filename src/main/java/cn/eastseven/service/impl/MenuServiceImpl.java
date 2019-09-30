package cn.eastseven.service.impl;

import cn.eastseven.security.model.MenuEntity;
import cn.eastseven.security.model.RoleEntity;
import cn.eastseven.security.model.UserEntity;
import cn.eastseven.security.repository.MenuRepository;
import cn.eastseven.security.repository.UserRepository;
import cn.eastseven.service.MenuService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static cn.eastseven.security.model.MenuEntity.LV_LEAF;
import static cn.eastseven.security.model.MenuEntity.LV_ROOT;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.toList;

/**
 * @author eastseven
 */
@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private UserRepository userRepository;

    private final ResourceLoader resourceLoader;

    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository,
                           ResourceLoader resourceLoader) {
        this.menuRepository = menuRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void init() {
        log.info(">>> 初始化权限菜单数据 start");

        menuRepository.deleteAll();

        try {
            Resource resource = resourceLoader.getResource("classpath:menu.json");
            log.debug(">>>{}", resource);
            log.debug(">>>{}", resource.getURI());
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            StringBuilder sb = new StringBuilder();
            reader.lines().forEach(sb::append);
            String jsonString = new String(sb.toString().getBytes(), UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonString);

            if (root.isArray()) {
                menuRepository.deleteAll();
                final String fChildren = "children";
                final String fTitle = "title";
                final String fTitleEn = "title2";
                final String fPath = "path";
                final String fIcon = "icon";
                final String fName = "name";
                final String fRoles = "roles";
                final String fHidden = "hidden";
                root.iterator().forEachRemaining(menu1 -> {
                    // 一级目录
                    String path1 = menu1.get(fPath).textValue();
                    String icon1 = menu1.get(fIcon).textValue();
                    String name1 = menu1.get(fName).textValue();
                    String title1 = menu1.get(fTitle).textValue();
                    String title1En = menu1.get(fTitleEn).textValue();
                    Boolean hidden1 = menu1.has(fHidden) ? menu1.get(fHidden).asBoolean() : Boolean.FALSE;
                    Set<String> roles1 = Sets.newHashSet();
                    if (menu1.get(fRoles).isArray()) {
                        menu1.get(fRoles).elements().forEachRemaining(action -> roles1.add(action.textValue()));
                    }

                    List<MenuEntity> list = Lists.newArrayList();

                    if (menu1.has(fChildren)) {
                        JsonNode children = menu1.get(fChildren);
                        children.iterator().forEachRemaining(menu2 -> {
                            // 二级目录
                            String path2 = menu2.get(fPath).textValue();
                            String icon2 = menu2.get(fIcon).textValue();
                            String name2 = menu2.get(fName).textValue();
                            String title2 = menu2.get(fTitle).textValue();
                            String title2En = menu2.get(fTitleEn).textValue();
                            Boolean hidden2 = menu2.has(fHidden) ? menu2.get(fHidden).asBoolean() : Boolean.FALSE;
                            Set<String> roles2 = Sets.newHashSet();
                            if (menu2.get(fRoles).isArray()) {
                                menu2.get(fRoles).elements().forEachRemaining(action -> roles2.add(action.textValue()));
                            }
                            // MenuEntity menuEntity2 = new MenuEntity(name2, title2, title2En, path2, icon2, LV_LEAF, hidden2, roles2);
                            MenuEntity menuEntity2 = MenuEntity.builder()
                                    .name(name2).title(title2).path(path2).icon(icon2).level(LV_LEAF).hidden(hidden2)
                                    .roles(roles2)
                                    .build();
                            list.add(menuEntity2);
                        });

                        menuRepository.saveAll(list);
                    }

                    // MenuEntity menuEntity = new MenuEntity(name1, title1, title1En, path1, icon1, LV_ROOT, hidden1, roles1);
                    MenuEntity menuEntity = MenuEntity.builder()
                            .name(name1).title(title1).path(path1).icon(icon1).level(LV_ROOT).hidden(hidden1)
                            .roles(roles1)
                            .build();
                    if (!list.isEmpty()) {
                        menuEntity.setChildren(list);
                    }

                    menuRepository.save(menuEntity);
                });
            }
        } catch (IOException e) {
            log.error("", e);
        }

        log.info(">>> 初始化权限菜单数据 done");
    }

    @Override
    public List<MenuEntity> all() {
        return menuRepository.findByLevelIs(LV_ROOT);
    }

    @Override
    public List<MenuEntity> tree(String userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("用户不存在"));
        return user.getRoles().stream()
                .map(RoleEntity::getMenus)
                .flatMap(Collection::stream)
                .filter(menu -> menu.getLevel() == LV_ROOT)
                .collect(toList());
    }
}
