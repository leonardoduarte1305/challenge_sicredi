package br.dev.leoduarte.sicredi.service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.leoduarte.sicredi.controller.dto.response.Resultado;
import br.dev.leoduarte.sicredi.exception.HorarioLimiteDeVotacaoException;
import br.dev.leoduarte.sicredi.exception.VotoJaComputadoException;
import br.dev.leoduarte.sicredi.model.Associado;
import br.dev.leoduarte.sicredi.model.Pauta;
import br.dev.leoduarte.sicredi.model.VotoNaPauta;
import br.dev.leoduarte.sicredi.model.enuns.Voto;
import br.dev.leoduarte.sicredi.repository.AssociadoRepository;
import br.dev.leoduarte.sicredi.repository.PautaRepository;
import br.dev.leoduarte.sicredi.repository.VotoNaPautaRepository;
import br.dev.leoduarte.sicredi.utils.FormatarData;

@Service
public class VotoNaPautaService {

	@Autowired
	private PautaRepository pautaRepo;

	@Autowired
	private AssociadoRepository assocRepo;

	@Autowired
	private VotoNaPautaRepository votoNaPautaRepo;

	private FormatarData formatada = new FormatarData();

	public ResponseEntity<Object> adicionarVotoDoAssociado(Long idPauta, Long idAssociado, Long voto,
			UriComponentsBuilder uri) {

		Pauta pauta = pautaRepo.findById(idPauta).get();

		if (sessaoExpirou(pauta)) {

			throw new HorarioLimiteDeVotacaoException(
					"O prazo de votação terminou em: " + formatada.formatar(pauta.getTempLimiteVotacao()));
		}

		Associado associado = assocRepo.findById(idAssociado).get();
		VotoNaPauta entidade = new VotoNaPauta(pauta, associado, voto);

		for (VotoNaPauta v : pauta.getVotos()) {
			if (v.getId().getAssociado().getId() == associado.getId()) {
				throw new VotoJaComputadoException("Associado já votou nesta pauta: " + pauta.getId());
			}
		}

		VotoNaPauta salvo = votoNaPautaRepo.save(entidade);

		return ResponseEntity.ok("Voto cadastrado");
	}

	private boolean sessaoExpirou(Pauta pauta) {
		return pauta.getTempLimiteVotacao().isBefore(LocalDateTime.now());
	}

	public ResponseEntity<Resultado> contabilizarVotacao(Long idPauta) {

		Pauta pauta = pautaRepo.findById(idPauta).get();

		int qtdVotosSim = pauta.getVotos().stream().filter(v -> v.getVoto().equals(Voto.SIM))
				.collect(Collectors.toList()).size();
		int qtdVotosNao = pauta.getVotos().size() - qtdVotosSim;

		return ResponseEntity.ok(new Resultado(pauta.getId(), qtdVotosSim, qtdVotosNao));
	}
}
