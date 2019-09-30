package cn.eastseven.security.repository;

import cn.eastseven.security.model.MenuEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author eastseven
 */
public interface MenuRepository extends MongoRepository<MenuEntity, String> {

    /**
     * 按级别查询
     *
     * @param level 等级
     * @return 结果
     */
    List<MenuEntity> findByLevelIs(int level);
}
