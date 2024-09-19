package com.devcaotics.infamus.controllers;

import com.devcaotics.infamus.model.entities.Professor;
import com.devcaotics.infamus.model.repository.RepositoryFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    @GetMapping("/cadastro")
    public String showCadastroPage(){
        return "cadastro";
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
