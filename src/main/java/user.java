import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;

// Colonnes DB - table User
// nom
// prenom
// email
// adresse
// age


public class user {

    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private int age;
    private String password;


    // Constructeur
    public user(String nom, String prenom, String email, String adresse, int age, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse=adresse;
        this.age = age;
        this.password= password;
    }

    // Si qqc est pas obligatoire : pour structure
    public user(String nom, String email, String adresse, String password) {
        this.nom = nom;
        this.email = email;
        this.adresse=adresse;
        this.password= password;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getAge() {
        return age;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Méthode pour afficher les informations de l'utilisateur
    public void afficher() {
    	System.out.println("*** Informations sur l'utilisateur ***");
        System.out.println("Nom: " + nom);
        System.out.println("Prénom: " + prenom);
        System.out.println("Email: " + email);
        System.out.println("Âge: " + age);
        System.out.println("Adresse: " + adresse);
        System.out.println("**************************************");
    }
	 
    
    // Répondre à une offre de bénévole ou une demande de bénéficiaire
    public void repondreRequete(Connection connexion, int idRequete) {
        String requeteSQL = "UPDATE requetes SET Status = ?, ContactUser = ? WHERE idrequetes = ? AND TypeRequete = 'offre'";

        try (PreparedStatement etat = connexion.prepareStatement(requeteSQL)) {
            etat.setString(1, "acceptée");  // MAJ l’offre à "acceptée"
            etat.setString(2, this.getEmail());
            etat.setInt(3, idRequete);

            int lignesAffectees = etat.executeUpdate();
            if (lignesAffectees > 0) {
                System.out.println("Offre acceptée avec succès !");
            } else {
                System.out.println("Erreur : l'offre n'a pas été trouvée ou mise à jour.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Proposer une offre via la classe offre
    public void proposerRequete(Connection connexion, String nom, String description, String typeRequete) {
        // SQL pour insérer l'offre dans la base de données
        String requeteSQL = "INSERT INTO requetes (NameRequete, FromUser, Description, Status, Date, TypeRequete, ContactUser) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement etat = connexion.prepareStatement(requeteSQL)) {
            etat.setString(1, nom);    
            etat.setString(2, this.getNom() + " " + this.getPrenom()); 
            etat.setString(3, description); 
            etat.setString(4, "En attente");          
            etat.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
            etat.setString(6, typeRequete);        
            etat.setString(7, this.getEmail());  
             

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

    public void modifierRequete(Connection connexion, requete requete) {
        Scanner scanner = new Scanner(System.in);
        boolean modificationEffectuee = false;

        try  {
            // Affichage des options de modifications à l'utilisateur
            System.out.println("Sélectionnez ce que vous souhaitez modifier :");
            System.out.println("1. Nom");
            System.out.println("2. Description");
            System.out.println("3. Date");
            System.out.println("4. Terminer les modifications");

            while (true){

                System.out.print("Choix : ");
                int choix = scanner.nextInt();
                scanner.nextLine(); // Consomme le saut de ligne après l'entier

                String updateSQL = "UPDATE requetes SET "; // Début de la requête SQL

                // Gestion des choix
                switch (choix) {
                    case 1:
                        System.out.print("Entrez le nouveau nom : ");
                        String nouveauNom = scanner.nextLine();
                        updateSQL += "NameRequete = ? WHERE idrequetes = ?";
                        try (PreparedStatement stmt = connexion.prepareStatement(updateSQL)) {
                            stmt.setString(1, nouveauNom);
                            stmt.setInt(2, requete.getId()); // Utilise l'ID de la requête
                            modificationEffectuee = stmt.executeUpdate() > 0;
                            requete.setNom(nouveauNom); // Met à jour l'objet localement
                        }
                        break;

                    case 2:
                        System.out.print("Entrez la nouvelle description : ");
                        String nouvelleDescription = scanner.nextLine();
                        updateSQL += "Description = ? WHERE idrequetes = ?";
                        try (PreparedStatement stmt = connexion.prepareStatement(updateSQL)) {
                            stmt.setString(1, nouvelleDescription);
                            stmt.setInt(2, requete.getId());
                            modificationEffectuee = stmt.executeUpdate() > 0;
                            requete.setDesc(nouvelleDescription);
                        }
                        break;

                    case 3:
                        System.out.print("Entrez la nouvelle date (format yyyy-MM-dd) : ");
                        String nouvelleDateStr = scanner.nextLine();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date nouvelleDate;
                        try {
                            nouvelleDate = sdf.parse(nouvelleDateStr);
                            updateSQL += "Date = ? WHERE idrequetes = ?";
                            try (PreparedStatement stmt = connexion.prepareStatement(updateSQL)) {
                                stmt.setDate(1, new java.sql.Date(nouvelleDate.getTime()));
                                stmt.setInt(2, requete.getId());
                                modificationEffectuee = stmt.executeUpdate() > 0;
                                requete.setDate(nouvelleDate);
                            }
                        } catch (ParseException e) {
                            System.out.println("Format de date incorrect. Veuillez réessayer.");
                        }
                        break;
                    case 4:
                        if (modificationEffectuee) {
                            System.out.println("Modifications enregistrées avec succès.");
                        } else {
                            System.out.println("Aucune modification enregistrée.");
                        }
                        return; // Quitte la boucle et termine la méthode
                    default:
                        System.out.println("Choix invalide. Veuillez sélectionner une option valide.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    public void consulterProfilUtilisateur(Connection connexion, String email) {
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
}


}
