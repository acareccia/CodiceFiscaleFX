package com.ilfalsodemetrio.utils.entity;

import groovy.beans.Bindable;
import lombok.Data;

import java.io.Serializable;

@Bindable
@Data
public class Localita implements Serializable {
	private Integer id;
	private String codiceIstat;
	private String descrizione;
	private String codiceFiscale;

	public Localita() {
	}

	public Localita(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
}
