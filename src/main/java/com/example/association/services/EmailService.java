package com.example.association.services;

import com.example.association.models.Don;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void envoyerRecuDon(Don don , File pdfFile) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(don.getEmail());
        helper.setSubject("Votre reçu de don");
        helper.setText("Bonjour " + don.getNom() + ",\n\nMerci pour votre don de " + don.getMontant() +
                " EUR pour notre cause \"" + don.getTypeDon() + "\".\nVeuillez trouver ci-joint votre reçu.\n\nBien cordialement,\nL'équipe de l'association");

        FileSystemResource file = new FileSystemResource(pdfFile);
        helper.addAttachment("recu_don.pdf", file);

        mailSender.send(message);
    }
}
