package com.ilfalsodemetrio.utils.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Persona implements Serializable {
	public enum Sesso {
		M,
		F
	}
	private String nome;
	private String cognome;
	private String sesso;
	private Date dataNascita;
	private String codiceFiscale;
	private Localita localita;

	@Override
	public String toString() {
		return "Persona{" +
				"nome='" + nome + '\'' +
				", cognome='" + cognome + '\'' +
				", sesso='" + sesso + '\'' +
				", dataNascita='" + dataNascita + '\'' +
				", codiceFiscale='" + codiceFiscale + '\'' +
				", localita=" + localita +
				'}';
	}
}
