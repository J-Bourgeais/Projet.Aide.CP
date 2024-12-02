Below is a sample **README.md** tailored for the "Projet.Aide.CP" application based on its GitHub repository and the project's potential setup.

---

# **Projet.Aide.CP**

## **Creators**

This project has been created by
BOURGEAIS Julie
GERARD Ilona
in the context of Project Management class at INSA Toulouse

---

## **Description**

"Projet.Aide.CP" is a Java-based application designed to facilitate connections between **volunteers** and **beneficiaries**. This tool allows users to create and manage requests for assistance, fostering a community-oriented approach to problem-solving. The project is built with a graphical interface using **Swing**, integrates with **Maven** for dependency management, and uses **JUnit** for testing.

---

## **Features**

### User Roles
- **Beneficiaries**: Can submit requests for help and manage existing ones.
- **Volunteers**: Browse requests and propose assistance.
- **Structure**: Can manage its requests

### Core Functionalities
- User registration and login.
- Request creation and tracking.
- Volunteer matching system.
- Graphical User Interface (GUI) built with **Swing**.

---

## **Project Structure**

```
Projet.Aide.CP/
├── src/
│   ├── main/
│   │   ├── java/               # Core application logic
│   │   └── resources/          # Configuration files
│   ├── test/                   # Unit tests (JUnit)
│   └── swing/                  # Swing UI components
├── pom.xml                     # Maven configuration file
├── README.md                   # Project documentation
└── target/                     # Compiled files and artifacts
```

---

## **Technologies**

- **Java**: Primary programming language.
- **Swing**: For building the GUI.
- **Maven**: Dependency management and build tool.
- **JUnit**: Unit testing framework.
- **GitHub**: Version control and collaboration.

---

## **Prerequisites**

- **Java JDK 17** or higher.
- **Maven 3.8+**.
- A supported IDE like IntelliJ IDEA, Eclipse, or NetBeans.

---

## **Installation**

1. Clone the repository:
   ```bash
   git clone https://github.com/J-Bourgeais/Projet.Aide.CP.git
   cd Projet.Aide.CP
   ```

2. Build the project using Maven:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn exec:java
   ```

---

## **Usage**

1. **Start the application**:
   - Launch the GUI through the main class `Main.java` using your IDE or `mvn exec:java`.
   
2. **Register**:
   - Create a new account as a beneficiary, volunteer or structure.

3. **Submit a Request** (Beneficiary):
   - Enter details for the help you need and submit the request.

4. **View Requests** (Volunteer):
   - Browse available requests and offer assistance.
   
5. **Manage Requests** (Structure):
   - Change a request status of a task from the structure.
  
6. **Modify or Delete your Requests** (Beneficiary and Volunteer):
   - Modify the name of the request, or the description.
  
7. **Delete your account** (Everyone)

8. **Post a Review** (Everyone):
   - Post a review and see all reviews of a given user.
  
9. **See a profile** (Everyone)
    
---

## **Testing**

JUnit tests are included in the project. To run all tests:
```bash
mvn test
```

---
