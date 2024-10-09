//Beneficiaire
public class beneficiaire extends user {

    // A modifier type demande
    protected String[] demandes;
    protected String[] enCours=null; //demande en cours de réalisation

    // Constructeur avec toutes les informations, y compris les demandes
    public beneficiaire(String nom, String prenom, String email, String adresse, int age, String[] demandes, String[] enCours) {
        super(nom, prenom, email, adresse, age); // Appel au constructeur de la classe parent
        this.demandes = demandes;
        this.enCours = enCours;
    }

    // Getter pour obtenir les demandes
    public String[] getDemandes() {
        return demandes;
    }

    // Setter pour modifier les demandes
    public void setDemandes(String[] demandes) {
        this.demandes = demandes;
    }

    // Override de demande --> a mdifier car demande va etre une classe --> utilisaiton de son afficher()
    @Override
    public void afficher() {
        super.afficher(); // Appel à la méthode afficher() de la classe parent
        System.out.println("Demandes :");
        if (demandes != null) {
            for (String demande : demandes) {
                System.out.println("- " + demande);
            }
        } else {
            System.out.println("Aucune demande");
        }
    }

    // a modifier avec le type demande --> new demande(a, b, x, ...)
    public void formulerDemande(String nouvelleDemande) {
        if (demandes == null) {
            demandes = new String[]{nouvelleDemande};
        } else {
            String[] nouvellesDemandes = new String[demandes.length + 1];
            System.arraycopy(demandes, 0, nouvellesDemandes, 0, demandes.length);
            nouvellesDemandes[demandes.length] = nouvelleDemande;
            demandes = nouvellesDemandes;
        }
    }

    public void repondreOffre(String nouvelleOffre) {
        if (enCours == null) {
            enCours = new String[]{nouvelleOffre};
            //Prevenir le benevole que son offre est acceptée
        } else {
            System.out.println("Vous avez deja une demande en cours\n");
        }
    }

}
