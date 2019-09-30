package cn.eastseven.service;

import cn.eastseven.security.model.MenuEntity;

import java.util.List;

/**
 * @author eastseven
 */
public interface MenuService {

    /**
     * 初始化数据
     */
    void init();

    /**
     * 所以数据
     *
     * @return 结果
     */
    List<MenuEntity> all();

    /**
     * 用户拥有的菜单
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<MenuEntity> tree(String userId);
}
