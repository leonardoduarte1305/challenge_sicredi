package br.dev.leoduarte.sicredi.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Resultado {

	private Long idPauta;
	private int qtdVotosSim;
	private int qtdVotosNao;
}
