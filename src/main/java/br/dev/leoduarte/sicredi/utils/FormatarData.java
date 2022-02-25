package br.dev.leoduarte.sicredi.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatarData {

	public String formatar(LocalDateTime t) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return t.format(formatter);
	}

}
