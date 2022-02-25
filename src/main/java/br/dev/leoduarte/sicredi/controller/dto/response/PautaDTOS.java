package br.dev.leoduarte.sicredi.controller.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.dev.leoduarte.sicredi.model.Pauta;
import lombok.Getter;

@Getter
public class PautaDTOS {

	private Long id;

	private String nomeAssembleia;

	private LocalDateTime tempLimiteVotacao;

	private List<AssociadoDTOS> associados;

	public PautaDTOS(Pauta obj) {
		this.id = obj.getId();
		this.nomeAssembleia = obj.getNomeAssembleia();
		this.tempLimiteVotacao = obj.getTempLimiteVotacao();
		this.associados = obj.getAssociados().stream().map(AssociadoDTOS::new).collect(Collectors.toList());
	}

}
