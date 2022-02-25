package br.dev.leoduarte.sicredi.controller.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class AssociadoDTOE {

	@NotNull(message = "O nome não pode ser nulo.")
	@NotBlank(message = "O nome não pode estar em branco")
	@NotEmpty(message = "O nome não pode estar vazio")
	@Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
	private String nome;

}
