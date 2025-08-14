package com.example.association.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
public class Don {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @Email(message = "Email invalide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

    @Min(value = 1, message = "Le montant doit être supérieur ou égal à 1")
    private double montant;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public LocalDateTime getDateDon() {
        return dateDon;
    }

    public void setDateDon(LocalDateTime dateDon) {
        this.dateDon = dateDon;
    }

    private String typeDon;

    @NotBlank(message = "Le mode de paiement est obligatoire")
    private String paiement;

    private LocalDateTime dateDon;
    private String stripeSessionId;
    private String statut; // Exemple : "EN_ATTENTE", "PAYÉ", "ÉCHEC"

    // Getters et setters
    public String getStripeSessionId() { return stripeSessionId; }
    public void setStripeSessionId(String stripeSessionId) { this.stripeSessionId = stripeSessionId; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }


    // Getters et setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public String getTypeDon() { return typeDon; }
    public void setTypeDon(String typeDon) { this.typeDon = typeDon; }

    public String getPaiement() { return paiement; }
    public void setPaiement(String paiement) { this.paiement = paiement; }
}
