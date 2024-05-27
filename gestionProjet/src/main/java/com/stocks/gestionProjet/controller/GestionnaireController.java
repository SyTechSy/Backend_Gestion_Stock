package com.stocks.gestionProjet.controller;

import com.stocks.gestionProjet.exceptions.NoContentException;
import com.stocks.gestionProjet.models.Gestionnaire;
import com.stocks.gestionProjet.models.Utilisateur;
import com.stocks.gestionProjet.service.GestionnaireService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.NotContextException;
import java.util.List;

@RestController
@RequestMapping("/api/gestionnaire")
public class GestionnaireController {

   @Autowired
    GestionnaireService gestionnaireService;

   // POUR AJOUTER UN GESTIONNAIRE
    @PostMapping("/ajouter")
    public ResponseEntity<Object> InscriptionGestionnaire(@Valid @RequestBody Gestionnaire gestionnaire) {
        Gestionnaire verifGestionnaire = gestionnaireService.ajouterGestionnaire(gestionnaire.getNomGestionnaire(), gestionnaire);
        if (verifGestionnaire != null) {
            return new ResponseEntity<>(gestionnaire, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Gestionnaire non existe", HttpStatus.NOT_FOUND);
        }
    }

    // CONNEXION DES GRESTIONNAIRES
    @PostMapping("/connexion")
    public String Connexion(@RequestParam("emailGestionnaire") String emailGestionnaire,
                            @RequestParam("motDePasseGestionnaire") String motDePasseGestionnaire) {
        Gestionnaire gestionnaire = gestionnaireService.connexionGestionnaire(emailGestionnaire, motDePasseGestionnaire);
        if (gestionnaire != null && gestionnaire.getMotDePasseGestionnaire().equals(motDePasseGestionnaire)) {
            gestionnaireService.generateVerificationCode(gestionnaire);
            return "Le code de vérification de votre compte est envoyer sur votre email " + gestionnaire.getNomGestionnaire() + " " + gestionnaire.getPrenomGestionnaire() + " !";
        }
        return "Invalid credentials";
    }

    @PostMapping("/verif")
    public ResponseEntity<?> verifyCode(
            @RequestParam String emailGestionnaire,
            @RequestParam String verificationCode) {

        Gestionnaire gestionnaire = gestionnaireService.findByEmail(emailGestionnaire);
        if (gestionnaire == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gestionnaire not found");
        }

        boolean isValid = gestionnaireService.verfyCode(gestionnaire, verificationCode);
        if (isValid) {
            return ResponseEntity.ok("Verification de votre code est fait avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Votre code verification est incorrect");
        }
    }

    // MODIFICATION DES GESTIONNAIRES
    @PutMapping("/modifier")
    public ResponseEntity<Object> modifierGestionnaire(@Valid @RequestBody Gestionnaire gestionnaire) {
        Gestionnaire verifGestionnaire = gestionnaireService.modifierGestionnaire(gestionnaire);
        if (verifGestionnaire != null) {
            return new ResponseEntity<>(verifGestionnaire, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("On peu pas modifier un gestionnaire non existe", HttpStatus.NOT_FOUND);
        }
    }

    // LIST DES GESTIONNAIRES
    @GetMapping("/list")
    public List<Gestionnaire> listGestionnaire() throws NotContextException {
        return gestionnaireService.gestionnaireList();
    }

    // SUPPRESSION DES GESTIONNAIRES
    @DeleteMapping("/supprimer")
    public ResponseEntity<String> supprimerGestionnaire(@Valid @RequestBody Gestionnaire gestionnaire) {
        String message = gestionnaireService.supprimerGestionnaire(gestionnaire);
        if (message.equals("Succès")) {
            return new ResponseEntity<>("Suppression gestionnaire avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cette gestionnaire n'existe pas", HttpStatus.NOT_FOUND);
        }
    }

    // AJOUTER PHOTO DE PROFIL DES GETIONNAIRES
    @PostMapping("/{id}/ajouter-profil")
    public ResponseEntity<Gestionnaire> ajouterPhotoProfil(@PathVariable Long id, @RequestParam("photo") MultipartFile imageFile) {
        try {
            Gestionnaire gestionnaireMisAJour = gestionnaireService.ajouterPhotoDeProfil(id, imageFile);
            return ResponseEntity.ok(gestionnaireMisAJour);
        } catch (Exception e) {
            ResponseEntity.status(500).body(null);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // MODIFICATION PHOTO DE PROFIL DES GETIONNAIRES
    @PutMapping("/{id}/modifier-profil")
    public ResponseEntity<Gestionnaire> modifierPhotoProfil(@PathVariable Long id, @RequestParam("photo") MultipartFile imageFile) {
        try {
            Gestionnaire gestionnaireMisAJour = gestionnaireService.modifierPhotoDeProfil(id, imageFile);
            return ResponseEntity.ok(gestionnaireMisAJour);
        } catch (Exception e) {
            ResponseEntity.status(500).body(null);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
