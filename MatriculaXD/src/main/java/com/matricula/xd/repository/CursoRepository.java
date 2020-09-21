package com.matricula.xd.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.matricula.xd.entity.Curso;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long>  {
}
