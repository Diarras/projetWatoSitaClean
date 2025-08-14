
package com.example.association.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "pageAccueil1";
    }

    @GetMapping("/accueil")
    public String accueil() {
        return "accueil";
    }

    @GetMapping("/a-propos")
    public String aPropos() {
        return "a_propos";
    }

    @GetMapping("/evenements")
    public String evenements() {
        return "evenements";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/ressources_et_documentations")
    public String ressources_et_documentations() {
        return "ressources_et_documentations";
    }

    @GetMapping("/nos-actions/education")
    public String education() {
        return "education";
    }

    @GetMapping("/don")
    public String don() {
        return "don";
    }

    @GetMapping("/formulaire-don")
    public String formulaireDon() {
        return "formulaire-don";
    }

    @GetMapping("/nos-actions/egalite")
    public String egalite() {
        return "egalite";
    }

    @GetMapping("/nos-actions/sante")
    public String sante() {
        return "sante";
    }

    @GetMapping("/actualites/interviews")
    public String interviews() {
        return "interviews";
    }

    @GetMapping("/actualites/portraits")
    public String portraits() {
        return "portraits";
    }

    @GetMapping("/actualites/bilans")
    public String bilans() {
        return "bilans";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Nom d'utilisateur ou mot de passe incorrect.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "Déconnexion réussie.");
        }
        return "login";
    }

}
