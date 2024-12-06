import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class mainTest {
	
	@BeforeEach
	public void DelTable() throws SQLException {
		Connection connexion=ConnexionBDD.GetConnexion();
		String deleteUser = "DELETE FROM Users";
	    try (PreparedStatement etat = connexion.prepareStatement(deleteUser)) {
	        etat.executeUpdate();
	    } 
	    String deleteRequetes = "DELETE FROM requetes";
	    try (PreparedStatement etat = connexion.prepareStatement(deleteRequetes)) {
	        etat.executeUpdate();
	    } 
	    
	    //Ajouter melo
		Object[] liste = new Object[]{"Bourgeais", "Melo", "melo@gmail.com", "5 rue des chiens", "22", "chien", "Beneficiaire"};
	    try {
			assertTrue(UserConnect.UserInscription(connexion, liste)==true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ConnexionBDD.CloseConnexion(connexion);
	}
	

	@Test
	public void Connexiontest() {		
		//connexion
		Object[] liste = new Object[]{"melo@gmail.com", "chien"};
		Connection connexion=ConnexionBDD.GetConnexion();
        assertTrue(UserConnect.UserConnection(connexion, liste)==true);
        ConnexionBDD.CloseConnexion(connexion);
        
	}
	
	
	@Test
    public void testInscription() throws SQLException, IOException {
		Connection connexion=ConnexionBDD.GetConnexion();
        // Ajouter un utilisateur pour le test
		String deleteUser = "DELETE FROM Users WHERE Nom = ? AND Prenom = ?";
	    try (PreparedStatement etat = connexion.prepareStatement(deleteUser)) {
	        etat.setString(1, "Jean");
	        etat.setString(2, "Charlie");
	        etat.executeUpdate();
	    }
		Object[] liste = new Object[]{"Jean", "Charlie", "jeancharlie@gmail.com", "5 rue des Lilas", "65", "charlette", "Beneficiaire"};
		
		/*String deleteUser = "DELETE FROM Users WHERE Nom = ? AND Prenom = ?";
	    try (PreparedStatement etat = connexion.prepareStatement(deleteUser)) {
	        etat.setString(1, "Jean");
	        etat.setString(2, "Charlie");
	        etat.executeUpdate();
	    }*/
        assertTrue(UserConnect.UserInscription(connexion, liste)==true);
        ConnexionBDD.CloseConnexion(connexion);

    }
	
	/*Test supprimer son compte*/
	@Test
	public void TestSuppression() throws SQLException, IOException {
		Connection connexion=ConnexionBDD.GetConnexion();
		//Inscription
		Object[] liste = new Object[]{"Jean", "Charlie", "jeancharlie@gmail.com", "5 rue des Lilas", "65", "charlette", "Beneficiaire"};
		assertTrue(UserConnect.UserInscription(connexion, liste)==true);
		assertTrue(User.SupprimerCompte(connexion, "jeancharlie@gmail.com"));
		System.out.println("Compte supprimé avec succès");
		
		ConnexionBDD.CloseConnexion(connexion);
		
	}
	
	
	
	/* Tests principaux pour les Avis */
	
	@Test
	public void testPosterAvis() throws SQLException {
		Connection connexion = ConnexionBDD.GetConnexion();

		
		//Delete all Avis
    	String deleteAvis = "UPDATE Users SET Avis = null";
 	    try (PreparedStatement etat = connexion.prepareStatement(deleteAvis)) {
 	        etat.executeUpdate();
 	    }
		
	    // Préparation des éléments à insérer
	    String nom = "Jean";//Bourgeais
	    String prenom = "Charlie";//Melo
	    int nbEtoiles = 5;
	    String description = "Excellent service, je suis ravie de mes croquettes !";
	    
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
	    
	    Connection connexion = ConnexionBDD.GetConnexion();

	    // Ajout de l'utilisateur
	    String insertUser = "INSERT INTO Users (Nom, Prenom, Avis) VALUES (?, ?, ?)";
	    try (PreparedStatement etat = connexion.prepareStatement(insertUser)) {
	        etat.setString(1, nom);
	        etat.setString(2, prenom);
	        etat.setString(3, "[]");
	        etat.executeUpdate();
	    }
	    
	    Avis.posterAvis(connexion, nom, prenom, 5, "J'ai adoré la neige");
	    Avis.posterAvis(connexion, nom, prenom, 3, "J'avais froid quand même...");
	    
	    

	    // Verif résultats
	    List<String> avis =Avis.consulterAvis(connexion, nom, prenom);

	    
	    assertTrue(avis.contains("Avis 1: 5 étoiles - J'ai adoré la neige"));
	    assertTrue(avis.contains("Avis 2: 3 étoiles - J'avais froid quand même..."));

	    // Nettoyage de la BDD
	    String deleteUser = "DELETE FROM Users WHERE Nom = ? AND Prenom = ?";
	    try (PreparedStatement etat = connexion.prepareStatement(deleteUser)) {
	        etat.setString(1, nom);
	        etat.setString(2, prenom);
	        etat.executeUpdate();
	    }
	    ConnexionBDD.CloseConnexion(connexion);
	}

	
	/* Tests principaux pour les actions de l'utilisateur sur les Requêtes */
	
	@Test
	public void testRepondreRequete() throws SQLException {
	    Connection connexion = ConnexionBDD.GetConnexion();

	    // Requête fictive dans la base de données ajoutée
	    String insertRequete = "INSERT INTO requetes (NameRequete, FromUser, Description, Status, Date, TypeRequete, Contact) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    String nomRequete = "TestRequete";
	    String email = "test@example.com";

	    try (PreparedStatement stmt = connexion.prepareStatement(insertRequete)) {
	        stmt.setString(1, nomRequete);
	        stmt.setString(2, "Melo");
	        stmt.setString(3, "Requête test");
	        stmt.setString(4, "En attente");
	        stmt.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
	        stmt.setString(6, "TypeTest");
	        stmt.setString(7, email);
	        stmt.executeUpdate();
	    }

	    // Appel de la méthode 
	    User.repondreRequete(connexion, nomRequete, email);

	    // Vérifier statut de la requête est passé à "acceptée"
	    String query = "SELECT Status FROM requetes WHERE NameRequete = ? AND Contact = ?";
	    try (PreparedStatement stmt = connexion.prepareStatement(query)) {
	        stmt.setString(1, nomRequete);
	        stmt.setString(2, email);
	        ResultSet rs = stmt.executeQuery();
	        assertTrue(rs.next());
	        assertEquals("acceptée", rs.getString("Status"));
	    }

	    // Nettoyage BDD
	    String deleteRequete = "DELETE FROM requetes WHERE NameRequete = ?";
	    try (PreparedStatement stmt = connexion.prepareStatement(deleteRequete)) {
	        stmt.setString(1, nomRequete);
	        stmt.executeUpdate();
	    }

	    ConnexionBDD.CloseConnexion(connexion);
	}

	
	@Test
	public void testProposerRequete() throws SQLException {
	    Connection connexion = ConnexionBDD.GetConnexion();

	    // Info user fictif
	    Object[] Alluserinfos = new Object[]{"Bourgeais", "Melo", "melo@example.com", "adresse", 30, "mdp", "Bénévole"};

	    String nomRequete = "NouvelleRequete";
	    String description = "Description de la requête";
	    String typeRequete = "TypeTest";

	    // Appeler la méthode à tester
	    User.proposerRequete(connexion, nomRequete, description, typeRequete, Alluserinfos);

	    // Vérifier que la requête a été insérée dans la BDD
	    String query = "SELECT * FROM requetes WHERE NameRequete = ? AND Contact = ?";
	    try (PreparedStatement stmt = connexion.prepareStatement(query)) {
	        stmt.setString(1, nomRequete);
	        stmt.setString(2, (String) Alluserinfos[2]);
	        ResultSet rs = stmt.executeQuery();
	        assertTrue(rs.next());
	        assertEquals(nomRequete, rs.getString("NameRequete"));
	        assertEquals(description, rs.getString("Description"));
	        assertEquals(typeRequete, rs.getString("TypeRequete"));
	        assertEquals("En attente", rs.getString("Status"));
	    }

	    // Nettoyage BDD
	    String deleteRequete = "DELETE FROM requetes WHERE NameRequete = ?";
	    try (PreparedStatement stmt = connexion.prepareStatement(deleteRequete)) {
	        stmt.setString(1, nomRequete);
	        stmt.executeUpdate();
	    }

	    ConnexionBDD.CloseConnexion(connexion);
	}
	
	
	@Test
	public void testSupprimerRequete() throws SQLException {
		Connection connexion = ConnexionBDD.GetConnexion();
		Object[] Alluserinfos = new Object[]{"Bourgeais", "Melo", "melo@example.com", "adresse", 30, "mdp", "Bénévole"};
		String nomRequete = "NouvelleRequete";
	    String description = "Description de la requête";
	    String typeRequete = "TypeTest";
	    User.proposerRequete(connexion, nomRequete, description, typeRequete, Alluserinfos);
	    
	    User.SupprimerRequete(connexion, nomRequete, "melo@example.com");
	    
	    String query = "SELECT Description FROM requetes WHERE NameRequete = ? and Contact = ?";
	    try (PreparedStatement stmt = connexion.prepareStatement(query)) {
	        stmt.setString(1, nomRequete);
	        stmt.setString(2, "melo@example.com");
	        ResultSet rs = stmt.executeQuery();
	        assertFalse(rs.next());
	    }
	    
	    ConnexionBDD.CloseConnexion(connexion);
		
	}

	
	@Test
	public void testModifierRequete() throws SQLException {
	    Connection connexion = ConnexionBDD.GetConnexion();

	    // Ajoute requête fictive
	    String insertRequete = "INSERT INTO requetes (NameRequete, FromUser, Description, Status, Date, TypeRequete, Contact) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    String nomRequete = "RequeteAModifier";

	    try (PreparedStatement stmt = connexion.prepareStatement(insertRequete)) {
	        stmt.setString(1, nomRequete);
	        stmt.setString(2, "Melo");
	        stmt.setString(3, "Description initiale");
	        stmt.setString(4, "En attente");
	        stmt.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
	        stmt.setString(6, "TypeTest");
	        stmt.setString(7, "melo@example.com");
	        stmt.executeUpdate();
	    }

	    // Appel méthode modification
	    Object[] Allusersinfos = Main.AllUserInfo(connexion, InterfaceGUI.getEmail());
	    User.modifierRequete(connexion, nomRequete, 2, "Description", Allusersinfos);

	    // Vérifier les modifications (ex ici, description mise à jour)
	    String query = "SELECT Description FROM requetes WHERE NameRequete = ?";
	    try (PreparedStatement stmt = connexion.prepareStatement(query)) {
	        stmt.setString(1, nomRequete);
	        ResultSet rs = stmt.executeQuery();
	        assertTrue(rs.next());
	        assertNotEquals("Description initiale", rs.getString("Description")); // S'assurer que la description a changé
	    }

	    // Nettoyage BDD
	    String deleteRequete = "DELETE FROM requetes WHERE NameRequete = ?";
	    try (PreparedStatement stmt = connexion.prepareStatement(deleteRequete)) {
	        stmt.setString(1, nomRequete);
	        stmt.executeUpdate();
	    }

	    ConnexionBDD.CloseConnexion(connexion);
	}


	@Test
	public void testConsulterProfilUtilisateur() throws SQLException {
	    Connection connexion = ConnexionBDD.GetConnexion();
	    
	    String email = "melo@example.com";
	    
	 // Nettoyage BDD
	    String deleteUser = "DELETE FROM Users WHERE email = ?";
	    try (PreparedStatement stmt = connexion.prepareStatement(deleteUser)) {
	        stmt.setString(1, email);
	        stmt.executeUpdate();
	    }

	    // Ajout user fictif
	    String insertUser = "INSERT INTO Users (Nom, Prenom, email, Adresse, Age, UserType) VALUES (?, ?, ?, ?, ?, ?)";
	    

	    try (PreparedStatement stmt = connexion.prepareStatement(insertUser)) {
	        stmt.setString(1, "Bourgeais");
	        stmt.setString(2, "Melo");
	        stmt.setString(3, email);
	        stmt.setString(4, "TestAdresse");
	        stmt.setInt(5, 2);
	        stmt.setString(6, "Bénévole");
	        stmt.executeUpdate();
	    }


	    // appel méthode consultation
	    Object[] user = User.consulterProfilUtilisateur(connexion, email);
	  
	    
	    assertEquals((String)user[0],"Bourgeais");
	    assertEquals((String)user[1],"Melo");
	    assertEquals((String)user[2],"melo@example.com");
	    assertEquals((String)user[3],"TestAdresse");
	    assertEquals((int)user[4],2);

	    
	   

	    ConnexionBDD.CloseConnexion(connexion);
	}

	
	
	/* Test pour la validation d'un service de la part d'une structure */
	
	@Test
    public void testValiderService() throws SQLException {
        Connection connexion = ConnexionBDD.GetConnexion();

        // Requête de test
        String nomRequete = "Nourrir Melo";
        String email = "melo@test.com";
        String description = "Test de validation ou refus";
        String typeRequete = "Service";
        String statusInitial = "en attente";

        // Suppression des anciennes données
        String deleteRequete = "DELETE FROM requetes WHERE NameRequete = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(deleteRequete)) {
            stmt.setString(1, nomRequete);
            stmt.executeUpdate();
        }

        // Insertion de la requête de tests
        String insertRequete = "INSERT INTO requetes (NameRequete, FromUser, Description, Status, Date, TypeRequete, Contact) VALUES (?, ?, ?, ?, CURRENT_DATE, ?, ?)";
        try (PreparedStatement stmt = connexion.prepareStatement(insertRequete)) {
            stmt.setString(1, nomRequete);
            stmt.setString(2, "John Doe");
            stmt.setString(3, description);
            stmt.setString(4, statusInitial);
            stmt.setString(5, typeRequete);
            stmt.setString(6, email);
            stmt.executeUpdate();
        }

        // Test 1 : Validation de la requête (pas de motif)
        Structure.validerService(connexion, nomRequete, email, true, null);

        String selectRequete = "SELECT Status FROM requetes WHERE NameRequete = ? AND Contact = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(selectRequete)) {
            stmt.setString(1, nomRequete);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                assertEquals("validé", rs.getString("Status"), "Le statut doit être 'validé'.");
            } else {
                fail("Requête non trouvée dans la base de données après validation.");
            }
        }

        // Test 2 : Refus de la requête avec une raison
        String raisonRefus = "Les informations sont incomplètes.";
        Structure.validerService(connexion, nomRequete, email, false, raisonRefus);

        try (PreparedStatement stmt = connexion.prepareStatement(selectRequete)) {
            stmt.setString(1, nomRequete);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                assertEquals("refusé", rs.getString("Status"), "Le statut doit être 'refusé'.");
            } else {
                fail("Requête non trouvée dans la base de données après refus.");
            }
        }

        // Nettoyage des données après test
        try (PreparedStatement stmt = connexion.prepareStatement(deleteRequete)) {
            stmt.setString(1, nomRequete);
            stmt.executeUpdate();
        }

        ConnexionBDD.CloseConnexion(connexion);
    }
	
	
	/* Tests à faire
	 * 
	 * @DONE - Poster un avis --> apparait dans la bdd
	 * @DONE - Poster une requête --> apparait dans la bdd
	 * @DONE - Consulter avis --> Comment tester ??
	 * Afficher requete par critère --> test bon critere, affichage, ...
	 * Valider service --> Modification de la bdd
	 * 
	 * */

}
