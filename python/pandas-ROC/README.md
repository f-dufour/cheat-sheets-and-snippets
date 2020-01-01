From the gist: https://gist.github.com/f-dufour/c747bcb3aca37ea8d89e0c208b47c09d

# Comment utiliser ce script

* Ouvrir le fichier `.mdb`dans MOE
* L'imprimer en pdf avec **pdf forge**
    - *Il est important de presser 'Print' et d'utiliser pdf forge sans quoi le copier-coller dans excel ne fonctionnera pas correctement*
    - *Le tableau de résultats dans le pdf ne doit pas déborder sur plusieurs pages en largeur*
* Ouvrir le pdf depuis un mac et faire:
    - cmd-A pour sélectionner toutes les données
    - cmd-C pour copier toutes les données dans le presse papier
    - Dans une feuille excel faire cmd-V pour coller les données
    - Enregistrer la feuille en `.csv` sous `input.csv` avec ";" comme séparateur de valeur.
* Mettre ce script dans le même dossier
* Executer le fichier python (voir ci dessous depuis un mac avec un exemple de résultats)

Remerciements: [Lucas S.](https://github.com/LucasESBS) pour l'enrichissement

![screen](https://user-images.githubusercontent.com/29833726/33278080-bd140482-d39a-11e7-8bdf-95e8129065ff.png)


*Testé avec python 3.6.3 sous macOS et Ubuntu avec Jupyter Notebook et python3 en ligne de commande*

# Note de compatibilité

* Les dépendances suivantes doivent être installées sur la machine: pandas, numpy et matplotlib. Voir [Ici](https://docs.python.org/3/installing/index.html) pour les installer avec `pip3`
* Certaines versions de python ne supportent pas les affectations multiples. On a:

```python
#La ligne 31:

count, target_number, total_number = 0,0,0

#Qui devient:

count = 0
target_number = 0
total_number = 0
```

# Si le CSV est généré autrement qu'avec Excel

[Zamzar.com](http://www.zamzar.com/convert/pdf-to-csv/) permet par exemple de traduire le pdf en `.csv`. Le script doit alors être ajusté:

* Notez que les entêtes des colonnes se terminent par un espace avec excel. On a en fait "mol " et "S " pour les 2 colonnes d'intérêt.  il faut les enlever du script si on utilise Zamzar.
    - "mol " devient "mol" partout dans le script
    - "S " devient "S" partout dans le script
* La taille du header devra être ajusté (typiquement = 1)
* Le séparateur ne sera pas forcément ";" (typiquement = ",")

On obtient alors:

```python
input_raw = pd.read_csv('input.csv', header=1, sep=(","))
```

# Si vous n'arrivez pas à ouvrir le pdf généré par MOE

C'est que ce n'est en fait pas un `.pdf` mais un `.ps`. Il suffit d'ajouter l'extension qui convient et ça devrait rouler (mac & linux en tout cas)
