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
public class StatPre {
	int occurences;
	int occurences_pre_er;
	int occurences_pre_eacc;

	public int getOccurences() {
		return occurences;
	}

	public void setOccurences(int occurences) {
		this.occurences = occurences;
	}

	public int getOccurences_pre_er() {
		return occurences_pre_er;
	}

	public void setOccurences_pre_er(int occurences_pre_er) {
		this.occurences_pre_er = occurences_pre_er;
	}

	public int getOccurences_pre_eacc() {
		return occurences_pre_eacc;
	}

	public void setOccurences_pre_eacc(int occurences_pre_eacc) {
		this.occurences_pre_eacc = occurences_pre_eacc;
	}
	
	public double computeScoreForEr() {
		return (double) ((double) occurences_pre_er / (double) occurences);
	}
	
	public double computeScoreForEacc() {
		return (double) ((double) occurences_pre_eacc / (double) occurences);
	}

	public String toDisplay() {
		return "occ:" + occurences 
				+ " er=" + occurences_pre_er +"="+computeScoreForEr()
				+" eacc=" + occurences_pre_eacc + " ="+computeScoreForEacc() ;
	}

}
