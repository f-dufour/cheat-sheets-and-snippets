#!/bin/python3
##Florent DUFOUR
###Novembre 2017


## -- IMPORTATIONS -- ##

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

input_raw = pd.read_csv('input.csv', header=2, sep=(";")) #Le dossier courant doit contenir 'input.csv'

## -- DEFINITION DES FONCTIONS POUR PLUS TARD -- ##

def cleaner (df):
    for k in df:
        if ((k != 'mol ') and (k!= 'S ')):
            del df[k]
    df = df.dropna(axis=0, how='any')
    df = df.drop(df[df['S '].map(lambda x: x.startswith('S'))].index)
    return df

def sorter (df):
    df['S '] = df['S '].astype('float')  #Convesion des str en float
    df = df.sort_values(by='S ')         #On peut maintenant trier les énergies
    return df

def enrichment(percent,name_list):       # D'après Lucas S.
    Size=(len(name_list)/100)*percent
    count, target_number, total_number = 0,0,0

    for j in name_list:
        if 'ZINC' not in j:
            target_number += 1
            total_number += 1
            count += 1
        else:
            total_number += 1
            count += 1
        if count>Size:
            break
    E=target_number/total_number
    print ('Enrichment for', percent,'% corresponds to',target_number,'target in',total_number,'total molecules')
    return E

## -- COEUR DU PROBLEME, calculer les valeurs pour tracer la courbe de ROC -- ##

output_cleaned = cleaner(input_raw)
output_sorted  = sorter(output_cleaned)

list_Mol = output_sorted['mol '].tolist() #Les molécules à tester sont dans une liste propre

name_List = [] #Initialisation axe des X
hauteur = [0]  #Initialisation axe des Y

for k in (list_Mol):         #Les poses à évaluer
    if (k not in name_List): #C'est une nouvelle molécule
        name_List += [k]     #On enregistre qu'on l'a déjà rencontré
        if ("CHEM" in k):    #C'est une nouvelle molécule qui est un positif
            hauteur += [hauteur[-1]+1] #On augmente d'un cran
        else:
            hauteur += [hauteur[-1]]   #On stagne

## -- COURBE DE ROC -- ##

X = np.linspace(0, 1, num=len(hauteur)) #Axe des X
Y = [x / 7 for x in hauteur]                       #Axe des Y pour les valeurs de ROC
Z = np.linspace(0, 1, num=len(hauteur))            #Axe des Y pour droite aléatoire

plt.plot(X,Y)       #Courbe de ROC
plt.plot(X,Z, '--') #Courbe de hasard

## -- STATISTIQUES -- ##

area_exp = np.trapz(Y,X,dx=1) #Aire sous la courbe expérimentale
area_ale = np.trapz(Z,X,dx=1) #Aire sous la courbe aléatoire
diff = (area_exp - area_ale)
Etot=7/107

## -- TOUS LES RESULTATS -- ##

output_sorted.to_csv("output.csv") #Le fichier CSV propre est ajouté dans le dossier de travail
print("\n ROC AUC=", area_exp,"\n","ROC=", diff,"\n", "Good protocole ? = ", diff>0, "\n") # ROC_AUC, ROC, Validation du protocole
print('EF5% =',enrichment(5,name_List)/Etot)  #EF5%
print('EF10%=',enrichment(10,name_List)/Etot) #EF10%
print('Nombre de molécules identifiées sur les 107:',len(Y)-1)

plt.xlabel('False positive rate')
plt.ylabel('True positive rate')
plt.title('ROC curve obtained with our best protocole \n against the VS1 database')

plt.show() #Courbe de ROC
