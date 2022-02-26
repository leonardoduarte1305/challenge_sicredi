package br.dev.leoduarte.sicredi.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.dev.leoduarte.sicredi.controller.dto.request.AssociadoDTOE;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "associado")
@NoArgsConstructor
@Getter
public class Associado implements Serializable {

	private static final long serialVersionUID = 6234656322024505165L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	public Associado(String nome) {
		this.nome = nome;
	}

	public Associado(AssociadoDTOE novoAssociado) {
		this.nome = novoAssociado.getNome();
	}

	public void setId(Long id) {
		this.id = id;
	}

}
