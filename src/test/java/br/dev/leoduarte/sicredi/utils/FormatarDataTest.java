package br.dev.leoduarte.sicredi.utils;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class FormatarDataTest {

	@Test
	void retornaOkSeFormatarCorretamenteAData() {
		Assert.assertTrue(new FormatarData().formatar(LocalDateTime.now())
				.matches("\\d{2}\\/\\d{2}\\/\\d{4}\\ \\d{2}\\:\\d{2}\\:\\d{2}"));
	}

}
