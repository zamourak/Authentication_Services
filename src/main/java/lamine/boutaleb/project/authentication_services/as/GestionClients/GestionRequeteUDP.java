/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamine.boutaleb.project.authentication_services.as.GestionClients;

import lamine.boutaleb.project.authentication_services.as.GestionDemandes.GestionDemandes;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import lamine.boutaleb.project.authentication_services.as.GestionDemandes.GestionAbstraite;
import lamine.boutaleb.project.authentication_services.as.logs.JsonLogger;

/**
 *
 * @author zamourak
 */
public class GestionRequeteUDP implements Runnable {

    public DatagramSocket getSocket() {
        return socket;
    }

    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    public GestionAbstraite getgD() {
        return gD;
    }

    public void setgD(GestionAbstraite gD) {
        this.gD = gD;
    }

    public DatagramPacket getDgram() {
        return dgram;
    }

    public void setDgram(DatagramPacket dgram) {
        this.dgram = dgram;
    }
    
    private DatagramSocket socket;
    private GestionAbstraite gD;
    private DatagramPacket dgram;
    
    public GestionRequeteUDP(GestionAbstraite gD){
        this.gD = gD;
    }
    
    public void travail(){
            // extraction des données
            String chaineEntree = new String(dgram.getData(), 0, dgram.getLength());
            String chaineReponse = "";
            String chaineLog[] = null;            
            byte[] octetsChaine;
            
            chaineReponse = gD.traitement(chaineEntree);
            octetsChaine = chaineEntree.getBytes();
            dgram.setData(octetsChaine);
            
           
        try {
            // on renvoie le message au client
            socket.send(dgram);
            if(chaineReponse.equals("DONE") || chaineReponse.equals("ERROR") || chaineReponse.equals("GOOD") || chaineReponse.equals("BAD")){
                chaineLog = chaineEntree.split(" ");
                JsonLogger.log(socket.getLocalAddress().toString(), socket.getPort(), "UDP", chaineLog[0], chaineLog[1], chaineReponse); ;
            }
        } catch (IOException ex) {
            Logger.getLogger(GestionRequeteUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        this.travail();
    }
}
