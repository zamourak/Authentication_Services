/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamine.boutaleb.project.authentication_services.as.GestionClients;

import lamine.boutaleb.project.authentication_services.as.GestionDemandes.GestionDemandes;
import lamine.boutaleb.project.authentication_services.as.Serveur.ServeurTCP;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import lamine.boutaleb.project.authentication_services.as.GestionDemandes.GestionAbstraite;
import lamine.boutaleb.project.authentication_services.as.logs.JsonLogger;

/**
 *
 * @author zamourak
 */
public class GestionClientTCP implements Runnable{

    private Socket sss;
    private GestionAbstraite gD;

    public GestionClientTCP(Socket sss, GestionAbstraite gD) {
        this.sss = sss;
        this.gD = gD;
    }
    
    
    public void travail() {

        // Construction d'un BufferedReader pour lire du texte envoyé à travers la connexion socket
        BufferedReader entreeSocket;
        try {
            entreeSocket = new BufferedReader(new InputStreamReader(sss.getInputStream()));

            // Construction d'un PrintStream pour envoyer du texte à travers la connexion socket
            PrintStream sortieSocket = new PrintStream(sss.getOutputStream());

            String chaineEntree = "";
            String chaineReponse = "";
            String chaineLog[] = null;
            while (chaineEntree != null) {
                // lecture d'une chaine envoyée à travers la connexion socket
                chaineEntree = entreeSocket.readLine();
                
                chaineReponse = gD.traitement(chaineEntree);
                
                if(chaineReponse.equals("DONE") || chaineReponse.equals("ERROR") || chaineReponse.equals("GOOD") || chaineReponse.equals("BAD")){
                    chaineLog = chaineEntree.split(" ");
                    JsonLogger.log(sss.getLocalAddress().toString(), sss.getPort(), "TCP", chaineLog[0], chaineLog[1], chaineReponse);
                }
                
                sortieSocket.println(chaineReponse); // on envoie la chaine au client
            }
        } catch (IOException ex) {
            Logger.getLogger(GestionClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            // on ferme nous aussi la connexion
            sss.close();
        } catch (IOException ex) {
            Logger.getLogger(GestionClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        this.travail();
    }
}
