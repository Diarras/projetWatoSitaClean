package com.example.association.controllers;

import com.example.association.models.Don;
import com.example.association.repositories.DonRepository;
import com.example.association.services.EmailService;
import com.example.association.utils.PdfGenerator;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.File;
import java.io.FileInputStream;

@Controller
@RequestMapping("/admin/dons")
public class AdminDonController {

    @Autowired
    private DonRepository donRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public String listDons(@RequestParam(required = false) String query,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "nom") String sortBy,
                           @RequestParam(defaultValue = "asc") String direction,
                           Model model) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, 10, sort);

        Page<Don> dons;
        if (query != null && !query.isEmpty()) {
            dons = donRepository.findByNomContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query, pageable);
        } else {
            dons = donRepository.findAll(pageable);
        }

        model.addAttribute("dons", dons);
        model.addAttribute("query", query);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        return "admin_dons";
    }

    @GetMapping("/pdf/{id}")
    public ResponseEntity<InputStreamResource> downloadPdf(@PathVariable Long id) throws Exception {
        Don don = donRepository.findById(id).orElse(null);
        if (don == null) return ResponseEntity.notFound().build();

        File pdf = PdfGenerator.generateRecuPdf(don);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(pdf));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=recu_don_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdf.length())
                .body(resource);
    }

    @PostMapping("/renvoyer/{id}")
    public String renvoyerRecu(@PathVariable Long id) throws Exception {
        Don don = donRepository.findById(id).orElse(null);
        if (don != null) {
            File pdf = PdfGenerator.generateRecuPdf(don);
            emailService.envoyerRecuDon(don, pdf);
        }
        return "redirect:/admin/dons";
    }
}
