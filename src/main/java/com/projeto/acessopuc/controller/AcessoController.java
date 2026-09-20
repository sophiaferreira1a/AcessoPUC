package com.projeto.acessopuc.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;


@Controller
public class AcessoController {

    //mostram as páginas

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        model.addAttribute("usuario", authentication.getName());
        return "home";
    }

    @GetMapping("/admin")
    public String admin(Model model, Authentication authentication) {
        model.addAttribute("usuario", authentication.getName());
        return "admin";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    //mostram formulários
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/recoverpassword")
    public String recoverpassword() {
        return "recoverpassword";
    }

    @GetMapping("/resetpassword")
    public String resetPassword(@RequestParam("token") String token, Model model) {
    model.addAttribute("token", token);   // entrega pro formulário reenviar escondido
    return "resetpassword";
    }
    
    //processam formulários
    @PostMapping("/register")
    public String handleRegister(@RequestParam("email") String email, @RequestParam("senha") String senha) {
        //caso existam regras de negócio, ficam aqui
        return "redirect:/login?cadastro=sucesso";   // devolve o navegador pro login
    }

    @PostMapping("/recoverpassword")
    public String handleRecover(@RequestParam("email") String email) {
        return "redirect:/recoverpassword?sucesso=email";
    }

    @PostMapping("/resetpassword")
    public String handleReset(@RequestParam("token") String token, @RequestParam("senha") String senha, @RequestParam("confirmarSenha") String confirmarSenha) {
        return "redirect:/login?senha=alterada";
    }
    
    
}
