package com.stocks.gestionProjet.repositories;

import com.stocks.gestionProjet.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameCategory(String nameCategory);
    Category findByIdCategory(long idCategory);
}
