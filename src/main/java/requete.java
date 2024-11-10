import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/*INFO REQUETE BDD
 * 
 * table name = requetes
 * 
 * colonnes :
 * idrequetes
 * NameRequete
 * FromUser (nom prenom ??)
 * Description
 * Status
 * Date
 * TypeRequete
 * Contact (l'email)
 * 
 * 
 * */


public class requete {


    //Afficher des requêtes selon un critère (méthode générique)
    
    public static void afficherRequetesParCritere(String critere, String valeur) {
        String requeteSQL = "SELECT NameRequete, FromUser, Description, Status, Date, TypeRequete FROM requetes WHERE " + critere + " = ?";

        try (Connection connexion = ConnexionBDD.GetConnexion();
             PreparedStatement stmt = connexion.prepareStatement(requeteSQL)) {

            stmt.setString(1, valeur);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Requêtes de type " + critere + " avec la valeur " + valeur + " :");

            while (rs.next()) {
                System.out.println("Requête : " + rs.getString("NameRequete"));
                System.out.println("Type : " + rs.getString("TypeRequete"));
                System.out.println("Description : " + rs.getString("Description"));
                System.out.println("Date : " + rs.getDate("Date"));
                System.out.println("Status : " + rs.getString("Status"));
                System.out.println("-----------------------------\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void afficherDemandesParEmail(String email) {
        afficherRequetesParCritere("Contact", email);
    }

    public static void afficherDemandesParType(String typeRequete) {
        afficherRequetesParCritere("TypeRequete", typeRequete);
    }

}