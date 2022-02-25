package br.dev.leoduarte.sicredi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.leoduarte.sicredi.controller.dto.request.AssociadoDTOE;
import br.dev.leoduarte.sicredi.controller.dto.response.AssociadoDTOS;
import br.dev.leoduarte.sicredi.service.AssociadoService;

@RestController
@RequestMapping(path = "/associados")
public class AssociadoController {

	@Autowired
	private AssociadoService service;

	/**
	 * Cadastra um novo Associado
	 * 
	 * @param novoAssociado
	 * @param uri
	 * @return
	 */
	@PostMapping
	public ResponseEntity<AssociadoDTOS> criarNovoAssociado(@Validated @RequestBody AssociadoDTOE novoAssociado,
			UriComponentsBuilder uri) {
		return service.criarNovo(novoAssociado, uri);
	}

	/**
	 * Busca um Associado pelo seu Id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<AssociadoDTOS> pesquisarPorId(@PathVariable Long id) {
		return service.pesquisarPorId(id);
	}

	// Associar as pautas em que ele pode votar
}
