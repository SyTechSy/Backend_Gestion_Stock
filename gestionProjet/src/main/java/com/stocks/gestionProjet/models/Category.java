package com.stocks.gestionProjet.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategory;

    @NotNull(message = "Le champs nom Categorie est vide")
    @Column(nullable = false)
    private String nameCategory;

    //@NotNull(message = "Le champs date de Categorie est vide")
    //@Column(nullable = false)
    private LocalDateTime dateAddCategory;

    @NotNull(message = "Le champs description Categorie est vide")
    @Column(nullable = false)
    private String descriptionCategory;
}
