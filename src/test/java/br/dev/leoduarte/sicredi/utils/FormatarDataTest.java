package br.dev.leoduarte.sicredi.utils;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class FormatarDataTest {

	@Test
	void retornaOkSeFormatarCorretamenteAData() {
		assertTrue(new FormatarData().formatar(LocalDateTime.now())
				.matches("\\d{2}\\/\\d{2}\\/\\d{4}\\ \\d{2}\\:\\d{2}\\:\\d{2}"));
	}

}
