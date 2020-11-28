package com.ilfalsodemetrio.utils.entity;

import groovy.beans.Bindable;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Bindable
public class Persona implements Serializable {
	private Integer id;
	private String nome;
	private String cognome;
	private String sesso;
	private Date dataNascita;
	private String dataNascitaString;
	private String codiceFiscale;
	private Localita localita;

	public String getDataNascitaString() {
		return new SimpleDateFormat("dd/MM/yyyy").format(dataNascita);
	}

	@Override
	public String toString() {
		return "Persona{" +
				"nome='" + nome + '\'' +
				", cognome='" + cognome + '\'' +
				", sesso=" + sesso +
				", dataNascitaString='" + dataNascitaString + '\'' +
				", codiceFiscale='" + codiceFiscale + '\'' +
				", localita=" + localita +
				'}';
	}
}
