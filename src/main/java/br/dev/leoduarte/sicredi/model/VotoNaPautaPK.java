package br.dev.leoduarte.sicredi.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class VotoNaPautaPK implements Serializable {

	private static final long serialVersionUID = -534813975736096104L;

	@ManyToOne
	@JoinColumn(name = "pauta_id")
	private Pauta pauta;

	@ManyToOne
	@JoinColumn(name = "associado_id")
	private Associado associado;

	public VotoNaPautaPK(Pauta pauta, Associado associado) {
		this.pauta = pauta;
		this.associado = associado;
	}

	public Associado getAssociado() {
		return associado;
	}

}
