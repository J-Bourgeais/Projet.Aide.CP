import java.util.ArrayList;
import java.util.List;

public class offre extends requete {
    private final List<String> reponses; // Liste des réponses des bénéficiaires

    public offre(String nom, String description) {
        super(nom, description);
        this.reponses = new ArrayList<>();
    }

    // Ajouter une réponse d'un bénéficiaire
    public void ajouterReponse(String reponse) {
        reponses.add(reponse);
    }

}
