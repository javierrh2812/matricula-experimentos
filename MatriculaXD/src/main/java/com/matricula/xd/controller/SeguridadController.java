package com.matricula.xd.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.matricula.xd.entity.Usuario;
import com.matricula.xd.service.UsuarioService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@SessionAttributes("usuario")
public class SeguridadController {

	@Autowired
	private UsuarioService usuarioService;

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();

	}

	// INFO DE CUENTA
	@GetMapping("/micuenta")
	public String detalle(Model model, @AuthenticationPrincipal User user, RedirectAttributes flash) {

		try {
			if (user == null) {
				flash.addFlashAttribute("warning", "Usuario no autenticado");
				return "redirect:/";
			} else {
				model.addAttribute("titulo", "Información de Usuario");
				model.addAttribute("usuario", usuarioService.encontrarUsuarioPorUsername(user.getUsername()));
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "usuario/form";
	}

	// VER
	@GetMapping("/usuarios/ver/{id}")
	public String detalle(Model model, @PathVariable(name = "id") Long id) {
		model.addAttribute("usuario", usuarioService.encontrarUsuarioPorId(id).get());
		model.addAttribute("titulo", "Información de Usuario");
		model.addAttribute("roles", usuarioService.getAllRoles());
		return "usuario/form";
	}

	@GetMapping("/usuarios")
	public String listaDeUsuarios(Model model) {
		model.addAttribute("usuarios", usuarioService.listaDeUsuarios());
		return "usuario/lista";
	}

	// NUEVO
	@GetMapping("/usuarios/nuevo")
	public String newEntity(Model model) {

		Usuario nuevo = new Usuario();
		model.addAttribute("usuario", nuevo);
		model.addAttribute("titulo", "Nuevo Usuario");
		model.addAttribute("roles", usuarioService.getAllRoles());
		return "usuario/form";
	}

	// ELIMINAR
	@GetMapping("/usuarios/eliminar/{id}")
	public String deleteUser(Model model, @PathVariable(name = "id") Long id) {
		usuarioService.eliminarUsuarioPorId(id);
		return "redirect:/";
	}

	// GUARDAR
	@PostMapping("/usuarios/guardar")
	public String saveAlumno(@Valid Usuario usuario, BindingResult result, Model model, 
			RedirectAttributes flash, SessionStatus status,
			@RequestParam(required = false, name = "pwd")String password) {


		String mensajeFlash = usuario.getId() == null ? "El usuario se registró existosamente"
					: "El usuario se modificó exitosamente";
		
		if (!(password==null || password=="")) {
			log.info("contraseña no vacía: "+password);
			usuario.setPassword(encoder().encode(password));
		}
		else {
			log.info("contraseña vacía");
		}
		
		log.info("contraseña nueva: "+usuario.getPassword());
		usuario.setRole(usuarioService.getRolById((long) 2));
		log.info("intentando guardar usuario:" + usuario.toString());
		usuarioService.guardarUsuario(usuario);
		
		flash.addFlashAttribute("success", mensajeFlash);
		status.setComplete();

		return "redirect:/usuarios/";
	}

}
