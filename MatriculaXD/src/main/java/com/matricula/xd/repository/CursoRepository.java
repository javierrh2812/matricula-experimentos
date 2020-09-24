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
	
	
}
