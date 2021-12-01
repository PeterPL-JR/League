package com.peterpl.league.methods;

public interface Print {

	public static void p() {
		System.out.println();
	}
	
	public static <S> void p(S str) {
		System.out.println(str);
	}
	
	public static <S> void pn(S str) {
		System.out.print(str);
	}
}
