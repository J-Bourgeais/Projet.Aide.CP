//structure

public class structure extends user {
	
	public structure(String nom, String email, String adresse) {
		super(nom,email,adresse);
	}
	
	public void validerService(boolean estValidee, requete requeteAValider) {
		if(estValidee) {
			requeteAValider.status = "validé";
			System.out.println("Vous venez de valider la requête " + requeteAValider.nom);
		} else {
			System.out.println("Vous venez de refuser la requête " + requeteAValider.nom);
		}
	}
	
}