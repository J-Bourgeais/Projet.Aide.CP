//main 


import java.util.Scanner;
import java.sql.Connection;



public class main {

    public static void main(String[] args) {


      //--------------------------Création Base de Donnée User------------------------------
        
     // Direct sur le truc de bdd
    
      //--------------------------Création Base de Donnée Requête------------------------------
        
        // Direct sur le truc de bdd

      //--------------------------Inscription et connexion------------------------------
        //récuperer ce que l'user écris
        Scanner scanner = new Scanner(System.in);

        
        
        //Inscriptin
        System.out.println("Bienvenue sur notre plateforme d'aide. Tapez 1 si vous avez déjà un compte, et 2 si vous voulez vous inscrire");
        int choix1 = scanner.nextInt();
        
        switch (choix1) {
            case 1 :
                Connection connexion=ConnexionBDD.GetConnexion();
                UserConnect.UserConnection(connexion);
                ConnexionBDD.CloseConnexion(connexion);

           //Mauvaise gestion des case : apparait meme si 1
            case 2 :
                Connection connexion1=ConnexionBDD.GetConnexion();
                UserConnect.UserInscription(connexion1);
                ConnexionBDD.CloseConnexion(connexion1);
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
