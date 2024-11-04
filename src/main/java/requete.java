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
 * idrequetes (auto-increment, on s'en fou)
 * NameRequete
 * FromUser (nom prenom ??)
 * Description
 * Date
 * TypeRequete
 * ContactUser (l'email, foreign key ??)
 * 
 * 
 * */


 //Need to add a status to the SQL List




public class requete {

    protected String nom;
    protected String desc;
    protected String status;
    protected Date date;

    public requete(String nom, String description) {
        this.nom = nom;
        this.desc = description;
        this.date = new Date();
        this.status = "en attente";

    }

    // deuxième constructeur car la description est optionnelle
    public requete(String nom) {
        this.nom = nom;
        this.date = new Date();
        this.status = "en attente";

    }

    public requete getRequete() {
        return this;
    }

    public String getNom() {
        return nom;
    }

    public String getDesc() {
        return desc;
    }

    //Afficher des requêtes selon un critère (méthode générique)
    
    public static void afficherRequetesParCritere(String critere, String valeur) {
        String requeteSQL = "SELECT NameRequete, FromUser, Description, Date, TypeRequete FROM requetes WHERE " + critere + " = ?";

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
                System.out.println("-----------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void afficherDemandesParEmail(String email) {
        afficherRequetesParCritere("ContactUser", email);
    }

    public static void afficherDemandesParType(String typeRequete) {
        afficherRequetesParCritere("TypeRequete", typeRequete);
    }

}