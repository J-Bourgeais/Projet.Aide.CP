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
	
	
	public String Namerequete;
	public String Typerequete;
	public String Description;
	public String date;
	public String Status;
	public String benev;
	
	public Requete(String Name, String type, String Desc, String date, String status, String benev) { 
		this.Namerequete=Name;
		this.Typerequete=type;
		this.Description=Desc;
		this.date=date;
		this.Status=status;
		this.benev=benev;
	}
	
	
	//Retourne les requetes correspondant au critère choisi
	
	//On peut faire critère Benevole, valeur email
	
	public static List<Object[]> RequetesParCritere(String critere, String valeur, Connection connexion) throws SQLException {
		List<Object[]> requetes = new ArrayList<>();
		String requeteSQL = "SELECT NameRequete, FromUser, Description, Status, Date, TypeRequete, Contact, Benevole FROM requetes WHERE " + critere + " = ?";
		PreparedStatement stmt = connexion.prepareStatement(requeteSQL);

        stmt.setString(1, valeur);
        ResultSet rs = stmt.executeQuery();

        System.out.println("Requêtes de type " + critere + " avec la valeur " + valeur + " :");

        while (rs.next()) {
        	Object[] requete = {rs.getString("NameRequete"), rs.getString("TypeRequete"), rs.getString("Description"), rs.getString("Date"), rs.getString("Status"), rs.getString("Contact"), rs.getString("Benevole")};
        	
        	requetes.add(requete);
        	
        }
        return requetes;
	
	}

   
}