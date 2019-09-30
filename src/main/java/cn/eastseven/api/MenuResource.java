package cn.eastseven.api;

import cn.eastseven.model.ApiResponse;
import cn.eastseven.security.JwtUser;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author eastseven
 */
public interface MenuResource {

    /**
     * 所以菜单
     *
     * @return 结果
     */
    ApiResponse all();

    /**
     * 当前用户的菜单树
     *
     * @param user 当前登录用户
     * @return 结果
     */
    ApiResponse menuTree(@ApiIgnore JwtUser user);
}
