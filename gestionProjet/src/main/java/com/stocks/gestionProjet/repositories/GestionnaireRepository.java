package com.stocks.gestionProjet.repositories;


import com.stocks.gestionProjet.models.Gestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GestionnaireRepository extends JpaRepository<Gestionnaire, Long> {
    Gestionnaire findByEmailGestionnaire(String email);
    Gestionnaire findByEmailGestionnaireAndMotDePasseGestionnaire(String email, String motDePasse);
    Gestionnaire findByIdGestionnaire(Long idGestionnaire);
}
