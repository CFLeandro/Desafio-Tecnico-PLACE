package com.placeti.avaliacao.service;

import com.placeti.avaliacao.dto.ComercioDTO;
import com.placeti.avaliacao.mapper.ComercioMapper;
import com.placeti.avaliacao.model.Cidade;
import com.placeti.avaliacao.model.Comercio;
import com.placeti.avaliacao.model.TipoComercio;
import com.placeti.avaliacao.repository.CidadeRepository;
import com.placeti.avaliacao.repository.ComercioRepository;
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
import static org.mockito.Mockito.*;

//---------------------------------------------------------------------------
/** Testes do ComercioService.
 *
 *  Além do CRUD, o ponto principal aqui é garantir que o comércio sempre
 *  fique amarrado a uma cidade que realmente existe. */
//---------------------------------------------------------------------------
@ExtendWith(MockitoExtension.class)
class ComercioServiceTest {

    @Mock
    private ComercioRepository comercioRepository;

    @Mock
    private CidadeRepository cidadeRepository;

    @Mock
    private ComercioMapper comercioMapper;

    @InjectMocks
    private ComercioService comercioService;

    private Cidade cidade;
    private Comercio comercio;
    private ComercioDTO comercioDTO;

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

        comercio = new Comercio();
        comercio.setId(10L);
        comercio.setNomeComercio("Farmácia Popular");
        comercio.setNomeResponsavel("Ana Souza");
        comercio.setTipoComercio(TipoComercio.FARMACIA);
        comercio.setCidade(cidade);

        comercioDTO = new ComercioDTO(10L, "Farmácia Popular", "Ana Souza", TipoComercio.FARMACIA, 1L);
    }

    @Test
    @DisplayName("Busca um comércio existente pelo id")
    void devePesquisarComercioPeloId() {
        when(comercioRepository.findById(10L)).thenReturn(Optional.of(comercio));
        when(comercioMapper.toDTO(comercio)).thenReturn(comercioDTO);

        ComercioDTO resultado = comercioService.pesquisarComercio(10L);

        assertThat(resultado).isEqualTo(comercioDTO);
    }

    @Test
    @DisplayName("Buscar um id que não existe deve estourar exceção")
    void deveLancarExcecaoQuandoComercioNaoExiste() {
        when(comercioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> comercioService.pesquisarComercio(99L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Lista os comércios de uma cidade")
    void devePesquisarComerciosPorCidade() {
        when(comercioRepository.findByCidadeId(1L)).thenReturn(List.of(comercio));
        when(comercioMapper.toDTOList(List.of(comercio))).thenReturn(List.of(comercioDTO));

        List<ComercioDTO> resultado = comercioService.pesquisarComerciosPorCidade(1L);

        assertThat(resultado).containsExactly(comercioDTO);
    }

    @Test
    @DisplayName("Ao incluir, o comércio deve ficar amarrado à cidade informada")
    void deveIncluirComercioVinculandoACidade() {
        ComercioDTO novoDTO = new ComercioDTO(null, "Padaria Pão Quente", "João Lima", TipoComercio.PADARIA, 1L);

        Comercio novoComercio = new Comercio();
        novoComercio.setNomeComercio("Padaria Pão Quente");
        novoComercio.setNomeResponsavel("João Lima");
        novoComercio.setTipoComercio(TipoComercio.PADARIA);

        when(comercioMapper.toEntity(novoDTO)).thenReturn(novoComercio);
        when(cidadeRepository.findById(1L)).thenReturn(Optional.of(cidade));

        comercioService.incluirComercio(novoDTO);

        verify(comercioRepository).save(novoComercio);
        assertThat(novoComercio.getCidade()).isEqualTo(cidade);
    }

    @Test
    @DisplayName("Comércio apontando para uma cidade que não existe não pode ser gravado")
    void deveLancarExcecaoAoIncluirComercioComCidadeInexistente() {
        ComercioDTO novoDTO = new ComercioDTO(null, "Padaria Pão Quente", "João Lima", TipoComercio.PADARIA, 99L);
        Comercio novoComercio = new Comercio();

        when(comercioMapper.toEntity(novoDTO)).thenReturn(novoComercio);
        when(cidadeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> comercioService.incluirComercio(novoDTO))
                .isInstanceOf(EntityNotFoundException.class);

        verify(comercioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Exclui um comércio que existe")
    void deveExcluirComercioExistente() {
        when(comercioRepository.existsById(10L)).thenReturn(true);

        comercioService.excluirComercio(10L);

        verify(comercioRepository).deleteById(10L);
    }

    @Test
    @DisplayName("Excluir um comércio inexistente não pode chamar o delete")
    void deveLancarExcecaoAoExcluirComercioInexistente() {
        when(comercioRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> comercioService.excluirComercio(99L))
                .isInstanceOf(EntityNotFoundException.class);

        verify(comercioRepository, never()).deleteById(anyLong());
    }
}
