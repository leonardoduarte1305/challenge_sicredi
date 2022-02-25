package br.dev.leoduarte.sicredi.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class VotoNaPautaPK implements Serializable {

	private static final long serialVersionUID = -534813975736096104L;

	@ManyToOne
	@JoinColumn(name = "pauta_id")
	private Pauta pauta;

	@ManyToOne
	@JoinColumn(name = "associado_id")
	private Associado associado;
}
