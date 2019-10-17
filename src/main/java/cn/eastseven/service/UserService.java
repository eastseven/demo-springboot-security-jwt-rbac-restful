package cn.eastseven.service;

import cn.eastseven.security.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * @author eastseven
 */
public interface UserService {

    /**
     * 登录
     *
     * @param username 账号
     * @param password 密码
     * @return 结果 token
     */
    String login(String username, String password);

    /**
     * 获取用户
     *
     * @param id 用户ID
     * @return 结果
     */
    UserEntity get(String id);

    /**
     * 分页
     *
     * @param pageable Pageable
     * @return 结果
     */
    Page<UserEntity> page(Pageable pageable);
}
