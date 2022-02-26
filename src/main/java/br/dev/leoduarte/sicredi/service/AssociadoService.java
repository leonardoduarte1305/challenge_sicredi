package br.dev.leoduarte.sicredi.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.leoduarte.sicredi.controller.dto.request.AssociadoDTOE;
import br.dev.leoduarte.sicredi.controller.dto.response.AssociadoDTOS;
import br.dev.leoduarte.sicredi.exception.EntidadeNaoEncontradaException;
import br.dev.leoduarte.sicredi.model.Associado;
import br.dev.leoduarte.sicredi.repository.AssociadoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AssociadoService {

	@Autowired
	private AssociadoRepository repository;

	public ResponseEntity<AssociadoDTOS> criarNovo(AssociadoDTOE novoAssociado, UriComponentsBuilder uriBuilder) {

		Associado salvo = repository.save(new Associado(novoAssociado));
		log.info("Associado criado, ID: {}", salvo.getId());

		AssociadoDTOS retorno = new AssociadoDTOS(salvo);

		URI uri = uriBuilder.path("/associados/{id}").buildAndExpand(retorno.getId()).toUri();

		return ResponseEntity.created(uri).body(retorno);
	}

	public ResponseEntity<AssociadoDTOS> pesquisarPorId(Long id) {
		return ResponseEntity.ok(new AssociadoDTOS(encontrarAssociado(id)));
	}

	private Associado encontrarAssociado(Long id) {
		return repository.findById(id).orElseThrow(() -> {
			throw new EntidadeNaoEncontradaException("Associado não encontrado id: " + id);
		});
	}

}
