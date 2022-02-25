package br.dev.leoduarte.sicredi.controller.dto.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;

@Getter
public class PautaDTOE {

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime tempLimiteVotacao;

	@NotNull(message = "O nome da assembleia não pode ser nulo.")
	@NotBlank(message = "O nome da assembleia não pode estar em branco")
	@NotEmpty(message = "O nome da assembleia não pode estar vazio")
	private String nomeAssembleia;
}
