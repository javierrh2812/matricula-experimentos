package com.matricula.xd.controller;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.matricula.xd.entity.Curso;
import com.matricula.xd.entity.Docente;
import com.matricula.xd.service.ICursoService;
import com.matricula.xd.service.IDocenteService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@SessionAttributes("curso")
@RequestMapping("/cursos")
public class CursoController {

	@Autowired
	private ICursoService cursoService;

	@Autowired
	private IDocenteService docenteService;

	// LISTAR
	@GetMapping(value = "/")
	public String listar(Model model) {
		List<Curso> cursos = cursoService.findAll();
		model.addAttribute("cursos", cursos);
		model.addAttribute("titulo", "Lista de cursos");
		return "curso/lista";
	}

	// VER INFO
	@GetMapping("/{id}/ver")
	public String info(@PathVariable(value = "id") Long id, Model model) {

		try {
			Optional<Curso> curso = cursoService.findById(id);

			if (!curso.isPresent()) {	
				model.addAttribute("info", "Curso no existe");
				return "redirect:/cursos/";
			} else {
				model.addAttribute("titulo", "Información de Curso");
				model.addAttribute("curso", curso.get());
				model.addAttribute("docentes", docenteService.findAllByDocenteHabilitado());
				model.addAttribute("action", "/cursos/guardar");
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "/curso/form";
	}

	// NUEVO
	@GetMapping(value = "/nuevo")
	public String nuevo(Model model, RedirectAttributes flash) {
		List<Docente> docenteshabilitados = docenteService.findAllByDocenteHabilitado();
		if(!docenteshabilitados.isEmpty()) {
		Curso curso = new Curso();
		model.addAttribute("curso", curso);
		model.addAttribute("titulo", "Nuevo Curso");
		model.addAttribute("action", "/cursos/guardar");
		model.addAttribute("docentes", docenteshabilitados);
		
		return "curso/form";
		}
		else {
			flash.addFlashAttribute("warning", "No existen docentes, o docentes habilitados para crear un nuevo curso");
			return "redirect:/cursos/";
		}
		
		}

	// GUARDAR
	@PostMapping(value = "/guardar")
	public String save(@Valid Curso curso, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		try {
			if (result.hasErrors()) {
				return "curso/form";
			}
			String mensajeFlash = (curso.getId() != null) ? "El curso se editó exitosamente " : "El Curso se registró exitosamente";
			cursoService.save(curso);
			status.setComplete();
			flash.addFlashAttribute("success", mensajeFlash);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "curso/form";
		}
		return "redirect:/cursos/";
	}

	// ELIMINAR
	@GetMapping("/{id}/eliminar")
	public String delete(Model model, @PathVariable(name = "id") Long id) {
		try {
			Optional<Curso> curso = cursoService.findById(id);
			if (curso.isPresent()) {
				curso.get().setHabilitado(false);
			}

		} catch (Exception e) {
			model.addAttribute("error", "Ha ocurrido un error");
		}
		return "redirect:/cursos/";
	}


	// FRAGMENTO

	@GetMapping(value = { "/busqueda", "/busqueda/{term}" })
	public String busquedaCursos(Model model, @PathVariable(required = false) String term) {
		log.info("buscando cursos por termino: " + term);
		if(term!=null)model.addAttribute("cursos",cursoService.findByNombreLike(term));
		else model.addAttribute("cursos", cursoService.findAll());
		return "curso/lista::listaCursos";
	}






}
