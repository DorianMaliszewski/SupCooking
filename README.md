# SupCooking
-------------------------------------------------------------------------
3JVA - Projet Supinfo

Projet fait avec :
> Glassfish     5.0
> Java          1.8.0
> J2EE          7
> Hibernate     4.3
> PostgreSQL    10.1

** Pour fonctionner l'application à besoin d'un accès à une base de données générée par hibernate **

Pour faire en sorte de pouvoir modifier/supprimer les catégories et les produits il faut modifier en base le role de l'utilisateur enregistrer par **ROLE_ADMIN**.
La modification et suppression de catégorie et de produits n'était pas préciser dans le sujet mais nous l'avons quand même fait.

** Le lien pour se connecter à l'api : <lien vers la racine du projet/api/{recipes,categories,products} **

Information sur la structures des fichiers :
> Classes web services se trouvent dans le package service.
> Classes des entités se trouvent dans le package models.
> Classes des fournisseurs de servicesse trouvent  dans le package providers.
> Classes des controleurs (servlets) se trouvent dans le package controllers.
> Classes des accès à la base de données se trouvent dans le package repositories.

** Ne pas supprimer le dossier upload se trouvant dans le dossier web il est important que l'upload soit fonctionnel. **

Le dossier javadoc à la racine du zip représente une javadoc de tous le projet. Il a la possibilité de la regénérer car toutes la documentation est encore en place.

