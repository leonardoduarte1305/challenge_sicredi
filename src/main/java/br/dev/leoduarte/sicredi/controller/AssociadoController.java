package br.dev.leoduarte.sicredi.controller;

import java.io.Serializable;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@Api(tags = "Associado Controller", protocols = "HTTP")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/associados")
public class AssociadoController implements Serializable {

	private static final long serialVersionUID = -6196616588859521668L;

	private final AssociadoService service;

	private final VotoNaPautaService votoNaPautaService;

	@ApiOperation(value = "Cadastra um Associado", //
			notes = "Recebe no body da requisição uma representação" //
					+ " Json do objeto AssociadoDTOE", //
			response = AssociadoDTOS.class)
	@PostMapping
	public ResponseEntity<AssociadoDTOS> criarNovoAssociado( //
			@Validated @RequestBody AssociadoDTOE novoAssociado, //
			UriComponentsBuilder uri) {
		return service.criarNovo(novoAssociado, uri);
	}

	@ApiOperation(value = "Busca um associado usando seu ID de registro como chave.", //
			notes = "Exemplo de uso: ~/associados/1", //
			response = AssociadoDTOS.class, //
			produces = "application/json")
	@GetMapping("/{id}")
	public ResponseEntity<AssociadoDTOS> pesquisarPorId( //
			@PathVariable Long id) {
		return service.pesquisarPorId(id);
	}

	@ApiOperation(value = "Cadastra um Voto", //
			notes = "Exemplo de uso: 0 - SIM / 1 - NAO", //
			response = AssociadoDTOS.class)
	@PostMapping("/{idPauta}/{idAssociado}/{voto}")
	public ResponseEntity<Object> votar( //
			@PathVariable Long idPauta, //
			@PathVariable Long idAssociado, //
			@PathVariable Long voto, //
			UriComponentsBuilder uri) {
		return votoNaPautaService.adicionarVotoDoAssociado(idPauta, idAssociado, voto, uri);
	}

}
