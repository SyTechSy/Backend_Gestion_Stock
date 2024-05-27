package com.stocks.gestionProjet.service;

import com.stocks.gestionProjet.models.Category;
import com.stocks.gestionProjet.models.Product;
import com.stocks.gestionProjet.repositories.CategoryRepository;
import com.stocks.gestionProjet.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // AJOUTER UN PRODUIT SERVICE
    public Product createProduct(Product product) {
        // Vérification si la catégorie existe déjà
        Optional<Category> optionalCategory = categoryRepository.findByNameCategory(product.getCategory().getNameCategory());
        if (optionalCategory.isPresent()) {
            product.setCategory(optionalCategory.get());
            product.setDateAddProduct(LocalDateTime.now());

            // Calcul du prix global
            double priceGlobalProduct = product.getPriceProduct() * product.getQuantityProduct();
            product.setPriceGlobalProduct(priceGlobalProduct);

            return productRepository.save(product);
        } else {
            throw new IllegalArgumentException("La catégorie spécifiée n'existe pas.");
        }
    }
}
