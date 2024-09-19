package com.devcaotics.infamus.controllers;

import com.devcaotics.infamus.model.entities.Professor;
import com.devcaotics.infamus.model.entities.Relato;
import com.devcaotics.infamus.model.repository.RepositoryFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    @GetMapping("/cadastro")
    public String exibirPaginaCadastro(){
        return "cadastro";
    }

    @GetMapping("/relatos")
    public String relato(Model m, HttpSession session, RedirectAttributes redirectAttributes) throws SQLException {
        // Verifica se o professor está logado
        Professor professor = (Professor) session.getAttribute("professor");
        if (professor == null) {
            redirectAttributes.addFlashAttribute("erroRelato", "Você precisa estar logado para visualizar os relatos.");
            return "redirect:/login";
        } else {
            System.out.println("Professor logado: " + professor.getNome() + " codigo: " + professor.getCodigo());
        }

        try {
            List<Relato> relatos = RepositoryFacade.getInstance().filterByCodigoProfessor(professor.getCodigo());
            m.addAttribute("relatos", relatos);
            System.out.println(relatos);
        } catch (SQLException e) {
            m.addAttribute("erro", "Erro ao carregar os relatos.");
            return "relatosProfessor";
        }

        return "relatosProfessor";
    }

    @PostMapping("/cadastro")
    public String cadastrarProfessor (Model m, HttpServletRequest request, Professor p, HttpSession session) {

        try {
            RepositoryFacade.getInstance().createProfessor(p);
            session.setAttribute("msg", "Professor Cadastrado com Sucesso.");

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            session.setAttribute("msg", "Erro ao cadastrar o professor.");
        }

        return "redirect:/login";
    }

}
