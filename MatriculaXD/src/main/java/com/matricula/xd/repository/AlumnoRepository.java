package com.matricula.xd.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.matricula.xd.entity.Alumno;

@Repository
public interface AlumnoRepository extends CrudRepository<Alumno, Long>  {
	

}
