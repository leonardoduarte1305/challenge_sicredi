package br.dev.leoduarte.sicredi.controller.dto.response;

import java.io.Serializable;

import br.dev.leoduarte.sicredi.model.Associado;
import lombok.Getter;

@Getter
public class AssociadoDTOS implements Serializable {

	private static final long serialVersionUID = 6532890292940933771L;

	private Long id;
	private String nome;

	public AssociadoDTOS(Associado obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}

}
