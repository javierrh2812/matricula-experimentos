package com.matricula.xd.controller;

import java.util.ArrayList;
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
@Controller
@SessionAttributes("matricula")
@RequestMapping("/matriculas")
public class MatriculaController {

	@Autowired
	private IMatriculaService matriculaService;

	@Autowired
	private IAlumnoService alumnoService;

	@Autowired
	private ICursoService cursoService;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("titulo", "Módulo Matrícula");
		return "/matricula/modulo";
	}	

	@GetMapping("/lista")
	public String list(Model model) {
		model.addAttribute("titulo", "Alumnos Matriculados");
		return "/matricula/lista";
	}

	// VER INFO
	@GetMapping("/{id}/ver")
	public String detalle(@PathVariable(value = "id") Long id, Model model) {

		try {
			Optional<Matricula> matricula = matriculaService.findById(id);

			if (!matricula.isPresent()) {
				model.addAttribute("warning", "Matricula no existe");
				return "redirect:/matricula/";
			} else {
				model.addAttribute("titulo", "Información de Matricula");
				model.addAttribute("matricula", matricula.get());
				model.addAttribute("nombre", matricula.get().getAlumno().getNombreCompleto());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "/matricula/form";
	}

	// NUEVa matricula
	@GetMapping(value = "/nuevo")
	public String nuevo(Model model) {

		Matricula matricula = new Matricula();
		model.addAttribute("matricula", matricula);
		model.addAttribute("alumnos", alumnoService.findAllAlumnosHabilitadosySinMatricula());
		model.addAttribute("cursos", cursoService.findCursosHabilitados());
		model.addAttribute("titulo", "Nueva Matricula");
		return "matricula/form";
	}

	// GUARDAR FORM
	@PostMapping(value = "/guardar")
	public String saveAlumno(@Valid Matricula matricula, BindingResult result,
			@RequestParam(value = "cursos[]", required = false) String[] cursos, @RequestParam(value = "docentes[]", required = false) Long[] docentes,
			Model model, RedirectAttributes flash, SessionStatus status) {
		if (docentes == null) {
			flash.addFlashAttribute("warning", "Error en el registro. No eligió cursos ni docentes");
			return "redirect:/matriculas/";
		}
		try {
			if (result.hasErrors()) {
				model.addAttribute("titulo", (matricula.getId() != null) ? "Editar Matricula" : "Nueva Matricula");
				return "matricula/form";
			}
			String mensajeFlash = (matricula.getId() != null) ? "La matricula de editó exitosamente"
					: "La matricula se registró exitosamente";

			// smatricula.setFechaMatricula(new Date());
			List<Curso> cursosMatriculados = new ArrayList<Curso>();
			for (int i = 0; i < docentes.length; i++) {
				Curso curso = cursoService.fetchByNombreAndDocenteId(cursos[i], docentes[i]);
				Integer matriculados = cursoService.contarAlumnosMatriculadosPorCursoYSemestre(curso.getId(), "2020-2");
				log.info("Alumnos matriculados en el curso " + curso.getNombre() + "->" + matriculados);
				if (matriculados < 10)
					cursosMatriculados.add(curso);
				else {
					flash.addFlashAttribute("warning", "Error en el registro. El curso tiene 10 alumnos como máximo ");
					return "redirect:/matriculas/";
				}
			}
			matricula.setCursosMatriculados(cursosMatriculados);
			matricula.getAlumno().setMatriculado(true);
			matriculaService.save(matricula);

			log.info(matricula.getAlumno().toString());
			status.setComplete();
			flash.addFlashAttribute("success", mensajeFlash);

		} catch (Exception e) {
			model.addAttribute("info", e.getMessage());
			return "matricula/form";
		}
		return "redirect:/matriculas/";
	}

	// ELIMINAR
	@GetMapping("/{id}/eliminar")
	public String delete(Model model, @PathVariable(name = "id") Long id) {
		try {
			Optional<Matricula> matricula = matriculaService.findById(id);
			if (matricula.isPresent()) {
				matriculaService.deleteById(id);
			}

		} catch (Exception e) {
			model.addAttribute("error", "Error: " + e.getMessage());
		}
		return "redirect:/matriculas/";
	}

}
