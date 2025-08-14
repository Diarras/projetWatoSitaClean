package com.example.association.repositories;

import com.example.association.models.ContactMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
    Page<ContactMessage> findByNomContainingIgnoreCaseOrSujetContainingIgnoreCase(String nom, String sujet, Pageable pageable);
}
