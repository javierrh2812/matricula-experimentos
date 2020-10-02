package com.matricula.xd.service;

import java.util.List;

import com.matricula.xd.entity.Curso;

public interface ICursoService extends CrudService<Curso, Long> {
	
	List<String> findCursosHabilitados();
	
	Curso fetchByNombreAndDocenteId(String nombre, Long docente_id);
	
	List<Long> fetchCursosIdBySemestre(String semestre);
	
	Integer contarAlumnosMatriculadosPorCursoYSemestre(Long idcurso, String semestre);
	
	List<Curso> findByNombreLike(String term);
	
	Boolean existeCursoPorCodigo(String codigo);
	
}
