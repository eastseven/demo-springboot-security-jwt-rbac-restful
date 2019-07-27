package cn.eastseven.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author d7
 */
@Api(value = "users", tags = "用户")
public interface UserResource {

    /**
     * 列表
     *
     * @return 结果
     */
    @ApiOperation("列表")
    Object list();

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

