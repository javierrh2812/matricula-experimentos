package com.matricula.xd.service;


import java.util.List;

import com.matricula.xd.entity.Alumno;

public interface IAlumnoService extends CrudService<Alumno, Long> {
	
	List<Alumno> fetchAlumnosByCursoAndCiclo(Long curso_id, String ciclo);
}
