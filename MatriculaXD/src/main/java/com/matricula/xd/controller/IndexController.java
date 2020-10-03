package com.matricula.xd.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
public class IndexController {


	@GetMapping()
	public String index(Model model, @AuthenticationPrincipal User user) {
		log.info("usuario ha iniciado sesión: "+user);
		model.addAttribute("titulo", "MatrículaXD");
		return "index";
	}

}
