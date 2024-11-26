import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserConnect {
	

    //Utilisé par InterfaceGUI
    public static boolean UserConnection(Connection connexion, Object[] infos) {
        Scanner scanner = new Scanner(System.in);

        String email = (String) infos[0];
        String password = (String) infos[1];
        
        String requeteSQL = "SELECT * FROM Users WHERE email = ? AND password = ?";
        boolean connected = false;

        try {
                    
            PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL);
            // Définir les paramètres du preparedStatement
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultat = preparedStatement.executeQuery();
          

            if (resultat.next()) {
                System.out.println("Vous êtes connecté !");
                connected = true;
            } else {
                System.out.println("Mauvaise email et/ou mauvaix mdp. Avez vous un compte ?");
            }

            // Fermer les ressources
            resultat.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        scanner.close();
        return connected;

    }

    
    //Utilisé par InterfaceGUI
    public static boolean UserInscription(Connection connexion, Object[] infos) {

        Scanner scanner = new Scanner(System.in);
        
        boolean succes = false;

        String nom = (String) infos[0];
        String prenom = (String) infos[1];
        String email = (String) infos[2];
        String adresse =(String) infos[3];
        int age = (int) infos[4];
        String password = (String) infos[5];
        String type = (String) infos[6];

        String strInsert = "INSERT INTO Users (Nom, Prenom, email, Adresse, Age, Password, UserType) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {

            PreparedStatement preparedStatement = connexion.prepareStatement(strInsert);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, adresse);
            preparedStatement.setInt(5, age);
            preparedStatement.setString(6, password);
            preparedStatement.setString(7, type);
            
            int lignesAffectees = preparedStatement.executeUpdate();
            
            if (lignesAffectees > 0) {
                System.out.println("Compte créé avec succès !");
                succes=true;
            } else {
                System.out.println("L'inscription a échoué.");
            }
            
            preparedStatement.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        scanner.close();
        return succes;

    }



}
