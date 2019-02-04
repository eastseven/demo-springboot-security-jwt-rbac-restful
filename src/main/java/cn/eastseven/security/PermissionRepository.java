package cn.eastseven.security;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author eastseven
 */
public interface PermissionRepository extends MongoRepository<PermissionEntity, String> {
}
