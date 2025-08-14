package com.example.association.controllers;

import com.example.association.models.Don;
import com.example.association.repositories.DonRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class PayementController {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${stripe.public.key}")
    private String stripePublicKey;

    @Autowired
    private DonRepository donRepository;

//    @PostMapping("/traitement-don")
//    public String traiterDon(@ModelAttribute Don don, Model model) {
//        don.setDateDon(LocalDateTime.now());
//        donRepository.save(don);
//
//        switch (don.getPaiement()) {
//            case "stripe":
//               // return "redirect:/paiement-stripe?montant=" + don.getMontant();
//                return "redirect:/paiement-stripe?montant=" + don.getMontant() + "&donId=" + don.getId();
//            case "paypal":
//                return "redirect:/paiement-paypal?montant=" + don.getMontant();
//            default:
//                model.addAttribute("message", "Mode de paiement non pris en charge.");
//                return "confirmation-don";
//        }
//    }


//    @GetMapping("/paiement-stripe")
//public void paiementStripe(@RequestParam double montant, @RequestParam Long donId, HttpServletResponse response) throws IOException {
//    Stripe.apiKey = stripeSecretKey;
//
//    SessionCreateParams params = SessionCreateParams.builder()
//            .setMode(SessionCreateParams.Mode.PAYMENT)
//            .setSuccessUrl("https://ton-site.com/succes")
//            .setCancelUrl("https://ton-site.com/annulation")
//            .addLineItem(
//                    SessionCreateParams.LineItem.builder()
//                            .setQuantity(1L)
//                            .setPriceData(
//                                    SessionCreateParams.LineItem.PriceData.builder()
//                                            .setCurrency("eur")
//                                            .setUnitAmount((long) (montant * 100))
//                                            .setProductData(
//                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
//                                                            .setName("Don à l'association")
//                                                            .build()
//                                            )
//                                            .build()
//                            )
//                            .build()
//            )
//            .build();
//
//    try {
//        Session session = Session.create(params);
//
//        Don don = donRepository.findById(donId).orElse(null);
//        if (don != null) {
//            don.setStripeSessionId(session.getId());
//            don.setStatut("EN_ATTENTE");
//            donRepository.save(don);
//        }
//
//        response.sendRedirect(session.getUrl()); // ✅ redirection externe
//    } catch (StripeException e) {
//        e.printStackTrace();
//        response.sendRedirect("/erreur-paiement");
//    }
//}
@GetMapping("/paiement-stripe")
public void paiementStripe(@RequestParam double montant, @RequestParam Long donId, HttpServletResponse response) throws IOException {
    Stripe.apiKey = stripeSecretKey;
    System.out.println("paiementStripe appelé avec montant = " + montant + ", donId = " + donId);

    SessionCreateParams params = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl("https://ton-site.com/succes")
            .setCancelUrl("https://ton-site.com/annulation")
            .addLineItem(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("eur")
                                            .setUnitAmount((long) (montant * 100))
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName("Don à l'association")
                                                            .build()
                                            )
                                            .build()
                            )
                            .build()
            )
            .build();

    try {
        Session session = Session.create(params);

        Don don = donRepository.findById(donId).orElse(null);
        if (don != null) {
            don.setStripeSessionId(session.getId());
            don.setStatut("EN_ATTENTE");
            donRepository.save(don);
        }

        response.sendRedirect(session.getUrl()); // ✅ redirection externe
    } catch (StripeException e) {
        e.printStackTrace();
        response.sendRedirect("/erreur-paiement");
    }
}


    @GetMapping("/paiement-paypal")
    public String paiementPaypal(@RequestParam double montant) {
        String businessEmail = "ton-email-paypal@example.com";
        String returnUrl = "https://ton-site.com/succes";
        String cancelUrl = "https://ton-site.com/annulation";

        String paypalUrl = "https://www.paypal.com/cgi-bin/webscr?cmd=_donations" +
                "&business=" + businessEmail +
                "&currency_code=EUR" +
                "&amount=" + montant +
                "&return=" + returnUrl +
                "&cancel_return=" + cancelUrl +
                "&item_name=Don à l'association";

        return "redirect:" + paypalUrl;
    }
}
