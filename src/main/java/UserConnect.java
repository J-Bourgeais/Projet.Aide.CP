import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserConnect {
    
    public static boolean UserConnection(Connection connexion) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Veuillez entrer votre adresse mail");
        String email = scanner.nextLine();
        //On dirait que ca s'affiche d'un coup --> Voir pourquoi
        System.out.println("Veuillez entrer votre mot de passe");
        String password = scanner.nextLine();
        
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

    public static boolean UserInscription(Connection connexion) {

        Scanner scanner = new Scanner(System.in);
        
        boolean succes = false;

        System.out.println("Veuiller saisir vos informations");
        System.out.println("Nom : ");
        String nom = scanner.nextLine();

        System.out.println("Prénom : ");
        String prenom = scanner.nextLine();

        System.out.println("Email : ");
        String email = scanner.nextLine();

        System.out.println("Adresse : ");
        String adresse = scanner.nextLine();

        System.out.println("Age : ");
        int age = scanner.nextInt();
        scanner.nextLine();  // Nettoyer la ligne restante

        System.out.println("Mot de passe : ");
        String password = scanner.nextLine();

        System.out.println("Type (Benevole, Beneficiaire, Structure) : ");
        String type = scanner.nextLine();

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
