//benev
//Beneficiaire

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class benev extends user {

    // A modifier type OFFRE @Julie du coup au vu du commentaire plus bas sur proposerOffre j'ai mis offre ici et non demande
    protected List<offre> offres;
    protected List<offre> demandes;
    protected List<demande> enCours; // Demandes en cours de réalisation

    // Constructeur avec toutes les informations
    public benev(String nom, String prenom, String email, String adresse, int age, String password) {
        super(nom, prenom, email, adresse, age, password);
        this.offres = new ArrayList<>();
        this.enCours = new ArrayList<>();
        this.demandes = new ArrayList<>();
    }

    // Getter pour obtenir les offres
    public List<offre> getOffres() {
        return offres;
    }

    // Setter pour modifier les offres
    public void setOffres(List<offre> offres) {
        this.offres = offres;
    }

    // afficher les informations du bénévole et ses offres
    @Override
    public void afficher() {
        super.afficher();
        System.out.println("Offres proposées :");
        if (offres.isEmpty()) {
            System.out.println("Aucune offre.");
        } else {
            for (offre offre : offres) {
                System.out.println("- " + offre.getNom() + ": " + offre.getDesc());
            }
        }
        // Afficher les requêtes associées à ce bénévole par email
        System.out.println("Requêtes associées :");
        requete.afficherRequetesParCritere("ContactUser", this.getEmail());
    }


    // Proposer une offre via la classe offre
    public void proposerOffre(Connection connexion, String nom, String description) {
        // Création de l'objet offre
        offre nouvelleOffre = new offre(nom, description);
        offres.add(nouvelleOffre);

        // SQL pour insérer l'offre dans la base de données
        String requeteSQL = "INSERT INTO requetes (NameRequete, FromUser, Description, Date, TypeRequete, ContactUser) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement etat = connexion.prepareStatement(requeteSQL)) {
            etat.setString(1, nom);    
            etat.setString(2, this.getNom() + " " + this.getPrenom()); 
            etat.setString(3, description);          
            etat.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
            etat.setString(5, "offre");        
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

    // Répondre à une demande en utilisant l'email
    public void repondreDemande(String emailBeneficiaire, String idDemande, String reponse) {
        // Enregistrer la réponse à la demande dans la base de données
        String requeteSQL = "INSERT INTO ReponsesDemande (EmailBenevole, IdDemande, Reponse) VALUES (?, ?, ?)";

        try (Connection connexion = ConnexionBDD.GetConnexion();
             PreparedStatement stmt = connexion.prepareStatement(requeteSQL)) {
             
            stmt.setString(1, this.getEmail()); 
            stmt.setString(2, idDemande); 
            stmt.setString(3, reponse); 

            int lignesAffectees = stmt.executeUpdate();
            if (lignesAffectees > 0) {
                System.out.println("Réponse enregistrée avec succès !");
            } else {
                System.out.println("Erreur lors de l'enregistrement de la réponse.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
