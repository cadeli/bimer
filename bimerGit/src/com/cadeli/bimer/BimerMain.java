package com.cadeli.bimer;

import java.util.List;

public class BimerMain {
	private static String VERSION_NUM = "0.1";

	
	
	public static void main(String[] args) {
		System.out.println("Bimer start version = "+ VERSION_NUM);

	    String txt = "ceci est un test que j'ai foirer, ça permet d'envoyé des trucs ";
		bimString(txt);

		//
		System.out.println("Bimer end");
	}
	
	/**
	 * Exemple d'utilisation 'normale' dans txt, le message à vérifier
	**/
	private static void  bimString(String txt) {
		
		Bimer bimer = new Bimer();
		
		
		// initialisation obligatoire
		bimer.bimerInit();
		
		// lance la detection des fautes
		bimer.decoupe(txt, Bimer.PROCESSMODE_BIM, false);
		
		// exemple d'affichage des erreurs
		System.out.println("in "+ txt);
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


}
