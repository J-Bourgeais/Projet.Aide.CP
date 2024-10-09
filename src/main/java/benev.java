//benev
//Beneficiaire
public class benev extends user {

    // A modifier type demande
    protected String[] offres;
    protected String[] enCours=null; //demande en cours de réalisation

    // Constructeur avec toutes les informations, y compris les demandes
    public benev(String nom, String prenom, String email, String adresse, int age, String password, String[] offres, String[] enCours) {
        super(nom, prenom, email, adresse, age, password); // Appel au constructeur de la classe parent
        this.offres = offres;
        this.enCours = enCours;
    }

    // Getter pour obtenir les demandes
    public String[] getOffres() {
        return offres;
    }

    // Setter pour modifier les demandes
    public void setOffres(String[] offres) {
        this.offres = offres;
    }

    // Override de demande --> a mdifier car demande va etre une classe --> utilisaiton de son afficher()
    @Override
    public void afficher() {
        super.afficher(); // Appel à la méthode afficher() de la classe parent
        System.out.println("Offres :");
        if (offres != null) {
            for (String offre : offres) {
                System.out.println("- " + offre);
            }
        } else {
            System.out.println("Aucune offre");
        }
    }

    // a modifier avec le type demande --> new demande(a, b, x, ...)
    public void formulerOffre(String nouvelleOffre) {
        if (offres == null) {
            offres = new String[]{nouvelleOffre};
        } else {
            String[] nouvellesOffres = new String[offres.length + 1];
            System.arraycopy(offres, 0, nouvellesOffres, 0, offres.length);
            nouvellesOffres[offres.length] = nouvelleOffre;
            offres = nouvellesOffres;
        }
    }


}
