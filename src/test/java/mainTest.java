import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;


class mainTest {

	@Test
	void test() {		
		//connexion
		Object[] liste = new Object[]{"melo@gmail.com", "chien"};
		Connection connexion=ConnexionBDD.GetConnexion();
        assertTrue(UserConnect.UserConnection(connexion, liste)==true);
        ConnexionBDD.CloseConnexion(connexion);

	}
	
	
	@Test
    public void testInscription() throws SQLException {
        // Ajouter un utilisateur pour le test

		Object[] liste = new Object[]{"Jean", "Charlie", "jeancharlie@gmail.com", 65, "charlette", "Beneficiaire"};
		Connection connexion=ConnexionBDD.GetConnexion();
        assertTrue(UserConnect.UserInscription(connexion, liste)==true);
        ConnexionBDD.CloseConnexion(connexion);

    }

}
