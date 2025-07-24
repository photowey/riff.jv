package io.github.photowey.riff.storage.orm.mybatis.assembler;

.yzclouduavplatformbusinessorganization.service.assembler;

import .infras.core.assembler.EntityAssembler;
import .yzclouduavplatformbusinessorganization.core.domain.dto.AuthenticationTokenDTO;
import .yzclouduavplatformbusinessorganization.core.domain.entity.AuthenticationToken;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

//@formatter:off

/**
 * {@code AuthenticationTokenAssembler}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/21
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthenticationTokenAssembler
    extends EntityAssembler<AuthenticationTokenDTO, AuthenticationToken> { }

//@formatter:on
