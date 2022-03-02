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

import br.dev.leoduarte.sicredi.controller.dto.request.PautaDTOE;
import br.dev.leoduarte.sicredi.controller.dto.response.AssociadoDTOS;
import br.dev.leoduarte.sicredi.controller.dto.response.PautaDTOS;
import br.dev.leoduarte.sicredi.controller.dto.response.Resultado;
import br.dev.leoduarte.sicredi.service.PautaService;
import br.dev.leoduarte.sicredi.service.VotoNaPautaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@Api(tags = "Pauta Controller", protocols = "HTTP")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/pautas")
public class PautaController implements Serializable {

	private static final long serialVersionUID = 6249390661562377407L;

	private final PautaService service;

	private final VotoNaPautaService votoNaPautaService;

	@ApiOperation(value = "Cadastra uma Pauta", //
			notes = "Recebe no body da requisição uma representação" //
					+ " Json do objeto PautaDTOE", //
			response = PautaDTOS.class)
	@PostMapping
	public ResponseEntity<PautaDTOS> criarNovaPauta( //
			@Validated @RequestBody PautaDTOE novaPauta, //
			UriComponentsBuilder uri) {
		return service.criarNova(novaPauta, uri);
	}

	@ApiOperation(value = "Busca uma pauta usando seu ID de registro como chave.", //
			notes = "Exemplo de uso: ~/pautas/1", //
			response = AssociadoDTOS.class, //
			produces = "application/json")
	@GetMapping("/{id}")
	public ResponseEntity<PautaDTOS> pesquisarPorId( //
			@PathVariable Long id) {
		return service.pesquisarPorId(id);
	}

	@ApiOperation(value = "Cadastra um associado em uma pauta usando ambos os IDs de registro como chaves.", //
			response = AssociadoDTOS.class, //
			produces = "application/json")
	@PostMapping("/{idPauta}/{idAssociado}")
	public ResponseEntity<PautaDTOS> cadastrarAssociadoNaPauta( //
			@PathVariable Long idPauta, //
			@PathVariable Long idAssociado, //
			UriComponentsBuilder uri) {
		return service.adicionarAssociado(idPauta, idAssociado, uri);
	}

	@ApiOperation(value = "Contabiliza os votos efetuados em uma pauta usando o ID da pauta como chave.", //
			response = Resultado.class, //
			produces = "application/json")
	@GetMapping("/contabilizar/{idPauta}")
	public ResponseEntity<Resultado> contabilizar( //
			@PathVariable Long idPauta, //
			UriComponentsBuilder uri) {
		return votoNaPautaService.contabilizarVotacao(idPauta);
	}

}
