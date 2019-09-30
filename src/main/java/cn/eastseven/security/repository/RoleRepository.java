package cn.eastseven.security.repository;

import cn.eastseven.security.model.RoleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author d7
 */
public interface RoleRepository extends MongoRepository<RoleEntity, String> {
}
