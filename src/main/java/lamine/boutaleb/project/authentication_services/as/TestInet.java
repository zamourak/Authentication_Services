/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamine.boutaleb.project.authentication_services.as;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zamourak
 */
public class TestInet {
	public static void main(String args[]){
        try {
            InetAddress adr1 = InetAddress.getLocalHost();
            System.out.println(adr1.toString());
            
            InetAddress[] adr = InetAddress.getAllByName("141.115.28.2");
            System.out.println(adr.toString());
            
            InetAddress adr2 = InetAddress.getByName("www.google.fr");
            System.out.println(adr2.toString());
            
            adr2 = InetAddress.getByName("www.atr.fr");
            System.out.println(adr2.toString());
            System.out.println("Tab");
            adr2 = InetAddress.getByName("www.google.fr");
            byte[] res = adr2.getAddress();
            for(int i = 0; i < res.length; i++){
                int q = res[i]<0 ? res[i] + 256 : res[i];
                System.out.println(q);
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(TestInet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
