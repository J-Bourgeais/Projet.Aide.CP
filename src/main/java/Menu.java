import java.sql.Connection;
import java.util.Scanner;

public class Menu {
    

	//Cette fct sera remplacée par l'interface graphique
	//TODO
    public static void ModifRequete(Connection connexion) {
        //Mdifier une demande ou une offre
        Scanner scannermodif = new Scanner(System.in);
        System.out.println("Voulez-vous modifier une de vos requêtes ? [y/n]");
        String choix = scannermodif.nextLine();
        boolean contin;
        int nbr = 0;
        while(choix=="y") {
        	
        	System.out.println("Quel est le nom de la requête que vous souhaitez modifier ?");
        	String name = scannermodif.nextLine();
        	System.out.println("Que voulez vous modifier ?");
            System.out.println("1. Nom");
            System.out.println("2. Description");
            System.out.println("3. Date");
            nbr = scannermodif.nextInt();
            User.modifierRequete(connexion, name, nbr, "changement", Main.AllUserInfo(connexion, InterfaceGUI.getEmail()));
            System.out.println("Voulez-vous modifier autre chose ? [y/n]");
            choix=scannermodif.nextLine();
                
            
            //Format de la date : yyyy-MM-dd
         
        }
        
    }


}
