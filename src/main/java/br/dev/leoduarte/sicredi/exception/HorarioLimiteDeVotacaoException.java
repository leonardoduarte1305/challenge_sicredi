package br.dev.leoduarte.sicredi.exception;

public class HorarioLimiteDeVotacaoException extends RuntimeException {

	private static final long serialVersionUID = 4253490962154246487L;

	public HorarioLimiteDeVotacaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public HorarioLimiteDeVotacaoException(String message) {
		super(message);
	}

}
