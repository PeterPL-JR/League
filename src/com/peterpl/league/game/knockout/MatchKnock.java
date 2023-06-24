package com.peterpl.league.game.knockout;

import com.peterpl.league.methods.*;

public class MatchKnock {
	
	public String[] texts;
	public String[] mode;
	
	public char[] groups;
	public char[] places;

	public int[] groupsInt;
	public int[] placesInt;

	public MatchKnock() {
		texts = new String[2];
		mode = new String[2];
		
		mode[0] = "team";
		mode[1] = "team";
		
		groups = new char[2];
		places = new char[2];

		groupsInt = new int[2];
		placesInt = new int[2];

		for (int i = 0; i < 2; i++) {
			groups[i] = '?';
			places[i] = '?';

			groupsInt[i] = -1;
			placesInt[i] = -1;
		}
	}

	public void setTeam(int index, char group, char place) {
		mode[index] = "team";
		
		groups[index] = group;
		places[index] = place;

		groupsInt[index] = ((int) group) - ((int) 'A');
		placesInt[index] = ((int) place) - ((int) '1');
	}
	
	public void setText(int index, String text) {
		mode[index] = "text";
		texts[index] = text;
	}

	public String getTeam(int index) {
		if(!mode[index].equals("team")) return texts[index];
		
		char[] string = { groups[index], places[index] };
		return new String(string);
	}
	
	public void sortGroups() {
		if(groups[0] > groups[1]) {
			char buffer = groups[0];
			int bufferInt = groupsInt[0];
			
			groups[0] = groups[1];
			groupsInt[0] = groupsInt[1];
			
			groups[1] = buffer;
			groupsInt[1] = bufferInt;
		}
	}
	
	public void print() {
		String team1 = (mode[0].equals("text")) ? (texts[0]) : (groups[0] + "" +  places[0]);
		String team2 = (mode[1].equals("text")) ? (texts[1]) : (groups[1] + "" +  places[1]);
		Print.p(team1 + " - " + team2);
	}
}
