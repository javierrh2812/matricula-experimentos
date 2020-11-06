package com.matricula.xd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.matricula.xd.entity.Docente;
import com.matricula.xd.service.ICursoService;
import com.matricula.xd.service.IDocenteService;
import com.matricula.xd.service.IAlumnoService;
import com.matricula.xd.service.ICursoService;
import com.matricula.xd.service.UsuarioService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api")
public class RESTController {
	
	@Autowired
	private ICursoService cursoService;
	@Autowired
	private IDocenteService docenteService;
	@Autowired
	private IAlumnoService alumnoService;
	@Autowired
	private UsuarioService usuarioService;



	@GetMapping("cursos")
	public List<Long> idCursos(@RequestParam(value="semestre")String semestre){
		log.info("Servicio REST - getIdCursos");
		return cursoService.fetchCursosIdBySemestre(semestre);
	}

	@GetMapping("/docentes")
	public ArrayList<Entry<Long,String>> docentesDeCurso(@RequestParam(value="curso")String curso){
		log.info("Servicio REST - getDocentesDeCurso:"+curso);
		HashMap <Long, String> docentes = new HashMap<Long, String>();
		List<Docente> docenteList = docenteService.fetchDocentesByCursoHabilitado(curso); 
		for(Docente d : docenteList) {
			docentes.put(d.getId(), d.getNombreCompleto());
		}
		return new ArrayList<Entry<Long,String>>(docentes.entrySet());
		
	}

	@GetMapping("/existeDni")
	public Boolean existeDocenteOAlumnoConDni(@RequestParam(value="dni")String dni){
		return (docenteService.existeDocenteConDni(dni) || alumnoService.existeAlumnoConDni(dni));
	}
	@GetMapping("/existeCodigo")
	public Boolean existeCodigoDeCurso(@RequestParam(value="codigo")String codigo){
		return (cursoService.existeCursoPorCodigo(codigo));
	}
	@GetMapping("/existeUsuario")
	public Boolean existeNombreDeUsuario(@RequestParam(value="usuario")String usuario){
		return (usuarioService.existeUsuarioPorUsername(usuario));
	}

}
