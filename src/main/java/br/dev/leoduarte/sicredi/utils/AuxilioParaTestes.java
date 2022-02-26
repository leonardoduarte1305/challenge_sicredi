package br.dev.leoduarte.sicredi.utils;

import java.time.LocalDateTime;

import br.dev.leoduarte.sicredi.controller.dto.request.PautaDTOE;
import br.dev.leoduarte.sicredi.model.Associado;
import br.dev.leoduarte.sicredi.model.Pauta;

public class AuxilioParaTestes {

	public Pauta novaPauta(long id, String nome, LocalDateTime plusMinutes) {
		PautaDTOE obj = new PautaDTOE(plusMinutes, nome);
		Pauta pauta = new Pauta(obj);
		pauta.setId(id);
		return pauta;
	}

	public Associado novoAssociadoComId(long id, String nome) {
		Associado associado = new Associado(nome);
		associado.setId(id);
		return associado;
	}
}
