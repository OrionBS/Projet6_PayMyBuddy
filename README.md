# Projet PayMyBuddy - Transaction's App
***

Une appli qui permettrait aux clients de transférer de l'argent pour gérer leurs finances ou payer leurs amis.

#### Lien Github : https://github.com/OrionBS/Projet6_PayMyBuddy

## Pour commencer

### Pré-requis

- IntelliJ (Environnement de développement)
- Java 11
- MySQL
- Maven 3.6.3

### Installation

```
cd /"dossier de téléchargement"/
git clone https://github.com/OrionBS/Projet6_PayMyBuddy
cd /Projet6_PayMyBuddy
mvn clean verify
```
### Paramétrage MySQL

Créez 2 variables d'environnement :

**Attention lorsque qu'une valeur est entre <>, merci de la remplacer par ce qu'il vous convient et de retirer les crochets.**
```
DB_USERNAME=<au choix username>
DB_PASSWORD=<au choix password>

Merci de faire correspondre vos choix aux variables initiées par la suite dans la BDD.
```

Dans la console, démarrez MySQL
```
mysql
```
Puis créez la base de donnée et l'utilisateur avec ses privilèges.
```
create database PMB_DB;
create user '<au choix username>'@'localhost' identified by '<au choix password>';
grant all privileges on PMB_DB.* to '<au choix username>'@'localhost';
flush privileges;
```

## Démarrage

```
java -jar PayMyBuddyApp.jar
```

## Diagramme UML
![UML_Diagram](src/main/resources/static/img/UML_diagram.png "Diagramme UML")

## Le Modèle Physique de Données
![MPD_Diagram](src/main/resources/static/img/MPD.png "Diagramme MPD")

## Web

http://localhost:8080

## Fabriqué avec

* [IntelliJ Community](https://www.jetbrains.com/idea/download/#section=windows) - Environnement de développement
* [Java SE JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) - JDK Java
* [Maven 3.6.3](http://maven.apache.org/download.cgi) - Maven

### Rapport SureFire

```
$ mvn surefire-report:report
```

## Versions

**Dernière version stable :** 1.0

## Auteurs
Listez le(s) auteur(s) du projet ici !
* **Orion Beauny** _alias_ [@OrionBS](https://github.com/OrionBS)
* Tous les [contributeurs](https://github.com/OrionBS/Projet5_SafetyNet/contributors)

## License

Ce projet est conçu pour répondre en tant que travail d'étudiant lors d'une soutenance.


