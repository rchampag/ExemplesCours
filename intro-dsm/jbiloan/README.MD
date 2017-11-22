# Introduction DSM

Ce projet sert d'introduction aux _Dependency Structure Matrix_ (DSM).  
Il s'agit d'une application Java qui permet de faire
divers calculs associés à un emprunt dans une institution financière.
L'application de départ et sa documentation se trouvent au
[https://sourceforge.net/projects/jbiloan/]().

Le code source de départ a été converti en projet Maven. Aucune autre modification
n'a été apportée au projet d'origine.
  
## Pré-requis 

L'exemple tel que livré ici est presque autonome. Les seuls prérequis sont que:

* un compilateur java doit être sur le chemin d'accès ("path");
* l'outil Maven doit être sur le chemin d'accès ("path").


## Particularités de l'exemple
Bien que vous puissiez manipuler l'exemple dans votre IDE favori, il est 
aussi possible de le compiler et l'exécuter à la ligne de commande. La compilation
est réalisée via Apache Maven. Un script de compilation [pom.xml](./pom.xml) est
fourni. **Une connexion Internet est requise pour compiler l'exemple** (au moins
la première fois sur un ordinateur donné).

## Compilation de l'exemple

* ouvrir une coquille ("shell": DOS, bash, ...) sur votre plate-forme préférée;
* se déplacer à l'endroit où se trouve le fichier pom.xml dans cette archive (en principe au même niveau que les présentes directives);
* lancer `mvn package` (par défaut, l'ensemble du code java est compilé et une archive JAR est créée).
 
## Exécution de l'exemple

Il y a trois options possibles pour exécuter ce système:

1. Dans le même coquille que pour la compilation, lancer `mvn exec:java`
2. Dans le même coquille que pour la compilation, se déplacer dans le répertoire où sont stockées les classes compilées avec la commande `cdtarget/classes`, et lancer `java loanmain.JbiLoan`
3. Dans un explorateur de fichier, se déplacer dans le répertoire `target` et double-cliquer sur `jbiloan-1.0.0.jar`

## Documentation du projet

1. Dans le même coquille que pour la compilation, lancer `mvn site`. Un site web de projet est généré dans le répertoire `target/site`.
2. Ouvrir le fichier [index.html](./target/site/index.html). Consulter la section "Project reports" tout en bas. Cette section contient deux sources de documentation intéressantes:
  1. la documentation javadoc du projet;
  2. une matrice de dépendances (DSM) du projet complet. Voir les ressources plus bas pour comprendre ce qu'est une DSM et savoir comment l'interpréter.

## Ressources utiles

* [Page wikipedia sur les DSM](https://en.wikipedia.org/wiki/Design_structure_matrix)
* [Documentation du dsm-maven-plugin](https://github.com/sevntu-checkstyle/dsm-maven-plugin)
* [Vidéo explicative sur comment lire une DSM](https://www.youtube.com/watch?v=4cL4xoy7cMc&list=PLLNGJX9z7bBrKL5Q2t74qaonX6nTPaj2s)
* [Intellij IDEA DSM tool window](https://www.jetbrains.com/help/idea/dsm-tool-window.html)
* [Intellij IDEA dependency viewer](https://www.jetbrains.com/help/idea/dependency-viewer.html)
* [Explications sur les DSM par Ndepend](https://www.ndepend.com/docs/dependency-structure-matrix-dsm)

Par Roger Champagne, P.Eng, Ph.D.
École de technologie supérieure
MÀJ 2017-Nov-22