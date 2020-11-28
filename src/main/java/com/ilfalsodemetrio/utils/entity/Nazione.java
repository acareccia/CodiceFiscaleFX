package com.ilfalsodemetrio.utils.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Nazione implements Serializable {
	private Integer id;
	private String codiceIstat;
	private String descrizione;
	private String tipo;
	private String codiceFiscale;
	private String sigla;
}
