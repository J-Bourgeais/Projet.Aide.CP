import java.util.ArrayList;
import java.util.List;

public class offre extends requete {
    private final List<String> reponses; // Liste des réponses des bénéficiaires

    public offre(String nom, String description) {
        super(nom, description);
        this.reponses = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    // Ajouter une réponse d'un bénéficiaire
    public void ajouterReponse(String reponse) {
        reponses.add(reponse);
    }

    // Afficher les détails de l'offre et les réponses
    @Override
    public void afficherRequete() {
        System.out.println("Offre : " + nom + " - " + desc);
        System.out.println("Statut : " + status);
        System.out.println("Date : " + date);
        System.out.println("Réponses des bénéficiaires :");
        if (reponses.isEmpty()) {
            System.out.println("Aucune réponse pour cette offre.");
        } else {
            for (String reponse : reponses) {
                System.out.println("- " + reponse);
            }
        }
    }
}
