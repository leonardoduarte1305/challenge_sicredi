package br.dev.leoduarte.sicredi.exception;

public class SessaoDeVotacaoExpiradaException extends RuntimeException {

	private static final long serialVersionUID = 4253490962154246487L;

	public SessaoDeVotacaoExpiradaException(String message, Throwable cause) {
		super(message, cause);
	}

	public SessaoDeVotacaoExpiradaException(String message) {
		super(message);
	}

}
