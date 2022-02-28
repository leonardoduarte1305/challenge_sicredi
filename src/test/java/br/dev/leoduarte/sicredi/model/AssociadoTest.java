package br.dev.leoduarte.sicredi.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssociadoTest {

	@Test
	void deveCriarUmAssociado() {
		Associado associado = new Associado("Joao");
		associado.setId(2L);

		Assertions.assertTrue(associado.getNome().equals("Joao"));
		Assertions.assertTrue(associado.getId().equals(2L));
	}

}
