package cn.eastseven.api.dto;

import cn.eastseven.security.model.MenuEntity;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.eastseven.security.model.MenuEntity.LV_ROOT;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

/**
 * @author d7
 */
@Data
public class MenuDTO implements Serializable {

    public MenuDTO(MenuEntity menu) {
        this.roles = Sets.newHashSet();
        this.children = Lists.newArrayList();

        BeanUtils.copyProperties(menu, this, "children");

        if (menu.getLevel() == LV_ROOT && isNotEmpty(menu.getChildren())) {
            this.children = menu.getChildren().stream().map(MenuDTO::new).collect(Collectors.toList());
        }
    }

    private String id;

    private String name;

    private String enTitle;

    private String path;

    private String title;

    private String icon;

    private Set<String> roles;

    private List<MenuDTO> children;

    /**
     * 菜单层级，目前只支持两级
     */
    private Integer level;

    /**
     * 排序用，流水号 索引
     */
    private Integer index;

    private Boolean hidden;
}
