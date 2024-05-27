package com.stocks.gestionProjet.service;

import com.stocks.gestionProjet.exceptions.DuplicateException;
import com.stocks.gestionProjet.exceptions.NotFoundException;
import com.stocks.gestionProjet.models.Gestionnaire;
import com.stocks.gestionProjet.models.Utilisateur;
import com.stocks.gestionProjet.repositories.GestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.NotContextException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
public class GestionnaireService {

    @Autowired
    GestionnaireRepository gestionnaireRepository;

    @Autowired
    PhotoServise photoServise;

    @Autowired
    EmailService emailService;

    // INSCRIPTION DES GESTIONNAIRES
    public Gestionnaire ajouterGestionnaire(String nomGestionnaire ,Gestionnaire gestionnaire) {
        if (gestionnaireRepository.findByEmailGestionnaire(gestionnaire.getEmailGestionnaire()) == null) {

            gestionnaire.setIdentifiantGestionnaire(generateIdentifiant(nomGestionnaire));
            gestionnaire.setMotDePasseGestionnaire(generateMotDePasse());

            return gestionnaireRepository.save(gestionnaire);
        } else {
            throw new DuplicateException("Cet email existe déjà");
        }
    }

    private String generateIdentifiant(String nomGestionnaire) {
        String charactersEmail = "abcdefghijklmnopqrstuvwxyz0123456789";
        int randomNum = (int) (Math.random() * 100);
        return nomGestionnaire.toLowerCase() + randomNum;
    }

    private String generateMotDePasse() {
        int lenght = 14;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder motDePasse = new StringBuilder();
        for (int i = 0; i < lenght; i++) {
            int index = (int) (Math.random() * characters.length());
            motDePasse.append(characters.charAt(index));
        }
        return motDePasse.toString();
    }


    // CONNEXION DES GRESTIONNAIRES
    public Gestionnaire connexionGestionnaire(String emailGestionnaire, String motDePasseGestionnaire) {
        if (gestionnaireRepository.findByEmailGestionnaireAndMotDePasseGestionnaire(emailGestionnaire, motDePasseGestionnaire) != null) {
            return gestionnaireRepository.findByEmailGestionnaireAndMotDePasseGestionnaire(emailGestionnaire, motDePasseGestionnaire);
        } else {
            throw new NotFoundException("Email ou votre mot de passe est incorret");
        }
    }

    // MODIFICATION DES GESTIONNAIRES
    public Gestionnaire modifierGestionnaire(Gestionnaire gestionnaire) {
        if (gestionnaireRepository.findById(gestionnaire.getIdGestionnaire()).isPresent()) {
            return gestionnaireRepository.save(gestionnaire);
        } else {
            throw new NotFoundException("Gestionnaire n'existe pas");
        }
    }

    // LIST DES GESTIONNAIRES
    public List<Gestionnaire> gestionnaireList() throws NotContextException {
        if (!gestionnaireRepository.findAll().isEmpty()) {
            return gestionnaireRepository.findAll();
        } else {
            throw new NotContextException("Aucun utilisateur na été trouver");
        }
    }

    // SUPPRESSION DES GESTIONNAIRES
    public String supprimerGestionnaire(Gestionnaire gestionnaire) {
        if (gestionnaireRepository.findByIdGestionnaire(gestionnaire.getIdGestionnaire()) != null) {
            gestionnaireRepository.delete(gestionnaire);
            return "Succès";
        } else {
            throw new NotFoundException("On peut pas supprimer un gestionnaire non existe");
        }
    }

    // AJOUTER PHOTO DE PROFIL DES GETIONNAIRES
    public Gestionnaire ajouterPhotoDeProfil(Long idGestionnaire, MultipartFile imageFile) throws Exception {

        Optional<Gestionnaire> optionalGestionnaire = gestionnaireRepository.findById(idGestionnaire);

        if (optionalGestionnaire.isPresent()) {
            Gestionnaire gestionnaire = optionalGestionnaire.get();
            String photoUrl = photoServise.ajouterPhoto(imageFile);

            gestionnaire.setPhoto(photoUrl);
            return gestionnaireRepository.save(gestionnaire);
        } else {
            throw new NotContextException("Gestionnaire avec ID " + idGestionnaire + "non trouvé");
        }
    }

    // MODIFICATION PHOTO DE PROFIL DES GETIONNAIRES
    public Gestionnaire modifierPhotoDeProfil(Long idGestionnaire, MultipartFile imageFile) throws Exception {

        Optional<Gestionnaire> optionalGestionnaire = gestionnaireRepository.findById(idGestionnaire);

        if (optionalGestionnaire.isPresent()) {
            Gestionnaire gestionnaire = optionalGestionnaire.get();
            String photoUrl = photoServise.modifierPhoto(imageFile);

            gestionnaire.setPhoto(photoUrl);
            return gestionnaireRepository.save(gestionnaire);
        } else {
            throw new NotContextException("Une erreur s'est produite lors de la mise à jour de l'annonce avec l'ID : " + idGestionnaire);
        }
    }


    public void generateVerificationCode(Gestionnaire gestionnaire) {
        String code = String.format("%05d", new Random().nextInt(10000));

        gestionnaire.setVerificationCode(code);

        gestionnaire.setVerificationCodeExpiration(LocalDateTime.now().plusMinutes(10));

        gestionnaireRepository.save(gestionnaire);

        emailService.sendVerificationEmail(gestionnaire.getEmailGestionnaire(), code);
    }

    public boolean verfyCode(Gestionnaire gestionnaire, String code) {
        return code.equals(gestionnaire.getVerificationCode()) &&
                gestionnaire.getVerificationCodeExpiration().isAfter(LocalDateTime.now());
    }

    public Gestionnaire findByEmail(String emailGestionnaire) {
        return gestionnaireRepository.findByEmailGestionnaire(emailGestionnaire);
    }


}
