package com.stocks.gestionProjet.controller;

import com.stocks.gestionProjet.models.Category;
import com.stocks.gestionProjet.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    // AJOUTER UN CATEGORIE
    @PostMapping("/ajouter")
    public ResponseEntity<Object> createCategory(@Valid @RequestBody Category category) {
        Category verifCategory = categoryService.addCategory(category);
        if (verifCategory != null) {
            return new ResponseEntity<>("Categorie crée avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Categorie non trouver", HttpStatus.NOT_FOUND);
        }
    }

    // MODIFIER UN CATEGORIE
    @PutMapping("/modifier")
    public ResponseEntity<Object> updateCategory(@Valid @RequestBody Category category) {
        Category verifCategory = categoryService.updateCategory(category);
        if (verifCategory != null) {
            return new ResponseEntity<>(verifCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Categorie non modifier", HttpStatus.NOT_FOUND);
        }
    }

    // LIST DES CATEGORIE
    @GetMapping("/list")
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    // SUPPRIMER UN CATEGORIE
    @DeleteMapping("/supprimer")
    public ResponseEntity<String> deleteCategory(@Valid @RequestBody Category category) {
        String message = categoryService.deleteCategory(category);
        if (message.equals("Succès")) {
            return new ResponseEntity<>("Categorie est supprimer avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cette categorie n'existe pas", HttpStatus.NOT_FOUND);
        }
    }
}
