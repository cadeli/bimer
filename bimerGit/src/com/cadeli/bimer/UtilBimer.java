package com.cadeli.bimer;

import java.io.FileWriter;
import java.io.IOException;

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

public class UtilBimer {

	public static boolean isEr(String word) {
		if (word.endsWith("er"))
			return true;
		return false;
	}
	
	public static boolean isEacc(String word) {
		if (word.endsWith("é"))
			return true;
		return false;
	}
	
	public static void toFile(String fileName,String s) {
		FileWriter fw;
		try {
			fw = new FileWriter(fileName, true);
			fw.write(s);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
