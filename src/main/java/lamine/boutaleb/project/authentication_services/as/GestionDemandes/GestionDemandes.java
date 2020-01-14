/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamine.boutaleb.project.authentication_services.as.GestionDemandes;

import lamine.boutaleb.project.authentication_services.as.ListeAuth;
import lamine.boutaleb.project.authentication_services.as.logs.JsonLogger;

/**
 *
 * @author zamourak
 */
public class GestionDemandes extends GestionAbstraite {

    private ListeAuth la;
    private boolean manager;
    
    public GestionDemandes(ListeAuth la, boolean manager) {
        this.la = la;
        this.manager = manager;
    }
/**
 *
 * Traitement de la chaine envoyée par le client, réalise le côté metier de l'application.
 */    
    public String traitement(String chaine) {
        // si elle est nulle c'est que le client a fermé la connexion
        if (chaine != null) {
            System.out.println("Chaine reçue : " + chaine);
            String[] tab = chaine.split(" ");

            if ((tab[0].equals("CHK"))) {
                if ((tab.length == 3) && (la.tester(tab[1], tab[2]) == true)) {
                    System.out.println(tab[0]);
                    System.out.println(tab[1]);
                    System.out.println(tab[2]);
                    chaine = "GOOD";
                } else {
                    chaine = "BAD";
                }
            }

            if ((tab[0].equals("ADD"))) {
                if ((tab.length == 3) && (la.creer(tab[1], tab[2]) == true) && manager == true) {
                    System.out.println(tab[0]);
                    System.out.println(tab[1]);
                    System.out.println(tab[2]);
                    chaine = "DONE";
                } else {
                    chaine = "ERROR";
                }
            }

            if ((tab[0].equals("DEL"))) {
                if ((tab.length == 3) && (la.supprimer(tab[1], tab[2]) == true) && manager == true) {
                    System.out.println(tab[0]);
                    System.out.println(tab[1]);
                    System.out.println(tab[2]);
                    chaine = "DONE";
                } else {
                    chaine = "ERROR";
                }
            }

            if ((tab[0].equals("MOD"))) {
                if ((tab.length == 3) && (la.mettreAJour(tab[1], tab[2]) == true) && manager == true) {
                    System.out.println(tab[0]);
                    System.out.println(tab[1]);
                    System.out.println(tab[2]);
                    chaine = "DONE";
                } else {
                    chaine = "ERROR";
                }
            }
        }
        return chaine;
    }
}
