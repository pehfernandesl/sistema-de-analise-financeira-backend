package jk.bsi.tcc.safi.service.mapper;

import java.util.Objects;
import jk.bsi.tcc.safi.domain.Usuario;
import jk.bsi.tcc.safi.service.dto.UsuarioDto;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Usuario} and its DTO {@link UsuarioDto}.
 */
@Mapper(componentModel = "spring")
public interface UsuarioMapper extends EntityMapper<UsuarioDto, Usuario> {
  /**
   * Instanciate entity from id.
   *
   * @param id desired id.
   * @return {@link Usuario} instance with desired id.
   */
  default Usuario fromId(Long id) {
    if (Objects.isNull(id)) {
      return null;
    }

    Usuario usuario = new Usuario();
    usuario.setId(id);
    return usuario;
  }
}
