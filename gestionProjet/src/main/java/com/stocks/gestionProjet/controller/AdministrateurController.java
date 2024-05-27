package com.stocks.gestionProjet.controller;

import com.stocks.gestionProjet.models.Administrateur;
import com.stocks.gestionProjet.service.AdministrateurService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/administrateur")
public class AdministrateurController {

    @Autowired
    AdministrateurService administrateurService;

    // POUR INSCRIPTION D'ADMINISTRATEUR CONTRÔLLEUR
    @PostMapping("/ajouter")
    public ResponseEntity<Object> ajouterAdministrateur(@Valid @RequestBody Administrateur administrateur) {
        Administrateur verifAdministrateur = administrateurService.ajouterAdministrateur(administrateur);
        if (verifAdministrateur != null) {
            return new ResponseEntity<>(administrateur, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cette administrateur n'existe pas",HttpStatus.NOT_FOUND);
        }
    }

    // POUR CONNEXION D'ADMINISTRATEUR CONTRÔLLEUR
    @PostMapping("/connexion")
    public String Connexion(@RequestParam("emailAdmin") String emailAdmin,
                            @RequestParam("motDePasseAdmin") String motDePasseAdmin) {
        Administrateur administrateur = administrateurService.connexionAdministrateur(emailAdmin, motDePasseAdmin);
        if (administrateur != null && administrateur.getMotDePasseAdmin().equals(motDePasseAdmin)) {
            administrateurService.generateVerificationCode(administrateur);
            return "Le code de vérification de votre compte est envoyer sur votre email " + administrateur.getLastNameAdmin() + " " + administrateur.getFirstNameAdmin() + " !";
        }
        return "Invalid credentials";
    }

    // LIST DES D'ADMINISTRATEUR CONTRÔLLEUR

    // MODIFICATION DE L'ADMINISTRATEUR CONTRÔLLEUR

    // SUPPRESSION DE L'ADMINISTRATEUR CONTRÔLLEUR

    // VERIFICATION DE CODE
    @PostMapping("/verif")
    public ResponseEntity<?> verifyCode(
            @RequestParam String emailAdmin,
            @RequestParam String verificationCode) {

        Administrateur administrateur = administrateurService.findByEmail(emailAdmin);
        if (administrateur == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Administrateur not found");
        }

        boolean isValid = administrateurService.verfyCode(administrateur, verificationCode);
        if (isValid) {
            return ResponseEntity.ok("Verification de votre code est fait avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Votre code verification est incorrect");
        }
    }

    // AJOUTER PHOTO DE PROFIL DES GETIONNAIRES
    @PostMapping("/{id}/ajouter-profil")
    public ResponseEntity<Administrateur> ajouterPhotoProfil(@PathVariable Long id, @RequestParam("photo") MultipartFile imageFile) {
        try {
            Administrateur administrateurMisAJour = administrateurService.ajouterPhotoDeProfil(id, imageFile);
            return ResponseEntity.ok(administrateurMisAJour);
        } catch (Exception e) {
            ResponseEntity.status(500).body(null);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // MODIFICATION PHOTO DE PROFIL DES GETIONNAIRES
    @PutMapping("/{id}/modifier-profil")
    public ResponseEntity<Administrateur> modifierPhotoProfil(@PathVariable Long id, @RequestParam("photo") MultipartFile imageFile) {
        try {
            Administrateur administrateurMisAJour = administrateurService.modifierPhotoDeProfil(id, imageFile);
            return ResponseEntity.ok(administrateurMisAJour);
        } catch (Exception e) {
            ResponseEntity.status(500).body(null);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
