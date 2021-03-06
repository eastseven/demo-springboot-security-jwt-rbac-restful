package cn.eastseven.security.repository;

import cn.eastseven.security.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by stephan on 20.03.16.
 */
public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserEntity findByUsername(String username);
}
