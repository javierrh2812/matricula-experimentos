package com.matricula.xd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.matricula.xd.entity.Docente;

@Repository
public interface DocenteRepository extends CrudRepository<Docente, Long>  {
	@Query("select d from Docente d join Curso c on d.id = c.docente.id where c.nombre=?1")
	List<Docente> fetchDocentesByCursoAndIsHabilitado(String curso);
}
