package com.exposicion.loginmvc.controller;

import com.exposicion.loginmvc.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {
// COMENTARIO DE PRUEBA

    @Autowired
    private AuthService authService;

    @GetMapping({"/", "/login"})
    public String showLoginForm(Model model) {
        return "login"; // Vista login.html
    }

    @PostMapping("/login")
    public String processLogin(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        // Usamos el Servicio para autenticar
        if (authService.authenticate(username, password)) {
            // Éxito: Redirecciona a la página de bienvenida
            return "redirect:/home";
        } else {
            // Fallo: Vuelve al login con mensaje de error
            model.addAttribute("error", "Credenciales incorrectas. Verifique usuario y contraseña.");
            return "login";
        }
    }
    
        @PostMapping("/franco")
    public String frfanco(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        // Usamos el Servicio para autenticar
        if (authService.authenticate(username, password)) {
            // Éxito: Redirecciona a la página de bienvenida
            return "redirect:/home";
        } else {
            // Fallo: Vuelve al login con mensaje de error
            model.addAttribute("error", "Credenciales incorrectas. Verifique usuario y contraseña.");
            return "login";
        }
    }

    // --- Petición GET: Página de Bienvenida (Home) ---
    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }
}

// EL FRANCO SE DURMIO
