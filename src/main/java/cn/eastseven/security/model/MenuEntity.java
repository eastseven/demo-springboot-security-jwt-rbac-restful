package cn.eastseven.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author eastseven
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("t_menu")
public class MenuEntity implements Serializable {

    public static final int LV_ROOT = 0;
    public static final int LV_LEAF = 1;

    @Id
    private String id;

    private String name;

    private String path;

    private String title;

    private String icon;

    private Set<String> roles;

    @DBRef
    private List<MenuEntity> children;

    /**
     * 菜单层级，目前只支持两级
     */
    private int level = LV_ROOT;

    /**
     * 排序用，流水号 索引
     */
    private int index = 1;

    private Boolean hidden;
}
