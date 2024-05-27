package com.stocks.gestionProjet.service;

import com.stocks.gestionProjet.exceptions.NotFoundException;
import com.stocks.gestionProjet.models.Utilisateur;
import com.stocks.gestionProjet.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class PhotoServise {

    public String ajouterPhoto(MultipartFile imageFile) throws Exception {
        if (imageFile != null ) {
                String imageLocation = "C:\\xampp\\htdocs\\gestionStockFile\\images";
            try {
                Path imageRootLocation = Paths.get(imageLocation);
                if (!Files.exists(imageRootLocation)) {
                    Files.createDirectories(imageRootLocation);
                }

                String imageName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path imagePath = imageRootLocation.resolve(imageName);
                Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                return "http://localhost/gestionStockFile/images/" + imageName;

            } catch (IOException e) {
                throw new Exception("Erreur lors du traitement du fichier image : " + e.getMessage());
            }
        } else {
            throw new Exception("Le fichier image est null");
        }
    }

    public String modifierPhoto(MultipartFile imageFile) throws Exception {
        if (imageFile != null) {
            try {
                String emplacementImage = "C:\\xampp\\\\htdocs\\gestionStockFile\\images";
                String nomImage = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path cheminImage = Paths.get(emplacementImage).resolve(nomImage);
                Files.copy(imageFile.getInputStream(), cheminImage, StandardCopyOption.REPLACE_EXISTING);
                return "http://localhost/gestionStockFile/images/" + nomImage;
            } catch (NotFoundException ex) {
                throw new NotFoundException("Une erreur s'est produite lors de la mise à jour de l'annonce avec l'ID : ");
            }
        } else {
            throw new Exception("Le fichier image est null");
        }
    }

}
