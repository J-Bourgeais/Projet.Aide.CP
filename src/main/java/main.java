import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class main {
	
	/* SQL error in ALlUserInfo fct
	 * Il reste a mettre en option d'accepter une mission et de la valider
	 * présentation
	 * Erreur dans les line found --> toujours la meme : a résoudre
	 * */
	
	
    public static Object[] AllUserInfo(Connection connexion, String email){
        String requeteSQL = "SELECT Nom, Prenom, email, Adresse, Age, Password, UserType FROM Users WHERE " + email + " = ?";

        try (
             PreparedStatement stmt = connexion.prepareStatement(requeteSQL)) {
        	
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            String Nom=rs.getString("Nom");
            String Prenom=rs.getString("Prenom");
            String Email=rs.getString("email");
            String Adresse=rs.getString("Adresse");
            int Age=rs.getInt("Age");
            String Password=rs.getString("Password");
            String UserType=rs.getString("UserType");
            return new Object[]{Nom, Prenom, Email, Adresse, Age, Password, UserType};
            

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

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
	
	

    public static void main(String[] args) throws SQLException {

    	/*Pour tester :
    	 * User deja inscrit : 
    	 * - email : melo@gmail.com
    	 * - mdp : chien
    	 * */

    	
    	//Ouvrir une nouvelle connexion à chaque fois, ou la laisser ouverte tout du long ????

        
        //Inscription
        System.out.println("Bienvenue sur notre plateforme d'aide. Tapez 1 si vous avez déjà un compte, et 2 si vous voulez vous inscrire");
        boolean connected = false;
        String email="";
        Scanner scanner = new Scanner(System.in);

        Connection connexion=ConnexionBDD.GetConnexion();

        while (!connected) {
        	int choix1 = scanner.nextInt();
        	scanner.nextLine();
            
            switch (choix1) {
                case 1 :
                    //Connection connexion=ConnexionBDD.GetConnexion();
                    Object[] UserInfo = UserInfoConnexion();
                    email=(String)UserInfo[0];
                    connected = UserConnect.UserConnection(connexion, UserInfo);
                    //ConnexionBDD.CloseConnexion(connexion); //Pourquoi ici ? --> dépend de la question l 86
                    break;

                case 2 :
                    //Connection connexion1=ConnexionBDD.GetConnexion();
                    Object[] UserInfo2 = UserInfoInscription();
                    email=(String)UserInfo2[2];
                    connected= UserConnect.UserInscription(connexion, UserInfo2);
                    //ConnexionBDD.CloseConnexion(connexion); //Pourquoi ici ? --> dépend de la question l 86
                    break;
            }
            
        }
        scanner.close();
        
        Object[] Alluserinfos = AllUserInfo(connexion, email); //SQL error maybe here

        //Connection connexion = ConnexionBDD.GetConnexion(); 
        if (connected) {
        	// Affichage du menu à l'utilisateur
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Bienvenue dans le menu principal. Veuillez sélectionner une option :");
            System.out.println("1. Formuler une requête (offre ou demande)");
            System.out.println("2. Consulter des requêtes"); //Consulter les siennes, ceux des autres (offres, demandes), possibilité de valider et modifier une requete une fois la bas
            System.out.println("3. Poster un avis");
            System.out.println("4. Consulter les avis");
            System.out.println("5. Consulter un profil");
            System.out.println("6. Quitter\n\n");

            System.out.println("Il faut d'abord consulter les demandes afin de pouvoir les modifier ou les valider\n");
            System.out.println("-----------------------------------------------------------------------------------\n");

            boolean quit = false;

            while (!(quit)){
                // Récupérer le choix de l'utilisateur
                System.out.print("Tapez un numéro (1-6) : ");
                String input = scanner1.nextLine();      
                int choix = Integer.parseInt(input);
                
                System.out.print("Test");
                // Gestion des choix
                
                String nom, description, type, PourQui, prenom, DeQui, mail;
                
                switch (choix) {
                    case 1:
                        System.out.println("Vous avez choisi de formuler une requête.");
                        Scanner scanner2 = new Scanner(System.in);
                        System.out.println("Quel est le type de votre requête ? Tapez 'offre' ou 'requête'");
                        type = scanner2.nextLine();
                        System.out.println("Quel est le nom de votre requête ?");
                        nom = scanner2.nextLine();
                        System.out.println("Quelle est la description de votre requête ?");
                        description = scanner2.nextLine();
                        user.proposerRequete(connexion, nom, description, type, Alluserinfos);
                        scanner2.close();
                        break;
                    case 2:
                        System.out.println("Vous avez choisi de consulter les requêtes.");
                        Menu.ConsultRequete(connexion, email);
                        Scanner scanner6 = new Scanner(System.in);
                        System.out.println("Voulez-vous accepter une de ces requêtes ? [y/n]");
                        String accept = scanner6.nextLine();
                        if (accept=="y") {
                        	System.out.println("Quelle est le nom de la requête ?");
                        	nom=scanner6.nextLine();
                        	System.out.println("Quel est l'adresse mail de l'émetteur de la requête ?");
                        	mail=scanner6.nextLine();
                        	user.repondreRequete(connexion, nom, mail);
                        }
                        if(Alluserinfos[6]=="Structure") {
                        	System.out.println("Voulez-vous changer le status d'une de ces requêtes ? [y/n]");
                        	accept = scanner6.nextLine();
                            if (accept=="y") {
                            	System.out.println("Quelle est le nom de la requête ?");
                            	nom=scanner6.nextLine();
                            	System.out.println("Quel est l'adresse mail de l'émetteur de la requête ?");
                            	mail=scanner6.nextLine();
                            	System.out.println("Tapez 1 si la requête est validée, 2 si elle est refusée");
                            	int val = scanner6.nextInt();
                            	String raison="";
                            	boolean valide;
                            	if (val==1) {
                            		valide=true;
                            	} else {
                            		valide=false;
                            		System.out.println("Quelle est la raison du refus ?");
                            		raison=scanner6.nextLine();
                            	}
                            	structure.validerService(connexion, nom, mail, valide, raison);
                            	
                            }
                        }
                        
                        break;
                    case 3:
                        System.out.println("Vous avez choisi de poster un avis");
                        Scanner scanner3 = new Scanner(System.in);
                        System.out.println("Votre avis est à destination de qui ? (Nom Prenom)");
                        PourQui = scanner3.nextLine();
                        String[] words = PourQui.split(" "); //MELO
                        nom=words[0];
                        prenom=words[1];
                        System.out.println("Combien d'étoile donnez vous au service par/pour "+ prenom + " ?");
                        int etoile = scanner3.nextInt();
                        System.out.println("Décrivez votre expérience. Tapez simplement un espace si vous ne souhaitez rien écrire");
                        description = scanner3.nextLine();
                        Avis.posterAvis(connexion, nom, prenom, etoile, description);
                        scanner3.close();
                        break;

                    case 4:
                        System.out.println("Vous avez choisi de consulter les avis");
                        Scanner scanner4 = new Scanner(System.in);
                        System.out.println("De qui voulez vous consulter les avis (Nom Prenom)");
                        DeQui = scanner4.nextLine();
                        String[] word = DeQui.split(" ");
                        nom=word[0];
                        prenom=word[1];
                        Avis.consulterAvis(connexion, nom, prenom);
                        scanner4.close();
                        break;
                    case 5:
                        System.out.println("Vous avez choisi de consulter un profil");
                        Scanner scanner5 = new Scanner(System.in);
                        System.out.println("Quelle est l'adresse mail de la personne dont vous souhaitez consulter le profil ?");
                        email = scanner5.nextLine();
                        user.consulterProfilUtilisateur(connexion, email);
                        break;
                    case 6:
                        System.out.println("Vous avez choisi de quitter l'application. A bientot !");
                        ConnexionBDD.CloseConnexion(connexion);
                        quit=true;
                        break;
                    default:
                        System.out.println("Option non valide. Veuillez entrer un numéro entre 1 et 6.");
                }
            }
         scanner1.close();
        }

        

        

        
    }
}
