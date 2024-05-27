package com.stocks.gestionProjet.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Administrateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdmin;

    @NotNull(message = "Le champs nom admin est vide")
    @Column(nullable = false)
    @Size(min = 2, message = "Le nom de famille d'Admion est trop courte")
    private String lastNameAdmin;

    @NotNull(message = "Le champs prenom admin est vide")
    @Column(nullable = false)
    @Size(min = 2, message = "Le prenom d'Admon est trop courte")
    private String firstNameAdmin;

    @NotNull(message = "Le champs email d'admin est vide")
    @Column(nullable = false)
    @Email(message = "Email admin est incorret")
    private String emailAdmin;

    @NotNull(message = "Le champs numero admin est vide")
    @Column(nullable = false)
    private int numberAdmin;

    @Column(nullable = false)
    private String photo;

    @NotNull(message = "Le champs mot de passe admin est vide")
    @Column(nullable = false)
    @Size(min = 7, message = "Le Mot de passe doit être 8 chiffre ou plus")
    private String motDePasseAdmin;

    @Column(nullable = false)
    private String verificationCode;

    //@Column(nullable = false)
    private LocalDateTime verificationCodeExpiration;
}
