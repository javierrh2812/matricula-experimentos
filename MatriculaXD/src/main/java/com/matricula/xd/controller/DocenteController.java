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

import com.matricula.xd.entity.Docente;
import com.matricula.xd.service.IDocenteService;

@Controller
@SessionAttributes("docente")
@RequestMapping("/docentes")
public class DocenteController {

	@Autowired
	private IDocenteService docenteService;

	// LISTAR
	@GetMapping
	public String listarDocentes(Model model) {

		List<Docente> docentes = docenteService.findAll();
		model.addAttribute("personas", docentes);
		model.addAttribute("esModuloAlumnos", false);
		model.addAttribute("titulo", "Lista de docentes");
		return "persona/lista";
	}

	// VER INFO
	@GetMapping("/{id}/ver")
	public String infoDocente(@PathVariable(value = "id") Long id, Model model) {

		try {
			Optional<Docente> docente = docenteService.findById(id);

			if (!docente.isPresent()) {
				model.addAttribute("info", "Docente no existe");
				return "redirect:/docentes";
			} else {
				model.addAttribute("titulo", "Información de Docente");
				model.addAttribute("persona", docente.get());
				model.addAttribute("action", "/docente/guardar");
				model.addAttribute("method", "post");

			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "/persona/form";
	}

	// NUEVO 
	@GetMapping(value = "/nuevo")
	public String nuevoDocente(Model model) {

		Docente docente = new Docente();
		model.addAttribute("persona", docente);
		model.addAttribute("titulo", "Nuevo Docente");
		model.addAttribute("action", "/docentes/guardar");
		model.addAttribute("method", "POST");
		return "persona/form";
	}

	
	// GUARDAR 
	@PostMapping(value = "/guardar")
	public String saveDocente(@Valid Docente docente, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		try {
			if (result.hasErrors()) {
				return "persona/form";
			}

			String mensajeFlash = (docente.getId() != null) ? "Docente editado" : "Docente Registrado";

			docenteService.save(docente);
			status.setComplete();
			flash.addFlashAttribute("success", mensajeFlash);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "persona/form";
 
		}
		return "redirect:/docentes";
	}

	
	//ELIMINAR
	@GetMapping("/{id}/eliminar")
	public String deleteUser(Model model, @PathVariable(name = "id") Long id) {
		try {
			Optional<Docente> docente = docenteService.findById(id);
			if (docente.isPresent()) {
				docente.get().setHabilitado(false);
			}

		} catch (Exception e) {
			model.addAttribute("error", "Ha ocurrido un error");
		}
		return "redirect:/docentes";
	}

}
