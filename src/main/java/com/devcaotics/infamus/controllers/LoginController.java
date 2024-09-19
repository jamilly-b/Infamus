package com.devcaotics.infamus.controllers;

import com.devcaotics.infamus.model.entities.Professor;
import com.devcaotics.infamus.model.repository.RepositoryFacade;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("senha") String senha,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) throws SQLException {
        // Buscar o professor no banco de dados usando o email
        Professor professor = RepositoryFacade.getInstance().readProfessorEmail(email);

        // Verificar se o professor existe e se a senha está correta
        if (professor != null && professor.getSenha().equals(senha)) {
            // Armazenar o professor na sessão
            session.setAttribute("professor", professor);

            // Redirecionar para a página inicial
            return "redirect:/";
        } else {
            // Se o login falhar, adicionar uma mensagem de erro e redirecionar de volta ao login
            redirectAttributes.addFlashAttribute("error", "Email ou senha inválidos");
            return "redirect:/login";
        }
    }


    // Logout para encerrar a sessão
        @GetMapping("/logout")
        public String logout(HttpSession session) {
            session.invalidate();  // Invalida a sessão
            return "redirect:/login";  // Redireciona para a página de login
        }

}
