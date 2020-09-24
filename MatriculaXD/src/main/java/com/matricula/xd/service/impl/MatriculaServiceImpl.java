package com.matricula.xd.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matricula.xd.entity.Matricula;
import com.matricula.xd.repository.MatriculaRepository;
import com.matricula.xd.service.IMatriculaService;

@Service
public class MatriculaServiceImpl implements IMatriculaService{

	@Autowired
	private MatriculaRepository matriculaRepository;
	
	@Override
	public Matricula save(Matricula entity) {
		return matriculaRepository.save(entity);
	}

	@Override
	public List<Matricula> findAll() {
		return (List<Matricula>) matriculaRepository.findAll();
	}

	@Override
	public Optional<Matricula> findById(Long ID) {
		return matriculaRepository.findById(ID);
	}

	@Override
	public void deleteById(Long ID) {
		findById(ID).get().getAlumno().setMatriculado(false);
		matriculaRepository.deleteById(ID);
	}


}
