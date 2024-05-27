package com.stocks.gestionProjet.controller;

import com.stocks.gestionProjet.models.Utilisateur;
import com.stocks.gestionProjet.service.UtilistateurService;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.NotContextException;
import java.util.List;

@RestController
@RequestMapping("api/utilisateur")
public class UtilisateurController {

    @Autowired
    UtilistateurService utilistateurService;

    // Ajouter un Utilisateur
    @PostMapping("/ajouter")
    public Utilisateur CreateUtilisateur(@RequestBody @Valid Utilisateur utilisateur) {
        return utilistateurService.createUtilisateur(utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getAge());
    }

    // Connexion de l'utilisateur
    @PostMapping("/connexion")
    public ResponseEntity<Object> connection(@RequestParam("email") String email, @RequestParam("motDePasse") String password) {
        Utilisateur verifUtilisateur = utilistateurService.connexionUtilisateur(email, password);
        if (verifUtilisateur != null) {
            return new ResponseEntity<>(verifUtilisateur, HttpStatus.OK);
        } else {
            return  new ResponseEntity<>("Utilisateur non existe", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list")
    public List<Utilisateur> GetUtilisateurs() throws NotContextException {
        return utilistateurService.ListUtilisateur();
    }

    @DeleteMapping("/supprimer")
    public ResponseEntity<String> supprimerUtilisateur(@RequestBody @Valid Utilisateur utilisateur) {
        String message = utilistateurService.supprimerUtilisateur(utilisateur);
        if (message.equals("Succès")) {
            return new ResponseEntity<>("Suppression utilisateur avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cette utilisateur n'existe pas", HttpStatus.NOT_FOUND);
        }
    }

    // Modification de l'Utilisateur
    @PutMapping(value = "/modifier")
    public ResponseEntity<Object> modifierUser(@RequestBody Utilisateur utilisateur) {
        Utilisateur verifUtilisateur = utilistateurService.modifierUtilisateur(utilisateur);
        if (verifUtilisateur != null) {
            return new ResponseEntity<>(verifUtilisateur, HttpStatus.OK);
        } else
            return new ResponseEntity<>("cette Utilisateur n'existe pas donc on peut pas modifier", HttpStatus.NOT_FOUND);
    }

    // Ajouter un photo de profil
    @PostMapping("/{id}/ajouter-profil")
    public ResponseEntity<Utilisateur> ajouterPhotoProfil(@PathVariable Long id, @RequestParam("photo") MultipartFile imageFile) {
        try {
            Utilisateur utilisateurMisAJour = utilistateurService.ajouterPhotoDeProfil(id, imageFile);
            return ResponseEntity.ok(utilisateurMisAJour);
        } catch (Exception e) {
            ResponseEntity.status(500).body(null);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Modifier un photo de profil
    @PutMapping("/{id}/modifier-profil")
    public ResponseEntity<Utilisateur> modifierPhotoProfil(@PathVariable Long id, @RequestParam("photo") MultipartFile imageFile) {
        try {
            Utilisateur utilisateurMisAJour = utilistateurService.modifierPhotoDeProfil(id, imageFile);
            return ResponseEntity.ok(utilisateurMisAJour);
        } catch (Exception e) {
            ResponseEntity.status(500).body(null);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


















    /*@PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String motDePasse) {
        Utilisateur utilisateur = utilistateurService.findByEmail(email);
        if (utilisateur != null && utilisateur.getMotDePasse().equals(motDePasse)) {
            utilistateurService.generateVerificationCode(utilisateur);
            return "Vérification de code envoyer";
        }
        return "Invalid credentials";
    }*/

    /*@PostMapping("/verif")
    public String verif(@RequestParam String email, @RequestParam String code) {
        Utilisateur utilisateur = utilistateurService.findByEmail(email);
        if (utilisateur != null && utilistateurService.verfyCode(utilisateur, code)) {
            return "Vérification successful";
        } else {
            return "Invalid verification code";
        }
    }
    */

    /*public ResponseEntity<Object> ajouterUtilisateur(@Valid @RequestBody Utilisateur utilisateur) throws NotContextException {
        Utilisateur verifUtilisateur = utilistateurService.creerUtilisateur(utilisateur);
        if (verifUtilisateur != null) {
            return new ResponseEntity<>("Inscription fait avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Utilisateur n'existe pas", HttpStatus.NOT_FOUND);
        }
    }*/


}
