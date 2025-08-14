
CREATE DATABASE IF NOT EXISTS association_db CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE association_db;

CREATE TABLE IF NOT EXISTS contact_message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    sujet VARCHAR(100) NOT NULL,
    message TEXT NOT NULL
);

-- Exemple d'insertion pour tester la connexion
INSERT INTO contact_message (nom, email, sujet, message)
VALUES ('Eric Dupont', 'eric@example.com', 'Demande d information', 'Bonjour, je souhaite en savoir plus sur vos actions.');
