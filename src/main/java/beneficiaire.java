import java.util.ArrayList;
import java.util.List;

public class beneficiaire extends user {

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

    // Méthode pour afficher toutes les demandes
    @Override
    public void afficher() {
        super.afficher();
        System.out.println("Demandes formulées :");
        if (demandes.isEmpty()) {
            System.out.println("Aucune demande.");
        } else {
            for (demande d : demandes) {
                d.afficherRequete();
            }
        }
    }

    // Formuler une nouvelle demande
    public void formulerDemande(String nom, String description) {
        demande nouvelleDemande = new demande(nom, description);
        demandes.add(nouvelleDemande);
    }


    // Répondre à une offre de bénévole
    public void repondreOffre(offre offre, String message) {
        if (enCours == null) { // Vérifie si aucune offre n'est déjà en cours
            enCours = offre;
            offre.ajouterReponse("Réponse de " + getNom() + " " + getPrenom() + " : " + message);
            System.out.println("Vous avez accepté l'offre : " + offre.getNom());
        } else {
            System.out.println("Vous avez déjà une offre en cours : " + enCours.getNom());
        }
    }
}
