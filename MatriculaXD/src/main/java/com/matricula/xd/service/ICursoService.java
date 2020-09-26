package com.matricula.xd.service;

import java.util.List;

import com.matricula.xd.entity.Curso;

public interface ICursoService extends CrudService<Curso, Long> {
	
	List<String> findCursosHabilitados();
	
	Curso fetchByNombreAndDocenteId(String nombre, Long docente_id);
	
}
