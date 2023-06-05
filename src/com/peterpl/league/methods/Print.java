package com.peterpl.league.methods;

public interface Print {

	/** Method printing an empty line in console */
	public static void p() {
		System.out.println();
	}
	
	/** This method is shorter version of System.out.println() */
	public static <S> void p(S str) {
		System.out.println(str);
	}

	/** This method is shorter version of System.out.print() */
	public static <S> void pn(S str) {
		System.out.print(str);
	}
}
