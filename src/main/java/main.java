//main 
/* Un menu : que voulez vous faire : 
1. S'inscire
2. ...
et on appelle la fct correspondant
 * Créer des gens
 * "Voulez vous vous inscrire ?"
 * oui
 * non
 * 
 * SI ui
 * entrz nm, prénm, ...
 * 
 * Vus etes qui ?
 * 
 * ENtrez de telle ou telle manière 
 * 
 * on créé le mec
 * 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        //récuperer ce que l'user écris


        //Liaison SQL
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Chargement du driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3306/SQL_Project"; // Remplace par ton URL de base de données
        String user = "projet_gei_035";  // Nom d'utilisateur de la base
        String password = "quiaw0Di";  // Mot de passe de la base
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion réussie !");
        } catch (SQLException e) {
            e.printStackTrace();
        }



        Scanner scanner = new Scanner(System.in);

        //Se connecter ou s'inscrire
        //TODO
        //Case et connection --> Faire 


        // Affichage du menu à l'utilisateur
        System.out.println("Bienvenue dans le menu principal. Veuillez sélectionner une option :");
        System.out.println("1. Formuler une demande");
        System.out.println("2. Consulter vos demandes");
        System.out.println("3. Supprimer une demande");
        System.out.println("4. Quitter");

        boolean quit = false;

        while (!(quit)){
            // Récupérer le choix de l'utilisateur
            System.out.print("Tapez un numéro (1-4) : ");
            int choix = scanner.nextInt();

            // Gestion des choix
            switch (choix) {
                case 1:
                    System.out.println("Vous avez choisi de formuler une demande.");
                    //formuler une demande
                    break;
                case 2:
                    System.out.println("Vous avez choisi de consulter vos demandes.");
                    //consulter une demande
                    break;
                case 3:
                    System.out.println("Vous avez choisi de supprimer votre demande.");
                    //modifier une demande
                    break;
                case 4:
                    System.out.println("Vous avez choisi de quitter l'application. A bientot !");
                    quit=true;
                    break;
                default:
                    System.out.println("Option non valide. Veuillez entrer un numéro entre 1 et 4.");
            }
        }

        

        // Fermeture du scanner
        scanner.close();
    }
}
