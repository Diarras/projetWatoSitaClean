
package com.example.association.controllers;

import com.example.association.models.Don;
import com.example.association.repositories.DonRepository;
import com.example.association.services.EmailService;
import com.example.association.utils.PdfGenerator;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.logging.Logger;

@RestController
public class StripeWebhookController {

    private static final Logger logger = Logger.getLogger(StripeWebhookController.class.getName());

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @Autowired
    private DonRepository donRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PdfGenerator pdfGenerator; // si tu as une classe dédiée

    @PostMapping("/stripe-webhook")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
                                                      @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().body("Invalid signature");
        }

        if ("checkout.session.completed".equals(event.getType())) {
            com.stripe.model.checkout.Session session = (com.stripe.model.checkout.Session)
                    event.getDataObjectDeserializer().getObject().orElse(null);

            if (session != null) {
                String sessionId = session.getId();
                Don don = donRepository.findByStripeSessionId(sessionId);
                if (don != null) {
                    don.setStatut("PAYÉ");
                    donRepository.save(don);

                    try {
                        File pdf = pdfGenerator.generateRecuPdf(don);
                        emailService.envoyerRecuDon(don, pdf);
                    } catch (Exception e) {
                        logger.warning("Erreur lors de l'envoi du reçu : " + e.getMessage());
                    }
                }
            }
        }

        return ResponseEntity.ok("Webhook reçu");
    }
}

