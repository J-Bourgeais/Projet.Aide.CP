import java.sql.Connection;
import java.util.Scanner;

public class Menu {
    

    public static void ModifRequete(Connection connexion) {
        //Mdifier une demande ou une offre
        Scanner scannermodif = new Scanner(System.in);
        System.out.println("Voulez-vous modifier une de vos requêtes ? [y/n]");
        String choix = scannermodif.nextLine();
        if (choix=="y"){
        	System.out.println("Quel est le nom de la requête que vous souhaitez modifier ?");
        	String name = scannermodif.nextLine();
            user.modifierRequete(connexion, name);
        }
    }


    public static void ConsultRequete(Connection connexion, String email) {
        //Consulter une demande ou une offre
        //"vuez vus accepter cette requete ?"
        //Seln le status de la demande
        //Engagement a a faire si beneve


         Scanner scanner = new Scanner(System.in);
        System.out.println("Tapez 1 pour consulter vos requetes");
        System.out.println("Tapez 2 pour consulter toutes les offres existantes");
        System.out.println("Tapez 3 pour consulter toutes les demandes existantes");
        int choix = scanner.nextInt();
        switch (choix) {
            case 1 :
                requete.afficherRequetesParCritere("ContactUser",email);
                ModifRequete(connexion);
                break;
            case 2 :
                requete.afficherRequetesParCritere("Offre",email);
                
                //Un Beneficiaire doit pouvoir accepter cette offre --> Le benevole en est informé

                break;
            case 3 :
                requete.afficherRequetesParCritere("Demande",email);
                
                //Un benevole doit pouvoir remplir cette demande --> le beneficiaire en est informé
                //Le benevole ne peut accepté que si la demande a un statut de "validé"
                //La structure (verif avec Alluserinfos) peut la passer à "validé"
                

                break;
        }
        scanner.close();


    }

}
