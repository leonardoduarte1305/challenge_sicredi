package br.dev.leoduarte.sicredi.service;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.leoduarte.sicredi.controller.dto.request.AssociadoDTOE;
import br.dev.leoduarte.sicredi.controller.dto.response.AssociadoDTOS;
import br.dev.leoduarte.sicredi.model.Associado;
import br.dev.leoduarte.sicredi.repository.AssociadoRepository;

@Service
public class AssociadoService {

	@Autowired
	private AssociadoRepository repository;

	public ResponseEntity<AssociadoDTOS> criarNovo(AssociadoDTOE novoAssociado, UriComponentsBuilder uriBuilder) {

		Associado salvo = repository.save(new Associado(novoAssociado));
		AssociadoDTOS retorno = new AssociadoDTOS(salvo);

		URI uri = uriBuilder.path("/associados/{id}").buildAndExpand(retorno.getId()).toUri();

		return ResponseEntity.created(uri).body(retorno);
	}

	public ResponseEntity<AssociadoDTOS> pesquisarPorId(Long id) {
		Optional<Associado> encontrado = repository.findById(id);

		if (encontrado.isPresent()) {
			return ResponseEntity.ok(new AssociadoDTOS(encontrado.get()));
		}

		return ResponseEntity.notFound().build();
	}

}
