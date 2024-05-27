package com.stocks.gestionProjet.service;

import com.stocks.gestionProjet.exceptions.DuplicateException;
import com.stocks.gestionProjet.exceptions.NotFoundException;
import com.stocks.gestionProjet.models.Administrateur;
import com.stocks.gestionProjet.models.Gestionnaire;
import com.stocks.gestionProjet.repositories.AdministrateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.NotContextException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AdministrateurService {

    @Autowired
    AdministrateurRepository administrateurRepository;

    @Autowired
    PhotoServise photoServise;

    @Autowired
    EmailService emailService;

    // POUR INSCRIPTION D'ADMINISTRATEUR SERVICE
    public Administrateur ajouterAdministrateur(Administrateur administrateur) {
        if (administrateurRepository.findByEmailAdmin(administrateur.getEmailAdmin()) == null) {
            return administrateurRepository.save(administrateur);
        } else {
            throw new DuplicateException("Cet email existe déjà");
        }
    }

    // POUR CONNEXION D'ADMINISTRATEUR SERVICE
    public Administrateur connexionAdministrateur(String emailAdmin, String motDePasseAdmin) {
        if (administrateurRepository.findByEmailAdminAndMotDePasseAdmin(emailAdmin, motDePasseAdmin) != null) {
            return administrateurRepository.findByEmailAdminAndMotDePasseAdmin(emailAdmin, motDePasseAdmin);
        } else {
            throw new NotFoundException("Email ou votre mot de passe est incorret");
        }
    }

    // LIST DES D'ADMINISTRATEUR SERVICE

    // MODIFICATION DE L'ADMINISTRATEUR SERVICE

    // SUPPRESSION DE L'ADMINISTRATEUR SERVICE

    // AJOUTER PHOTO DE PROFIL DES GETIONNAIRES
    public Administrateur ajouterPhotoDeProfil(Long idAdmin, MultipartFile imageFile) throws Exception {

        Optional<Administrateur> optionalAdministrateur = administrateurRepository.findById(idAdmin);

        if (optionalAdministrateur.isPresent()) {
            Administrateur administrateur = optionalAdministrateur.get();
            String photoUrl = photoServise.ajouterPhoto(imageFile);

            administrateur.setPhoto(photoUrl);
            return administrateurRepository.save(administrateur);
        } else {
            throw new NotContextException("Administrateur avec ID " + idAdmin + "non trouvé");
        }
    }

    // MODIFICATION PHOTO DE PROFIL DES GETIONNAIRES
    public Administrateur modifierPhotoDeProfil(Long idAdmin, MultipartFile imageFile) throws Exception {

        Optional<Administrateur> optionalAdministrateur = administrateurRepository.findById(idAdmin);

        if (optionalAdministrateur.isPresent()) {
            Administrateur administrateur = optionalAdministrateur.get();
            String photoUrl = photoServise.modifierPhoto(imageFile);

            administrateur.setPhoto(photoUrl);
            return administrateurRepository.save(administrateur);
        } else {
            throw new NotContextException("Une erreur s'est produite lors de la mise à jour de l'annonce avec l'ID : " + idAdmin);
        }
    }


    public void generateVerificationCode(Administrateur administrateur) {
        String code = String.format("%05d", new Random().nextInt(10000));

        administrateur.setVerificationCode(code);

        administrateur.setVerificationCodeExpiration(LocalDateTime.now().plusMinutes(10));

        administrateurRepository.save(administrateur);

        emailService.sendVerificationEmail(administrateur.getEmailAdmin(), code);
    }

    public boolean verfyCode(Administrateur administrateur, String code) {
        return code.equals(administrateur.getVerificationCode()) &&
                administrateur.getVerificationCodeExpiration().isAfter(LocalDateTime.now());
    }

    public Administrateur findByEmail(String emailAdmin) {
        return administrateurRepository.findByEmailAdmin(emailAdmin);
    }
}
