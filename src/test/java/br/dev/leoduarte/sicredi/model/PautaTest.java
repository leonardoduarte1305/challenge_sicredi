package br.dev.leoduarte.sicredi.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import br.dev.leoduarte.sicredi.controller.dto.request.PautaDTOE;
import br.dev.leoduarte.sicredi.exception.DataInvalidaException;

public class PautaTest {

	@Test
	void naoDeveCriarPautaComTempoMenorQueAgora() {
		PautaDTOE dtoE = new PautaDTOE(LocalDateTime.now().minusMinutes(10), "Assembleia");
		Exception e = assertThrows(DataInvalidaException.class, () -> {
			new Pauta(dtoE);
		});

		String esperada = "Data invalida:";
		String retornada = e.getMessage();

		assertTrue(retornada.startsWith(esperada));

	}

	@Test
	void deveCriarPautaComTempoPadraoSeTempoNaoInformado() {
		PautaDTOE dtoE = new PautaDTOE(null, "Assembleia");
		Pauta pauta = new Pauta(dtoE);

		String retornada = pauta.getTempLimiteVotacao().toString();
		String esperada = LocalDateTime.now().plusMinutes(1).toString();

		assertTrue(retornada.startsWith(esperada.substring(0, 19)));

	}

}
