package br.dev.leoduarte.sicredi.service;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.leoduarte.sicredi.controller.dto.response.Resultado;
import br.dev.leoduarte.sicredi.exception.AssociadoNaoCadastradoNaPautaException;
import br.dev.leoduarte.sicredi.exception.SessaoDeVotacaoExpiradaException;
import br.dev.leoduarte.sicredi.exception.VotoJaComputadoException;
import br.dev.leoduarte.sicredi.model.Associado;
import br.dev.leoduarte.sicredi.model.Pauta;
import br.dev.leoduarte.sicredi.model.VotoNaPauta;
import br.dev.leoduarte.sicredi.model.enuns.Voto;
import br.dev.leoduarte.sicredi.repository.VotoNaPautaRepository;
import br.dev.leoduarte.sicredi.utils.FormatarData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class VotoNaPautaService {

	private final PautaService service;

	private final VotoNaPautaRepository votoNaPautaRepo;

	private final FormatarData form = new FormatarData();

	public ResponseEntity<Object> adicionarVotoDoAssociado(Long idPauta, Long idAssociado, Long voto,
			UriComponentsBuilder uri) {

		Pauta pauta = service.encontrarPauta(idPauta);
		verificaSeSessaoJaExpirou(pauta);

		Associado associado = service.encontrarAssociado(idAssociado);

		associadoNaoEstaNaPauta(pauta, associado);

		if (!pauta.getAssociados().isEmpty()) {
			verificaVotoDuplo(pauta, associado);
		}

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
		return (int) pauta.getVotos().stream().filter(v -> v.getVoto().equals(Voto.NAO)).count();
	}

	private int contarVotosSim(Pauta pauta) {
		return (int) pauta.getVotos().stream().filter(v -> v.getVoto().equals(Voto.SIM)).count();
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

	private void associadoNaoEstaNaPauta(Pauta pauta, Associado associado) {
		if (!pauta.getAssociados().contains(associado)) {
			throw new AssociadoNaoCadastradoNaPautaException("Este associado não pode votar nesta pauta");
		}

	}
}
