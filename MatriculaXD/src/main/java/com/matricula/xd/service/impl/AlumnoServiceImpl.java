package com.matricula.xd.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matricula.xd.entity.Alumno;
import com.matricula.xd.repository.AlumnoRepository;
import com.matricula.xd.service.IAlumnoService;

@Service
public class AlumnoServiceImpl implements IAlumnoService{

	@Autowired
	private AlumnoRepository alumnoRepository;
	
	@Override
	public Alumno save(Alumno entity) {
		return alumnoRepository.save(entity);
	}

	@Override
	public List<Alumno> findAll() {
		return (List<Alumno>) alumnoRepository.findAll();
	}

	@Override
	public Optional<Alumno> findById(Long ID) {
		return alumnoRepository.findById(ID);
	}

	@Override
	public void deleteById(Long ID) {
		alumnoRepository.deleteById(ID);
	}

}
