package lamine.boutaleb.project.authentication_services.as.Serveur;

import lamine.boutaleb.project.authentication_services.as.GestionClients.GestionRequeteUDP;
import lamine.boutaleb.project.authentication_services.as.ListeAuth;
import lamine.boutaleb.project.authentication_services.as.GestionDemandes.GestionDemandes;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import lamine.boutaleb.project.authentication_services.as.GestionDemandes.GestionAbstraite;

public class ServeurUDP implements Runnable {

    private int port;
    private GestionAbstraite gD;
    private GestionRequeteUDP gRudp;
    
    public ServeurUDP(int port, GestionAbstraite gD, GestionRequeteUDP gRudp){
        this.port = port;
        this.gD = gD;
        this.gRudp = gRudp;
    }
    
    public static void main(String[] args) throws Exception {
        int port = 28414;

        ListeAuth la = new ListeAuth();
        GestionAbstraite gD = new GestionDemandes(la, false);
        GestionRequeteUDP gRudp = new GestionRequeteUDP(gD);
        ServeurUDP sUDP = new ServeurUDP(port, gD, gRudp);
        sUDP.travailler();
    }

    public void travailler(){
        // Création d'un socket UDP sur le port 40000
        DatagramSocket socket;
        try {
            socket = new DatagramSocket(port);
        

        // tampon pour recevoir les données des datagrammes UDP
        final byte[] tampon = new byte[1024];

        // objet Java permettant de recevoir un datagramme UDP
        DatagramPacket dgram = new DatagramPacket(tampon, tampon.length);
        
        gRudp.setSocket(socket);
        gRudp.setDgram(dgram);
        
        while (true) {
                try {
                    // attente et réception d'un datagramme UDP  
                    socket.receive(dgram); 
                    gRudp.travail();
                } catch (IOException ex) {
                    Logger.getLogger(ServeurUDP.class.getName()).log(Level.SEVERE, null, ex);
                }

            // on replace la taille du tampon au max
            // elle a été modifiée lors de la réception
            dgram.setData(tampon);
        }
        } catch (SocketException ex) {
            Logger.getLogger(ServeurUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        this.travailler();
    }
}
