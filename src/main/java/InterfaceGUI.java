import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InterfaceGUI {
    private JFrame frame;
    public static String email;
    
    public Connection connexion;
    public InterfaceGUI(Connection connexion) {
        this.connexion = connexion; // Sauvegarde la connexion
    }

    

    public void createAndShowGUI() {
        frame = new JFrame("Help App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Ne ferme pas immédiatement
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	frame.dispose(); // Ferme la fenêtre
            	if (connexion != null) {
                    ConnexionBDD.CloseConnexion(connexion);
                }
                System.exit(0);  // Arrête le programme
            }
        });
        
        if (connexion == null) {
            JOptionPane.showMessageDialog(frame, "La connexion à la base de données a échoué.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println("Connexion réussie à la base de données.");
        }
        

        showWelcomePage();

        frame.setVisible(true);
    }
    
    public void setEmail(String mail) {
    	email=mail;
    }
    
    public static String getEmail() {
    	return email;
    }

    private void showWelcomePage() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to our new App : Help", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton loginButton = new JButton("Se connecter");
        JButton registerButton = new JButton("S'inscrire");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        panel.add(welcomeLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();

        loginButton.addActionListener(e -> showLoginPage());
        registerButton.addActionListener(e -> showRegisterPage());
    }

    
    
    
    //Appeler si le bouton "Se connecter" a été cliqué
    public void showLoginPage() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Mot de passe:");
        JPasswordField passwordField = new JPasswordField();

        JButton submitButton = new JButton("Se connecter");

        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty space
        panel.add(submitButton);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();

        submitButton.addActionListener(e -> {
            Object[] loginData = {
                    emailField.getText(),
                    new String(passwordField.getPassword())
            };
            validateConnexion(loginData); //TOSEE
        });
    }
    
    
    //Appelé sur le bouton "Inscription" a été cliqué
    public void showRegisterPage() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));

        JLabel nameLabel = new JLabel("Nom:");
        JTextField nameField = new JTextField();

        JLabel firstNameLabel = new JLabel("Prénom:");
        JTextField firstNameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel addressLabel = new JLabel("Adresse:");
        JTextField addressField = new JTextField();

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();

        JLabel passwordLabel = new JLabel("Mot de passe:");
        JPasswordField passwordField = new JPasswordField();

        JLabel userTypeLabel = new JLabel("Type d'utilisateur:");
        JComboBox<String> userTypeComboBox = new JComboBox<>(new String[]{"Beneficiare", "Benevole", "Structure"});

        JButton submitButton = new JButton("S'inscrire");

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(userTypeLabel);
        panel.add(userTypeComboBox);
        panel.add(new JLabel()); // Empty space
        panel.add(submitButton);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();

        submitButton.addActionListener(e -> {
            Object[] registrationData = {
                    nameField.getText(),
                    firstNameField.getText(),
                    emailField.getText(),
                    addressField.getText(),
                    ageField.getText(),
                    new String(passwordField.getPassword()),
                    userTypeComboBox.getSelectedItem()
            };
            validateInscription(registrationData);//TOSEE
        });
    }

    
    
    private void validateConnexion(Object[] data) {
        // Ici, appelez votre fonction externe pour valider les données (connexion ou inscription)
   
    	JLabel displayLabel = new JLabel("");
    	displayLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	//Se connecter : Enlever les parties de scanner
    	boolean connected=UserConnect.UserConnection(connexion, data);
    	setEmail((String)data[0]);
    	if(connected) {
    		displayLabel.setText("Connexion Réussie ! ");
    		showMenu();
    	}
        
    }
    
    
    
    private void validateInscription(Object[] data) {
        // Ici, appelez votre fonction externe pour valider les données (connexion ou inscription)
    	
    	//Se connecter : Enlever les parties de scanner
    	boolean connected= UserConnect.UserInscription(connexion, data);
    	setEmail((String)data[2]);
    	if(connected) {
    		showMenu();
    	}
    }
    
    
    
  
    private void showMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel menuLabel = new JLabel("Menu Principal", SwingConstants.CENTER);
        menuLabel.setFont(new Font("Arial", Font.BOLD, 24));
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton option1 = new JButton("Formuler une requête (Offre ou Demande)");
        JButton option2 = new JButton("Consulter des requêtes");
        JButton option3 = new JButton("Poster un avis");
        JButton option4 = new JButton("Consulter des avis");
        JButton option5 = new JButton("Consulter un profil");
        JButton option6 = new JButton("Supprimer son compte");
        JButton logoutButton = new JButton("Se déconnecter");

        option1.setAlignmentX(Component.CENTER_ALIGNMENT);
        option2.setAlignmentX(Component.CENTER_ALIGNMENT);
        option3.setAlignmentX(Component.CENTER_ALIGNMENT);
        option4.setAlignmentX(Component.CENTER_ALIGNMENT);
        option5.setAlignmentX(Component.CENTER_ALIGNMENT);
        option6.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(menuLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Space
        panel.add(option1);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Space
        panel.add(option2);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Space
        panel.add(option3);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Space
        panel.add(option4);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Space
        panel.add(option5);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Space
        panel.add(option6);
        
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Space
        panel.add(logoutButton);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();


        option1.addActionListener(e -> handleRequestFormulation(Main.AllUserInfo(connexion, email)));
        option2.addActionListener(e -> handleViewRequests(Main.AllUserInfo(connexion, email)));
        option3.addActionListener(e -> handlePostReview());
        option4.addActionListener(e -> handleViewReviews());
        option5.addActionListener(e -> handleViewProfile());
        option6.addActionListener(e -> handleSuppression());
        logoutButton.addActionListener(e -> showWelcomePage());
    }
    
 // Fonctions correspondant aux actions possibles
    
    
    
    private void handleRequestFormulation(Object[] data) {
        System.out.println("Vous avez choisi de formuler une requête");

        // Demander le nom de la requête
        String requestName = JOptionPane.showInputDialog(frame, "Entrez le nom de la requête:");

        if (requestName == null || requestName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Le nom de la requête est requis.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Demander la description de la requête
        String description = JOptionPane.showInputDialog(frame, "Entrez la description de la requête:");

        if (description == null || description.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "La description de la requête est requise.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Demander le type de la requête (Offre ou Demande)
        String[] options = {"Offre", "Demande"};
        int choice = JOptionPane.showOptionDialog(frame,
                "Sélectionnez le type de la requête:",
                "Type de Requête",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == JOptionPane.CLOSED_OPTION) {
            JOptionPane.showMessageDialog(frame, "Sélection du type de requête est obligatoire.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Récupérer le type sélectionné
        String requestType = options[choice];
        
        User.proposerRequete(connexion, requestName, description, requestType, data);
        
    }

    private void handleViewRequests(Object[] data) {
        System.out.println("Action : Consulter des requêtes.");

        // Créer un panneau pour afficher les requêtes
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Ajouter un label pour informer l'utilisateur
        JLabel titleLabel = new JLabel("Consulter les requêtes :", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Créer des boutons radio pour choisir l'option de consultation des requêtes
        JRadioButton ownRequestsButton = new JRadioButton("Voir mes propres requêtes");
        JRadioButton allOffersButton = new JRadioButton("Voir toutes les offres");
        JRadioButton allDemandsButton = new JRadioButton("Voir toutes les demandes");

        // Grouper les boutons radio pour que l'utilisateur ne puisse en sélectionner qu'un
        ButtonGroup group = new ButtonGroup();
        group.add(ownRequestsButton);
        group.add(allOffersButton);
        group.add(allDemandsButton);

        // Par défaut, sélection de "Voir mes propres requêtes"
        ownRequestsButton.setSelected(true);

        // Ajouter les boutons radio au panneau
        panel.add(titleLabel);
        panel.add(ownRequestsButton);
        panel.add(allOffersButton);
        panel.add(allDemandsButton);

        // Créer un bouton pour afficher les requêtes en fonction de la sélection
        JButton showRequestsButton = new JButton("Afficher les requêtes");

        // Ajouter le bouton au panneau
        panel.add(showRequestsButton);

        // Créer la fenêtre pour afficher les requêtes
        JFrame requestFrame = new JFrame("Consulter les Requêtes");
        requestFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        requestFrame.setSize(400, 300);
        requestFrame.getContentPane().add(panel);
        requestFrame.setVisible(true);

        // Action quand l'utilisateur clique sur le bouton pour afficher les requêtes
        showRequestsButton.addActionListener(e -> {
            // Déterminer le type de requête à afficher en fonction de la sélection
            String critere = null;
            String valeur = null;

            if (ownRequestsButton.isSelected()) {
                critere = "Contact"; // Voir les propres requêtes
                valeur = getEmail();
            } else if (allOffersButton.isSelected()) {
                critere = "TypeRequete"; // Voir toutes les offres
                valeur = "Offre";
            } else if (allDemandsButton.isSelected()) {
                critere = "TypeRequete"; // Voir toutes les demandes
                valeur = "Demande" ;
            }

            // Récupérer les requêtes en fonction du type sélectionné
            List<String> requetes =  new ArrayList<>();
            try {
				requetes = Requete.RequetesParCritere(critere, valeur, connexion);
			} catch (SQLException e1) {
				e1.printStackTrace();
			} //Liste des requetes correspondant aux critères
            
            // Vérifier si des requêtes sont disponibles
            if (requetes == null || requetes.size() == 0) {
                JOptionPane.showMessageDialog(requestFrame, "Aucune requête disponible pour cette option.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Créer une JComboBox avec les requêtes disponibles
            JComboBox<String> requeteComboBox = new JComboBox<>(requetes.toArray(new String[0]));
            requeteComboBox.setPreferredSize(new Dimension(300, 25));

            // Créer un panneau pour afficher la liste des requêtes sélectionnées
            JPanel listPanel = new JPanel();
            listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

            // Ajouter le JComboBox au panneau
            listPanel.add(new JLabel("Sélectionnez une requête :"));
            listPanel.add(requeteComboBox);

            // Ajouter un bouton pour accepter une requête
            JButton acceptButton = new JButton("Accepter cette requête");

            // Ajouter un bouton pour refuser
            JButton rejectButton = new JButton("Refuser cette requête");

            // Ajouter les boutons au panneau
            listPanel.add(acceptButton);
            listPanel.add(rejectButton);

            // Créer une nouvelle fenêtre pour afficher les options de requêtes
            JFrame listFrame = new JFrame("Sélectionner une requête");
            listFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            listFrame.setSize(400, 300);
            listFrame.getContentPane().add(listPanel);
            listFrame.setVisible(true);

            // Action pour accepter la requête
            acceptButton.addActionListener(e1 -> {
                String selectedRequete = (String) requeteComboBox.getSelectedItem();
                if (selectedRequete != null) {
                    // Demander à l'utilisateur s'il souhaite accepter cette requête
                    int confirmation = JOptionPane.showConfirmDialog(listFrame, "Voulez-vous accepter cette requête ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        // Demander le nom de la requête et l'email de l'émetteur
                        String nomRequete = JOptionPane.showInputDialog(listFrame, "Quel est le nom de la requête ?");
                        String mailEmetteur = JOptionPane.showInputDialog(listFrame, "Quel est l'adresse email de l'émetteur de la requête ?");

                        // Traiter la réponse à la requête
                        if (nomRequete != null && mailEmetteur != null) {
                            // Appel à la méthode pour répondre à la requête
                            User.repondreRequete(connexion, nomRequete, mailEmetteur);
                            JOptionPane.showMessageDialog(listFrame, "Vous avez accepté la requête.");
                        }
                    }
                }
            });

            // Action pour refuser la requête
            rejectButton.addActionListener(e1 -> {
                String selectedRequete = (String) requeteComboBox.getSelectedItem();
                if (selectedRequete != null) {
                    // Demander confirmation avant de refuser
                    int confirmation = JOptionPane.showConfirmDialog(listFrame, "Voulez-vous refuser cette requête ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(listFrame, "Vous avez refusé la requête.");
                        listFrame.dispose();  // Fermer la fenêtre
                    }
                }
            });
            
            
            
            
            //PARTIE STRUCTURE
            
            if (data[6].equals("Structure")) {
                // Créer un bouton pour changer le statut de la requête
                JButton changeStatusButton = new JButton("Changer le statut de la requête");

                // Ajouter le bouton au panneau
                listPanel.add(changeStatusButton);

                // Action pour le bouton "Changer le statut de la requête"
                changeStatusButton.addActionListener(e2 -> {
                    // Vérifier si l'utilisateur veut changer le statut
                    int confirmation = JOptionPane.showConfirmDialog(listFrame, "Voulez-vous changer le statut d'une de ces requêtes ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        // Demander à l'utilisateur de choisir une requête
                        String nomRequete = JOptionPane.showInputDialog(listFrame, "Quel est le nom de la requête ?");
                        String mailEmetteur = JOptionPane.showInputDialog(listFrame, "Quel est l'adresse email de l'émetteur de la requête ?");

                        // Créer des boutons pour valider ou refuser la requête
                        JPanel statusPanel = new JPanel();
                        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));

                        // Ajouter des boutons "Validé" et "Pas Validé"
                        JButton validateButton = new JButton("Validé");
                        JButton notValidateButton = new JButton("Pas Validé");

                        // Ajouter les boutons au panneau
                        statusPanel.add(new JLabel("Voulez-vous valider ou refuser cette requête ?"));
                        statusPanel.add(validateButton);
                        statusPanel.add(notValidateButton);

                        // Ajouter un champ de texte pour la raison si la requête est refusée
                        JTextArea reasonArea = new JTextArea(3, 20);
                        reasonArea.setLineWrap(true);
                        reasonArea.setWrapStyleWord(true);
                        reasonArea.setVisible(false);  // Masquer au départ
                        JScrollPane scrollPane = new JScrollPane(reasonArea);
                        statusPanel.add(scrollPane);

                        // Créer une nouvelle fenêtre pour changer le statut de la requête
                        JFrame statusFrame = new JFrame("Changer le statut de la requête");
                        statusFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        statusFrame.setSize(400, 300);
                        statusFrame.getContentPane().add(statusPanel);
                        statusFrame.setVisible(true);

                        // Action pour valider la requête
                        validateButton.addActionListener(e3 -> {
                            // Appel à la méthode pour valider la requête
                            structure.validerService(connexion, nomRequete, mailEmetteur, true, "");
                            JOptionPane.showMessageDialog(statusFrame, "La requête a été validée.");
                            statusFrame.dispose(); // Fermer la fenêtre
                        });

                        // Action pour refuser la requête
                        notValidateButton.addActionListener(e3 -> {
                            // Afficher la zone de texte pour entrer la raison du refus
                            reasonArea.setVisible(true);

                            // Ajouter un bouton pour confirmer le refus
                            JButton confirmRejectButton = new JButton("Confirmer le refus");
                            statusPanel.add(confirmRejectButton);

                            confirmRejectButton.addActionListener(e4 -> {
                                // Récupérer la raison du refus
                                String reason = reasonArea.getText().trim();

                                if (reason.isEmpty()) {
                                    JOptionPane.showMessageDialog(statusFrame, "Veuillez fournir une raison pour le refus.", "Erreur", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    // Appel à la méthode pour refuser la requête avec la raison
                                    structure.validerService(connexion, nomRequete, mailEmetteur, false, reason);
                                    JOptionPane.showMessageDialog(statusFrame, "La requête a été refusée.");
                                    statusFrame.dispose(); // Fermer la fenêtre
                                }
                            });
                        });
                    }
                });
            }
            
            
            
        });
    }

    


    private void handlePostReview() {
        System.out.println("Action : Poster un avis.");

        //Nouveau panneau pour le formulaire
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Champs pour le nom et prénom
        JLabel nomLabel = new JLabel("Nom :");
        JTextField nomField = new JTextField();
        JLabel prenomLabel = new JLabel("Prénom :");
        JTextField prenomField = new JTextField();

        // ComboBox pour le nombre d'étoiles (de 0 à 5)
        JLabel etoilesLabel = new JLabel("Nombre d'étoiles (0 à 5) :");
        String[] etoilesOptions = {"0", "1", "2", "3", "4", "5"};
        JComboBox<String> etoilesComboBox = new JComboBox<>(etoilesOptions);

        // Champ pour la description (texte multiligne)
        JLabel descriptionLabel = new JLabel("Description :");
        JTextArea descriptionArea = new JTextArea(5, 20); // 5 lignes et 20 colonnes
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);

        // Bouton pour soumettre le formulaire
        JButton submitButton = new JButton("Soumettre l'avis");

        // Ajouter les composants au panneau
        panel.add(nomLabel);
        panel.add(nomField);
        panel.add(prenomLabel);
        panel.add(prenomField);
        panel.add(etoilesLabel);
        panel.add(etoilesComboBox);
        panel.add(descriptionLabel);
        panel.add(scrollPane);
        panel.add(submitButton);

        // Créer la fenêtre et l'ajouter au contenu
        JFrame reviewFrame = new JFrame("Poster un Avis");
        reviewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reviewFrame.setSize(400, 400);
        reviewFrame.getContentPane().add(panel);
        reviewFrame.setVisible(true);

        // Ajouter un écouteur pour le bouton de soumission
        submitButton.addActionListener(e -> {
            // Récupérer les valeurs saisies par l'utilisateur
            String pourNom = nomField.getText();
            String pourPrenom = prenomField.getText();
            int nbEtoiles = Integer.parseInt((String) etoilesComboBox.getSelectedItem()); // Convertir en int
            String description = descriptionArea.getText();

            // Fermer la fenêtre de postage d'avis après soumission 
            reviewFrame.dispose();

  
            try {
				Avis.posterAvis(connexion, pourNom, pourPrenom, nbEtoiles, description);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        });
    }


    private void handleViewReviews() {
        System.out.println("Action : Consulter des avis.");

        // Créer un panneau pour l'interface graphique
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Ajouter un label d'instruction
        JLabel instructionLabel = new JLabel("Entrez le nom et le prénom de la personne pour consulter les avis :", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Ajouter un champ de texte pour le nom
        JLabel nameLabel = new JLabel("Nom:");
        JTextField nameField = new JTextField();
        
        // Ajouter un champ de texte pour le prénom
        JLabel firstNameLabel = new JLabel("Prénom:");
        JTextField firstNameField = new JTextField();

        // Créer un bouton pour consulter les avis
        JButton consultButton = new JButton("Consulter les avis");

        // Ajouter les composants au panneau
        panel.add(instructionLabel);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(consultButton);

        // Créer une fenêtre pour afficher le formulaire
        JFrame reviewFrame = new JFrame("Consulter les avis");
        reviewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reviewFrame.setSize(400, 300);
        reviewFrame.getContentPane().add(panel);
        reviewFrame.setVisible(true);

        // Action lorsque l'utilisateur clique sur le bouton pour consulter les avis
        consultButton.addActionListener(e -> {
            String nom = nameField.getText().trim();
            String prenom = firstNameField.getText().trim();

            // Vérification que les champs ne sont pas vides
            if (nom.isEmpty() || prenom.isEmpty()) {
                JOptionPane.showMessageDialog(reviewFrame, "Le nom et le prénom sont requis.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Appel à la méthode pour consulter les avis
            List<String> avis = Avis.consulterAvis(connexion, nom, prenom); 
            
            
            // Vérifier si des avis ont été trouvés
            if (avis == null || avis.isEmpty()) {
                JOptionPane.showMessageDialog(reviewFrame, "Aucun avis trouvé pour cette personne.", "Information", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Créer une fenêtre ou un panneau pour afficher les avis
                StringBuilder avisText = new StringBuilder("<html><ul>");
                for (String a : avis) {
                    avisText.append("<li>").append(a).append("</li>");
                }
                avisText.append("</ul></html>");

                JLabel avisLabel = new JLabel(avisText.toString());
                JOptionPane.showMessageDialog(reviewFrame, avisLabel, "Avis de " + nom + " " + prenom, JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }


    private void handleViewProfile() {
        System.out.println("Action : Consulter un profil.");

        // Créer un panneau pour l'interface graphique
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Ajouter un label d'instruction
        JLabel instructionLabel = new JLabel("Entrez l'adresse email de la personne dont vous souhaitez consulter le profil :", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Ajouter un champ de texte pour l'email
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        // Créer un bouton pour consulter le profil
        JButton consultButton = new JButton("Consulter le Profil");

        // Ajouter les composants au panneau
        panel.add(instructionLabel);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(consultButton);

        // Créer la fenêtre pour afficher le formulaire
        JFrame profileFrame = new JFrame("Consulter le Profil");
        profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        profileFrame.setSize(400, 300);
        profileFrame.getContentPane().add(panel);
        profileFrame.setVisible(true);

        // Action lorsque l'utilisateur clique sur le bouton pour consulter le profil
        consultButton.addActionListener(e -> {
            String email = emailField.getText().trim();

            // Vérification que l'email n'est pas vide
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(profileFrame, "L'email est requis.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Appel à la méthode pour consulter le profil
            Object[] profile = User.consulterProfilUtilisateur(connexion, email); // Méthode qui renvoie un tableau d'informations

            // Vérification que le profil a bien été trouvé
            if (profile == null || profile.length == 0) {
                JOptionPane.showMessageDialog(profileFrame, "Aucun profil trouvé pour cet email.", "Information", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Créer une fenêtre pour afficher les informations du profil
                String profileInfo = "<html><ul>";
                profileInfo += "<li><b>Nom :</b> " + profile[0] + "</li>";
                profileInfo += "<li><b>Prénom :</b> " + profile[1] + "</li>";
                profileInfo += "<li><b>Email :</b> " + profile[2] + "</li>";
                profileInfo += "<li><b>Adresse :</b> " + profile[3] + "</li>";
                profileInfo += "<li><b>Âge :</b> " + profile[4] + "</li>";
                profileInfo += "<li><b>Type d'utilisateur :</b> " + profile[5] + "</li>";
                profileInfo += "</ul></html>";

                JLabel profileLabel = new JLabel(profileInfo);
                JOptionPane.showMessageDialog(profileFrame, profileLabel, "Profil de " + profile[0] + " " + profile[1], JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    
    private void handleSuppression() {
    	boolean ok = User.SupprimerCompte(connexion, getEmail());
    	showWelcomePage();
    }

}
