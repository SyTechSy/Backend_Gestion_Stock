package com.stocks.gestionProjet.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtilisateur;

    @NotNull(message = "Champs nom vide")
    @Size(min = 1, message = "Nom est courte")
    @Column(nullable = false)
    private String nom;

    @NotNull(message = "Champs prenom vide")
    @Size(min = 2, message = "Prenom est courte")
    @Column(nullable = false)
    private String prenom;

    @NotNull(message = "Champs age vide")
    @Column(nullable = false)
    private int age;

    //@NotNull(message = "Champs email vide")
    @Email(message = "Email est nom valable")
    @Column(nullable = false)
    private String email;

    //@Column(nullable = false)
    private String photo;

    //@NotNull(message = "Champs Mot de passe vide")
    @Size(min = 7, message = "Mot de passe est courte")
    @Column(nullable = false)
    private String motDePasse;

}
