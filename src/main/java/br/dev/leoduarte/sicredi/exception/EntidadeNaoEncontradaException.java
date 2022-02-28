package br.dev.leoduarte.sicredi.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 6932806072678527237L;

	public EntidadeNaoEncontradaException(String message) {
		super(message);
	}

}
