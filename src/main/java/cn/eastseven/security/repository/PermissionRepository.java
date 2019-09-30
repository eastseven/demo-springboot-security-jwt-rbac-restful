package cn.eastseven.security.repository;

import cn.eastseven.security.model.PermissionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author eastseven
 */
public interface PermissionRepository extends MongoRepository<PermissionEntity, String> {
}
