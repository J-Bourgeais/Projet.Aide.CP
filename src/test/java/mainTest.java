import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.ResultSet;

import org.junit.jupiter.api.Test;


class mainTest {

	@Test
	public void test() {		
		//connexion
		Object[] liste = new Object[]{"melo@gmail.com", "chien"};
		Connection connexion=ConnexionBDD.GetConnexion();
        assertTrue(UserConnect.UserConnection(connexion, liste)==true);
        ConnexionBDD.CloseConnexion(connexion);

	}
	
	
	@Test
    public void testInscription() throws SQLException {
        // Ajouter un utilisateur pour le test
		Object[] liste = new Object[]{"Jean", "Charlie", "jeancharlie@gmail.com", "5 rue des Lilas", 65, "charlette", "Beneficiaire"};
		Connection connexion=ConnexionBDD.GetConnexion();
        assertTrue(UserConnect.UserInscription(connexion, liste)==true);
        ConnexionBDD.CloseConnexion(connexion);

    }
	
	@Test
	public void testPosterAvis() throws SQLException {
	    // Préparation des éléments à insérer
	    String nom = "Bourgeais";
	    String prenom = "Melo";
	    int nbEtoiles = 5;
	    String description = "Excellent service, je suis ravie de mes croquettes !";
	    Connection connexion = ConnexionBDD.GetConnexion();

	    // Ajout de l'utilisateur
	    String insertUser = "INSERT INTO Users (Nom, Prenom, Avis) VALUES (?, ?, ?)";
	    try (PreparedStatement etat = connexion.prepareStatement(insertUser)) {
	        etat.setString(1, nom);
	        etat.setString(2, prenom);
	        etat.setString(3, "[]");
	        etat.executeUpdate();
	    }

	    // Appel de la méthode à tester
	    Avis.posterAvis(connexion, nom, prenom, nbEtoiles, description);

	    // Verif résultats
	    String requeteSelect = "SELECT Avis FROM Users WHERE Nom = ? AND Prenom = ?";
	    try (PreparedStatement etat = connexion.prepareStatement(requeteSelect)) {
	        etat.setString(1, nom);
	        etat.setString(2, prenom);
	        ResultSet rs = etat.executeQuery();

	        assertTrue(rs.next()); // Vérifie que l'utilisateur existe
	        String avisJson = rs.getString("Avis");
	        assertNotNull(avisJson); // Vérifie qu'il y a un avis
	        JSONArray avisArray = new JSONArray(avisJson);
	        assertEquals(1, avisArray.length()); // Vérifie qu'il y a un seul avis

	        JSONObject avis = avisArray.getJSONObject(0);
	        assertEquals(nbEtoiles, avis.getInt("nbEtoiles"));
	        assertEquals(description, avis.getString("description"));
	    }

	    // Nettoyage de la BDD
	    String deleteUser = "DELETE FROM Users WHERE Nom = ? AND Prenom = ?";
	    try (PreparedStatement etat = connexion.prepareStatement(deleteUser)) {
	        etat.setString(1, nom);
	        etat.setString(2, prenom);
	        etat.executeUpdate();
	    }
	    ConnexionBDD.CloseConnexion(connexion);
	}

	
	@Test
	public void testConsulterAvis() throws SQLException {
		// Préparation des éléments à insérer puis consulter
	    String nom = "Gerard";
	    String prenom = "Paquito";
	    JSONArray listeAvis = new JSONArray();
	    JSONObject avis1 = new JSONObject();
	    avis1.put("nbEtoiles", 5);
	    avis1.put("description", "J'ai adoré la neige");
	    JSONObject avis2 = new JSONObject();
	    avis2.put("nbEtoiles", 3);
	    avis2.put("description", "J'avais froid quand même...");
	    listeAvis.put(avis1);
	    listeAvis.put(avis2);

	    Connection connexion = ConnexionBDD.GetConnexion();

	    // Ajout de l'utilisateur
	    String insertUser = "INSERT INTO Users (Nom, Prenom, Avis) VALUES (?, ?, ?)";
	    try (PreparedStatement etat = connexion.prepareStatement(insertUser)) {
	        etat.setString(1, nom);
	        etat.setString(2, prenom);
	        etat.setString(3, listeAvis.toString());
	        etat.executeUpdate();
	    }

	    // Verif résultats
	    Avis.consulterAvis(connexion, nom, prenom);

	    // Verif l'affichage (via l'utilisation de la sortie standard) 
	    // @MELO on aurait pu faire la même en regardant les logs directement, mais moins visuel ??
	    ByteArrayOutputStream sortieCapturee = new ByteArrayOutputStream();
	    PrintStream fluxOriginal = System.out; // Sauvegarde de la sortie originale
	    System.setOut(new PrintStream(sortieCapturee)); // Redirection de la sortie

	    try {
	        // Appel de la méthode à tester
	        Avis.consulterAvis(connexion, nom, prenom);
	    } finally {
	        System.setOut(fluxOriginal); // Rétablir la sortie standard originale
	    }

	    String sortie = sortieCapturee.toString();
	    assertTrue(sortie.contains("Liste des avis pour Gerard Paquito:"));
	    assertTrue(sortie.contains("Avis 1: 5 étoiles - J'ai adoré la neige"));
	    assertTrue(sortie.contains("Avis 2: 3 étoiles - J'avais froid quand même..."));

	    // Nettoyage de la BDD
	    String deleteUser = "DELETE FROM Users WHERE Nom = ? AND Prenom = ?";
	    try (PreparedStatement etat = connexion.prepareStatement(deleteUser)) {
	        etat.setString(1, nom);
	        etat.setString(2, prenom);
	        etat.executeUpdate();
	    }
	    ConnexionBDD.CloseConnexion(connexion);
	}

	
	/* Tests à faire
	 * 
	 * Poster un avis --> apparait dans la bdd
	 * Poster une requête --> apparait dans la bdd
	 * Consulter avis --> Comment tester ??
	 * Afficher requete par critère --> test bon critere, affichage, ...
	 * Valider service --> Modification de la bdd
	 * 
	 * */

}
