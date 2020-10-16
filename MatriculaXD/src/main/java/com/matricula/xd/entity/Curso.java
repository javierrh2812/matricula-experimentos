package com.matricula.xd.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "cursos")
public @Data class Curso{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 30)
	private String nombre;
	
	@Column(unique=true, nullable = false, length = 5)
	private String codigo;
	
	@OneToOne
	private Docente docente;

	@Column(nullable=false)
	private Boolean habilitado=true;
	
	public String titulo() {
		return "Curso: "+nombre + "/ Docente:" + docente.getNombre() + " "+docente.getApellido();
	}

}