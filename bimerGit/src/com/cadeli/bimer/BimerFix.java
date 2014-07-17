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

import java.util.ArrayList;
import java.util.List;

public class BimerFix {
	public static final int NOTHING = 0;
	public static final int ER_DETECTED = 10;
	public static final int E_ACC_DETECTED = 20;
	public static final int ER_DETECTED_FIXABLE = 30;
	public static final int E_ACC_DETECTED_FIXABLE = 40;
	public static final int BIM_ER_TO_EACC = 50;
	public static final int BIM_EACC_TO_ER = 60;

	private static String previous_word = "";
	private static String word = "";
	private static boolean a_acc;

	private static List<BimerError> bimerErrors = new ArrayList<BimerError>();
	
	public static void init() {
		bimerErrors.clear();
	}

	public static int processWord(String word, int num_word) {
		// System.out.println("processWord:" + word + ":" + num_word);
		a_acc = false;
		BimerFix.word = word;
		int ret = NOTHING;
		if (previous_word.equals("a")) {
			a_acc = true;
		}
		if (!DicoPre.isInExclude(word)) {
			if (UtilBimer.isEr(word)) {
				if (DicoPre.isFixable(previous_word)) {
					ret = fix(previous_word, ER_DETECTED);
						// System.out.println("processWord:[ER]" + previous_word + " " + word + ":" + num_word);
				} else {
					ret = ER_DETECTED;
				}
			}
			if (UtilBimer.isEacc(word)) {
				if (DicoPre.isFixable(previous_word)) {
					ret = fix(previous_word, E_ACC_DETECTED);
					// System.out.println("processWord:[E_ACC]" + previous_word + " " + word + ":" + num_word);
				} else {
					ret = E_ACC_DETECTED;
				}
			}
		} else {
			// System.out.println("processWord:exclude = "+ word);
		}
		previous_word = word;
		return ret;
	}

	private static int fix(String previous_word, int suffix) {
		StatPre statPre = DicoPre.getStatPre(previous_word);
		// System.out.println("fix:" +word+"="+ statPre.toDisplay());
		if (suffix == ER_DETECTED) {
			// System.out.println("fix:ER="+ word);
			if (statPre.computeScoreForEacc() > DicoPre.getSeuil_detection()) {
				BimerError bimerError = new BimerError();
				bimerError.setType(BIM_ER_TO_EACC);
				bimerError.setWord(word);
				bimerError.setPreviousWord(previous_word);
				bimerError.setScore(statPre.computeScoreForEacc());
				bimerErrors.add(bimerError);
				return BIM_ER_TO_EACC;
			}
			// System.out.println("fix:BIM seuil eacc not reached " + word + "=" + statPre.toDisplay());
			return ER_DETECTED;
		}

		if (suffix == E_ACC_DETECTED) {
			// System.out.println("fix:EACC="+ word);
			if (statPre.computeScoreForEr() > DicoPre.getSeuil_detection()) {
				BimerError bimerError = new BimerError();
				bimerError.setType(BIM_EACC_TO_ER);
				bimerError.setWord(word);
				bimerError.setPreviousWord(previous_word);
				bimerError.setScore(statPre.computeScoreForEacc());
				bimerErrors.add(bimerError);
				return BIM_EACC_TO_ER;
			}
			// System.out.println("fix:BIM seuil er not reached " + word + "=" + statPre.toDisplay());
			return E_ACC_DETECTED;
		}
		System.out.println("fix:*** internal error fix " + previous_word + "=" + suffix);
		return 0; // should never happens
	}

	public static String getReport() {
		if (a_acc == true) {
			return "****" + " (a -> à) " + word + "** ";
		}
		return "**" + word + "** ";
	}

	public static String getWord() {
		return word;
	}

	public static List<BimerError> getErrors() {
		return bimerErrors;
	}
}
