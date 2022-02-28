package br.dev.leoduarte.sicredi.controller.dto.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Resultado implements Serializable {

	private static final long serialVersionUID = -7306217644565940926L;

	private Long idPauta;
	private int qtdVotosSim;
	private int qtdVotosNao;
}
