package br.dev.leoduarte.sicredi.configuration.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErroDeDtoEntrada {

	private String campo;
	private String erro;

}
