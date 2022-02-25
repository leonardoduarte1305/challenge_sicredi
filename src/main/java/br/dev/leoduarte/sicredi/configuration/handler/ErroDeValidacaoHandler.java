package br.dev.leoduarte.sicredi.configuration.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.dev.leoduarte.sicredi.exception.DataInvalidaException;
import br.dev.leoduarte.sicredi.exception.HorarioLimiteDeVotacaoException;
import br.dev.leoduarte.sicredi.exception.VotoJaComputadoException;

@RestControllerAdvice(basePackages = { "br.dev.leoduarte.sicredi.controller" })
public class ErroDeValidacaoHandler {

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeDtoEntrada> handle(MethodArgumentNotValidException exception) {

		List<ErroDeDtoEntrada> dto = new ArrayList<ErroDeDtoEntrada>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroDeDtoEntrada erro = new ErroDeDtoEntrada(e.getField(), mensagem);
			dto.add(erro);
		});

		return dto;
	}

	@ExceptionHandler(DataInvalidaException.class)
	public ResponseEntity<String> handleData(DataInvalidaException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(VotoJaComputadoException.class)
	public ResponseEntity<String> handleData(VotoJaComputadoException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(HorarioLimiteDeVotacaoException.class)
	public ResponseEntity<String> handleData(HorarioLimiteDeVotacaoException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
