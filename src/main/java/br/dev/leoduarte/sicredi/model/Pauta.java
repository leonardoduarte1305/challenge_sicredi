package br.dev.leoduarte.sicredi.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.dev.leoduarte.sicredi.controller.dto.request.PautaDTOE;
import br.dev.leoduarte.sicredi.exception.DataInvalidaException;
import lombok.Getter;

@Entity
@Getter
@Table(name = "pauta")
public class Pauta implements Serializable {

	private static final long serialVersionUID = 881902711178798222L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nomeAssembleia;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime tempLimiteVotacao;

	@ManyToMany
	private List<Associado> associados;

	@OneToMany(mappedBy = "id.pauta")
	private List<VotoNaPauta> votos;

	public Pauta() {
		this.associados = new ArrayList<>();
		this.votos = new ArrayList<>();
	}

	public Pauta(PautaDTOE obj) {
		this.associados = new ArrayList<>();
		this.nomeAssembleia = obj.getNomeAssembleia();
		this.tempLimiteVotacao = verificarLimiteVotacao(obj.getTempLimiteVotacao());
		this.votos = new ArrayList<>();
	}

	private LocalDateTime verificarLimiteVotacao(LocalDateTime t) {

		if (t == null) {
			return LocalDateTime.now().plusMinutes(1L);
		}

		if (t.isBefore(LocalDateTime.now())) {
			throw new DataInvalidaException(
					"Data invalida: " + t.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
							+ ", Na criacao da classe: " + Pauta.class.getName());
		}

		return t;
	}

	public void adicionarAssociado(Associado associado) {
		this.associados.add(associado);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setVotos(List<VotoNaPauta> votos) {
		this.votos = votos;
	}

}
