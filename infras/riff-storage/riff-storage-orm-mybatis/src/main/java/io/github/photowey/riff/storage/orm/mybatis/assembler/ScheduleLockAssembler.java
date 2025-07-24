package io.github.photowey.riff.storage.orm.mybatis.assembler;

.yzclouduavplatformbusinessorganization.service.assembler;

import .infras.core.assembler.EntityAssembler;
import .yzclouduavplatformbusinessorganization.core.domain.dto.ScheduleLockDTO;
import .yzclouduavplatformbusinessorganization.core.domain.entity.ScheduleLock;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

//@formatter:off

/**
 * {@code ScheduleLockAssembler}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ScheduleLockAssembler
    extends EntityAssembler<ScheduleLockDTO, ScheduleLock> { }

//@formatter:on
