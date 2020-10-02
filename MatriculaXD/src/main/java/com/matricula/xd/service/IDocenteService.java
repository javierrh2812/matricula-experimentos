package com.matricula.xd.service;

import java.util.List;

import com.matricula.xd.entity.Docente;

public interface IDocenteService extends CrudService<Docente, Long> {
	
	List<Docente> fetchDocentesByCursoHabilitado(String curso);
	
	Boolean existeDocenteConDni(String dni);
	
	List<Docente> findByNombreOrApellidoLike(String term);
	
	List<Docente> findAllByDocenteHabilitado();
}
