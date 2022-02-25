package br.dev.leoduarte.sicredi.model;

import java.io.Serializable;
import java.time.LocalDateTime;
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

@Entity
@Table(name = "pauta")
public class Pauta implements Serializable {

	private static final long serialVersionUID = 881902711178798222L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime tempLimiteVotacao;

	@ManyToMany
	private List<Associado> associados;

	@OneToMany(mappedBy = "id.pauta")
	private List<VotoNaPauta> votos = new ArrayList<>();
}
