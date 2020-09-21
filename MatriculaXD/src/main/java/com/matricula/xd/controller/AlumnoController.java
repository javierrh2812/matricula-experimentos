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

import com.matricula.xd.entity.Alumno;
import com.matricula.xd.service.IAlumnoService;

@Controller
@SessionAttributes("alumno")
@RequestMapping("/alumnos")
public class AlumnoController {

	@Autowired
	private IAlumnoService alumnoService;

	// LISTAR
	@GetMapping
	public String listAlumnos(Model model) {

		List<Alumno> alumnos = alumnoService.findAll();
		model.addAttribute("esModuloAlumnos", true);
		model.addAttribute("personas", alumnos);
		model.addAttribute("titulo", "Lista de alumnos");
		return "persona/lista";
	}

	// VER INFO
	@GetMapping("/{id}/ver")
	public String detailsPAlumno(@PathVariable(value = "id") Long id, Model model) {

		try {
			Optional<Alumno> alumno = alumnoService.findById(id);

			if (!alumno.isPresent()) {
				model.addAttribute("info", "Alumno no existe");
				return "redirect:/alumnos";
			} else {
				model.addAttribute("titulo", "Información de Alumno");
				model.addAttribute("persona", alumno.get());
				model.addAttribute("action", "/alumnos/guardar");
				model.addAttribute("method", "post");

			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "/persona/form";
	}

	// NUEVO ALUMNO
	@GetMapping(value = "/nuevo")
	public String newAlumno(Model model) {

		Alumno alumno = new Alumno();
		model.addAttribute("persona", alumno);
		model.addAttribute("titulo", "Nuevo Alumno");
		model.addAttribute("action", "/alumnos/guardar");
		model.addAttribute("method", "POST");
		return "persona/form";
	}

	
	// GUARDAR FORM
	@PostMapping(value = "/guardar")
	public String saveAlumno(@Valid Alumno alumno, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		try {
			if (result.hasErrors()) {
				model.addAttribute("titulo", "Editar Alumno");
				return "persona/form";
			}

			String mensajeFlash = (alumno.getId() != null) ? "Alumno editado" : "Alumno Registrado";

			alumnoService.save(alumno);
			status.setComplete();
			flash.addFlashAttribute("success", mensajeFlash);
		} catch (Exception e) {
			model.addAttribute("info", "El Alumno ya esta Registrado");
			return "persona/form";
 
		}
		return "redirect:/alumnos";
	}

	
	//ELIMINAR
	@GetMapping("/{id}/eliminar")
	public String deleteUser(Model model, @PathVariable(name = "id") Long id) {
		try {
			Optional<Alumno> alumno = alumnoService.findById(id);
			if (alumno.isPresent()) {
				alumno.get().setHabilitado(false);
			}

		} catch (Exception e) {
			model.addAttribute("error", "Ha ocurrido un error");
		}
		return "redirect:/alumnos";
	}

}
