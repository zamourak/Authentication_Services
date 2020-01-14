/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamine.boutaleb.project.authentication_services.as.logs;

import lamine.boutaleb.project.authentication_services.as.Serveur.ServeurTCP;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import lamine.boutaleb.project.authentication_services.as.GestionDemandes.GestionAbstraite;
import lamine.boutaleb.project.authentication_services.as.GestionDemandes.GestionDemandes;
import lamine.boutaleb.project.authentication_services.as.GestionDemandes.GestionLogs;
import lamine.boutaleb.project.authentication_services.as.ListeAuth;

public class LoggerAS {

    public static void main(String[] args) {
        LoggerAS loggerAS = new LoggerAS();
        loggerAS.travailler();
    }

    public void travailler(){
        
        //Le port sur lequel le serveur TCP qui gère les log va écouter
        final Integer TCPLogPort = 3244;

        //on essai de lancer le serveur de log
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filtre = new FileNameExtensionFilter("Fichier json", "logService", "json");
        fileChooser.setFileFilter(filtre);
        int returnVal = fileChooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Les logs seront placés dans le fichier suivant : " +
                    fileChooser.getSelectedFile().getName());
        }
        
        
        ListeAuth la = new ListeAuth();
        GestionAbstraite gD = new GestionLogs(fileChooser.getSelectedFile().getPath());

        // on lance le serveur de log avec le fichier selectionné par l'utilisateur
        ServeurTCP TCPLogger = new ServeurTCP(TCPLogPort, gD);
        Thread th = new Thread(TCPLogger);
        th.start();
    }
}
