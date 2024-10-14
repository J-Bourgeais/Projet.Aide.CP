//main 


import java.util.Scanner;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;


public class main {

    public static void main(String[] args) {



        //ConnexionBDD.GetConnexion();



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
        Connection connection = null;

       
      //--------------------------Création Base de Donnée User------------------------------
        
     // Requête SQL pour créer la table utilisateurs
        String creationTableSQL = "CREATE TABLE IF NOT EXISTS utilisateurs ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "nom VARCHAR(50), "
                + "prenom VARCHAR(50), "
                + "email VARCHAR(100), "
                + "adresse VARCHAR(100), "
                + "age INT, "
                + "password VARCHAR(50), "
                + "type VARCHAR(50)"
                + ")";
        
        
        try {
            // Établir la connexion à la base de données
            Connection connexion = DriverManager.getConnection(url, user, mdp);

            // Créer un objet Statement pour exécuter des requêtes SQL
            Statement statement = connexion.createStatement();

            // Exécuter la requête pour créer la table
            statement.execute(creationTableSQL);
            System.out.println("Table 'utilisateurs' créée avec succès (ou existante déjà) !");

            // Fermer la connexion
            statement.close();
            connexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        
        
        
        
      //--------------------------Création Base de Donnée Requête------------------------------
        
     // Demander comment on sait qu'on check-utilise cette bdd ou l'autre sur MySql via sdbd
        
        
        // Requête SQL pour créer la table utilisateurs
           String creationTableRequete = "CREATE TABLE IF NOT EXISTS utilisateurs ("
                   + "id INT AUTO_INCREMENT PRIMARY KEY, "
                   + "nom VARCHAR(100), "
                   + "description VARCHAR(50), "
                   + "date Date, "
                   + "status VARCHAR(100), "
                   + "type VARCHAR(50)"
                   + ")";
           
           
           try {
               // Établir la connexion à la base de données
               Connection connexion = DriverManager.getConnection(url, user, mdp);
   
               // Créer un objet Statement pour exécuter des requêtes SQL
               Statement statement = connexion.createStatement();
   
               // Exécuter la requête pour créer la table
               statement.execute(creationTableRequete);
               System.out.println("Table 'requête' créée avec succès (ou existante déjà) !");
   
               // Fermer la connexion
               statement.close();
               connexion.close();
   
           } catch (SQLException e) {
               e.printStackTrace();
           }
       
        
        
        
        
        
      //--------------------------Inscription et connexion------------------------------
        //récuperer ce que l'user écris
        Scanner scanner = new Scanner(System.in);
        
        //Inscriptin
        System.out.println("Bienvenue sur ntre patefrme d'aide. Tapez 1 si vus avez déjà un cmpte, et 2 si vus vuez vus inscrire");
        int choix1 = scanner.nextInt();
        
        switch (choix1) {
            case 1 :
                System.out.println("Veuier entrer vtre adresse mai");
                String email1 = scanner.nextLine();
                //On dirait que ca s'affiche d'un coup --> Voir pourquoi
                System.out.println("Veuier entrer vtre mt de passe");
                String password1 = scanner.nextLine();
                
                String requeteSQL = "SELECT * FROM utilisateurs WHERE email = ? AND password = ?";
                try {
                    connection = DriverManager.getConnection(url, user, mdp);
                    PreparedStatement preparedStatement = connection.prepareStatement(requeteSQL);
                    // Définir les paramètres du preparedStatement
                    preparedStatement.setString(1, email1);
                    preparedStatement.setString(2, password1);

                    ResultSet resultat = preparedStatement.executeQuery();

                    if (resultat.next()) {
                        System.out.println("Vous êtes connecté !");
                    } else {
                        System.out.println("Mauvaise email et/ou mauvaix mdp. Avez vous un compte ?");
                    }

                    // Fermer les ressources
                    resultat.close();
                    preparedStatement.close();
                    connection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }                 
           
           //Mauvaise gestion des case : apparait meme si 1
            case 2 :
                //Demander chacun des eements
                
                System.out.println("Veuier saisir vs infrmatins");
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

                String strInsert = "INSERT INTO utilisateurs (nom, prenom, email, adresse, age, password, type) VALUES (?, ?, ?, ?, ?, ?, ?)";
                
              //Insertin
                
                try {
                    connection = DriverManager.getConnection(url, user, mdp);
                    System.out.println("Connexion réussie !");
                    PreparedStatement preparedStatement = connection.prepareStatement(strInsert);
                    preparedStatement.setString(1, nom);
                    preparedStatement.setString(2, prenom);
                    preparedStatement.setString(3, email);
                    preparedStatement.setString(4, adresse);
                    preparedStatement.setInt(5, age);
                    preparedStatement.setString(6, password);
                    preparedStatement.setString(7, type);
                    
                    int lignesAffectees = preparedStatement.executeUpdate();
                    
                    if (lignesAffectees > 0) {
                        System.out.println("Cmpte créé avec succès !");
                    } else {
                        System.out.println("L'inscriptin a échoué.");
                    }
                    
                    preparedStatement.close();
                    connection.close();
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                

                /* Est ce qu'il y a vraiment besoin de classes dans ce cas ?
                 * On a créé nos utilisateurs dans la base de données,
                 et ils peuvent avoir leurs choix d'actions ici (via des case)
                 C'est pas très beau, mais à quoi servirais le reste ?
                 On peut rajouter un élément dans la base de donnée "Requêtes" qui est un tableau
                 */
        
        }
        
        
        //Le mettre dans un if connecté
        
        //Peut etre faire des sous programme de gestion des inscription, connexion, menu
        //SOulage main mais pas vraiment utile ??


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
