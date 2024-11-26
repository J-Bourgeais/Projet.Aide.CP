import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;



public class User {

    
    // Répondre à une offre de bénévole ou une demande de bénéficiaire
    public static void repondreRequete(Connection connexion, String NameRequete, String email) {
        String requeteSQL = "UPDATE requetes SET Status = ? WHERE NameRequete = ? AND Contact = ?";

        try (PreparedStatement etat = connexion.prepareStatement(requeteSQL)) {
            etat.setString(1, "acceptée");  // MAJ l’offre ou demande à "acceptée"
            etat.setString(2, NameRequete);
            etat.setString(3, email);

            int lignesAffectees = etat.executeUpdate();
            if (lignesAffectees > 0) {
                System.out.println("Requête acceptée avec succès !");
            } else {
                System.out.println("Erreur : la requête n'a pas été trouvée ou a été mise à jour.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static boolean checkName(Connection connexion, String nomRequete, Object[] Alluserinfos){
        String requeteSQL = "SELECT NameRequete FROM requetes WHERE Contact = ?";

        try (
            PreparedStatement stmt = connexion.prepareStatement(requeteSQL)) {
            stmt.setString(1, (String) Alluserinfos[2]);
            ResultSet rs = stmt.executeQuery();
            boolean ok=true;

            while (rs.next()) {
                if (rs.getString("NameRequete")==nomRequete){
                    ok=false;
                }
            }
            return ok;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Proposer une offre via la classe offre
    public static void proposerRequete(Connection connexion, String nomRequete, String description, String typeRequete, Object[] Alluserinfos) {
        
        //Alluserinfos : Nom, Prenom, email, Adresse, Age, Password, UserType

        if (checkName(connexion, nomRequete, Alluserinfos)){ //Verifier qu'aucune autre requete du même user n'a le meme nom
            String requeteSQL = "INSERT INTO requetes (NameRequete, FromUser, Description, Status, Date, TypeRequete, Contact) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement etat = connexion.prepareStatement(requeteSQL)) {
                etat.setString(1, nomRequete);    
                etat.setString(2, (String) Alluserinfos[0] + " " + Alluserinfos[1]); 
                etat.setString(3, description); 
                etat.setString(4, "En attente");          
                etat.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
                etat.setString(6, typeRequete);        
                etat.setString(7, (String) Alluserinfos[2]);  
                

                int lignesAffectees = etat.executeUpdate();
                if (lignesAffectees > 0) {
                    System.out.println("Offre ajoutée avec succès dans la base de données !");
                } else {
                    System.out.println("L'ajout de l'offre a échoué.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
    } //OK INTERFACE

    
    //Interdire d'appeler 2 de ses requete pareil
    
    //N'est actuellement pas utilisé

    public static void modifierRequete(Connection connexion, String nomRequete, int choix) {
    	System.out.println("kikou");
        Scanner scanner = new Scanner(System.in);
        boolean modificationEffectuee = false;

        try  {
            // Affichage des options de modifications à l'utilisateur
            /*System.out.println("Sélectionnez ce que vous souhaitez modifier :");
            System.out.println("1. Nom");
            System.out.println("2. Description");
            System.out.println("3. Date");
            System.out.println("4. Terminer les modifications");*/

            //while (true){

                System.out.print("Choix : ");
                //int choix = scanner.nextInt();
                //scanner.nextLine(); // Consomme le saut de ligne après l'entier

                String updateSQL = "UPDATE requetes SET "; // Début de la requête SQL

                // Gestion des choix
                switch (choix) {
                    case 1:
                        System.out.print("Entrez le nouveau nom : ");
                        String nouveauNom = scanner.nextLine();
                        updateSQL += "NameRequete = ? WHERE NameRequete = ?";
                        try (PreparedStatement stmt = connexion.prepareStatement(updateSQL)) {
                            stmt.setString(1, nouveauNom);
                            stmt.setString(2, nomRequete); // Utilise le nom unique précédent de la requete
                            modificationEffectuee = stmt.executeUpdate() > 0;
                            //requete.setNom(nouveauNom); // Met à jour l'objet localement --> On a rien localement
                        }
                        break;

                    case 2:
                        System.out.print("Entrez la nouvelle description : ");
                        String nouvelleDescription = "Description";
                        if(scanner.hasNextLine()) {
                        	nouvelleDescription = scanner.nextLine();
                        } 
                        updateSQL += "Description = ? WHERE NameRequete = ?";
                        try (PreparedStatement stmt = connexion.prepareStatement(updateSQL)) {
                            stmt.setString(1, nouvelleDescription);
                            stmt.setString(2, nomRequete);
                            modificationEffectuee = stmt.executeUpdate() > 0;
                            //requete.setDesc(nouvelleDescription); --> On a rien en local
                        }
                        break;

                    case 3:
                        System.out.print("Entrez la nouvelle date (format yyyy-MM-dd) : ");
                        String nouvelleDateStr = scanner.nextLine();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date nouvelleDate;
                        try {
                            nouvelleDate = sdf.parse(nouvelleDateStr);
                            updateSQL += "Date = ? WHERE NameRequete = ?";
                            try (PreparedStatement stmt = connexion.prepareStatement(updateSQL)) {
                                stmt.setDate(1, new java.sql.Date(nouvelleDate.getTime()));
                                stmt.setString(2, nomRequete);
                                modificationEffectuee = stmt.executeUpdate() > 0;
                                //requete.setDate(nouvelleDate); --> On a rien en local
                            }
                        } catch (ParseException e) {
                            System.out.println("Format de date incorrect. Veuillez réessayer.");
                        }
                        break;
                    /*case 4:
                        if (modificationEffectuee) {
                            System.out.println("Modifications enregistrées avec succès.");
                        } else {
                            System.out.println("Aucune modification enregistrée.");
                        }
                        return; // Quitte la boucle et termine la méthode*/
                    default:
                        System.out.println("Choix invalide. Veuillez sélectionner une option valide.");
                }
            //}
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    //Retourne les infos du profil pour ensuite être affiché par InterfaceGUI
    
    public static Object[] consulterProfilUtilisateur(Connection connexion, String email) {
        String query = "SELECT Nom, Prenom, email, Adresse, Age, UserType FROM Users WHERE email = ?";
        
        Object[] profil = null;
        
        try (PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            // verif si le profil existe et afficher les informations
            if (rs.next()) {
            	String Nom = rs.getString("Nom");
                String Prenom = rs.getString("Prenom");
                String Email = rs.getString("email");
                String Adresse = rs.getString("Adresse");
                int Age = rs.getInt("Age");
                String UserType = rs.getString("UserType");

                // Return an array with all the user information
                profil = new Object[]{Nom, Prenom, Email, Adresse, Age, UserType};
       
            } else {
                System.out.println("Aucun profil trouvé pour cet email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profil;

    }
    
    
    /*public static void consulterProfilUtilisateur(Connection connexion, String email) {
    String query = "SELECT Nom, Prenom, email, Adresse, Age, UserType FROM Users WHERE email = ?";
    
    try (PreparedStatement stmt = connexion.prepareStatement(query)) {
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        
        // verif si le profil existe et afficher les informations
        if (rs.next()) {
            System.out.println("Profil de l'utilisateur :");
            System.out.println("Nom : " + rs.getString("nom"));
            System.out.println("Prénom : " + rs.getString("prenom"));
            System.out.println("Email : " + rs.getString("email"));
            System.out.println("Adresse : " + rs.getString("adresse"));
            System.out.println("Âge : " + rs.getInt("age"));
            System.out.println("Type de compte : " + rs.getString("UserType")); // benevole ou beneficiaire
        } else {
            System.out.println("Aucun profil trouvé pour cet email.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}*/


}
