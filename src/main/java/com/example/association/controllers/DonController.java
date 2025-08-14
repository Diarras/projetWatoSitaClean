
package com.example.association.controllers;

import com.example.association.models.Don;
import com.example.association.repositories.DonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class DonController {

    @Autowired
    private DonRepository donRepository;

    @PostMapping("/traitement-don")
    public String traiterDon(@Valid @ModelAttribute Don don, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", "Erreur dans le formulaire.");
            return "formulaire-don";
        }

        don.setDateDon(LocalDateTime.now());
        Don savedDon = donRepository.save(don);

        switch (savedDon.getPaiement()) {
            case "stripe":
                return "redirect:/paiement-stripe?montant=" + savedDon.getMontant() + "&donId=" + savedDon.getId();
            case "paypal":
                return "redirect:/paiement-paypal?montant=" + savedDon.getMontant();
            default:
                model.addAttribute("message", "Mode de paiement non pris en charge.");
                return "confirmation-don";
        }
    }
}
