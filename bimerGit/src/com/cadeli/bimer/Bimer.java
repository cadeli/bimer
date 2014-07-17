/*
 * This source is part of the
 * com.cadeli
 * repository.
 *
 * Copyright (C) 2014 cadeli  ( cadeli.drummachine@yahoo.fr  )
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, see
 * <http://www.gnu.org/licenses/>.
 */

/**
 * Ce programme est un programmme écrit (beaucoup trop) rapidement et jetable, 
 * autant dire que ce n'est certainement pas un exemple à suivre...
 * Ce programme n'a pas été suffisament testé et manque cruellement de commentaires
 * Ce code n'est donc ni optimisé, ni robuste, ni lisible 
 * 
 * le but de ce programme est de vérifier l'accord des verbes en er ou é pour du Français
 * il est loin de detecter toutes les erreurs et en plus il y a des faux positifs ( le programme indique une faute alors qu'il n'y en a pas)
 
 * Ce programme fonctionne avec une liste de mots à exclure de la vérification (des mots en é qui ne sont pas des verbes (ex : "fiabilité" )
 * et une liste de mots en er qui ne sont pas des verbes ( exemple : "premier")
 * malheureusement il ne desambiguise pas les mots qui peuvent être à la fois des verbes ou des substantifs
 * (exemples : cité, envoyé )  ces mots là, on peut les placer dans les fichiers d'exclusion pour éviter d'avoit trop de faux positifs
 * Il est possible d'améliorer le programme en ajoutant des listes de mots à exclure
 * 
 * Ce programme utilise des données qui ont été acquises par apprentissage sur des textes en français correct
 * (mais malheureusement trop peu)
 * 
 * Il est possible de l'améliorer en lui fournissant un nouvel apprentissage plus fourni (au moins plusieurs centaines de Ko de texte).
 * 
 * Même s'il est possible d'améliorer ce programme, la technique même de détection ne permet pas d'en faire 
 * un outil utilisable dans un contexte un peu sérieux  (cependant, ça marche dans certains cas et on peut s'amuser avec, c'est le but) 
 * 
 * */

package com.cadeli.bimer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Bimer {
	

	private static final String LEARN_DATA_ER_STATS_TXT = "learn_data/er_stats.txt";
	public static final int PROCESSMODE_LEARN = 10;
	public static final int PROCESSMODE_BIM = 20;
	public static final int PROCESSMODE_LOADEXCLUDE = 30;
	public static final int PROCESSMODE_READ_STAT_ER = 40;

	private static int fixable;
	private static int detected;
	private static int numLigne;
	private static int errors;


	/**
	 * Exemple d'utilisation 'normale' dans txt, le message à vérifier
	**/
	private static void command_bimString(String txt) {
		
		
		// initialisation obligatoire
		load_dico_excude();
		initFixMode();
		tuneFixMode();
		
		// lance la detection des fautes
		decoupe(txt, PROCESSMODE_BIM, false);

		
		// exemple d'affichage des erreurs
		if (errors > 0) {
			System.out.println("" + errors + " error in : " + txt);
		}
		List<BimerError> bimerErrors = BimerFix.getErrors();
		for (BimerError bimerError : bimerErrors) {
			if (bimerError.getType() == BimerFix.BIM_ER_TO_EACC) {
				System.out.println("error :" + bimerError.getPreviousWord() + " " + bimerError.getWord() + " (er -> é) ");
			}
			if (bimerError.getType() == BimerFix.BIM_EACC_TO_ER) {
				System.out.println("error :" + bimerError.getPreviousWord() + " " + bimerError.getWord() + " (é -> er) ");
			}
		}
	}

	/**
	 * Exemple de correction pour un fichier
	 */
	private static void Command_bimFile() {
		// fix file mode
		load_dico_excude();
		initFixMode();
		tuneFixMode();
		readFile("learn_data/log.txt", PROCESSMODE_BIM, true);
		report();
		//
	}

	/**
	 *  mode apprentissage de bimer (reservé aux experts ) 
	 */
	private static void Command_learn() {
		// clear
		DicoPre.clear();
		// learn mode
		load_dico_excude();
		readFile("learn_data/data_test.txt", PROCESSMODE_LEARN, false);
		System.out.println("result:");
		DicoPre.save(LEARN_DATA_ER_STATS_TXT);
		DicoPre.display(8);
	}
	
	public static void bimerInit() {
		load_dico_excude();
		initFixMode();
		tuneFixMode();
	}

	private static void report() {
		System.out.println("lines=" + numLigne + " detected=" + detected + " fixable=" + fixable + " errors=" + errors);
	}

	private static void initFixMode() {
		fixable = 0;
		detected = 0;
		errors = 0;
		numLigne = 0;
		readFile(LEARN_DATA_ER_STATS_TXT, PROCESSMODE_READ_STAT_ER, false);
		BimerFix.init();
	}

	private static void tuneFixMode() {
		DicoPre.setSeuil_occurences(4);
		DicoPre.setSeuil_detection(0.7); // must be > 0.5
	}

	private static void load_dico_excude() {
		readFile("learn_data/exclude_er.txt", PROCESSMODE_LOADEXCLUDE, false);
		readFile("learn_data/exclude_eacc.txt", PROCESSMODE_LOADEXCLUDE, false);
	}

	public static void readFile(String fileName, int processMode, boolean verbose) {
		BufferedReader reader = null;
		try {
			String ligne = "";
			reader = new BufferedReader(new FileReader(fileName));
			ligne = reader.readLine();
			while ((ligne) != null) {
				ligne = reader.readLine();
				if (ligne != null) {
					switch (processMode) {
					case PROCESSMODE_LEARN:
						decoupe(ligne, processMode, verbose);
						break;
					case PROCESSMODE_READ_STAT_ER:
						LearnPre_er.addStatEr(ligne, numLigne);
						break;
					case PROCESSMODE_BIM:
						decoupe(ligne, processMode, verbose);
						break;
					case PROCESSMODE_LOADEXCLUDE:
						decoupe(ligne, processMode, verbose);
						break;
					}
					numLigne++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					// reader.reset();
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return;
	}

	/**
	 * 
	 * @param txt
	 * @param processMode
	 * @param verbose
	 * @return nombre de mots dans txt
	 */
	public static int decoupe(String txt, int processMode, boolean verbose) {
		String word[] = txt.split("[ |\\s|\n|\r|\t|(|)|,|'|’|«|»|_|/|.|…|:|\"|“]");
		int num_word = 0;
		for (int wordNumber = 0; wordNumber < word.length; wordNumber++) {
			String raw1 = word[wordNumber];
			raw1 = raw1.replaceAll("[0-9]", "");
			raw1 = raw1.trim();
			raw1 = raw1.replaceAll("^[\\-]", "");
			raw1 = raw1.replaceAll("[\\-]$", "");
			raw1 = raw1.toLowerCase(Locale.FRANCE);
			if (raw1.length() < 30 && raw1.length() > 0) {
				switch (processMode) {
				case PROCESSMODE_LEARN:
					LearnPre_er.addWord(raw1, num_word);
					break;
				case PROCESSMODE_LOADEXCLUDE:
					LearnPre_er.addExcludeWord(raw1);
					break;
				case PROCESSMODE_BIM:
					int ret = BimerFix.processWord(raw1, num_word);
					switch (ret) {
					case BimerFix.E_ACC_DETECTED_FIXABLE:
						fixable++;
						detected++;
						break;
					case BimerFix.ER_DETECTED_FIXABLE:
						fixable++;
						detected++;
						break;
					case BimerFix.E_ACC_DETECTED:
						detected++;
						break;
					case BimerFix.ER_DETECTED:
						detected++;
						break;
					case BimerFix.BIM_EACC_TO_ER:
						if (verbose) {
							System.out.println("BIM  " + BimerFix.getReport() + "é -> er : " + txt);
						}
						detected++;
						fixable++;
						errors++;
						break;
					case BimerFix.BIM_ER_TO_EACC:
						if (verbose) {
							System.out.println("BIM " + BimerFix.getReport() + "er -> é : " + txt);
						}
						detected++;
						fixable++;
						errors++;
						break;
					}
				}
			}
			num_word++;
		}
		return num_word;
	}
}
