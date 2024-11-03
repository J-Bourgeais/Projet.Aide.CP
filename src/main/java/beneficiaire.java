import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class beneficiaire extends user {

    //Listes plus vraiment utiles
    protected List<demande> demandes; // Liste des demandes du bénéficiaire
    protected offre enCours = null; // Offre en cours acceptée par le bénéficiaire

    public beneficiaire(String nom, String prenom, String email, String adresse, int age, String password) {
        super(nom, prenom, email, adresse, age, password);
        this.demandes = new ArrayList<>();
    }

    // Obtenir les demandes (via la liste des demandes)
    public List<demande> getDemandes() {
        return demandes;
    }

    // Méthode pour afficher toutes les demandes --> A modifier version sql
    @Override
    public void afficher() {
        super.afficher();
        System.out.println("Demandes formulées :");
        if (demandes.isEmpty()) {
            System.out.println("Aucune demande.");
        } else {
            for (demande d : demandes) {
                System.out.println("Demande : " + d.nom + ", Description : " + d.desc + ", Status : " + d.status);
            }
        }
    }
    
    // Formuler une nouvelle demande
    public void formulerDemande(Connection connexion, String nom, String description) {
        // Création de l'objet demande
        demande nouvelleDemande = new demande(nom, description); //Plus vraiment utile
        //demandes.add(nouvelleDemande); // Plus utile avec SQL

        //  SQL pour insérer la demande dans la base de données
        String requeteSQL = "INSERT INTO requetes (NameRequete, FromUser, Description, Date, TypeRequete, ContactUser, Status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement etat = connexion.prepareStatement(requeteSQL)) {
            etat.setString(1, nom);                
            etat.setString(2, this.getNom() + " " + this.getPrenom());
            etat.setString(3, description);           
            etat.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
            etat.setString(5, "demande");              
            etat.setString(6, this.getEmail());  
            etat.setString(7, "En attente");             

            int lignesAffectees = etat.executeUpdate();
            if (lignesAffectees > 0) {
                System.out.println("Demande ajoutée avec succès dans la base de données !");
            } else {
                System.out.println("L'ajout de la demande a échoué.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Répondre à une offre de bénévole
    public void repondreOffre(Connection connexion, int idOffre) {
        String requeteSQL = "UPDATE requetes SET Status = ?, ContactUser = ? WHERE idrequetes = ? AND TypeRequete = 'offre'";

        try (PreparedStatement etat = connexion.prepareStatement(requeteSQL)) {
            etat.setString(1, "acceptée");  // MAJ l’offre à "acceptée"
            etat.setString(2, this.getEmail());
            etat.setInt(3, idOffre);

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

}
