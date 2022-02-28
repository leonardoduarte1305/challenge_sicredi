package br.dev.leoduarte.sicredi.controller.dto.request;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PautaDTOE implements Serializable {

	private static final long serialVersionUID = -684513565407917407L;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime tempLimiteVotacao;

	@NotNull(message = "O nome da assembleia não pode ser nulo.")
	@NotBlank(message = "O nome da assembleia não pode estar em branco")
	@NotEmpty(message = "O nome da assembleia não pode estar vazio")
	private String nomeAssembleia;
}
