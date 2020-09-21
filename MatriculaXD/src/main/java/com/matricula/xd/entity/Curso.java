package com.matricula.xd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "cursos")
public @Data class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombre", nullable = false, length = 30)
	private String nombre;
	
	@Column(name = "codigo", unique=true, nullable = false, length = 5)
	private String codigo;
	
	@ManyToOne
	@JoinColumn(nullable =false)
	private Docente docente;
	
	@Column(nullable=false)
	private Boolean habilitado=true;

}
