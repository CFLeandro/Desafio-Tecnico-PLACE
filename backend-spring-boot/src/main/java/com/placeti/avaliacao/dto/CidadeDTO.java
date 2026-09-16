package com.placeti.avaliacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO que guarda os dados de uma cidade
 */
public record CidadeDTO(

        //---------------------------------------
        // Atributos do DTO
        //---------------------------------------

        // No cadastro o id vem nulo, na alteração vem preenchido
        Long id,

        @NotBlank(message = "O nome da cidade é obrigatório")
        @Size(max = 100, message = "O nome da cidade deve ter no máximo 100 caracteres")
        String nome,

        @NotBlank(message = "A UF é obrigatória")
        @Size(min = 2, max = 2, message = "A UF deve ter exatamente 2 caracteres")
        String uf,

        @NotNull(message = "É obrigatório informar se a cidade é capital")
        Boolean capital

) {

}
