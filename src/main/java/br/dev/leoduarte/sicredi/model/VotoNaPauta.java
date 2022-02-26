package br.dev.leoduarte.sicredi.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.dev.leoduarte.sicredi.exception.VotoInvalidoException;
import br.dev.leoduarte.sicredi.model.enuns.Voto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class VotoNaPauta implements Serializable {

	private static final long serialVersionUID = -2008220307480097391L;

	@JsonIgnore
	@EmbeddedId
	private VotoNaPautaPK id;

	@Enumerated(EnumType.STRING)
	private Voto voto;

	public VotoNaPauta(Pauta pauta, Associado associado, Long voto) {
		this.id = new VotoNaPautaPK(pauta, associado);
		this.voto = adicionarVoto(voto);
	}

	private Voto adicionarVoto(Long voto) {
		if (voto.equals(0L)) {
			return Voto.SIM;
		} else if (voto.equals(1L)) {
			return Voto.NAO;
		}

		throw new VotoInvalidoException("Alternativa de voto inválida: " + voto + " - " + VotoNaPauta.class.getName());
	}

	public void setVoto(Voto voto) {
		this.voto = voto;
	}

}
