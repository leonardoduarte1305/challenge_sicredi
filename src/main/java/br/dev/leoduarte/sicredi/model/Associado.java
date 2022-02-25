package br.dev.leoduarte.sicredi.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "associado")
public class Associado implements Serializable {

	private static final long serialVersionUID = 6234656322024505165L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@ManyToMany(mappedBy = "associados")
	private List<Pauta> pautas;

}
