package com.matricula.xd.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matricula.xd.entity.Curso;
import com.matricula.xd.repository.CursoRepository;
import com.matricula.xd.service.ICursoService;

@Service
public class CursoServiceImpl implements ICursoService{

	@Autowired
	private CursoRepository cursoRepository;

	@Override
	public Curso save(Curso entity) {
		// TODO Auto-generated method stub
		return cursoRepository.save(entity);
	}

	@Override
	public List<Curso> findAll() {
		// TODO Auto-generated method stub
		return (List<Curso>) cursoRepository.findAll();
	}

	@Override
	public Optional<Curso> findById(Long ID) {
		// TODO Auto-generated method stub
		return cursoRepository.findById(ID);
	}

	@Override
	public void deleteById(Long ID) {
		cursoRepository.deleteById(ID);
	}

	

}
