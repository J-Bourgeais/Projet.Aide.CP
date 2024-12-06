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
        frame.setSize(800, 600);
        
        
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
        // Créer un panneau principal avec BorderLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Titre de bienvenue
        JLabel welcomeLabel = new JLabel("Welcome to our new App : Help", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Créer un JLabel pour l'image
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/Images/Aide.png"));
        JLabel imageLabel = new JLabel(imageIcon);

        // Créer les boutons
        JButton loginButton = new JButton("Se connecter");
        JButton registerButton = new JButton("S'inscrire");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // Ajouter les composants au panneau
        panel.add(welcomeLabel, BorderLayout.NORTH); // Le titre en haut
        panel.add(imageLabel, BorderLayout.CENTER);  // L'image au centre (entre le titre et les boutons)
        panel.add(buttonPanel, BorderLayout.SOUTH);  // Les boutons en bas

        // Mettre à jour la fenêtre
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();

        // Actions pour les boutons
        loginButton.addActionListener(e -> showLoginPage());
        registerButton.addActionListener(e -> showRegisterPage());
    }



    
    
    
    //Appeler si le bouton "Se connecter" a été cliqué
    public void showLoginPage() {
        // Créer un panneau principal avec GridBagLayout pour centrer les éléments
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Utilisation de GridBagLayout pour un alignement flexible
        GridBagConstraints constraints = new GridBagConstraints();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // Ajout de marges autour du panneau

        // Créer le titre "CONNEXION"
        JLabel titleLabel = new JLabel("CONNEXION");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Police plus grande pour le titre
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2; // Le titre prend toute la largeur du panneau
        constraints.insets = new Insets(0, 0, 20, 0); // Espacement sous le titre
        panel.add(titleLabel, constraints);

        // Créer un champ pour l'email
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        emailField.setPreferredSize(new Dimension(250, 30)); // Ajuste la taille du champ de texte

        // Ajouter l'emailLabel et emailField
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1; // Le champ prend une seule colonne
        constraints.insets = new Insets(10, 10, 5, 10); // Espacement entre les champs
        panel.add(emailLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(emailField, constraints);

        // Créer un champ pour le mot de passe
        JLabel passwordLabel = new JLabel("Mot de passe:");
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(250, 30)); // Ajuste la taille du champ de texte

        // Ajouter le passwordLabel et passwordField
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(passwordField, constraints);

        // Créer un bouton de soumission
        JButton submitButton = new JButton("Se connecter");
        submitButton.setPreferredSize(new Dimension(250, 40)); // Taille fixe pour le bouton
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer le bouton

        // Ajouter le bouton de soumission
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2; // Le bouton prend toute la largeur
        constraints.insets = new Insets(20, 10, 10, 10); // Espacement autour du bouton
        panel.add(submitButton, constraints);

        // Mettre à jour le contenu de la fenêtre
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();

        // Action pour le bouton de soumission
        submitButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            Object[] loginData = { email, password };
            validateConnexion(loginData); // Valider la connexion
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
            try {
				validateInscription(registrationData);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
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
    
    
    
    private void validateInscription(Object[] data) throws IOException {
        // Ici, appelez votre fonction externe pour valider les données (connexion ou inscription)
    	
    	//Se connecter : Enlever les parties de scanner
    	boolean connected= UserConnect.UserInscription(connexion, data);
    	setEmail((String)data[2]);
    	if(connected) {
    		showMenu();
    	}
    }
    
    
    //RequetesParCritere("Benevole", getEmail(), connexion);
    /*List<Object[]> requetes = new ArrayList<>();
    try {
        requetes = Requete.RequetesParCritere(critere, valeur, connexion);
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(requestFrame, "Erreur lors de la récupération des requêtes.", "Erreur", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (requetes == null || requetes.isEmpty()) {
        JOptionPane.showMessageDialog(requestFrame, "Aucune requête disponible pour cette option.", "Information", JOptionPane.INFORMATION_MESSAGE);
        return;
    }*/
    
    
    
    private void showMenu() {
        JTabbedPane tabbedPane = new JTabbedPane();
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel menuLabel = new JLabel("Menu Principal", SwingConstants.CENTER);
        menuLabel.setFont(new Font("Arial", Font.BOLD, 24));
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Déclaration des boutons
        JButton option1 = new JButton("Formuler une requête (Offre ou Demande)");
        JButton option2 = new JButton("Consulter des requêtes");
        JButton option3 = new JButton("Poster un avis");
        JButton option4 = new JButton("Consulter des avis");
        JButton option5 = new JButton("Consulter un profil");
        JButton option6 = new JButton("Supprimer son compte");
        JButton logoutButton = new JButton("Se déconnecter");

        // Appliquer une taille uniforme à tous les boutons
        Dimension buttonSize = new Dimension(300, 50); 
        JButton[] buttons = {option1, option2, option3, option4, option5, option6, logoutButton};
        for (JButton button : buttons) {
            button.setPreferredSize(buttonSize);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        // Ajouter les composants au panneau
        panel.add(menuLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Espace entre le titre et le premier bouton
        for (JButton button : buttons) {
            panel.add(button);
            panel.add(Box.createRigidArea(new Dimension(0, 10))); // Espace entre les boutons
        }

        // Section pour les missions acceptées
        JLabel acceptedMissionsLabel = new JLabel("Missions Acceptées", SwingConstants.CENTER);
        acceptedMissionsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        acceptedMissionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Récupérer les missions acceptées
        List<Object[]> acceptedMissions = new ArrayList<>();
        try {
			acceptedMissions = Requete.RequetesParCritere("Benevole", getEmail(), connexion);
		} catch (SQLException e) {
			e.printStackTrace();
		}

        // Panneau pour afficher les missions
        JPanel missionsPanel = new JPanel();
        missionsPanel.setLayout(new BoxLayout(missionsPanel, BoxLayout.Y_AXIS));

        if (acceptedMissions.size() == 0) {
            JLabel noMissionLabel = new JLabel("Aucune mission acceptée pour le moment. Si vous venez d'en accepter une, ce sera mis à jour à votre prochaine connexion");
            noMissionLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            noMissionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            missionsPanel.add(noMissionLabel);
        } else {
            for (Object missionObject : acceptedMissions) {
                Object[] mission = (Object[]) missionObject;
                String nameRequete = (String) mission[0];
                String typeRequete = (String) mission[1];
                String description = (String) mission[2];
                String date = (String) mission[3];
                String status = (String) mission[4];
                String contact = (String) mission[5];
                String benevole = (String) mission[6];

                // Créer un panneau pour une mission
                JPanel missionPanel = new JPanel();
                missionPanel.setLayout(new BoxLayout(missionPanel, BoxLayout.Y_AXIS));
                missionPanel.setBorder(BorderFactory.createTitledBorder(nameRequete)); // Titre de la mission
                missionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Ajouter les détails de la mission
                missionPanel.add(new JLabel("Type: " + typeRequete));
                missionPanel.add(new JLabel("Description: " + description));
                missionPanel.add(new JLabel("Date: " + date));
                missionPanel.add(new JLabel("Statut: " + status));
                missionPanel.add(new JLabel("Contact: " + contact));
                missionPanel.add(new JLabel("Bénévole: " + benevole));

                // Espacement entre les missions
                missionPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                // Ajouter le panneau de mission au panneau principal
                missionsPanel.add(missionPanel);
            }
        }

        // Ajouter un panneau défilant pour les missions
        JScrollPane scrollPane = new JScrollPane(missionsPanel);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ajouter les missions sous les boutons
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Espace avant les missions
        panel.add(acceptedMissionsLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Espace avant la liste des missions
        panel.add(scrollPane);

        // Ajouter l'onglet Menu
        tabbedPane.addTab("Menu", panel);

        // Configuration de la fenêtre
        frame.getContentPane().removeAll();
        frame.getContentPane().add(tabbedPane);
        frame.revalidate();
        frame.repaint();

        // Actions des boutons
        option1.addActionListener(e -> {
            int index = tabbedPane.indexOfTab("Formuler une requête");
            if (index == -1) {
                JPanel requestPanel = handleRequestFormulation();
                tabbedPane.addTab("Formuler une requête", requestPanel);
                tabbedPane.setSelectedIndex(tabbedPane.indexOfTab("Formuler une requête"));
            } else {
                tabbedPane.setSelectedIndex(index);
            }
        });
        option2.addActionListener(e -> {
            int index = tabbedPane.indexOfTab("Consulter des Requêtes");
            if (index == -1) {
                JPanel requestPanel = handleViewRequests(Main.AllUserInfo(connexion, email));
                tabbedPane.addTab("Consulter des Requêtes", requestPanel);
                tabbedPane.setSelectedIndex(tabbedPane.indexOfTab("Consulter des Requêtes"));
            } else {
                tabbedPane.setSelectedIndex(index);
            }
        });
        option3.addActionListener(e -> {
            int index = tabbedPane.indexOfTab("Poster un Avis");
            if (index == -1) {
                JPanel requestPanel = handlePostReview();
                tabbedPane.addTab("Poster un Avis", requestPanel);
                tabbedPane.setSelectedIndex(tabbedPane.indexOfTab("Poster un Avis"));
            } else {
                tabbedPane.setSelectedIndex(index);
            }
        });
        option4.addActionListener(e -> {
            int index = tabbedPane.indexOfTab("Consulter des Avis");
            if (index == -1) {
                JPanel requestPanel = handleViewReviews();
                tabbedPane.addTab("Consulter des Avis", requestPanel);
                tabbedPane.setSelectedIndex(tabbedPane.indexOfTab("Consulter des Avis"));
            } else {
                tabbedPane.setSelectedIndex(index);
            }
        });
        option5.addActionListener(e -> {
            int index = tabbedPane.indexOfTab("Voir un profil");
            if (index == -1) {
                JPanel requestPanel = handleViewProfile();
                tabbedPane.addTab("Voir un profil", requestPanel);
                tabbedPane.setSelectedIndex(tabbedPane.indexOfTab("Voir un profil"));
            } else {
                tabbedPane.setSelectedIndex(index);
            }
        });
        option6.addActionListener(e -> handleSuppression());
        logoutButton.addActionListener(e -> showWelcomePage());
    }


    
 // Fonctions correspondant aux actions possibles
    
    private JPanel handleRequestFormulation() {
        // Panel pour l'onglet "Formuler une requête"
        System.out.println("Action : Formuler une requête");

        JPanel requestPanel = new JPanel();
        requestPanel.setLayout(new BoxLayout(requestPanel, BoxLayout.Y_AXIS));

        // Titre
        JLabel titleLabel = new JLabel("Formuler une Requête");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Champ "Nom de la requête" - plus petit
        JLabel nameLabel = new JLabel("Nom de la requête :");
        JTextField nameField = new JTextField(20); // Largeur du champ plus petite
        nameField.setPreferredSize(new Dimension(50, 30));  // Taille du champ ajustée

        // Champ "Description de la requête" - plus grand
        JLabel descriptionLabel = new JLabel("Description de la requête :");
        JTextArea descriptionArea = new JTextArea(5, 20); // Taille plus petite que précédemment
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionScrollPane.setPreferredSize(new Dimension(100, 100)); // Taille ajustée

        // Champ "Type de la requête"
        JLabel typeLabel = new JLabel("Type de la requête :");
        String[] types = {"Offre", "Demande"};
        JComboBox<String> typeComboBox = new JComboBox<>(types);
        typeComboBox.setPreferredSize(new Dimension(50, 30)); // Taille du combo box ajustée

        // Bouton de soumission
        JButton submitButton = new JButton("Soumettre");
        submitButton.setPreferredSize(new Dimension(250, 40)); // Taille uniforme du bouton

        // Centrer tous les éléments horizontalement
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        typeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ajouter les composants avec espacement entre eux
        requestPanel.add(titleLabel);
        requestPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacement entre titre et champ
        requestPanel.add(nameLabel);
        requestPanel.add(nameField);
        requestPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacement entre champ et description
        requestPanel.add(descriptionLabel);
        requestPanel.add(descriptionScrollPane);
        requestPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacement entre description et type
        requestPanel.add(typeLabel);
        requestPanel.add(typeComboBox);
        requestPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacement avant bouton
        requestPanel.add(submitButton);

        // Action du bouton de soumission
        submitButton.addActionListener(e -> {
            String requestName = nameField.getText().trim();
            String description = descriptionArea.getText().trim();
            String requestType = (String) typeComboBox.getSelectedItem();

            if (requestName.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Le nom de la requête est requis.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (description.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "La description de la requête est requise.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Soumettre la requête
            User.proposerRequete(connexion, requestName, description, requestType, Main.AllUserInfo(connexion, email));
            JOptionPane.showMessageDialog(frame, "Requête soumise avec succès !");
        });

        return requestPanel;
    }


    private JPanel handleViewRequests(Object[] data) {
        System.out.println("Action : Consulter des requêtes.");

        // Panneau principal pour les options de consultation
        JPanel viewRpanel = new JPanel();
        viewRpanel.setLayout(new BoxLayout(viewRpanel, BoxLayout.Y_AXIS));

        // Titre
        JLabel titleLabel = new JLabel("Consulter les requêtes :", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        viewRpanel.add(titleLabel);

        // Boutons radio pour les options de consultation
        JRadioButton ownRequestsButton = new JRadioButton("Voir mes propres requêtes");
        JRadioButton allOffersButton = new JRadioButton("Voir toutes les offres");
        JRadioButton allDemandsButton = new JRadioButton("Voir toutes les demandes");

        ButtonGroup group = new ButtonGroup();
        group.add(ownRequestsButton);
        group.add(allOffersButton);
        group.add(allDemandsButton);

        ownRequestsButton.setSelected(true);
        viewRpanel.add(ownRequestsButton);
        viewRpanel.add(allOffersButton);
        viewRpanel.add(allDemandsButton);

        // Bouton pour afficher les requêtes
        JButton showRequestsButton = new JButton("Afficher les requêtes");
        viewRpanel.add(showRequestsButton);

        // Fenêtre principale
        JFrame requestFrame = new JFrame("Consulter les Requêtes");


        // Action pour afficher les requêtes
        showRequestsButton.addActionListener(e -> {
            String critere = null;
            String valeur = null;

            if (ownRequestsButton.isSelected()) {
                critere = "Contact";
                valeur = getEmail();
            } else if (allOffersButton.isSelected()) {
                critere = "TypeRequete";
                valeur = "Offre";
            } else if (allDemandsButton.isSelected()) {
                critere = "TypeRequete";
                valeur = "Demande";
            }

            // Récupérer les requêtes
            List<Object[]> requetes = new ArrayList<>();
            try {
                requetes = Requete.RequetesParCritere(critere, valeur, connexion);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(requestFrame, "Erreur lors de la récupération des requêtes.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (requetes == null || requetes.isEmpty()) {
                JOptionPane.showMessageDialog(requestFrame, "Aucune requête disponible pour cette option.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Panneau avec une barre de défilement pour afficher les requêtes
            JPanel requestListPanel = new JPanel();
            requestListPanel.setLayout(new BoxLayout(requestListPanel, BoxLayout.Y_AXIS));

            for (Object[] requete : requetes) {
                // Données de la requête
                String nameRequete = (String) requete[0];
                String typeRequete = (String) requete[1];
                String description = (String) requete[2];
                String date = (String) requete[3];
                String status = (String) requete[4];
                String contact = (String) requete[5];
                String benev = (String) requete[6];

                // Panneau pour chaque requête
                JPanel singleRequestPanel = new JPanel();
                singleRequestPanel.setLayout(new BoxLayout(singleRequestPanel, BoxLayout.Y_AXIS));
                singleRequestPanel.setBorder(BorderFactory.createTitledBorder(nameRequete));

                // Ajouter les informations au panneau
                singleRequestPanel.add(new JLabel("Type : " + typeRequete));
                singleRequestPanel.add(new JLabel("Description : " + description));
                singleRequestPanel.add(new JLabel("Date : " + date));
                singleRequestPanel.add(new JLabel("Statut : " + status));
                singleRequestPanel.add(new JLabel("Contact : " + contact));
                singleRequestPanel.add(new JLabel("Benevole ayant accepté la mission: " + benev));

                // Options pour chaque requête
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

                JButton acceptButton = new JButton("Accepter");
                JButton validateButton = new JButton("Valider");
                JButton novalidateButton = new JButton("Refuser");
                JButton deleteButton = new JButton("Supprimer");
                JButton modifyButton = new JButton("Modifier");

                // Ajouter les options au panneau
                buttonPanel.add(acceptButton);
                if ("Structure".equals(data[6])) {
                    buttonPanel.add(validateButton);
                    buttonPanel.add(novalidateButton);
                }

                if (getEmail().equals(contact)) {
                	 buttonPanel.add(deleteButton);
                     buttonPanel.add(modifyButton);
                }
               

                singleRequestPanel.add(buttonPanel);
                requestListPanel.add(singleRequestPanel);

                // Actions pour les boutons
                acceptButton.addActionListener(e1 -> {
                	System.out.println(status);
                    if (status!="refusé" || status!="en attente") { //NE MARCHE PAS
                    	User.repondreRequete(connexion, nameRequete, contact);
                        JOptionPane.showMessageDialog(requestFrame, "Vous avez accepté la requête : " + nameRequete);
                    } else {
                    	JOptionPane.showMessageDialog(requestFrame, "La requête est en attente de validation ou refusé. Vous ne pouvez pas l'accepter.", "Erreur", JOptionPane.WARNING_MESSAGE);
                    }
                    
                });

                validateButton.addActionListener(e1 -> {
                    structure.validerService(connexion, nameRequete, contact, true, "");
                    JOptionPane.showMessageDialog(requestFrame, "Vous avez validé la requête : " + nameRequete);
                });
                
                novalidateButton.addActionListener(e1 -> {
                	String reason = JOptionPane.showInputDialog(requestFrame, "Veuillez indiquer la raison du refus :");

                    if (reason == null || reason.trim().isEmpty()) {
                        // Si la raison est vide ou annulée, ne pas continuer
                        JOptionPane.showMessageDialog(requestFrame, "Vous devez fournir une raison pour refuser la requête.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Appeler la méthode validerService avec la raison
                        structure.validerService(connexion, nameRequete, contact, false, reason);
                        JOptionPane.showMessageDialog(requestFrame, "Vous avez refusé la requête : " + nameRequete + " pour la raison : " + reason);
                    }

                });
                
                

                deleteButton.addActionListener(e1 -> {
                    JOptionPane.showMessageDialog(requestFrame, "Suppression de la requête : " + nameRequete);
                    // Implémenter la suppression ici
                    boolean supp = User.SupprimerRequete(connexion, nameRequete, contact);
  
                    if (supp) {
                    	JOptionPane.showMessageDialog(requestFrame, "Requête supprimée avec succès.");
                    } else {
                    	JOptionPane.showMessageDialog(requestFrame, "Erreur lors de la suppression de la requête.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                });

                
                modifyButton.addActionListener(e1 -> {
                    // Afficher une boîte de dialogue pour que l'utilisateur choisisse ce qu'il veut modifier
                	JOptionPane.showMessageDialog(requestFrame, "Modification de la requête : " + nameRequete);
                    String[] options = {"Modifier le nom", "Modifier la description"};
                    int choix = JOptionPane.showOptionDialog(requestFrame, 
                                                              "Que voulez-vous modifier ?", 
                                                              "Modifier Requête", 
                                                              JOptionPane.DEFAULT_OPTION, 
                                                              JOptionPane.QUESTION_MESSAGE, 
                                                              null, options, options[0]);

                    // Si l'utilisateur a choisi "Modifier le nom" ou "Modifier la description"
                    if (choix == 0 || choix == 1) {
                        // Demander la nouvelle valeur selon le choix
                        String label = (choix == 0) ? "Entrez le nouveau nom de la requête :" : "Entrez la nouvelle description de la requête :";
                        String newValue = JOptionPane.showInputDialog(requestFrame, label);

                        if (newValue != null && !newValue.trim().isEmpty()) {
                            // Appeler la méthode pour modifier la requête
                            int typeModification = (choix == 0) ? 1 : 2;  // 1 pour modifier le nom, 2 pour la description
                            User.modifierRequete(connexion, nameRequete, typeModification, newValue, data);

                            // Confirmer la modification
                            String field = (choix == 0) ? "nom" : "description";
                            JOptionPane.showMessageDialog(requestFrame, field + " de la requête modifié avec succès.");
                        } else {
                            JOptionPane.showMessageDialog(requestFrame, "Valeur vide, la modification a été annulée.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

            }

            // Ajouter une barre de défilement
            JScrollPane scrollPane = new JScrollPane(requestListPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            // Afficher les requêtes dans une nouvelle fenêtre
            JFrame listFrame = new JFrame("Liste des Requêtes");
            listFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            listFrame.setSize(600, 500);
            listFrame.getContentPane().add(scrollPane);
            listFrame.setVisible(true);
        });

        return viewRpanel;
    }


    


    private JPanel handlePostReview() {
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
        /*reviewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reviewFrame.setSize(400, 400);
        reviewFrame.getContentPane().add(panel);
        reviewFrame.setVisible(true);*/

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
        return panel;
    }


    private JPanel handleViewReviews() {
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
        /*reviewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reviewFrame.setSize(400, 300);
        reviewFrame.getContentPane().add(panel);
        reviewFrame.setVisible(true);*/

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
        return panel;
    }


    private JPanel handleViewProfile() {
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
        /*profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        profileFrame.setSize(400, 300);
        profileFrame.getContentPane().add(panel);
        profileFrame.setVisible(true);*/

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
        return panel;
    }
    
    private void handleSuppression() {
    	boolean ok = User.SupprimerCompte(connexion, getEmail());
    	showWelcomePage();
    }

}
