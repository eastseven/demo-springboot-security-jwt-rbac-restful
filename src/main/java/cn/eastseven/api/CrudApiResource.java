package cn.eastseven.api;

import cn.eastseven.model.ApiResponse;

public interface CrudApiResource {

    ApiResponse list(int page, int size);
}
