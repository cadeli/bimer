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

public class LearnPre_er {

	private static String previous_word = "";

	public static void addWord(String word, int num_word) {
		if (!previous_word.isEmpty()) {
			if (!DicoPre.isInExclude(word)) {
				if (!previous_word.equals("la")) { // TODO exclude word en é !!!!!
					if (!previous_word.equals("l")) { // TODO exclude word en é !!!!!
						if (UtilBimer.isEr(word)) {
							DicoPre.add(previous_word, DicoPre.ER);
						}
						if (UtilBimer.isEacc(word)) {
							DicoPre.add(previous_word, DicoPre.EACC);
						}
					}
				}
			}
		}
		previous_word = word;
	}

	public static void addExcludeWord(String word) {
		DicoPre.addExclude(word);
	}

	public static void addStatEr(String line, int num_line) {
		String fields[] = line.split(" ");
		DicoPre.add(fields[0], 
				new Integer(fields[1]).intValue(), 
				new Integer(fields[2]).intValue(), 
				new Integer(fields[3]).intValue());
	}
}
