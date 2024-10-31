import java.util.Date;

public class requete {

    protected String nom;
    protected String desc;
    protected String status;
    protected Date date;

    public requete(String nom, String description) {
        this.nom = nom;
        this.desc = description;
        this.date = new Date();
        this.status = "en attente";

    }

    // deuxième constructeur car la description est optionnelle
    public requete(String nom) {
        this.nom = nom;
        this.date = new Date();
        this.status = "en attente";

    }

    public requete getRequete() {
        return this;
    }
    
    public void afficherRequete() {
        System.out.println("Requête : " + nom + " - " + desc);
        System.out.println("Statut : " + status);
        System.out.println("Date : " + date);
    }

}