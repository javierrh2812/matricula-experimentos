package com.matricula.xd.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matricula.xd.entity.Docente;
import com.matricula.xd.repository.DocenteRepository;
import com.matricula.xd.service.IDocenteService;

@Service
public class DocenteServiceImpl implements IDocenteService{

	@Autowired
	private DocenteRepository docenteRepository;

	@Override
	public Docente save(Docente entity) {
		return docenteRepository.save(entity);
		
	}

	@Override
	public List<Docente> findAll() {
		return (List<Docente>) docenteRepository.findAll();
	}

	@Override
	public Optional<Docente> findById(Long ID) {
		return docenteRepository.findById(ID);
	}

	@Override
	public void deleteById(Long ID) {
		docenteRepository.deleteById(ID);
	}

	@Override
	public List<Docente> fetchDocentesByCursoHabilitado(String curso) {
		return docenteRepository.fetchDocentesByCursoAndIsHabilitado(curso);
	}

	@Override
	public Boolean existeDocenteConDni(String dni) {
		if (docenteRepository.findByDni(dni).isEmpty()) return false;
		else return true;
	}

	@Override
	public List<Docente> findByNombreOrApellidoLike(String term) {
		String newTerm = '%'+term+'%';
		return docenteRepository.findByNombreOrApellidoLike(newTerm);
	}

	@Override
	public List<Docente> findAllByDocenteHabilitado() {
		return docenteRepository.findByHabilitado(true);
	}


}
