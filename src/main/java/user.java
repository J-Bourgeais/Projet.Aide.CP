public class user {

    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private int age;
    private String password;


    // Constructeur
    public user(String nom, String prenom, String email, String adresse, int age, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse=adresse;
        this.age = age;
        this.password= password;
    }

    // Si qqc est pas obligatoire : pour structure
    public user(String nom, String email, String adresse, String password) {
        this.nom = nom;
        this.email = email;
        this.adresse=adresse;
        this.password= password;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getAge() {
        return age;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Méthode pour afficher les informations de l'utilisateur
    public void afficher() {
    	System.out.println("*** Informations sur l'utilisateur ***");
        System.out.println("Nom: " + nom);
        System.out.println("Prénom: " + prenom);
        System.out.println("Email: " + email);
        System.out.println("Âge: " + age);
        System.out.println("Adresse: " + adresse);
        System.out.println("**************************************");
    }

    //Methode pour poster un avis à quelqu'un

	 public void posterAvis(user pourQui, int nbEtoiles, String description) {
	    	avis monAvis = new avis(this, pourQui, nbEtoiles,description);
	    	System.out.println("Votre avis a bien été posté " + monAvis.deQui.nom);
	 }
	 
	 public void posterAvis(user pourQui, int nbEtoiles) {
		 	avis monAvis = new avis(this, pourQui, nbEtoiles);
		 	System.out.println("Votre avis a bien été posté "  + monAvis.deQui.nom);
	 }
    
    
}
