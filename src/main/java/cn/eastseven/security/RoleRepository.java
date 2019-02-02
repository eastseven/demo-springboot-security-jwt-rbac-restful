package cn.eastseven.security;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author d7
 */
public interface RoleRepository extends MongoRepository<RoleEntity, String> {
}
