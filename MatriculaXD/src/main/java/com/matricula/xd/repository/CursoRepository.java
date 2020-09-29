package com.matricula.xd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.matricula.xd.entity.Curso;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long>  {
	
	@Query("select distinct(c.nombre) from Curso c where c.habilitado=true")
	List<String> fetchByDistintNombreAndIsHabilitado();
	
	@Query("select c from Curso c where c.nombre=?1 and c.docente.id=?2")
	Curso findByNombreAndDocenteId(String nombre, Long id);
	
	@Query("select distinct(cm.id) from Matricula m JOIN m.cursosMatriculados cm where m.semestre=?1")
	List<Long> fetchCursosIdsBySemestre(String semestre);
}
