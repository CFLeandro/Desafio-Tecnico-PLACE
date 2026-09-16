package com.placeti.avaliacao.mapper;

import com.placeti.avaliacao.dto.CidadeDTO;
import com.placeti.avaliacao.model.Cidade;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

//----------------------------------------------------------
/** Converte Cidade em CidadeDTO e vice-versa */
//----------------------------------------------------------
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CidadeMapper {

    CidadeDTO toDTO(Cidade cidade);

    Cidade toEntity(CidadeDTO dto);

    List<CidadeDTO> toDTOList(List<Cidade> cidades);
}
