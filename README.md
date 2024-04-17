# SAMLops

[![codecov](https://codecov.io/gh/mohamira12/SAMLops/graph/badge.svg?token=37dM2ZFoVv)](https://codecov.io/gh/mohamira12/SAMLops)

Description des fonctionnalités fournies
1. Chargement de données à partir d’un tableaux et d'un fichier CSV

Nous avons deux constructeurs : 
- Le premier : `DataFrame(List<String> columns, List<List<Object>> data)` charger les données à partir d’un tableau

- Le deuxième :  `DataFrame(String filePath)` charge les données à partir d'un fichier CSV. On lit les noms de colonnes à partir de la première ligne du fichier et on déduit les types de données des colonnes à partir de la deuxième ligne.

2. Affichage des données

- La méthode `display()` affiche toutes les données du DataFrame sous forme de tableau.
- La méthode `displayFirstRows(int n)` affiche les premières `n` lignes de la DataFrame.
- La méthode `displayLastRows(int n)` affiche les dernières `n` lignes de la DataFrame.


3. Opérations statistiques

- La méthode `calculateMean(String columnName)` calcule la moyenne des valeurs dans une colonne spécifique.
- La méthode `calculateMin(String columnName)` calcule la valeur minimale dans une colonne spécifique.
- La méthode `calculateMax(String columnName)` calcule la valeur maximale dans une colonne spécifique.

4. Sélection de lignes par indices

-La méthode `selectRowsByIndices(List<Integer> indices)` permet de sélectionner des lignes spécifiques du DataFrame en fournissant une liste d'indices.

-selectColumnsByLabels(List<String> selectedColumns)
Cette méthode prend une liste de noms de colonnes et retourne un nouveau DataFrame contenant uniquement ces colonnes spécifiées. Elle filtre les données en gardant uniquement les valeurs des colonnes sélectionnées pour toutes les lignes.

–advancedSelectionWithColumnValue(String columnName, Object value)
Cette méthode permet de filtrer les lignes en se basant sur une valeur spécifique dans une colonne donnée. Elle retourne un nouveau DataFrame contenant seulement les lignes où la colonne choisie à la valeur spécifiée.

5-Méthodes utilitaires :

- `determineType(String value)`: Cette méthode détermine le type de données pour une valeur donnée.
- `convertToType(String value, Class<?> type)`: Cette méthode convertit une valeur de type String en un type de données spécifié.
- `determineColumnTypes(List<List<Object>> data)`: Cette méthode détermine les types de données des colonnes en analysant les données du DataFrame.
-parseCSV(String filePath): La méthode parseCSV lit un fichier CSV, extrait les noms des colonnes, détermine les types de données pour chaque colonne et remplit le DataFrame avec les données converties. Cette méthode est utilisée dans le deuxième constructeur.

Ces fonctions permettent aux utilisateurs de charger des données, d'afficher les données, d'effectuer des opérations statistiques et de sélectionner des lignes spécifiques dans le DataFrame. Elles offrent ainsi une gamme complète de fonctionnalités pour la manipulation de données tabulaires en Java.

=============================================================================
Choix d'outils
Pour ce projet, les outils suivants ont été utilisés :
Langage de programmation : Java
Gestionnaire de versions : Git
Système de contrôle de version : GitHub
Outil de construction : Maven
couverture de code : JaCoCo
Environnement de développement intégré (IDE) : vscode
Intégration et déploiement continus : Github Actions 
Documentation : GIthub pages 

=============================================================================
Workflow Git et procédure de validation des Pull/Merge requests
Le workflow Git mis en place pour ce projet suit les étapes suivantes :
Création d'une branche de fonctionnalité à partir de la branche principale (main).
Développement de la fonctionnalité sur la branche de fonctionnalité (feauture/nom_de_la_fonctionnalite).
Création d'un Pull Request 
Revue de code par une/toutes les membres du groupe. 
Intégration continue via GitHub Actions : exécution des tests automatisés et vérifications de style.
Validation (ou pas) du Pull Request.
Fusion (Merge) de la Pull Request dans la branche principale.

====================================================================================
Docker
Cette image est utilisée pour construire et tester le projet Java. Elle inclut Maven pour la gestion des dépendances et OpenJDK 11 pour la compatibilité avec les sources Java. La construction est effectuée en copiant le pom.xml et le dossier src dans l'image, suivi par l'exécution de mvn clean install pour compiler et tester le projet.
Après la construction, l’image openjdk:11-jre-slim est utilisée pour exécuter l'application. Le fichier JAR compilé est copié de l'image de construction à l'image d'exécution pour réduire la taille et ne conserver que les composants nécessaires à l'exécution de l'application.








