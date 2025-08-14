package com.example.association.repositories;


import com.example.association.models.Don;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonRepository extends JpaRepository<Don, Long> {
    Don findByStripeSessionId(String stripeSessionId);

    Page<Don> findByNomContainingIgnoreCaseOrEmailContainingIgnoreCase(String nom, String email, Pageable pageable);
}
