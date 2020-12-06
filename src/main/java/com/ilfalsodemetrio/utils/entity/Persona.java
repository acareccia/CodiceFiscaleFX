package com.ilfalsodemetrio.utils.entity;

import groovy.beans.Bindable;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Bindable
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
