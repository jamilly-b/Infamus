package com.devcaotics.infamus.controllers;

import java.util.List;

import jakarta.servlet.http.HttpSession;
import com.devcaotics.infamus.model.entities.Professor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devcaotics.infamus.model.entities.Estudante;
import com.devcaotics.infamus.model.repository.RepositoryFacade;


@Controller
public class MainController {

	@RequestMapping({"/*","/estudante/*","/relatos/*"})
	public String inicial(Model m,  HttpSession session) {
		// Verifica se o professor está logado
		Professor professor = (Professor) session.getAttribute("professor");
		if (professor == null) {
			return "redirect:/login";
		}

		List<Estudante> estudantes = RepositoryFacade.getInstance().readAllEstudantes();
		m.addAttribute("estudantes", estudantes);

		return "index";

	}

}
