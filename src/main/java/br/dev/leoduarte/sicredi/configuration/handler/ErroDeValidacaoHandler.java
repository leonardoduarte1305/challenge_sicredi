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

import br.dev.leoduarte.sicredi.exception.AssociadoNaoCadastradoNaPautaException;
import br.dev.leoduarte.sicredi.exception.DataInvalidaException;
import br.dev.leoduarte.sicredi.exception.EntidadeNaoEncontradaException;
import br.dev.leoduarte.sicredi.exception.SessaoDeVotacaoExpiradaException;
import br.dev.leoduarte.sicredi.exception.VotoInvalidoException;
import br.dev.leoduarte.sicredi.exception.VotoJaComputadoException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

			log.info("Erro de DTO Entrada: [Campo: {} - Erro: {}]", //
					erro.getCampo(), erro.getErro());
		});

		return dto;
	}

	@ExceptionHandler(DataInvalidaException.class)
	public ResponseEntity<String> handle(DataInvalidaException e) {
		log.info("Data Invalida - {}", e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(VotoJaComputadoException.class)
	public ResponseEntity<String> handle(VotoJaComputadoException e) {
		log.info("Voto Ja Computado - {}", e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(SessaoDeVotacaoExpiradaException.class)
	public ResponseEntity<String> handle(SessaoDeVotacaoExpiradaException e) {
		log.info("Horario Encerrado - {}", e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<String> handle(EntidadeNaoEncontradaException e) {
		log.info("Entidade Nao Cadastrada - {}", e.getMessage());
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(VotoInvalidoException.class)
	public ResponseEntity<String> handle(VotoInvalidoException e) {
		log.info("Voto Invalido - {}", e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(AssociadoNaoCadastradoNaPautaException.class)
	public ResponseEntity<String> handle(AssociadoNaoCadastradoNaPautaException e) {
		log.info("Associado não cadastrado na pauta - {}", e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
