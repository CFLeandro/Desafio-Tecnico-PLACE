package com.placeti.avaliacao.service;

import com.placeti.avaliacao.dto.ComercioDTO;
import com.placeti.avaliacao.mapper.ComercioMapper;
import com.placeti.avaliacao.model.Cidade;
import com.placeti.avaliacao.model.Comercio;
import com.placeti.avaliacao.repository.CidadeRepository;
import com.placeti.avaliacao.repository.ComercioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

//------------------------------------------------------------------
/** Service usado para manter os comércios das cidades */
//------------------------------------------------------------------
@Service
public class ComercioService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final ComercioRepository comercioRepository;
	private final CidadeRepository cidadeRepository;
	private final ComercioMapper comercioMapper;

	//--------------------------------------------------------------
	/** Construtor. Além do repositório de comércio, precisa do de
	 *  cidade para resolver o vínculo entre os dois */
	//--------------------------------------------------------------
	public ComercioService(ComercioRepository comercioRepository,
							CidadeRepository cidadeRepository,
							ComercioMapper comercioMapper) {
		this.comercioRepository = comercioRepository;
		this.cidadeRepository = cidadeRepository;
		this.comercioMapper = comercioMapper;
	}

	//---------------------------------------------------------
	/** Método que busca um comércio pelo seu ID */
	//---------------------------------------------------------
	public ComercioDTO pesquisarComercio(Long id) {
		logger.info("Pesquisando comércio com id {}", id);

		Comercio comercio = comercioRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Comércio não encontrado para o id: " + id));

		return comercioMapper.toDTO(comercio);
	}

	//---------------------------------------------------------
	/** Método que retorna todos os comércios cadastrados */
	//---------------------------------------------------------
	public List<ComercioDTO> pesquisarComercios() {
		logger.info("Pesquisando todos os comércios cadastrados");

		return comercioMapper.toDTOList(comercioRepository.findAll());
	}

	//---------------------------------------------------------------
	/** Método que retorna os comércios de uma cidade específica */
	//---------------------------------------------------------------
	public List<ComercioDTO> pesquisarComerciosPorCidade(Long idCidade) {
		logger.info("Pesquisando comércios da cidade {}", idCidade);

		return comercioMapper.toDTOList(comercioRepository.findByCidadeId(idCidade));
	}

	//----------------------------------------------------------
	/** Método chamado para incluir um novo comércio */
	//----------------------------------------------------------
	public void incluirComercio(ComercioDTO dto) {
		logger.info("Incluindo novo comércio: {}", dto.nomeComercio());

		Comercio comercio = comercioMapper.toEntity(dto);
		comercio.setId(null);
		comercio.setCidade(buscarCidade(dto.idCidade()));

		comercioRepository.save(comercio);
	}

	//----------------------------------------------------------
	/** Método chamado para alterar os dados de um comércio */
	//----------------------------------------------------------
	public void alterarComercio(ComercioDTO dto) {
		logger.info("Alterando comércio com id {}", dto.id());

		if (dto.id() == null || !comercioRepository.existsById(dto.id())) {
			throw new EntityNotFoundException("Comércio não encontrado para o id: " + dto.id());
		}

		Comercio comercio = comercioMapper.toEntity(dto);
		comercio.setCidade(buscarCidade(dto.idCidade()));

		comercioRepository.save(comercio);
	}

	//----------------------------------------------------------
	/** Método chamado para excluir um comércio */
	//----------------------------------------------------------
	public void excluirComercio(Long idComercio) {
		logger.info("Excluindo comércio com id {}", idComercio);

		if (!comercioRepository.existsById(idComercio)) {
			throw new EntityNotFoundException("Comércio não encontrado para o id: " + idComercio);
		}

		comercioRepository.deleteById(idComercio);
	}

	//----------------------------------------------------------
	/** Busca a cidade informada. Se não existir, melhor falhar aqui
	 *  do que gravar um comércio apontando para o nada */
	//----------------------------------------------------------
	private Cidade buscarCidade(Long idCidade) {
		return cidadeRepository.findById(idCidade)
				.orElseThrow(() -> new EntityNotFoundException("Cidade não encontrada para o id: " + idCidade));
	}
}
