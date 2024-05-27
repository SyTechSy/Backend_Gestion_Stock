package com.stocks.gestionProjet.service;

import com.stocks.gestionProjet.exceptions.NotFoundException;
import com.stocks.gestionProjet.models.Utilisateur;
import com.stocks.gestionProjet.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.NotContextException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UtilistateurService {

    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    PhotoServise photoServise;

    // POUR LA CREATION DE UTILISATEUR
    public Utilisateur createUtilisateur(String nom, String prenom, int age) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setAge(age);

        utilisateur.setEmail(generateEmail(nom, prenom));
        utilisateur.setMotDePasse(generateMotDePasse());

        return utilisateurRepository.save(utilisateur);
    }

    private String generateEmail(String nom, String prenom) {
        String charactersEmail = "abcdefghijklmnopqrstuvwxyz0123456789";
            int randomNum = (int) (Math.random() * 100);
            return nom.toLowerCase()  + randomNum + "user" + "@gmail.com";
    }

    private String generateMotDePasse() {
        int lenght = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder motDePasse = new StringBuilder();
        for (int i = 0; i < lenght; i++) {
            int index = (int) (Math.random() * characters.length());
            motDePasse.append(characters.charAt(index));
        }
        return motDePasse.toString();
    }

    // POUR LA CONNEXION DE L'UTILISATEUR
    public Utilisateur connexionUtilisateur(String email, String motDePasse) {
        if (utilisateurRepository.findByEmailAndMotDePasse(email, motDePasse) != null) {
            return utilisateurRepository.findByEmailAndMotDePasse(email, motDePasse);
        } else {
            throw new NotFoundException("Email ou votre mot de passe est incorret");
        }
    }

    // POUR LA MODIFICATION de L'utilisateur
    public Utilisateur modifierUtilisateur(Utilisateur utilisateur) {
        if (utilisateurRepository.findById(utilisateur.getIdUtilisateur()).isPresent()) {
            return utilisateurRepository.save(utilisateur);
        } else
            return null;
    }

    // Ajouter un photo de profil
    public Utilisateur ajouterPhotoDeProfil(Long idUtilisateur, MultipartFile imageFile) throws Exception {

        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(idUtilisateur);

        if (optionalUtilisateur.isPresent()) {
            Utilisateur utilisateur = optionalUtilisateur.get();
            String photoUrl = photoServise.ajouterPhoto(imageFile);

            utilisateur.setPhoto(photoUrl);
            return utilisateurRepository.save(utilisateur);
        } else {
            throw new NotContextException("Utilisateur avec ID " + idUtilisateur + "non trouvé");
        }
    }

    // modifier photo de profil
    public Utilisateur modifierPhotoDeProfil(Long idUtilisateur, MultipartFile imageFile) throws Exception {

        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(idUtilisateur);

        if (optionalUtilisateur.isPresent()) {
            Utilisateur utilisateur = optionalUtilisateur.get();
            String photoUrl = photoServise.modifierPhoto(imageFile);

            utilisateur.setPhoto(photoUrl);
            return utilisateurRepository.save(utilisateur);
        } else {
            throw new NotContextException("Une erreur s'est produite lors de la mise à jour de l'annonce avec l'ID : " + idUtilisateur);
        }
    }

    // List des Utilisateur
    public List<Utilisateur> ListUtilisateur() throws NotContextException {
        if (!utilisateurRepository.findAll().isEmpty()) {
            return utilisateurRepository.findAll();
        } else {
            throw new NotContextException("Aucun utilisateur na été trouver");
        }
    }

    // Suppression des utilisateur
    public String supprimerUtilisateur(Utilisateur utilisateur) {
        if (utilisateurRepository.findByIdUtilisateur(utilisateur.getIdUtilisateur()) != null) {
            utilisateurRepository.delete(utilisateur);
            return "Succès";
        } else {
            throw new NotFoundException("On ne peut pas supprimer quelque chose qui n'existe pas");
        }
    }



    /*@Autowired
    private EmailService emailService;*/

    /*public Utilisateur creerUtilisateur(Utilisateur utilisateur) throws NotContextException {
        if (utilisateurRepository.findByEmail(utilisateur.getEmail()) == null) {
            return utilisateurRepository.save(utilisateur);
        } else {
            throw new NotContextException("On ne peut pas Cree un utilistateur qui existe déjà!");
        }
    }*/

    /*
    public void generateVerificationCode(Utilisateur utilisateur) {
        String code = String.format("%06d", new Random().nextInt(10000));

        utilisateur.setVerificationCode(code);

        utilisateur.setVerificationCodeExpiration(LocalDateTime.now().plusMinutes(10));

        utilisateurRepository.save(utilisateur);

        emailService.sendVerificationEmail(utilisateur.getEmail(), code);
    }

    public boolean verfyCode(Utilisateur utilisateur, String code) {
        return code.equals(utilisateur.getVerificationCode()) && utilisateur.getVerificationCodeExpiration().isAfter(LocalDateTime.now());
    }

    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

     */
}
