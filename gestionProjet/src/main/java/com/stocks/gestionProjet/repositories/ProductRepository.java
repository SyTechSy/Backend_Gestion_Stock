package com.stocks.gestionProjet.repositories;

import com.stocks.gestionProjet.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
