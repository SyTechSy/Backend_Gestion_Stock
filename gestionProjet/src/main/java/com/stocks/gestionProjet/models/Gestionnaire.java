package com.stocks.gestionProjet.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Gestionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGestionnaire;

    @Column(nullable = false)
    private String nomGestionnaire;

    @Column(nullable = false)
    private String prenomGestionnaire;

    @Column(nullable = false)
    private String phoneGestionnaire;

    @Column(nullable = false)
    private String photo;

    @Column(nullable = false)
    private String identifiantGestionnaire;

    @Column(nullable = false)
    private String emailGestionnaire;

    @Column(nullable = false)
    private String motDePasseGestionnaire;

    @Column(nullable = false)
    private String verificationCode;

    //@Column(nullable = false)
    private LocalDateTime verificationCodeExpiration;
}
