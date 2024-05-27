package com.stocks.gestionProjet.service;

import com.stocks.gestionProjet.exceptions.DuplicateException;
import com.stocks.gestionProjet.exceptions.NotFoundException;
import com.stocks.gestionProjet.models.Category;
import com.stocks.gestionProjet.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    // AJOUTER UN CATEGORIE
    public Category addCategory(Category category) {
        if (categoryRepository.findByNameCategory(category.getNameCategory()) == null) {
            category.setDateAddCategory(LocalDateTime.now());
            return categoryRepository.save(category);
        } else {
            throw new DuplicateException("Cette categorie existe déjà");
        }
    }

    // MODIFIER UN CATEGORIE
    public Category updateCategory(Category category) {
        if (categoryRepository.findByIdCategory(category.getIdCategory()) != null) {
            return categoryRepository.save(category);
        } else {
            throw new NotFoundException("Cette categorie n'existe pas");
        }
    }

    // LIST DES CATEGORIE
    public List<Category> getAllCategories() {
        if (!categoryRepository.findAll().isEmpty()) {
            return categoryRepository.findAll();
        } else {
            throw new NotFoundException("List des categories nom trouver");
        }
    }

    // SUPPRIMER UN CATEGORIE
    public String deleteCategory(Category category) {
        if (categoryRepository.findByIdCategory(category.getIdCategory()) != null) {
            categoryRepository.delete(category);
            return "Succès";
        } else {
            throw new NotFoundException("On peut pas supprimer quelque chose qui n'existe pas");
        }
    }
}
