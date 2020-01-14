/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamine.boutaleb.project.authentication_services.as.GestionDemandes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zamourak
 */
public class GestionLogs extends GestionAbstraite {
    
    /**
     * Le chemin du fichier contenant la journalisation
     */    
    private String path;
    
    public GestionLogs(String path){
        this.path = path;
    }
/**
 *
 * Traitement d'une demande de journalisation.
 */    
    public String traitement(String chaine) {
        try {
            Files.write(Paths.get(path), chaine.concat(System.getProperty("line.separator")).getBytes(), StandardOpenOption.APPEND);
            return "Journalisation réussite";
        } catch (IOException ex) {
            Logger.getLogger(GestionLogs.class.getName()).log(Level.SEVERE, null, ex);
            return "Echec de journalisation";
        }
    }
}
