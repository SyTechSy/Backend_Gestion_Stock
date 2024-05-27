package com.stocks.gestionProjet.controller;

import com.stocks.gestionProjet.models.Category;
import com.stocks.gestionProjet.models.Product;
import com.stocks.gestionProjet.repositories.CategoryRepository;
import com.stocks.gestionProjet.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepository categoryRepository;

    // AJOUTER UN PRODUIT CONTRÔLLEUR
    @PostMapping("/ajouter")
    public ResponseEntity<Object> ajouterProduct(@Valid @RequestBody Product product) {
        Optional<Category> existingCategory = categoryRepository.findByNameCategory(product.getCategory().getNameCategory());

        if (existingCategory.isPresent()) {
            product.setCategory(existingCategory.get());

            Product savedProduct = productService.createProduct(product);
            return ResponseEntity.ok(savedProduct);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La catégorie spécifiée n'existe pas.");
        }
    }
}
