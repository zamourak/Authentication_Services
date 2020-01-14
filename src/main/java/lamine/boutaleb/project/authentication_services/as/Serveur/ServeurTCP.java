package lamine.boutaleb.project.authentication_services.as.Serveur;

import lamine.boutaleb.project.authentication_services.as.GestionClients.GestionClientTCP;
import lamine.boutaleb.project.authentication_services.as.ListeAuth;
import lamine.boutaleb.project.authentication_services.as.GestionDemandes.GestionDemandes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import lamine.boutaleb.project.authentication_services.as.GestionDemandes.GestionAbstraite;

public class ServeurTCP implements Runnable {

    private int port;
    private GestionAbstraite gD;

    public ServeurTCP(int port, GestionAbstraite gD) {
        this.port = port;
        this.gD = gD;
    }

    public static void main(String args[]) {
        int port = 28414;
        ListeAuth la = new ListeAuth();
        GestionDemandes gD = new GestionDemandes(la, false);
        ServeurTCP sTCP = new ServeurTCP(port, gD);
        sTCP.travail();

    }

    public void travail() {
        ServerSocket ssg;
        try {
            // Création d'un socket serveur générique sur le port 40000
            ssg = new ServerSocket(port);

            try {
                while (true) {

                    // On attend une connexion puis on l'accepte
                    Socket sss = ssg.accept();
                    GestionClientTCP gctcp = new GestionClientTCP(sss, gD);
                    Thread t = new Thread(gctcp);
                    t.start();

                }
            } catch (IOException ex) {
                Logger.getLogger(ServeurTCP.class.getName()).log(Level.SEVERE, null, ex);
            }
            ssg.close();
        } catch (IOException ex) {
            Logger.getLogger(ServeurTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        this.travail();
    }

}