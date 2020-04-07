# Projet 6: Formation Développeur d'application Java

Ce projet a été réalisé à l'aide de spring boot.

## Requierement

- Un serveur Tomcat
- Un serveur MariaDB
- Java JRE 1.8

## Compilation

### Déploiement sur serveur TOMCAT:

__**Attention:**__ Penser à éditer le fichier application.properties pour personnaliser l'accès à la base de donnée


````
git clone https://github.com/Coubiac/escalade.git
cd escalade
./mvnw package
````
Un fichier ````war```` sera généré dans le dossier ````target````

Ce fichier peut être déployé sur un serveur TOMCAT.

