package com.example.association.controllers;

import com.example.association.models.Admin;
import com.example.association.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/inscription-admin")
    public String showForm(Model model) {
        model.addAttribute("admin", new Admin());
        return "inscription-admin";
    }

    @GetMapping("/admin/liste")
    public String listeAdmins(Model model) {
        model.addAttribute("admins", adminRepository.findAll());
        return "liste-admins";
    }



    @PostMapping("/inscription-admin")
    public String registerAdmin(@ModelAttribute Admin admin, Model model) {
        if (adminRepository.findByUsername(admin.getUsername()).isPresent()) {
            model.addAttribute("error", "Ce nom d'utilisateur existe déjà.");
            return "inscription-admin";
        }

        System.out.println("Tentative d'inscription : " + admin.getUsername() + ", " + admin.getEmail());

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRole("ROLE_ADMIN");

        System.out.println("Nouvel admin : " + admin.getUsername() + ", " + admin.getEmail());

        adminRepository.save(admin);

        return "redirect:/login";
    }

}
