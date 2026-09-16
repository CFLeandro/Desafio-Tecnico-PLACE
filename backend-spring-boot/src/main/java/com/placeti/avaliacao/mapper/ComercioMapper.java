package com.placeti.avaliacao.mapper;

import com.placeti.avaliacao.dto.ComercioDTO;
import com.placeti.avaliacao.model.Comercio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

//----------------------------------------------------------
/** Converte Comercio em ComercioDTO e vice-versa */
//----------------------------------------------------------
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ComercioMapper {

    @Mapping(source = "cidade.id", target = "idCidade")
    ComercioDTO toDTO(Comercio comercio);

    // O mapper não enxerga o repositório, então quem busca a cidade pelo id é o service
    @Mapping(target = "cidade", ignore = true)
    Comercio toEntity(ComercioDTO dto);

    List<ComercioDTO> toDTOList(List<Comercio> comercios);
}
