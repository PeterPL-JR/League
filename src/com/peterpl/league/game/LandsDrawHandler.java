package com.peterpl.league.game;

import java.util.*;

import com.peterpl.league.*;
import com.peterpl.league.files.*;

public class LandsDrawHandler {
	private HashMap<String, Integer[]> landsMax = new HashMap<>();
	private static final int TeamsCount = 0, MaxInGroup = 1;
	
	void init(String[][] teams, int teamsN, int groupsN) {
		
		for(String land : FlagsSheet.landsNames) {
			int teamsCount = countLand(teams, land);
			int maxInGroup = (int) Math.ceil((double) teamsCount / (double) groupsN);
			
			Integer[] stats = new Integer[2];
			stats[TeamsCount] = teamsCount;
			stats[MaxInGroup] = maxInGroup;
			
			if(teamsCount > 0) {
				landsMax.put(land, stats);
			}
		}
	}
	
	boolean checkLands(String[] group, String team, int groupIndex, int potIndex) {
		String land = FlagTeam.getLand(team);
		return checkLandsNumber(group, land) || countTeams(group) >= potIndex + 1;
	}
	
	private int countLand(String[] teamsArray, String land) {
		int counter = 0;
		for(String team : teamsArray) {
			if(team != null && FlagTeam.getLand(team).equals(land)) {
				counter++;
			}
		}
		return counter;
	}
	private int countLand(String[][] teamsArray, String land) {
		int counter = 0;
		for(String[] teams : teamsArray) {
			counter += countLand(teams, land);
		}
		return counter;
	}
	
	private boolean checkLandsNumber(String[] group, String land) {
		int landCount = countLand(group, land);
		int maxInGroup = (int) landsMax.get(land)[MaxInGroup];
		
		return landCount >= maxInGroup;
	}
	
	private int countTeams(String[] teamsArray) {
		int counter = 0;
		for(String team : teamsArray) {
			if(team != null) {
				counter++;
			}
		}
		return counter;
	}
	public int countTeams(String[][] teamsArray, int potIndex) {
		int counter = 0;
		for(String[] group : teamsArray) {
			for(int t = 0; t < group.length; t++) {
				if(t == potIndex && group[t] != null) { 
					counter++;
				}
			}
		}
		return counter;
	}
}