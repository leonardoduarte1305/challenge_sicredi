package br.dev.leoduarte.sicredi.controller;

import java.io.Serializable;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.leoduarte.sicredi.controller.dto.request.PautaDTOE;
import br.dev.leoduarte.sicredi.controller.dto.response.PautaDTOS;
import br.dev.leoduarte.sicredi.controller.dto.response.Resultado;
import br.dev.leoduarte.sicredi.service.PautaService;
import br.dev.leoduarte.sicredi.service.VotoNaPautaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/pautas")
public class PautaController implements Serializable {

	private static final long serialVersionUID = 6249390661562377407L;

	private final PautaService service;

	private final VotoNaPautaService votoNaPautaService;

	@PostMapping
	public ResponseEntity<PautaDTOS> criarNovaPauta( //
			@Validated @RequestBody PautaDTOE novaPauta, //
			UriComponentsBuilder uri) {
		return service.criarNova(novaPauta, uri);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PautaDTOS> pesquisarPorId( //
			@PathVariable Long id) {
		return service.pesquisarPorId(id);
	}

	@PostMapping("/{idPauta}/{idAssociado}")
	public ResponseEntity<Object> cadastrarAssociadoNaPauta( //
			@PathVariable Long idPauta, //
			@PathVariable Long idAssociado, //
			UriComponentsBuilder uri) {
		return service.adicionarAssociado(idPauta, idAssociado, uri);
	}

	@GetMapping("/contabilizar/{idPauta}")
	public ResponseEntity<Resultado> contabilizar( //
			@PathVariable Long idPauta, //
			UriComponentsBuilder uri) {
		return votoNaPautaService.contabilizarVotacao(idPauta);
	}

}
