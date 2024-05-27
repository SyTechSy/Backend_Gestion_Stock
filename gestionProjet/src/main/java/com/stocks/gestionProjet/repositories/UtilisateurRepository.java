package com.stocks.gestionProjet.repositories;

import com.stocks.gestionProjet.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByEmail(String email);
    Utilisateur findByEmailAndMotDePasse(String email, String motDePasse);
    Utilisateur findByIdUtilisateur(long idUtilisateur);
}
