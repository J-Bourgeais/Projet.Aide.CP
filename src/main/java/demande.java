

public class demande extends requete{

    public demande(String nom, String description) {
        super(nom,description);
    }

    // deuxième constructeur car la description est optionnelle
    public demande(String nom) {
        super(nom);
    }

}