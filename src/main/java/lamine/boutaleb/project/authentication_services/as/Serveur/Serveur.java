/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamine.boutaleb.project.authentication_services.as.Serveur;

import lamine.boutaleb.project.authentication_services.as.GestionClients.GestionRequeteUDP;
import lamine.boutaleb.project.authentication_services.as.GestionDemandes.GestionAbstraite;
import lamine.boutaleb.project.authentication_services.as.ListeAuth;
import lamine.boutaleb.project.authentication_services.as.GestionDemandes.GestionDemandes;
import lamine.boutaleb.project.authentication_services.as.logs.LoggerAS;

/**
 *
 * @author zamourak
 */
public class Serveur {
    public static void main(String[] args) throws Exception {

        //TCP
        ListeAuth la = new ListeAuth();
        GestionAbstraite gD = new GestionDemandes(la, false);
        ServeurTCP sTCP = new ServeurTCP(28414, gD);

        //TCP Manager
        GestionAbstraite gDM = new GestionDemandes(la, true);
        ServeurTCP sTCPM = new ServeurTCP(28415, gDM);
        
        
        //UDP
        GestionRequeteUDP gRudp = new GestionRequeteUDP(gD);
        ServeurUDP sUDP = new ServeurUDP(28414, gD, gRudp);

        //Logger
        LoggerAS lAS = new LoggerAS();
        //Lancement du logger et du serveur de log
        lAS.travailler();
        
        Thread th = new Thread(sTCP);
        th.start();
        Thread thM = new Thread(sTCPM);
        thM.start();
        Thread th2 = new Thread(sUDP);
        th2.start();
    }
}
