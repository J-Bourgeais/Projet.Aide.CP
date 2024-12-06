import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.util.Scanner;
//import java.util.Date;

/*
 * INFORMATION : Cette classe groupe la majeure partie des actions qu'un utilisateur peut réaliser
 * Supprimer un compte, Répondre/Supprimer/Proposer/Modifier une requête, 
 * Consulter un profil et vérifier l'existence d'un nom dans la BDD
 */

public class User {

	
	public static boolean SupprimerCompte(Connection connexion, String email) {
		String deleteUser = "DELETE FROM Users WHERE email= ?";
		boolean ok=false;
	    try (PreparedStatement etat = connexion.prepareStatement(deleteUser)) {
	        etat.setString(1, email);
	        int lignesAffectees = etat.executeUpdate();
            if (lignesAffectees > 0) {
                ok=true;
            } 
	    } catch (SQLException e) {
            e.printStackTrace();
        }
	    return ok;
	}
	
	
    
    // Répondre à une offre de bénévole ou une demande de bénéficiaire
    public static void repondreRequete(Connection connexion, String NameRequete, String email) {
        String requeteSQL = "UPDATE requetes SET Status = ?, Benevole = ? WHERE NameRequete = ? AND Contact = ?";

        try (PreparedStatement etat = connexion.prepareStatement(requeteSQL)) {
            etat.setString(1, "acceptée");  // MAJ l’offre ou demande à "acceptée"
            etat.setString(2, InterfaceGUI.getEmail()); //email de la personne qui accepte
            etat.setString(3, NameRequete);
            etat.setString(4, email);
       

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
    

    
    //Possibilité de supprimer uniquement une de ses requêtes
    public static boolean SupprimerRequete(Connection connexion, String NameRequete, String email) {
    	boolean supp = false;
    	String deleteRequete = "DELETE FROM requetes WHERE NameRequete = ? and Contact = ?";
	    try (PreparedStatement stmt = connexion.prepareStatement(deleteRequete)) {
	        stmt.setString(1, NameRequete);
	        stmt.setString(2,  email);
	        int lignesAffectees = stmt.executeUpdate();
            if (lignesAffectees > 0) {
                System.out.println("Requête supprimée avec succès");
                supp=true;
            } else {
                System.out.println("Erreur : la requête n'a pas été trouvée ou a été mise à jour.");
            }
	    } catch (SQLException e) {
            e.printStackTrace();
        }
	    return supp;
 
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
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        if (checkName(connexion, nomRequete, Alluserinfos)){ //Verifier qu'aucune autre requete du même user n'a le meme nom
            String requeteSQL = "INSERT INTO requetes (NameRequete, FromUser, Description, Status, Date, TypeRequete, Contact) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement etat = connexion.prepareStatement(requeteSQL)) {
                etat.setString(1, nomRequete);    
                etat.setString(2, (String) Alluserinfos[0] + " " + Alluserinfos[1]); 
                etat.setString(3, description); 
                etat.setString(4, "En attente");          
                etat.setString(5, formatter.format(new java.sql.Date(new java.util.Date().getTime())));
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
        
    } 
    
    //N'est actuellement pas utilisé

    public static void modifierRequete(Connection connexion, String nomRequete, int choix, String change, Object[] Alluserinfos) {
    	//change sera le string du nouvel élément qu'on veut modifier : nouveau nom, nouvelle description, nouvelle date
    
        boolean modificationEffectuee = false;

        try  {

                String updateSQL = "UPDATE requetes SET "; // Début de la requête SQL

                // Gestion des choix
                switch (choix) {
                    case 1:
                    	if (checkName(connexion, change, Alluserinfos)) {
                    		updateSQL += "NameRequete = ? WHERE NameRequete = ?";
                            try (PreparedStatement stmt = connexion.prepareStatement(updateSQL)) {
                                stmt.setString(1, change);
                                stmt.setString(2, nomRequete); // Utilise le nom unique précédent de la requete
                                modificationEffectuee = stmt.executeUpdate() > 0;
                            }
                    	}
                        
                        break;

                    case 2:
  
                        updateSQL += "Description = ? WHERE NameRequete = ?";
                        try (PreparedStatement stmt = connexion.prepareStatement(updateSQL)) {
                            stmt.setString(1, change);
                            stmt.setString(2, nomRequete);
                            modificationEffectuee = stmt.executeUpdate() > 0;
                        }
                        break;

                  
                    default:
                        System.out.println("Choix invalide. Veuillez sélectionner une option valide.");
                }
        } catch (SQLException e) {
            e.printStackTrace();
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
    



}
