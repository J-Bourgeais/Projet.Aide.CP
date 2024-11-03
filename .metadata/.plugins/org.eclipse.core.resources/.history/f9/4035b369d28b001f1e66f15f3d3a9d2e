import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnexionBDD {

    public static Connection GetConnexion() {
        
        //--------------------------Connexion à MySql------------------------------
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Chargement du driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        
      //--------------------------Info de Connexion------------------------------
        String url = "jdbc:mysql://localhost:3306/SQL_Project"; // Remplace par ton URL de base de données
        String user = "projet_gei_035";  // Nom d'utilisateur de la base
        String mdp = "quiaw0Di";  // Mot de passe de la base
        Connection connexion = null;

        try {
            // Établir la connexion à la base de données
            connexion = DriverManager.getConnection(url, user, mdp);            

        } catch (SQLException a) {
            a.printStackTrace();
        }

        return connexion;
        
    }

    public static void CloseConnexion(Connection connexion) {
        try {
            connexion.close();
        } catch (SQLException a) {
            a.printStackTrace();
        }
    }


    // requete a furnir dans e main
    public static void DoStatement(Connection connexion, String requete) {
        try {
            // Créer un objet Statement pour exécuter des requêtes SQL
            Statement statement = connexion.createStatement();

            // Exécuter la requête pour créer la table
            statement.execute(requete);
            System.out.println("Requete executé avec succès !");

            statement.close();
            // Fermer la connexion dans e main


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    //INSERT IN BDD WITH PREPARED STATEMENT ?????
    
    

    }
