package br.dev.leoduarte.sicredi.service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.leoduarte.sicredi.controller.dto.response.Resultado;
import br.dev.leoduarte.sicredi.exception.SessaoDeVotacaoExpiradaException;
import br.dev.leoduarte.sicredi.exception.VotoJaComputadoException;
import br.dev.leoduarte.sicredi.model.Associado;
import br.dev.leoduarte.sicredi.model.Pauta;
import br.dev.leoduarte.sicredi.model.VotoNaPauta;
import br.dev.leoduarte.sicredi.model.enuns.Voto;
import br.dev.leoduarte.sicredi.repository.VotoNaPautaRepository;
import br.dev.leoduarte.sicredi.utils.FormatarData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VotoNaPautaService {

	@Autowired
	private PautaService service;

	@Autowired
	private VotoNaPautaRepository votoNaPautaRepo;

	private FormatarData form = new FormatarData();

	public ResponseEntity<Object> adicionarVotoDoAssociado(Long idPauta, Long idAssociado, Long voto,
			UriComponentsBuilder uri) {

		Pauta pauta = service.encontrarPauta(idPauta);
		verificaSeSessaoJaExpirou(pauta);

		Associado associado = service.encontrarAssociado(idAssociado);
		verificaVotoDuplo(pauta, associado);

		votoNaPautaRepo.save(new VotoNaPauta(pauta, associado, voto));
		log.info("Voto cadastrado, Pauta ID: {} - Associado ID: {}", pauta.getId(), associado.getId());

		return ResponseEntity.ok("Voto cadastrado");
	}

	public ResponseEntity<Resultado> contabilizarVotacao(Long idPauta) {

		Pauta pauta = service.encontrarPauta(idPauta);

		if (pauta.getVotos().size() == 0) {
			log.debug("Sem votos para a paura, ID: {}", pauta.getId());
			return ResponseEntity.ok(new Resultado(pauta.getId(), 0, 0));
		}

		return ResponseEntity.ok(new Resultado(pauta.getId(), contarVotosSim(pauta), contarVotosNao(pauta)));
	}

	private int contarVotosNao(Pauta pauta) {
		return pauta.getVotos().stream().filter(v -> v.getVoto().equals(Voto.NAO)).collect(Collectors.toList()).size();
	}

	private int contarVotosSim(Pauta pauta) {
		return pauta.getVotos().stream().filter(v -> v.getVoto().equals(Voto.SIM)).collect(Collectors.toList()).size();
	}

	private boolean verificaSeSessaoJaExpirou(Pauta pauta) {
		if (pauta.getTempLimiteVotacao().isBefore(LocalDateTime.now())) {
			throw new SessaoDeVotacaoExpiradaException(
					"O prazo de votação terminou em: " + form.formatar(pauta.getTempLimiteVotacao()));
		}

		return true;
	}

	private boolean verificaVotoDuplo(Pauta pauta, Associado associado) {
		for (VotoNaPauta v : pauta.getVotos()) {
			if (v.getId().getAssociado().getId() == associado.getId()) {
				throw new VotoJaComputadoException("Associado já votou nesta pauta: " + pauta.getId());
			}
		}

		return true;
	}
}
