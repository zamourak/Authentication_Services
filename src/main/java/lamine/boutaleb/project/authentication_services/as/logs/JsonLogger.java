package lamine.boutaleb.project.authentication_services.as.logs;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * Classe Singleton qui permet de logger des requêtes vers un serveur de log sur le port 3244 de la machine locale
 * 
 * @author torguet
 *
 */
public class JsonLogger {
	
	// Attributs du JsonLogger
        private Socket socket = null;
        private PrintStream socketOutput = null;
        private int loggerPort;
	/**
	 * Initialisation de la classe JsonLogger suivant le pattern Singleton.
	 */
	private JsonLogger() {
            try {
                this.loggerPort = 3244;
                this.socket = new Socket("localhost", 3244);
            } catch (IOException ex) {
                System.out.println("La connexion avec le serveur de journalisation n'a pas pu être mise en place.");
            }
            
                // Construction d'un PrintStream pour envoyer les logs à travers la connexion socket
              try {
                 this.socketOutput = new PrintStream(this.socket.getOutputStream());;
            } catch (IOException e) {
            System.out.println("Impossible d'ouvrir la connection");
        }

            
	}
	
	/**
	 * Transforme une requête en Json
	 * 
	 * @param host machine client
	 * @param port port sur la machine client
	 * @param proto protocole de transport utilisé
	 * @param type type de la requête
	 * @param login login utilisé
	 * @param result résultat de l'opération
	 * @return un objet Json correspondant à la requête
	 */
	private static JsonObject reqToJson(String host, int port, String proto, String type, String login, String result) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("host", host)
		   	   .add("port", port)
		   	   .add("proto", proto)
			   .add("type", type)
			   .add("login", login)
			   .add("result", result)
			   .add("date", new Date().toString());

		return builder.build();
	}
	
	/**
	 *  singleton
	 */
	private static JsonLogger logger = null;
	
	/**
	 * récupération du logger qui est créé si nécessaire
	 * 
	 * @return le logger
	 */
	private static JsonLogger getLogger() {
		if (logger == null) {
			logger = new JsonLogger();
		}
		return logger;
	}
	
	/**
	 * méthode pour logger
	 * 
	 * @param host machine client
	 * @param port port sur la machine client
	 * @param proto protocole de transport utilisé
	 * @param type type de la requête
	 * @param login login utilisé
	 * @param result résultat de l'opération
	 */
	public static void log(String host, int port, String proto, String type, String login, String result) {
            //Récupération du singleton
            JsonLogger logger = getLogger();
	
            //Transformation des donnnées en format json
            JsonObject log = JsonLogger.reqToJson(host, port, proto, type, login, result);
            
            //Envoi au serveur des données transformées
            JsonLogger.getLogger().socketOutput.println(log.toString());            
            
	}
}
