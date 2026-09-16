package com.placeti.avaliacao.controller;

import com.placeti.avaliacao.dto.ComercioDTO;
import com.placeti.avaliacao.service.ComercioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//--------------------------------------------------
/** Endpoint para consultar e manter comércios */
//--------------------------------------------------
@RestController
@RequestMapping("/comercios")
public class ComercioController {

	private final ComercioService comercioService;

	//--------------------------------------------------------------
	/** Construtor. Recebe o service que concentra as regras */
	//--------------------------------------------------------------
	public ComercioController(ComercioService comercioService) {
		this.comercioService = comercioService;
	}

	//----------------------------------------------------------
	/** Endpoint que retorna um comércio conforme seu ID */
	//----------------------------------------------------------
	@GetMapping("/{id}")
	public ResponseEntity<ComercioDTO> buscarPeloId(@PathVariable Long id) {
		// Responde GET em http://localhost:8080/placeti/comercios/1
		return ResponseEntity.ok(comercioService.pesquisarComercio(id));
	}

	//----------------------------------------------------------
	/** Endpoint que retorna todos os comércios cadastrados */
	//----------------------------------------------------------
	@GetMapping
	public List<ComercioDTO> pesquisarComercios() {
		// Responde GET em http://localhost:8080/placeti/comercios
		return comercioService.pesquisarComercios();
	}

	//------------------------------------------------------------------------
	/** Endpoint que retorna os comércios de uma cidade */
	//------------------------------------------------------------------------
	@GetMapping("/cidade/{idCidade}")
	public List<ComercioDTO> pesquisarComerciosPorCidade(@PathVariable Long idCidade) {
		// Responde GET em http://localhost:8080/placeti/comercios/cidade/1
		return comercioService.pesquisarComerciosPorCidade(idCidade);
	}

	//----------------------------------------------------------
	/** Endpoint para incluir novo comércio */
	//----------------------------------------------------------
	@PostMapping
	public ResponseEntity<Void> incluirComercio(@Valid @RequestBody ComercioDTO comercioDto) {
		//	Responde POST em http://localhost:8080/placeti/comercios
		//	Envia JSON no body:
		//	{
		//	 	"nomeComercio": "Padaria Central",
		//	  	"nomeResponsavel": "Maria Silva",
		//	   	"tipoComercio": "PADARIA",
		//	   	"idCidade": 1
		//	}
		comercioService.incluirComercio(comercioDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	//----------------------------------------------------------
	/** Endpoint para alterar comércio */
	//----------------------------------------------------------
	@PutMapping
	public ResponseEntity<Void> alterarComercio(@Valid @RequestBody ComercioDTO comercioDto) {
		// Responde PUT em http://localhost:8080/placeti/comercios, com o id no corpo
		comercioService.alterarComercio(comercioDto);
		return ResponseEntity.ok().build();
	}

	//----------------------------------------------------------
	/** Endpoint para excluir um comércio */
	//----------------------------------------------------------
	@DeleteMapping("/{idComercio}")
	public ResponseEntity<Void> excluirComercio(@PathVariable Long idComercio) {
		// Responde DELETE em http://localhost:8080/placeti/comercios/{idComercio}
		comercioService.excluirComercio(idComercio);
		return ResponseEntity.noContent().build();
	}
}
