package com.stocks.gestionProjet.repositories;

import com.stocks.gestionProjet.models.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministrateurRepository extends JpaRepository<Administrateur, Long> {
    Administrateur findByEmailAdmin(String email);
    Administrateur findByEmailAdminAndMotDePasseAdmin(String emailAdmin, String motDePasseAdmin);
}
