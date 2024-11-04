import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    //Methode pour poster un avis à quelqu'un

	 public void posterAvis(user pourQui, int nbEtoiles, String description) {
	    	avis monAvis = new avis(this, pourQui, nbEtoiles,description);
	    	System.out.println("Votre avis a bien été posté " + monAvis.deQui.nom);
	 }
	 
	 public void posterAvis(user pourQui, int nbEtoiles) {
		 	avis monAvis = new avis(this, pourQui, nbEtoiles);
		 	System.out.println("Votre avis a bien été posté "  + monAvis.deQui.nom);
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
        String requeteSQL = "INSERT INTO requetes (NameRequete, FromUser, Description, Date, TypeRequete, ContactUser) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement etat = connexion.prepareStatement(requeteSQL)) {
            etat.setString(1, nom);    
            etat.setString(2, this.getNom() + " " + this.getPrenom()); 
            etat.setString(3, description);          
            etat.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
            etat.setString(5, typeRequete);        
            etat.setString(6, this.getEmail());  

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
