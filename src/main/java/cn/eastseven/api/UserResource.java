package cn.eastseven.api;

import cn.eastseven.api.dto.UsernamePasswordRequest;
import cn.eastseven.model.ApiResponse;
import cn.eastseven.security.JwtUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author d7
 */
@Api(value = "users", tags = "用户")
public interface UserResource {

    /**
     * 登录
     *
     * @param request 账号密码
     * @return 结果
     */
    @ApiOperation("登录")
    ApiResponse login(UsernamePasswordRequest request);

    /**
     * 登出
     *
     * @return 结果
     */
    @ApiOperation("登出")
    ApiResponse logout();

    /**
     * 个人信息
     *
     * @param user 登录用户
     * @return 结果
     */
    @ApiOperation("信息")
    ApiResponse info(@ApiIgnore JwtUser user);

    /**
     * 列表
     *
     * @param page Page
     * @param size Size
     * @return 结果
     */
    @ApiOperation("列表")
    Object list(int page, int size);

    /**
     * 详情
     *
     * @param id ID
     * @return 结果
     */
    @ApiOperation("详情")
    Object get(String id);

    /**
     * 添加
     *
     * @param request 请求
     * @return 结果
     */
    @ApiOperation("添加")
    Object add(Object request);

    /**
     * 修改
     *
     * @param request 请求
     * @return 结果
     */
    @ApiOperation("修改")
    Object modify(Object request);

    /**
     * 删除
     *
     * @param id ID
     * @return 结果
     */
    @ApiOperation("删除")
    Object remove(String id);
}

