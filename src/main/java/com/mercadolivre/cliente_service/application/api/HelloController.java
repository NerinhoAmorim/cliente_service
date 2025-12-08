package com.mercadolivre.cliente_service.application.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    public String helloPage(Model model) {
        model.addAttribute("title", "Alô Mundo");
        return "hello"; // renderiza templates/hello.html
    }
}
