import java.util.Scanner;

import javax.swing.SwingUtilities;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {


    public static Object[] AllUserInfo(Connection connexion, String email){
        String requeteSQL = "SELECT Nom, Prenom, email, Adresse, Age, Password, UserType FROM Users WHERE email = ?";

        try (
        		PreparedStatement stmt = connexion.prepareStatement(requeteSQL)) {
        	
	            stmt.setString(1, email);
	            ResultSet rs = stmt.executeQuery();
	
	            if (rs.next()) {
	                // Extract values from the current row
	                String Nom = rs.getString("Nom");
	                String Prenom = rs.getString("Prenom");
	                String Email = rs.getString("email");
	                String Adresse = rs.getString("Adresse");
	                int Age = rs.getInt("Age");
	                String Password = rs.getString("Password");
	                String UserType = rs.getString("UserType");

	                // Return an array with all the user information
	                return new Object[]{Nom, Prenom, Email, Adresse, Age, Password, UserType};
	            } else {
	                // Handle the case where no user is found
	                System.out.println("No user found with the given email.");
	                return null;
	            }
            

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) throws SQLException {
    	
    	
    	Connection connexion = ConnexionBDD.GetConnexion();
    	
    	//Lancer InterfaceGUI
    	SwingUtilities.invokeLater(() -> new InterfaceGUI(connexion).createAndShowGUI());
    	

    	/*Pour tester :
    	 * User toujours inscrit : 
    	 * - email : melo@gmail.com
    	 * - mdp : chien
    	 * */

      
         
        }

    }

