import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class InterfaceGUI extends Frame implements WindowListener , ActionListener, AdjustmentListener {
	private Panel main_panel;
	
	
	// Pour pouvoir fermer la fenetre avec la croix
    public void windowClosing(WindowEvent arg0) {
        System.exit(0);  // Quitter l'application
    }
    public void windowOpened(WindowEvent arg0) {}
    public void windowClosed(WindowEvent arg0) {}
    public void windowIconified(WindowEvent arg0) {}
    public void windowDeiconified(WindowEvent arg0) {}
    public void windowActivated(WindowEvent arg0) {}
    public void windowDeactivated(WindowEvent arg0) {}
	
	//Changer les scanner en input dans le gui, avec un return.
    //Ici : cases pour écrire, listes déroulantes si uniquement plusieurs choix, ...
    
    //On appelle directement les fcts qui return ce qu'on veut dans le main
    
    
}
