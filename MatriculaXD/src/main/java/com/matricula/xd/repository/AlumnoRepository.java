package com.matricula.xd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.matricula.xd.entity.Alumno;

@Repository
public interface AlumnoRepository extends CrudRepository<Alumno, Long>  {

	
	@Query("select a from Alumno a "
			+ "join Matricula m on a.id=m.alumno.id "
			+ "join m.cursosMatriculados cm "
			+ "join Curso c on cm.id=c.id "
			+ "where m.semestre=?2 and c.id=?1")
	List<Alumno> fetchAlumnosByCursoAndCiclo(Long idCurso, String ciclo);
	
	List<Alumno> findByDni(String dni);
	
	@Query("select a from Alumno a where LOWER(a.nombre) like LOWER(?1) or LOWER(a.apellido) like LOWER(?1)")
	List<Alumno> findByNombreOrApellidoLike(String term1);
	
	@Query("select a from Alumno a where a.habilitado = true and a.matriculado=false")
	List<Alumno> findAllByAlumnosHabilitadosySinMatricula();
	
}
