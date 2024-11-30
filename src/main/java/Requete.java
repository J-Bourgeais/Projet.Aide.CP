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
	
	public Requete(String Name, String type, String Desc, String date, String status) { //TODO DATE --> DONE
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
        	String reque= "Nom : " + rs.getString("NameRequete") + "\n, Type : " + rs.getString("TypeRequete") + "\n, Description : '"+ rs.getString("Description")+"'\n, Date : "+ rs.getString("Date")+ "\n, Status actuel : " + rs.getString("Status"); 
        	requetes.add(reque);
        	reque="";
        }
        return requetes;
	
	}

   
}