package cn.eastseven.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author d7
 */
@Api(value = "users", tags = "用户", description = "接口")
@RestController
@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserResource {

    @ApiOperation("列表")
    @GetMapping
    Object list() {
        return null;
    }

    @ApiOperation("详情")
    @GetMapping("/{id}")
    Object get(@PathVariable String id) {
        return null;
    }

    @ApiOperation("添加")
    @PostMapping
    Object add(@RequestBody Object request) {
        return null;
    }

    @ApiOperation("修改")
    @PutMapping
    Object modify(@RequestBody Object request) {
        return null;
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    Object remove(@PathVariable String id) {
        return null;
    }
}

