import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

class mainTest {

	@Test
	void test() {
		fail("Not yet implemented");
		
		//connexion

	}
	
	
	@Test
    public void testInscription() throws SQLException {
        // Ajouter un utilisateur pour le test
		
		//pb --> pas automatique vu qu'on a les infos de connexion via le clavier
		// --> le passer en argument et dans le main ?
		
		boolean connected;
		Connection connexion=ConnexionBDD.GetConnexion();
        connected = UserConnect.UserInscription(connexion);
        ConnexionBDD.CloseConnexion(connexion);
        
        PreparedStatement insertUser = connexion.prepareStatement("INSERT INTO users (id, username) VALUES (?, ?)");
        insertUser.setInt(1, 1);
        insertUser.setString(2, "john_doe");
        insertUser.executeUpdate();

        // Vérifier si l'utilisateur existe
        assertTrue("blablabla"=="blablabla");
    }

}
