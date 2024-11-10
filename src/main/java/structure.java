//structure

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class structure extends user {
	
	
	
	public void validerService(Connection connexion, String NameRequete, boolean estValidee) {
    // Déterminer le nouveau statut en fonction de estValidee
	//MAJ avec le nom de la requête (en considérant qu'elle est unique - evite d'avoir ID)
    String nouveauStatut = estValidee ? "validé" : "refusé";
    String updateSQL = "UPDATE requetes SET status = ? WHERE NameRequete = ?";
    
    try (PreparedStatement stmt = connexion.prepareStatement(updateSQL)) {
        stmt.setString(1, nouveauStatut);
        stmt.setString(2, NameRequete);
        
        int lignesAffectees = stmt.executeUpdate();
        if (lignesAffectees > 0) {
            // Mettre à jour l'objet localement si la requête SQL est réussie
            //requeteAValider.setStatus(nouveauStatut); --> Rien de local
            System.out.println("Vous venez de " + (estValidee ? "valider" : "refuser") + " la requête " + NameRequete);
            
            //if estValidee==false --> fournir une justification
            
            
        } else {
            System.out.println("La validation de la requête a échoué. Veuillez vérifier le nom de la requête.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
	
}