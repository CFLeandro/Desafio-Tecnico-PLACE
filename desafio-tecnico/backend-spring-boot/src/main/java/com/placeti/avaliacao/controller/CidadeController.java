package com.placeti.avaliacao.controller;

import com.placeti.avaliacao.dto.CidadeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//--------------------------------------------------
/** Endpoint para consultar e manter cidades */
//--------------------------------------------------
@RequestMapping("/cidades")
public class CidadeController {

	//----------------------------------------------------------
	/** Endpoint que retorna uma cidade conforme seu ID */
	//----------------------------------------------------------
	public ResponseEntity<CidadeDTO> buscarPeloId(Long id) {
		// TODO: Responde GET em http://localhost:8080/placeti/cidades/1
	}
	
	//----------------------------------------------------------
	/** Endpoint que retorna todas as cidades cadastradas */
	//----------------------------------------------------------
	public List<CidadeDTO> pesquisarCidades() {
		// TODO: Responde GET em http://localhost:8080/placeti/cidades
	}
	
	//----------------------------------------------------------
	/** Endpoint para incluir nova cidade */
	//----------------------------------------------------------
	public void incluirCidade(CidadeDTO cidadeDto) {
		//	TODO: Responde POST em http://localhost:8080/placeti/cidades
		//	Envia JSON no body:
		//	{
		//	 	"nome": "Florianópolis",
		//	  	"uf": "SC",
		//	   	"capital": true
		//	}
	}	
	
	//----------------------------------------------------------
	/** Endpoint para alterar cidade */
	//----------------------------------------------------------
	public void alterarCidade(CidadeDTO cidadeDto) {
		// TODO: Responde PUT em http://localhost:8080/placeti/cidades
		//   Envia JSON no body:
		//   {
		//     "id": 11,
		//     "nome": "Blumenau",
		//     "uf": "SC",
		//     "capital": false
		//   }
	}
	
	//----------------------------------------------------------
	/** Endpoint para excluir uma cidade */
	//----------------------------------------------------------
	public void excluirCidade(Long idCidade) {
		// Responde DELETE em http://localhost:8080/placeti/cidades/{idCidade}
	}	
}
