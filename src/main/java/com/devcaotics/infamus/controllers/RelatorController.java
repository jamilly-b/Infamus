package com.devcaotics.infamus.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.devcaotics.infamus.model.entities.Estudante;
import com.devcaotics.infamus.model.entities.Relato;
import com.devcaotics.infamus.model.repository.RepositoryFacade;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("/relatos")
public class RelatorController {
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/{codigoEstudante}")
	public String viewRelatos(Model m,@PathVariable("codigoEstudante") int codigo) {
		
		try {
			Estudante e = RepositoryFacade.getInstance().readEstudante(codigo);
			
			List<Relato> relatos = RepositoryFacade.getInstance().filterRelatoByCodigoEstudante(codigo);
		
			if(e != null) {
				m.addAttribute("relatos", relatos);
				m.addAttribute("estudante", e);
				m.addAttribute("relato", new Relato());
			}
			
			return "relatos";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			session.setAttribute("msg", "O estudante com o codigo "+codigo+" "
					+ "Não está cadastrado!");
			
			return "relatos";
			
		}
		
		
	}
	
	@PostMapping("/save")
	public String saveRelato(Model m, Relato r,@RequestParam("codigoEstudante") int codigo) {
		
		Estudante e;
		try {
			e = RepositoryFacade.getInstance().readEstudante(codigo);
			
			r.setEstudante(e);
			
			RepositoryFacade.getInstance().createRelato(r);
			session.setAttribute("msg", "Relato Cadastrado com sucesso!");
			
			return viewRelatos(m, codigo);
			
		} catch (SQLException exp) {
			// TODO Auto-generated catch block
			session.setAttribute("msg", "erro ao cadastrar o relato!");
			
			return viewRelatos(m, codigo);
		}
		
	}
	
	@PostMapping("/save2")
	public String saveRelato2(Model m, Relato r) {
		
		Estudante e;
		try {
			
			
			RepositoryFacade.getInstance().createRelato(r);
			session.setAttribute("msg", "Relato Cadastrado com sucesso!");
			
			return viewRelatos(m, r.getEstudante().getCodigo());
			
		} catch (SQLException exp) {
			// TODO Auto-generated catch block
			session.setAttribute("msg", "erro ao cadastrar o relato!");
			
			return viewRelatos(m, r.getEstudante().getCodigo());
		}
		
	}

}
