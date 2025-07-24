package io.github.photowey.riff.middleware.database.orm.mybatis.repository;

.yzclouduavplatformbusinessorganization.repository.database;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import .yzclouduavplatformbusinessorganization.core.domain.entity.SystemRole;
import org.apache.ibatis.annotations.Param;

/**
 * {@code SystemRoleRepository}
 *  仓储类
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
public interface SystemRoleRepository extends BaseMapper<SystemRole> {

    /**
     * 根据
     * 主键标识-物理删除
     *
     * @param id 主键标识
     */
    void physicalDelete(@Param("id") Long id);

    /**
     * 根据
     * 主键标识-逻辑删除
     *
     * @param id 主键标识
     */
    // void logicalDelete(@Param("id") Long id);
}
