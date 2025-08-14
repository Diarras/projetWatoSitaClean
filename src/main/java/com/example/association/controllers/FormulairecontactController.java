package com.example.association.controllers;

import com.example.association.models.ContactMessage;
import com.example.association.repositories.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;


@Controller
public class FormulairecontactController {


    @Autowired
    private ContactMessageRepository contactMessageRepository;


    @GetMapping("/contact/formulaire")
    public String showForm(Model model) {
        model.addAttribute("contactMessage", new ContactMessage());
        return "formulaire";
    }


//    @PostMapping("/contact/formulaire")
//    public String submitForm(@Valid @ModelAttribute ContactMessage contactMessage, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            return "formulaire";
//        }
//        model.addAttribute("confirmation", "Merci " + contactMessage.getNom() + ", votre message a été envoyé !");
//        return "formulaire";
//    }


    @PostMapping("/contact/formulaire")
    public String submitForm(@Valid @ModelAttribute ContactMessage contactMessage, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "formulaire";
        }
        contactMessage.setDate(LocalDateTime.now());

        contactMessageRepository.save(contactMessage); // ✅ Enregistrement en base

        model.addAttribute("confirmation", "Merci " + contactMessage.getNom() + ", votre message a été envoyé !");
        return "accueil";
    }


}
