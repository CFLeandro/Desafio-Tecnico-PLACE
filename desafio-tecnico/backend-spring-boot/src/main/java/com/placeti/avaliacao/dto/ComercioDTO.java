package com.placeti.avaliacao.dto;

import com.placeti.avaliacao.model.TipoComercio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO que guarda os dados de um comércio
 */
public record ComercioDTO(

        //---------------------------------------
        // Atributos do DTO
        //---------------------------------------

        Long id,

        @NotBlank(message = "O nome do comércio é obrigatório")
        @Size(max = 100, message = "O nome do comércio deve ter no máximo 100 caracteres")
        String nomeComercio,

        @NotBlank(message = "O nome do responsável é obrigatório")
        @Size(max = 100, message = "O nome do responsável deve ter no máximo 100 caracteres")
        String nomeResponsavel,

        @NotNull(message = "O tipo de comércio é obrigatório")
        TipoComercio tipoComercio,

        // Só o id da cidade, para não trazer a entidade inteira aninhada na resposta
        @NotNull(message = "É obrigatório informar a cidade do comércio")
        Long idCidade

) {

}
