//benev
//Beneficiaire

import java.util.ArrayList;
import java.util.List;

public class benev extends user {

    // A modifier type OFFRE @Julie du coup au vu du commentaire plus bas sur proposerOffre j'ai mis offre ici et non demande
    protected List<offre> offres;
    protected List<demande> enCours; // Demandes en cours de réalisation

    // Constructeur avec toutes les informations
    public benev(String nom, String prenom, String email, String adresse, int age, String password) {
        super(nom, prenom, email, adresse, age, password);
        this.offres = new ArrayList<>(); // Initialisation de la liste d'offres
        this.enCours = new ArrayList<>(); // Initialisation de la liste de demandes en cours
    }

    // Getter pour obtenir les offres
    public List<offre> getOffres() {
        return offres;
    }

    // Setter pour modifier les offres
    public void setOffres(List<offre> offres) {
        this.offres = offres;
    }

    // Méthode pour afficher les informations du bénévole et ses offres
    @Override
    public void afficher() {
        super.afficher();
        System.out.println("Offres proposées :");
        if (offres.isEmpty()) {
            System.out.println("Aucune offre.");
        } else {
            for (offre o : offres) {
                o.afficherRequete();
            }
        }
    }

    // a modifier avec le type demande --> new demande(a, b, x, ...)
    // public void formulerOffre(String nouvelleOffre) {
    //     if (offres == null) {
    //         offres = new String[]{nouvelleOffre};
    //     } else {
    //         String[] nouvellesOffres = new String[offres.length + 1];
    //         System.arraycopy(offres, 0, nouvellesOffres, 0, offres.length);
    //         nouvellesOffres[offres.length] = nouvelleOffre;
    //         offres = nouvellesOffres;
    //     }
    // }

    // Proposer une offre via la classe offre
    // @Julie j'ai préféré utilisé la classe offre plutôt que demande comme t'avais indiqué (ça me paraissait plus logique ??)
    public void proposerOffre(String nom, String description) {
        offre nouvelleOffre = new offre(nom, description);
        offres.add(nouvelleOffre);
    }


}
