package com.ilfalsodemetrio.utils.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Localita implements Serializable {
	private String descrizione;
	private String provincia;
	private String codiceFiscale;

	public Localita() { }

	public Localita(String codiceFiscale, String descrizione, String provincia) {
		this.codiceFiscale = codiceFiscale;
		this.descrizione = descrizione;
		this.provincia = provincia;
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append(descrizione.toUpperCase())
				.append(" (")
				.append(provincia.toUpperCase())
				.append(")")
				.toString();
	}
}
