package com.matricula.xd.service;

import java.util.List;

import com.matricula.xd.entity.Docente;

public interface IDocenteService extends CrudService<Docente, Long> {
	
	List<Docente> fetchDocentesByCursoHabilitado(String curso);
	
}
