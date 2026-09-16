package com.placeti.avaliacao.service;

import com.placeti.avaliacao.dto.CidadeDTO;
import com.placeti.avaliacao.mapper.CidadeMapper;
import com.placeti.avaliacao.model.Cidade;
import com.placeti.avaliacao.repository.CidadeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

//---------------------------------------------------------------------------
/** Testes do ProjetoService.
 *
 *  O repositório e o mapper são mockados, então aqui a gente testa só a
 *  regra de negócio - sem subir o Spring e sem encostar no banco. */
//---------------------------------------------------------------------------
@ExtendWith(MockitoExtension.class)
class ProjetoServiceTest {

    @Mock
    private CidadeRepository cidadeRepository;

    @Mock
    private CidadeMapper cidadeMapper;

    @InjectMocks
    private ProjetoService projetoService;

    private Cidade cidade;
    private CidadeDTO cidadeDTO;

    //-------------------------------------------------------
    // Massa de teste usada na maioria dos cenários
    //-------------------------------------------------------
    @BeforeEach
    void setUp() {
        cidade = new Cidade();
        cidade.setId(1L);
        cidade.setNome("Florianópolis");
        cidade.setUf("SC");
        cidade.setCapital(true);

        cidadeDTO = new CidadeDTO(1L, "Florianópolis", "SC", true);
    }

    @Test
    @DisplayName("Busca uma cidade existente pelo id")
    void devePesquisarCidadePeloId() {
        when(cidadeRepository.findById(1L)).thenReturn(Optional.of(cidade));
        when(cidadeMapper.toDTO(cidade)).thenReturn(cidadeDTO);

        CidadeDTO resultado = projetoService.pesquisarCidade(1L);

        assertThat(resultado).isEqualTo(cidadeDTO);
        verify(cidadeRepository).findById(1L);
    }

    @Test
    @DisplayName("Buscar um id que não existe deve estourar exceção")
    void deveLancarExcecaoQuandoCidadeNaoExiste() {
        when(cidadeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projetoService.pesquisarCidade(99L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Lista todas as cidades cadastradas")
    void devePesquisarTodasAsCidades() {
        when(cidadeRepository.findAll()).thenReturn(List.of(cidade));
        when(cidadeMapper.toDTOList(List.of(cidade))).thenReturn(List.of(cidadeDTO));

        List<CidadeDTO> resultado = projetoService.pesquisarCidades();

        assertThat(resultado).hasSize(1).containsExactly(cidadeDTO);
    }

    @Test
    @DisplayName("Ao incluir, o id precisa ir nulo para não sobrescrever outro registro")
    void deveIncluirNovaCidade() {
        CidadeDTO novaCidadeDTO = new CidadeDTO(null, "Blumenau", "SC", false);

        Cidade novaCidade = new Cidade();
        novaCidade.setNome("Blumenau");
        novaCidade.setUf("SC");
        novaCidade.setCapital(false);

        when(cidadeMapper.toEntity(novaCidadeDTO)).thenReturn(novaCidade);

        projetoService.incluirCidade(novaCidadeDTO);

        verify(cidadeRepository).save(novaCidade);
        assertThat(novaCidade.getId()).isNull();
    }

    @Test
    @DisplayName("Altera uma cidade que existe")
    void deveAlterarCidadeExistente() {
        when(cidadeRepository.existsById(1L)).thenReturn(true);
        when(cidadeMapper.toEntity(cidadeDTO)).thenReturn(cidade);

        projetoService.alterarCidade(cidadeDTO);

        verify(cidadeRepository).save(cidade);
    }

    @Test
    @DisplayName("Alterar uma cidade inexistente não pode salvar nada")
    void deveLancarExcecaoAoAlterarCidadeInexistente() {
        CidadeDTO dto = new CidadeDTO(50L, "Inexistente", "XX", false);
        when(cidadeRepository.existsById(50L)).thenReturn(false);

        assertThatThrownBy(() -> projetoService.alterarCidade(dto))
                .isInstanceOf(EntityNotFoundException.class);

        verify(cidadeRepository, never()).save(any());
    }

    @Test
    @DisplayName("Exclui uma cidade que existe")
    void deveExcluirCidadeExistente() {
        when(cidadeRepository.existsById(1L)).thenReturn(true);

        projetoService.excluirCidade(1L);

        verify(cidadeRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Excluir uma cidade inexistente não pode chamar o delete")
    void deveLancarExcecaoAoExcluirCidadeInexistente() {
        when(cidadeRepository.existsById(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> projetoService.excluirCidade(99L))
                .isInstanceOf(EntityNotFoundException.class);

        verify(cidadeRepository, never()).deleteById(anyLong());
    }
}
