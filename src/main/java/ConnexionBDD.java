import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnexionBDD {

    public static Connection GetConnexion() {

        
      //--------------------------Info de Connexion------------------------------
        String url = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_035"; 
        String user = "projet_gei_035";  
        String mdp = "quiaw0Di";  
        Connection connexion = null;

        try {
            System.out.println("//////");
            // Établir la connexion à la base de données
            connexion = DriverManager.getConnection(url, user, mdp);  
            System.out.println("****");

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




    }
