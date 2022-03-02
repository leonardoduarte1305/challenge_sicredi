package br.dev.leoduarte.sicredi.controller.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.dev.leoduarte.sicredi.model.Pauta;
import br.dev.leoduarte.sicredi.utils.FormatarData;

public class PautaDTOS implements Serializable {

	private static final long serialVersionUID = 1574747007213848914L;

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

	public Long getId() {
		return id;
	}

	public String getNomeAssembleia() {
		return nomeAssembleia;
	}

	public String getTempLimiteVotacao() {
		return new FormatarData().formatar(tempLimiteVotacao);
	}

	public List<AssociadoDTOS> getAssociados() {
		return associados;
	}

}
