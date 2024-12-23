//structure

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/*
 * INFORMATION : Cette classe permet à une structure de valider ou non un service au sein de son établissement
 * C'est une classe fille de la classe User
 */

public class Structure extends User {
	
	public static void validerService(Connection connexion, String NameRequete, String email, boolean estValidee, String raison) {
    // Déterminer le nouveau statut en fonction de estValidee
    String nouveauStatut = estValidee ? "validé" : "refusé";
    String updateSQL = "UPDATE requetes SET status = ? WHERE NameRequete = ? AND Contact = ?";
    
    try (PreparedStatement stmt = connexion.prepareStatement(updateSQL)) {
        stmt.setString(1, nouveauStatut);
        stmt.setString(2, NameRequete);
        stmt.setString(3, email);
        
        int lignesAffectees = stmt.executeUpdate();
        if (lignesAffectees > 0) {
        	
            System.out.println("Vous venez de " + (estValidee ? "valider" : "refuser") + " la requête " + NameRequete);
            if(estValidee==false) {
            	System.out.println("Raison du refus : "+ raison);
            }
            
            
        } else {
            System.out.println("La validation de la requête a échoué. Veuillez vérifier le nom de la requête.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
	
}
