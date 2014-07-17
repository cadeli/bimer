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

package com.cadeli.bimer;

import java.io.File;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DicoPre {

	static Hashtable<String, StatPre> dicoPre = new Hashtable<String, StatPre>();
	static HashSet<String> dicoExclude = new HashSet<String>();

	public static final int ER = 10;
	public static final int EACC = 20;

	private static int seuil_occurences = 4;
	private static double seuil_detection = 0.6;

	
	public static void add(String word, int occurences, int occurences_pre_er,int occurences_pre_eacc) {
		StatPre statPre = new StatPre();
		statPre.setOccurences(occurences);
		statPre.setOccurences_pre_er(occurences_pre_er);
		statPre.setOccurences_pre_eacc(occurences_pre_eacc);
		dicoPre.put(word, statPre);
	}
	
	public static void add(String word, int cat) {
		StatPre statPre = new StatPre();
		if (dicoPre.containsKey(word)) {
			int occ = dicoPre.get(word).getOccurences();
			int pre_er = dicoPre.get(word).getOccurences_pre_er();
			int pre_eacc = dicoPre.get(word).getOccurences_pre_eacc();
			statPre.setOccurences(occ + 1);
			if (cat == ER) {
				statPre.setOccurences_pre_eacc(pre_eacc);
				statPre.setOccurences_pre_er(pre_er + 1);
			}
			if (cat == EACC) {
				statPre.setOccurences_pre_eacc(pre_eacc + 1);
				statPre.setOccurences_pre_er(pre_er);
			}
		} else {
			statPre.setOccurences(1);
			if (cat == ER) {
				statPre.setOccurences_pre_er(1);
			}
			if (cat == EACC) {
				statPre.setOccurences_pre_eacc(1);
			}
		}
		dicoPre.put(word, statPre);
		// display(0);
	}

	public static void addExclude(String word) {
		dicoExclude.add(word);
	}

	public static void clear() {
		dicoPre.clear();
	}

	public static boolean isFixable(String word) {
		if (isInDico(word) == false) {
			return false;
		}
		StatPre statpre = (StatPre) dicoPre.get(word);
		if (statpre.getOccurences() > seuil_occurences) {
			return true;
		}
		return false;
	}

	public static boolean isInDico(String word) {
		if (dicoPre.containsKey(word)) {
			return true;
		}
		return false;
	}

	public static boolean isInExclude(String word) {
		if (dicoExclude.contains(word)) {
			return true;
		}
		return false;
	}

	public static StatPre getStatPre(String word) {
		return (StatPre) dicoPre.get(word);
	}

	public static void display(int n) {
		Set set = dicoPre.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			if (((StatPre) entry.getValue()).getOccurences() > n) {
				String displayStatPre = ((StatPre) entry.getValue()).toDisplay();
				System.out.println(">" + entry.getKey() + "< : " + displayStatPre);
			}
		}
		System.out.println(">size=" + dicoPre.size());
	}

	public static void save(String fileName) {
		try {
			File file = new File(fileName);
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Set set = dicoPre.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			StatPre statPre = (StatPre) entry.getValue();
			UtilBimer.toFile(fileName, (String) entry.getKey() + " "+statPre.getOccurences() + " " + statPre.getOccurences_pre_er() + " " + statPre.getOccurences_pre_eacc()+"\n");
		}
	}

	public static void setSeuil_occurences(int seuil_occurences) {
		DicoPre.seuil_occurences = seuil_occurences;
	}

	public static double getSeuil_detection() {
		return seuil_detection;
	}

	public static void setSeuil_detection(double seuil_detection) {
		DicoPre.seuil_detection = seuil_detection;
	}

}
