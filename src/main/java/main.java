//main 


import java.util.Scanner;
import java.sql.Connection;



public class main {
	
	
	public static Object[] UserInfoConnexion() {
		Scanner scanner = new Scanner(System.in);

        System.out.println("Veuillez entrer votre adresse mail");
        String email = scanner.nextLine();
        //On dirait que ca s'affiche d'un coup --> Voir pourquoi
        System.out.println("Veuillez entrer votre mot de passe");
        String password = scanner.nextLine();
        scanner.close();
        return new Object[]{email, password};
	}
	
	public static Object[] UserInfoInscription() {
		Scanner scanner = new Scanner(System.in);

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
        scanner.close();
        return new Object[]{nom, prenom, email, adresse, age, password, type};
	}
	
	

    public static void main(String[] args) {

    	/*Pour tester :
    	 * User deja inscrit : 
    	 * - email : melo@gmail.com
    	 * - mdp : chien
    	 * 
    	 *	d'autres infos --> Ne se connecte pas 
    	 *
    	 * 
    	 * */
        
        //Inscription
        System.out.println("Bienvenue sur notre plateforme d'aide. Tapez 1 si vous avez déjà un compte, et 2 si vous voulez vous inscrire");
        boolean connected = false;
        Scanner scanner = new Scanner(System.in);
        while (!connected) {
        	int choix1 = scanner.nextInt();
            
            switch (choix1) {
                case 1 :
                    Connection connexion=ConnexionBDD.GetConnexion();
                    connected = UserConnect.UserConnection(connexion, UserInfoConnexion());
                    ConnexionBDD.CloseConnexion(connexion);
                    break;

               //Mauvaise gestion des case : apparait meme si 1
                case 2 :
                    Connection connexion1=ConnexionBDD.GetConnexion();
                    connected = UserConnect.UserInscription(connexion1, UserInfoInscription());
                    ConnexionBDD.CloseConnexion(connexion1);
                    break;
            }
            scanner.close();
        }
        
        
        if (connected) {
        	// Affichage du menu à l'utilisateur
            System.out.println("Bienvenue dans le menu principal. Veuillez sélectionner une option :");
            System.out.println("1. Formuler une demande");
            System.out.println("2. Consulter vos demandes");
            System.out.println("3. Supprimer une demande");
            System.out.println("4. Quitter");

             // Mettre + de choix et rajouter (si structure) ValiderRequete()

            boolean quit = false;
            Scanner scanner1 = new Scanner(System.in);

            while (!(quit)){
                // Récupérer le choix de l'utilisateur
                System.out.print("Tapez un numéro (1-4) : ");
                //y'a rien ecrit, ca capte rien
                
                //ca laisse pas le temps d'écrire
                
                //pourquoi ????
                int choix = 0;
                if (scanner1.hasNextInt()) {
                	choix=scanner1.nextInt();
                } else {
                	choix=0;
                	System.out.println("nothing written");
                	break;
                }
                

                
                System.out.print("Test");


                // Gestion des choix
                switch (choix) {
                    case 1:
                        System.out.println("Vous avez choisi de formuler une demande.");
                        Menu.DoRequete();
                        break;
                    case 2:
                        System.out.println("Vous avez choisi de consulter vos demandes.");
                        Menu.ConsultRequete();
                        break;
                    case 3:
                        System.out.println("Vous avez choisi de supprimer votre demande.");
                        Menu.ModifRequete();
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
            scanner1.close();
        }

        

        

        
    }
}
