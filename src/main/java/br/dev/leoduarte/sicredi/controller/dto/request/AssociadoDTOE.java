package br.dev.leoduarte.sicredi.controller.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssociadoDTOE implements Serializable {

	private static final long serialVersionUID = 6631370239834224366L;

	@NotNull(message = "O nome não pode ser nulo.")
	@NotBlank(message = "O nome não pode estar em branco")
	@NotEmpty(message = "O nome não pode estar vazio")
	@Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
	private String nome;

}
