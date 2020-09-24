package com.matricula.xd.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.matricula.xd.entity.Matricula;

@Repository
public interface MatriculaRepository extends CrudRepository<Matricula, Long>  {
	
	
}
