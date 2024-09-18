package com.devcaotics.infamus.controllers;

import java.sql.SQLException;
import java.util.List;

import com.devcaotics.infamus.model.entities.Relato;
import com.fasterxml.jackson.databind.DatabindContext;
import jakarta.servlet.http.HttpSession;
import com.devcaotics.infamus.model.entities.Professor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devcaotics.infamus.model.entities.Estudante;
import com.devcaotics.infamus.model.repository.RepositoryFacade;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class MainController {

	@RequestMapping({"/*","/estudante/*"})
	public String inicial(Model m, HttpSession session) {
		List<Estudante> estudantes = RepositoryFacade.getInstance().readAllEstudantes();
		m.addAttribute("estudantes", estudantes);

		Professor professor = (Professor) session.getAttribute("professor");
		m.addAttribute("professor", professor);

		return "index";
	}


}
