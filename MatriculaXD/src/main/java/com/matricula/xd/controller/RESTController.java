package com.matricula.xd.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.matricula.xd.entity.Curso;
import com.matricula.xd.entity.Matricula;
import com.matricula.xd.service.IAlumnoService;
import com.matricula.xd.service.ICursoService;
import com.matricula.xd.service.IMatriculaService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api")
public class RESTController {

	@Autowired
	private IMatriculaService matriculaService;
	
	@Autowired
	private IAlumnoService alumnoService;
	
	@Autowired
	private ICursoService cursoService;


	@GetMapping("cursos")
	public List<Long> idsCursos(@RequestParam(value="semestre")String semestre){
		return cursoService.fetchCursosIdBySemestre(semestre);
	}

}
