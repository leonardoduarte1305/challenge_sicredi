package br.dev.leoduarte.sicredi.model.enuns;

public enum Voto {

	SIM(0), NAO(1);

	private int cod;

	Voto(int i) {
		this.cod = i;
	}

	public int getCod() {
		return cod;
	}

}
