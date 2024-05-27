package com.stocks.gestionProjet.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;

    @NotNull(message = "Le champs nom produit ne doit pas être vide")
    @Column(nullable = false)
    private String nameProduct;

    @NotNull(message = "Le champs prix produit ne doit pas être vide")
    @Column(nullable = false)
    private double priceProduct;


    @Column(nullable = false)
    private double priceGlobalProduct;

    @NotNull(message = "Le champs quantité produit ne doit pas être vide")
    @Column(nullable = false)
    private int quantityProduct;

    @NotNull(message = "Le champs date expiration produit ne doit pas être vide")
    @Column(nullable = false)
    private String dateExpirationProduct;

    //@NotNull(message = "Le champs date Ajouter produit ne doit pas être vide")
    @Column(nullable = false)
    private LocalDateTime dateAddProduct;

    @NotNull(message = "Le champs description produit ne doit pas être vide")
    @Column(nullable = false)
    private String descriptionProduct;

    @ManyToOne
    @JoinColumn(name = "idAdmin")
    private Administrateur administrateur;

    @ManyToOne
    @JoinColumn(name = "idCategory")
    private Category category;
}
