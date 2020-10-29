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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.matricula.xd.entity.Docente;
import com.matricula.xd.service.IDocenteService;

import lombok.extern.slf4j.Slf4j;

@Controller
@SessionAttributes("docente")
@RequestMapping("/docentes")
@Slf4j
public class DocenteController {

	@Autowired
	private IDocenteService docenteService;

	// LISTAR
	@GetMapping(value="/")
	public String listarDocentes(Model model) {
		
		log.info("listando docentes");
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
				return "redirect:/docentes/";
			} else {
				model.addAttribute("titulo", "Información de Docente");
				model.addAttribute("persona", docente.get());
				model.addAttribute("action", "/docentes/guardar");
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "persona/form";
	}

	// NUEVO 
	@GetMapping(value = "/nuevo")
	public String nuevoDocente(Model model) {

		Docente docente = new Docente();
		model.addAttribute("persona", docente);
		model.addAttribute("titulo", "Nuevo Docente");
		model.addAttribute("action", "/docentes/guardar");
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
			String mensajeFlash = (docente.getId() != null) ? "El docente se editó exitosamente" : "El docente se registró exitosamente";		
			log.info("docente a editar o guardar: " +docente.toString());
			docenteService.save(docente);
			status.setComplete();
			flash.addFlashAttribute("success", mensajeFlash);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "persona/form";
		}		
		return "redirect:/docentes/";
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

	
	
	
	
	
	
	
	
	
	
	@GetMapping("/select")
	public String getDocentesPorCurso(@RequestParam(name="curso") String curso, Model model) {
		
		log.info("pidiendo docentes de curso "+curso);
		model.addAttribute("docentes", docenteService.fetchDocentesByCursoHabilitado(curso));
		return "matricula/form :: #docenteSelect";
	}
	
	
	
	@GetMapping(value={"busqueda/","/busqueda/{term}"})
	public String getBusqueda(Model model, @PathVariable(required=false) String term) {
		
		if(term!=null)model.addAttribute("personas",docenteService.findByNombreOrApellidoLike(term) );
		else model.addAttribute("personas", docenteService.findAll());
		log.info("buscando docentes: " + term);
		return "persona/lista::listaPersonas";
	}
}
