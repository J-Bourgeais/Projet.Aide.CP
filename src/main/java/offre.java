

public class offre extends requete{

    public offre(String nom, String description) {
        super(nom,description);
    }

    public offre(String nom) {
        super(nom);
    }
    
    @Override
    public void afficherRequete() {
    	System.out.println("Offre : " + this.nom);
    }


}