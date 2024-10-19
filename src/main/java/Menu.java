import java.sql.Connection;
import java.util.Scanner;

public class Menu {
    
    public static void DoRequete() {
        //formuler une demande ou une offre
    }


    public static void ModifRequete() {
        //Mdifier une demande ou une offre
    }


    public static void ConsultRequete(String email) {
        //Consulter une demande ou une offre
        //"vuez vus accepter cette requete ?"
        //Seln le status de la demande
        //Engagement a a faire si beneve




         Scanner scanner = new Scanner(System.in);
        System.out.println("Tapez 1 pour consulter vos requetes");
        System.out.println("Tapez 2 pour consulter les offres existantes (en general, de tout le monde)");
        System.out.println("Tapez 3 pour consulter les demandes existantes (en general, de tout le monde)");
        int choix = scanner.nextInt();
        switch (choix) {
            case 1 :
                requete.afficherRequetesParEmail(email);
                break;
            case 2 :
                requete.afficherRequetesParType("Offre");
                //"vuez vus accepter cette requete ?"
        //Seln le status de la demande
        //Engagement a a faire si beneve

                break;
            case 3 :
                requete.afficherRequetesParType("Demande");
                //"vuez vus accepter cette requete ?"
        //Seln le status de la demande
        //Engagement a a faire si beneve
                break;
        }
        scanner.close();


    }

    public static void ConsultProfil() {
        //Consulter un Profil
    }

    public static void ValiderRequete() {
        //Valider une demande ou une offre
        //Structure uniquement
    }


}
