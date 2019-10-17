package cn.eastseven.api.dto;

import cn.eastseven.security.model.MenuEntity;
import com.google.common.collect.Sets;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Set;

/**
 * @author d7
 */
@Data
public class MenuSimpleDTO implements Serializable {

    public MenuSimpleDTO(MenuEntity menu) {
        this.roles = Sets.newHashSet();
        BeanUtils.copyProperties(menu, this, "children");
    }

    private String id;

    private String name;

    private String path;

    private String title;

    private String icon;

    private Set<String> roles;


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
