import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

// BDD : Users
// colonne 	BDD : Avis

// idUsers 
// Nom
// Prenom
// email
// Adresse
// Age
// Password
// UserType
// Avis

/*
 * INFORMATION : Cette classe s'occupe de Poster et consulter un avis émis par un utilisateur
 */

public class Avis {

    //Methode pour poster un avis à quelqu'un

	 public static void posterAvis(Connection connexion, String pourNom, String pourPrenom, int nbEtoiles, String description) throws SQLException {
        //Récupérer les avis existants
        String requeteSelect = "SELECT Avis FROM Users WHERE Nom = ? AND Prenom = ?";
        JSONArray listeAvis = new JSONArray();
        try (PreparedStatement etat = connexion.prepareStatement(requeteSelect)) {
            etat.setString(1, pourNom);
            etat.setString(2, pourPrenom);
            ResultSet rs = etat.executeQuery();

            if (rs.next()) {
                String avisExistants = rs.getString("Avis");
                if (avisExistants != null && !avisExistants.isEmpty()) {
                    listeAvis = new JSONArray(avisExistants);  // Charger les avis existants
                }
            }
        }

        //Ajouter notre avis à la liste
        JSONObject nouvelAvis = new JSONObject();
        nouvelAvis.put("nbEtoiles", nbEtoiles);
        nouvelAvis.put("description", description);
        listeAvis.put(nouvelAvis);

        String requeteSQL = "UPDATE Users SET Avis = ? WHERE Nom = ? AND Prenom = ?";
        try (PreparedStatement etat = connexion.prepareStatement(requeteSQL)) {
            etat.setString(1, listeAvis.toString());  // MàJ l’offre à "acceptée"
            etat.setString(2, pourNom);
            etat.setString(3, pourPrenom);

            int lignesAffectees = etat.executeUpdate();
            if (lignesAffectees > 0) {
                System.out.println("Avis posté avec succès !");
            } else {
                System.out.println("Erreur : l'avis n'a pas pu être posté.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

	 
	 //Retourne la liste des avis pour l'affichage via InterfaceGUI
	 
    public static List<String> consulterAvis(Connection connexion, String pourNom, String pourPrenom) {
    	
    	List<String> Listeavis = new ArrayList<>();
    	
    	String Av="";
  
        String requeteSQL = "SELECT Avis FROM Users WHERE Nom = ? AND Prenom = ?";
        try (PreparedStatement etat = connexion.prepareStatement(requeteSQL)) {
            etat.setString(1, pourNom);
            etat.setString(2, pourPrenom);
            
            ResultSet result = etat.executeQuery();
            
            if (result.next()) {
                String avisJson = result.getString("Avis");
                if (avisJson != null && !avisJson.isEmpty()) {
                    
                    // Charger les avis dans un JSONArray
                    JSONArray listAvis = new JSONArray(avisJson);
    
                    // Afficher chaque avis
                    System.out.println("Liste des avis pour " + pourNom + " " + pourPrenom + ":");
                    for (int i = 0; i < listAvis.length(); i++) {
                        JSONObject avis = listAvis.getJSONObject(i);
                        int nbEtoiles = avis.getInt("nbEtoiles");
                        String description = avis.getString("description");
                        Av="Avis " + (i + 1) + ": " + nbEtoiles + " étoiles - " + description;
                        Listeavis.add(Av);
                        //System.out.println("Avis " + (i + 1) + ": " + nbEtoiles + " étoiles - " + description);
                    }
                } else {
                    System.out.println("Aucun avis disponible pour cet utilisateur.");
                }
            } else {
                System.out.println("Utilisateur non trouvé.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        return Listeavis;
    }


}
