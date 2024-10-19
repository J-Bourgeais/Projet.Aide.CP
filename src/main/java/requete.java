import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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



    //Afficher des requetes d'une prsn en particuier
    
    public static void afficherRequetesParEmail(String email) {

        Connection connexion=ConnexionBDD.GetConnexion();

        String requeteSQL = "SELECT NameRequete, FromUser, Description, Date, TypeRequete FROM requetes WHERE ContactUser = ?";
        
        try (PreparedStatement stmt = connexion.prepareStatement(requeteSQL)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            System.out.println("Requêtes De la part de l'utilisateur " + rs.getString("FromUser" + ":"));

            while (rs.next()) {
                System.out.println("Requête: " + rs.getString("NameRequete"));
                System.out.println("de type: " + rs.getString("TypeRequete"));
                System.out.println("De la part de l'utilisateur: " + rs.getString("FromUser"));
                System.out.println("Date: " + rs.getDate("Date"));
                System.out.println("Description: " + rs.getString("Description"));
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnexionBDD.CloseConnexion(connexion);
    }


    //Afficher des requetes d'un type en particuier

    public static void afficherRequetesParType(String typeRequete) {

        Connection connexion=ConnexionBDD.GetConnexion();

        String requeteSQL = "SELECT NameRequete, FromUser, Description, Date, TypeRequete, ContactUser FROM requetes WHERE TypeRequete = ?";
        
        try (PreparedStatement stmt = connexion.prepareStatement(requeteSQL)) {
            stmt.setString(1, typeRequete);
            ResultSet rs = stmt.executeQuery();
            
            System.out.println("Requêtes de type " + rs.getString("TypeRequete" + ":"));

            while (rs.next()) {
                System.out.println("Nom de la requête: " + rs.getString("NameRequete"));
                System.out.println("De la part de l'utilisateur: " + rs.getString("FromUser"));
                System.out.println("Contact (Email): " + rs.getString("ContactUser"));
                System.out.println("Date: " + rs.getDate("Date"));
                System.out.println("Description: " + rs.getString("Description"));
                
                
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnexionBDD.CloseConnexion(connexion);
    }




}