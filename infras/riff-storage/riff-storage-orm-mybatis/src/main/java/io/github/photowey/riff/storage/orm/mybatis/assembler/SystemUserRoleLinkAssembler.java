package io.github.photowey.riff.storage.orm.mybatis.assembler;

.yzclouduavplatformbusinessorganization.service.assembler;

import .infras.core.assembler.EntityAssembler;
import .yzclouduavplatformbusinessorganization.core.domain.dto.SystemUserRoleLinkDTO;
import .yzclouduavplatformbusinessorganization.core.domain.entity.SystemUserRoleLink;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

//@formatter:off

/**
 * {@code SystemUserRoleLinkAssembler}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SystemUserRoleLinkAssembler
    extends EntityAssembler<SystemUserRoleLinkDTO, SystemUserRoleLink> { }

//@formatter:on
