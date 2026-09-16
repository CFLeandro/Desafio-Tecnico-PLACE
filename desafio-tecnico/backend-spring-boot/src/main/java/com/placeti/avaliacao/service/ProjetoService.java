package com.placeti.avaliacao.service;

import com.placeti.avaliacao.dto.CidadeDTO;
import com.placeti.avaliacao.mapper.CidadeMapper;
import com.placeti.avaliacao.model.Cidade;
import com.placeti.avaliacao.repository.CidadeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

//------------------------------------------------------------------
/** Service usado para acessar os repositórios da aplicação */
//------------------------------------------------------------------
@Service
public class ProjetoService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final CidadeRepository cidadeRepository;
	private final CidadeMapper cidadeMapper;

	//--------------------------------------------------------------
	/** Construtor. Recebe o repositório e o mapper de cidade */
	//--------------------------------------------------------------
	public ProjetoService(CidadeRepository cidadeRepository, CidadeMapper cidadeMapper) {
		this.cidadeRepository = cidadeRepository;
		this.cidadeMapper = cidadeMapper;
	}

	//---------------------------------------------------------
	/** Método que busca uma cidade pelo seu ID */
	//---------------------------------------------------------
	public CidadeDTO pesquisarCidade(Long id) {
		logger.info("Pesquisando cidade com id {}", id);

		Cidade cidade = cidadeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Cidade não encontrada para o id: " + id));

		return cidadeMapper.toDTO(cidade);
	}

	//---------------------------------------------------------
	/** Método que retorna todas as cidades cadastradas */
	//---------------------------------------------------------
	public List<CidadeDTO> pesquisarCidades() {
		logger.info("Pesquisando todas as cidades cadastradas");

		return cidadeMapper.toDTOList(cidadeRepository.findAll());
	}

	//----------------------------------------------------------
	/** Método chamado para incluir uma nova cidade */
	//----------------------------------------------------------	
	public void incluirCidade(CidadeDTO dto) {
		logger.info("Incluindo nova cidade: {}", dto.nome());

		Cidade cidade = cidadeMapper.toEntity(dto);

		// Zera o id para não correr o risco de sobrescrever uma cidade existente
		// caso venha um id no corpo da requisição
		cidade.setId(null);

		cidadeRepository.save(cidade);
	}

	//----------------------------------------------------------
	/** Método chamado para alterar os dados de uma cidade */
	//----------------------------------------------------------
	public void alterarCidade(CidadeDTO dto) {
		logger.info("Alterando cidade com id {}", dto.id());

		// Confere antes de salvar, senão o save acabaria criando um registro novo
		if (dto.id() == null || !cidadeRepository.existsById(dto.id())) {
			throw new EntityNotFoundException("Cidade não encontrada para o id: " + dto.id());
		}

		cidadeRepository.save(cidadeMapper.toEntity(dto));
	}

	//----------------------------------------------------------
	/** Método chamado para excluir uma cidade */
	//----------------------------------------------------------	
	public void excluirCidade(Long idCidade) {
		logger.info("Excluindo cidade com id {}", idCidade);

		if (!cidadeRepository.existsById(idCidade)) {
			throw new EntityNotFoundException("Cidade não encontrada para o id: " + idCidade);
		}

		cidadeRepository.deleteById(idCidade);
	}
}
