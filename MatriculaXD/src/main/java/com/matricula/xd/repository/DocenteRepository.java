package com.matricula.xd.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.matricula.xd.entity.Docente;

@Repository
public interface DocenteRepository extends CrudRepository<Docente, Long>  {
}
