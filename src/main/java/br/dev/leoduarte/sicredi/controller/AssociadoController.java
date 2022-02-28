package br.dev.leoduarte.sicredi.controller;

import java.io.Serializable;

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
import br.dev.leoduarte.sicredi.service.VotoNaPautaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/associados")
public class AssociadoController implements Serializable {

	private static final long serialVersionUID = -6196616588859521668L;

	@Autowired
	private final AssociadoService service;

	@Autowired
	private final VotoNaPautaService votoNaPautaService;

	@PostMapping
	public ResponseEntity<AssociadoDTOS> criarNovoAssociado( //
			@Validated @RequestBody AssociadoDTOE novoAssociado, //
			UriComponentsBuilder uri) {
		return service.criarNovo(novoAssociado, uri);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AssociadoDTOS> pesquisarPorId( //
			@PathVariable Long id) {
		return service.pesquisarPorId(id);
	}

	@PostMapping("/{idPauta}/{idAssociado}/{voto}")
	public ResponseEntity<Object> votar( //
			@PathVariable Long idPauta, //
			@PathVariable Long idAssociado, //
			@PathVariable Long voto, //
			UriComponentsBuilder uri) {
		return votoNaPautaService.adicionarVotoDoAssociado(idPauta, idAssociado, voto, uri);
	}

}
