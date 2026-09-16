package com.placeti.avaliacao.controller;

import com.placeti.avaliacao.dto.CidadeDTO;
import com.placeti.avaliacao.service.ProjetoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//--------------------------------------------------
/** Endpoint para consultar e manter cidades */
//--------------------------------------------------
@RestController
@RequestMapping("/cidades")
public class CidadeController {

	private final ProjetoService projetoService;

	//--------------------------------------------------------------
	/** Construtor. Recebe o service que concentra as regras */
	//--------------------------------------------------------------
	public CidadeController(ProjetoService projetoService) {
		this.projetoService = projetoService;
	}

	//----------------------------------------------------------
	/** Endpoint que retorna uma cidade conforme seu ID */
	//----------------------------------------------------------
	@GetMapping("/{id}")
	public ResponseEntity<CidadeDTO> buscarPeloId(@PathVariable Long id) {
		// Responde GET em http://localhost:8080/placeti/cidades/1
		return ResponseEntity.ok(projetoService.pesquisarCidade(id));
	}

	//----------------------------------------------------------
	/** Endpoint que retorna todas as cidades cadastradas */
	//----------------------------------------------------------
	@GetMapping
	public List<CidadeDTO> pesquisarCidades() {
		// Responde GET em http://localhost:8080/placeti/cidades
		return projetoService.pesquisarCidades();
	}

	//----------------------------------------------------------
	/** Endpoint para incluir nova cidade */
	//----------------------------------------------------------
	@PostMapping
	public ResponseEntity<Void> incluirCidade(@Valid @RequestBody CidadeDTO cidadeDto) {
		//	Responde POST em http://localhost:8080/placeti/cidades
		//	Envia JSON no body:
		//	{
		//	 	"nome": "Florianópolis",
		//	  	"uf": "SC",
		//	   	"capital": true
		//	}
		projetoService.incluirCidade(cidadeDto);

		// 201 com o corpo vazio: o front só precisa saber que deu certo
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}	

	//----------------------------------------------------------
	/** Endpoint para alterar cidade */
	//----------------------------------------------------------
	@PutMapping
	public ResponseEntity<Void> alterarCidade(@Valid @RequestBody CidadeDTO cidadeDto) {
		// Responde PUT em http://localhost:8080/placeti/cidades
		//   Envia JSON no body:
		//   {
		//     "id": 11,
		//     "nome": "Blumenau",
		//     "uf": "SC",
		//     "capital": false
		//   }
		projetoService.alterarCidade(cidadeDto);
		return ResponseEntity.ok().build();
	}

	//----------------------------------------------------------
	/** Endpoint para excluir uma cidade */
	//----------------------------------------------------------
	@DeleteMapping("/{idCidade}")
	public ResponseEntity<Void> excluirCidade(@PathVariable Long idCidade) {
		// Responde DELETE em http://localhost:8080/placeti/cidades/{idCidade}
		projetoService.excluirCidade(idCidade);
		return ResponseEntity.noContent().build();
	}	
}
