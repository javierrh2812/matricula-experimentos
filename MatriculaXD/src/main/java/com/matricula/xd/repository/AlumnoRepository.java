package com.matricula.xd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.matricula.xd.entity.Alumno;

@Repository
public interface AlumnoRepository extends CrudRepository<Alumno, Long>  {
	/*
	select * from alumnos as a 
	 inner join matriculas as m  on a.id = m.alumno_id  
	 inner join matriculas_cursos_matriculados as cm on m.id = cm.matricula_id
	 inner join cursos as c on cm.cursos_matriculados_id = c.id
	 where m.semestre='2020-2' and c.id=2
	
	
	*/
	
	@Query("select a from Alumno a "
			+ "join Matricula m on a.id=m.alumno.id "
			+ "join m.cursosMatriculados cm "
			+ "join Curso c on cm.id=c.id "
			+ "where m.semestre=?2 and c.id=?1")
	List<Alumno> fetchAlumnosByCursoAndCiclo(Long idCurso, String ciclo);
	
}
