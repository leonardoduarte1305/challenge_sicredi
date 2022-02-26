package br.dev.leoduarte.sicredi.service;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.leoduarte.sicredi.controller.dto.request.PautaDTOE;
import br.dev.leoduarte.sicredi.controller.dto.response.PautaDTOS;
import br.dev.leoduarte.sicredi.exception.EntidadeNaoEncontradaException;
import br.dev.leoduarte.sicredi.model.Associado;
import br.dev.leoduarte.sicredi.model.Pauta;
import br.dev.leoduarte.sicredi.repository.AssociadoRepository;
import br.dev.leoduarte.sicredi.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PautaService {

	private final PautaRepository pautaRepo;

	private final AssociadoRepository assocRepo;

	@Transactional
	public ResponseEntity<PautaDTOS> criarNova(PautaDTOE novaPauta, UriComponentsBuilder uriBuilder) {
		Pauta salva = pautaRepo.save(new Pauta(novaPauta));
		log.info("Pauta criada, ID: {}", salva.getId());

		PautaDTOS retorno = new PautaDTOS(salva);

		URI uri = uriBuilder.path("/pautas/{id}").buildAndExpand(retorno.getId()).toUri();

		return ResponseEntity.created(uri).body(retorno);
	}

	public ResponseEntity<PautaDTOS> pesquisarPorId(Long id) {
		return ResponseEntity.ok(new PautaDTOS(encontrarPauta(id)));
	}

	@Transactional
	public ResponseEntity<Object> adicionarAssociado(Long idPauta, Long idAssociado, UriComponentsBuilder uriBuilder) {

		Pauta pauta = encontrarPauta(idPauta);
		Associado associado = encontrarAssociado(idAssociado);

		pauta.adicionarAssociado(associado);
		Pauta salva = pautaRepo.save(pauta);
		log.info("Associado inserido, Pauta ID: {}", salva.getId());

		return ResponseEntity.status(HttpStatus.OK).body(new PautaDTOS(salva));
	}

	public Pauta encontrarPauta(Long id) {
		return pautaRepo.findById(id).orElseThrow(() -> {
			throw new EntidadeNaoEncontradaException("Pauta não encontrada id: " + id);
		});
	}

	public Associado encontrarAssociado(Long id) {
		return assocRepo.findById(id).orElseThrow(() -> {
			throw new EntidadeNaoEncontradaException("Associado não encontrado id: " + id);
		});
	}

}
