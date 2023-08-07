

# [![My Skills](https://skills.thijs.gg/icons?i=java)](https://skills.thijs.gg) MotsFleches

## Présentation

L’objectif du travail est la réalisation d’une application permettant d’accompagner la conception de
grilles de mots fléchés. Il ne s’agit pas de faire une application pour jouer aux mots fléchés, il ne s’agit
pas de faire une application qui remplit automatiquement la grille.
Quand une grille est terminée, l’application permet d’imprimer la grille sans les définitions afin qu’une
autre personne puisse s’amuser à la remplir.

![MotsFleches](/img/motfleches.png)

## 1. Création d'une grille 

Création d’une grille carrée de n x n cases ou d’une grille rectangulaire de n x m cases
Le positionnement des cases contenant les définitions de mots doit pouvoir être fait au hasard. Les
cases de définition permettent d’indiquer si le mot s’écrit à droite de la case (à l’horizontale) ou en
dessous de la case (à la verticale). La définition elle-même contient une indication (flèche) indiquant
l’orientation du mot. A cette indication il est possible d’ajouter une définition textuelle.

## 2. Définitions

La grille de travail permet d’ajouter et de supprimer des définitions selon le contexte des cases alentour,
qu’elles soient remplies ou pas. Vous trouverez en annexe les différents cas de figure qui peuvent être
rencontrés.

## 3. Support d'un dictionnaire

L’application se base sur l’utilisation d’un fichier contenant l’ensemble des mots qui pourront être
utilisés. Un fichier initial est fourni, il contient des mots triés dans l’ordre alphabétique. L’objectif est
que l’utilisateur puisse chercher un mot dans ce fichier selon certains critères, par exemple, un mot d’un
nombre de lettres donné (n lettres), un mot commençant par une lettre donnée (lettre i), un mot d’un
nombre de lettres donné et contenant une certaine lettre à une position donnée (n lettres avec la lettre
i en position p), un mot avec les lettres i1, i2, in en position p1, p2, pn.

## 4. Support graphique

La conception d’une grille de jeu nécessite l’utilisation d’un environnement graphique permettant de
positionner une définition, indiquer sa direction et indiquer le nombre de lettres.
Votre application utilisera les librairies Swing ou Java FX.

## 5. Évolutivité

Votre application devra mettre en œuvre le patron MVC afin que votre application puisse évoluer de
différents points de vue :
    - Logique métier : la réutiliser pour une autre application
    - Modèle de données : remplacer le dictionnaire sous forme de fichier par une base de données
    - Remplacement de la vue Swing ou Java FX par une vue HTML/CSS

### ATTENTION :

    - Le développement de la vue ne sera autorisé par le professeur que dans la mesure où les parties
    modèle et contrôleur sont déjà fonctionnelles
    
### Remarques :

Différences entre la vue et le modèle :
    - les mots disponibles dans le dictionnaire font partie du modèle de données
    - les mots qui apparaissent dans la grille graphique (la vue) sont une représentation visuelle d’un
    tableau de données (le modèle) : la « grille visuelle » est mise à jour selon les informations
    présentes dans ce tableau de données qu’on appellera « grille virtuelle » ou « ModeleGrille ». De
    cette façon, le contrôleur peut aller chercher des données dans le modèle pour demander de
    rafraîchir l’affichage des données dans la vue. Il fera cela via l’utilisation de méthodes spécifiques
    au modèle et à la vue. Ainsi, le fonctionnement est maintenu même si le format des données
    change où si l’interface graphique change
