package com.placeti.avaliacao.service;

import com.placeti.avaliacao.dto.CidadeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

//------------------------------------------------------------------
/** Service usado para acessar os repositórios da aplicação */
//------------------------------------------------------------------
public class ProjetoService {

	private final Logger logger = LoggerFactory.getLogger(getClass());


	//---------------------------------------------------------
	/** Método que busca uma cidade pelo seu ID */
	//---------------------------------------------------------
	public CidadeDTO pesquisarCidade(Long id) {
	}

	//---------------------------------------------------------
	/** Método que retorna todas as cidades cadastradas */
	//---------------------------------------------------------
	public List<CidadeDTO> pesquisarCidades() {

	}

	//----------------------------------------------------------
	/** Método chamado para incluir uma nova cidade */
	//----------------------------------------------------------	
	public void incluirCidade(CidadeDTO dto) {
		
	}

	//----------------------------------------------------------
	/** Método chamado para alterar os dados de uma cidade */
	//----------------------------------------------------------
	public void alterarCidade(CidadeDTO dto) {

	}

	//----------------------------------------------------------
	/** Método chamado para excluir uma cidade */
	//----------------------------------------------------------	
	public void excluirCidade(Long idCidade) {

	}
}
