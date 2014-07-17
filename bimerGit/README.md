bimer
=======

 Ce programme est un programmme écrit (beaucoup trop) rapidement et jetable, 
 autant dire que ce n'est certainement pas un exemple à suivre...
 Ce programme n'a pas été suffisament testé et manque cruellement de commentaires
 Ce code n'est donc ni optimisé, ni robuste, ni lisible 
 
 le but de ce programme est de vérifier l'accord des verbes en er ou é pour du Français
 il est loin de detecter toutes les erreurs et en plus il y a des faux positifs ( le programme indique une faute alors qu'il n'y en a pas)
 
 Ce programme fonctionne avec une liste de mots à exclure de la vérification (des mots en é qui ne sont pas des verbes (ex : "fiabilité" )
 et une liste de mots en er qui ne sont pas des verbes ( exemple : "premier")
 malheureusement il ne desambiguise pas les mots qui peuvent être à la fois des verbes ou des substantifs
 (exemples : cité, envoyé )  ces mots là, on peut les placer dans les fichiers d'exclusion pour éviter d'avoir trop de faux positifs
 Il est possible d'améliorer le programme en ajoutant des listes de mots à exclure
 
 Ce programme utilise des données qui ont été acquises par apprentissage sur des textes en français correct
 (mais malheureusement trop peu)
 
 Il est possible de l'améliorer en lui fournissant un nouvel apprentissage plus fourni (au moins plusieurs centaines de Ko de texte).
 
 Même s'il est possible d'améliorer ce programme, la technique même de détection ne permet pas d'en faire 
 un outil utilisable dans un contexte un peu sérieux  (cependant, ça marche dans certains cas et on peut s'amuser avec, c'est le but) 
 


Licence
-------

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, see
<http://www.gnu.org/licenses/>.
