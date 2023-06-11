package com.peterpl.league.files;

import java.io.*;
import java.util.*;

import com.peterpl.league.methods.*;

public class CountriesFileHandler {
	
	public static final String[] langs = { "en", "pl" };
	private final String[][] chars = { 
		{ "Ą", "@0104" }, { "ą", "@0105" }, { "Ć", "@0106" }, { "ć", "@0107" }, { "Ę", "@0118" }, { "ę", "@0119" }, 
		{ "Ł", "@0141" }, { "ł", "@0142" }, { "Ń", "@0143" }, { "ń", "@0144" }, { "Ó", "@00d3" }, { "ó", "@00f3" }, 
		{ "Ś", "@015a" }, { "ś", "@015b" }, { "Ż", "@017b" }, { "ż", "@017c" }, { "Ź", "@0179" }, { "ź", "@017a" }, 
	};

	public String[] loadFile(String name, String lang) throws Exception {
		
		String fileName = "/data/countries_" + name + "_" + lang + ".dat";
		
		ObjectInputStream read = new ObjectInputStream(CountriesFileHandler.class.getResourceAsStream(fileName));
		ArrayList<String> names = new ArrayList<>();

		int[] array;
		while (true) {
			try {
				array = (int[]) read.readObject();
			} catch (EOFException e) {
				break;
			}
			String str = "";
			for (int ch : array)
				str += ((char) ch);
			names.add(str);
		}
		read.close();
		
		String[] namesArray = new String[names.size()];
		for(int i = 0; i < namesArray.length; i++) {
			namesArray[i] = names.get(i);
		}
		return namesArray;
	}
	
	public String encodeString(String str) {
		if(str == null) return null;
		
		for(String[] special : chars) {
			str = str.replaceAll(special[0], special[1]);
		}
		return str;
	}
	
	public String decodeString(String str) {
		if(str == null) return null;

		for(String[] special : chars) {
			str = str.replaceAll(special[1], special[0]);
		}
		return str;
	}
	
	private void printTeams(ArrayList<String> teamsNames) {
		for (int i = 0; i < teamsNames.size(); i++) {
			String codeName = teamsNames.get(i);
			String name = decodeString(codeName);
			
			Print.p(name);
		}
		Print.p();
	}
}
