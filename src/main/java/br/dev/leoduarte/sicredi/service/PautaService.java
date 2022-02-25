package br.dev.leoduarte.sicredi.service;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.leoduarte.sicredi.controller.dto.request.PautaDTOE;
import br.dev.leoduarte.sicredi.controller.dto.response.PautaDTOS;
import br.dev.leoduarte.sicredi.model.Associado;
import br.dev.leoduarte.sicredi.model.Pauta;
import br.dev.leoduarte.sicredi.repository.AssociadoRepository;
import br.dev.leoduarte.sicredi.repository.PautaRepository;
import br.dev.leoduarte.sicredi.repository.VotoNaPautaRepository;
import br.dev.leoduarte.sicredi.utils.FormatarData;

@Service
public class PautaService {

	@Autowired
	private PautaRepository pautaRepo;

	@Autowired
	private AssociadoRepository assocRepo;

	@Autowired
	private VotoNaPautaRepository votoNaPautaRepo;

	private FormatarData formatada = new FormatarData();

	public ResponseEntity<PautaDTOS> criarNova(PautaDTOE novaPauta, UriComponentsBuilder uriBuilder) {
		Pauta salva = pautaRepo.save(new Pauta(novaPauta));
		PautaDTOS retorno = new PautaDTOS(salva);

		URI uri = uriBuilder.path("/pautas/{id}").buildAndExpand(retorno.getId()).toUri();

		return ResponseEntity.created(uri).body(retorno);
	}

	public ResponseEntity<PautaDTOS> pesquisarPorId(Long id) {
		Optional<Pauta> encontrado = pautaRepo.findById(id);

		if (encontrado.isPresent()) {
			return ResponseEntity.ok(new PautaDTOS(encontrado.get()));
		}

		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<Object> adicionarAssociado(Long idPauta, Long idAssociado, UriComponentsBuilder uriBuilder) {

		Optional<Pauta> encontrada = pautaRepo.findById(idPauta);
		if (encontrada.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pauta não encontrada id: " + idPauta);
		}

		Optional<Associado> encontrado = assocRepo.findById(idAssociado);
		if (encontrado.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Associado não cadastrado id: " + idAssociado);
		}

		Pauta pauta = encontrada.get();
		pauta.adicionarAssociado(encontrado.get());

		Pauta salva = pautaRepo.save(pauta);
		PautaDTOS retorno = new PautaDTOS(salva);

		return ResponseEntity.status(HttpStatus.OK).body(retorno);
	}

}
