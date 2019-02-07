package cn.eastseven.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author d7
 */
@Api(value = "users", tags = "用户", description = "接口")
public interface UserResource {

    @ApiOperation("列表")
    Object list();

    @ApiOperation("详情")
    Object get(String id);

    @ApiOperation("添加")
    Object add(Object request);

    @ApiOperation("修改")
    Object modify(Object request);

    @ApiOperation("删除")
    Object remove(String id);
}

