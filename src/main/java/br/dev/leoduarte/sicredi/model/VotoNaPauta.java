package br.dev.leoduarte.sicredi.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.dev.leoduarte.sicredi.model.enuns.Voto;

@Entity
public class VotoNaPauta implements Serializable {

	private static final long serialVersionUID = -2008220307480097391L;

	@JsonIgnore
	@EmbeddedId
	private VotoNaPautaPK id = new VotoNaPautaPK();

	@Enumerated(EnumType.STRING)
	private Voto voto;
}
