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

public class BimerError {
	int type;
	String Word;
	String PreviousWord;
	double score;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getWord() {
		return Word;
	}
	public void setWord(String word) {
		Word = word;
	}
	public String getPreviousWord() {
		return PreviousWord;
	}
	public void setPreviousWord(String previousWord) {
		PreviousWord = previousWord;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
	
}
