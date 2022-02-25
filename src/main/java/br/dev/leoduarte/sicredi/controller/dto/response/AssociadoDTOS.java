package br.dev.leoduarte.sicredi.controller.dto.response;

import br.dev.leoduarte.sicredi.model.Associado;
import lombok.Getter;

@Getter
public class AssociadoDTOS {

	private Long id;
	private String nome;

	public AssociadoDTOS(Associado obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}

}
