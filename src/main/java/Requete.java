import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


public class Requete {


    //Afficher des requêtes selon un critère (méthode générique)
	
	/*if (ownRequestsButton.isSelected()) {
            typeRequete = "own"; // Voir les propres requêtes
        } else if (allOffersButton.isSelected()) {
            typeRequete = "offer"; // Voir toutes les offres
        } else if (allDemandsButton.isSelected()) {
            typeRequete = "demand"; // Voir toutes les demandes
        }

        // Récupérer les requêtes en fonction du type sélectionné
        String[] requetes = Menu.ConsultRequete(connexion, email, typeRequete);*/
	
	public String Namerequete;
	public String Typerequete;
	public String Description;
	public Date date;
	public String Status;
	
	public Requete(String Name, String type, String Desc, Date date, String status) {
		this.Namerequete=Name;
		this.Typerequete=type;
		this.Description=Desc;
		this.date=date;
		this.Status=status;
	}
	
	
	//Retourne les requetes correspondant au critère choisi
	
	public static List<String> RequetesParCritere(String critere, String valeur, Connection connexion) throws SQLException {
		List<String> requetes = new ArrayList<>();
		String requeteSQL = "SELECT NameRequete, FromUser, Description, Status, Date, TypeRequete FROM requetes WHERE " + critere + " = ?";
		PreparedStatement stmt = connexion.prepareStatement(requeteSQL);

        stmt.setString(1, valeur);
        ResultSet rs = stmt.executeQuery();

        System.out.println("Requêtes de type " + critere + " avec la valeur " + valeur + " :");

        while (rs.next()) {
        	String reque= "Nom : " + rs.getString("NameRequete") + "\n, Type : " + rs.getString("TypeRequete") + "\n, Description : '"+ rs.getString("Description")+"'\n, Date : "+ rs.getDate("Date")+ "\n, Status actuel : " + rs.getString("Status"); 
        	requetes.add(reque);
        	reque="";
        }
        return requetes;
	
	}

	//Remplacé dans InterfaceGUI
    
    /*public static void afficherRequetesParCritere(String critere, String valeur) {
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
    }*/

}