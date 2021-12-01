package com.peterpl.league.game;

import com.peterpl.league.*;
import com.peterpl.league.game.gui.*;
import com.peterpl.league.game.rounds.*;

public class TableHandler {

	public int[] extraIndexes;
	
	public int[] sort(RankingTable group, Team[] teams) {
		int length = teams.length;

		int[] table = new int[length];
		for (int i = 0; i < length; i++) {
			table[i] = i;
		}

		Team.sortByStats(teams, table);
		
		for (int i = 0; i < table.length; i++) {
			group.setTeam(i, teams[table[i]]);
		}

		return table;
	}

	public Team[] extraTeams(RankingTable extraTable, GroupRound[] groupRounds, Team[][] teams, int[][] tables, int place) {

		RankingTable[] groups = new RankingTable[groupRounds.length];
		for(int i = 0; i < groups.length; i++) {
			groups[i] = groupRounds[i].group;
			groups[i].setColor(place, RankingTable.red);
		}
		
		Team[] thirdTeams = new Team[League.groupsCount];
		for (int i = 0; i < thirdTeams.length; i++) {
			thirdTeams[i] = teams[i][tables[i][place]];
		}

		int length = thirdTeams.length;
		
		int[] table = new int[length];
		for (int i = 0; i < length; i++) {
			table[i] = i;
		}

		int extraTeams = 0;
		if(League.gameMode == "Teams24")
			extraTeams = 4;
		if(League.gameMode == "Teams12")
			extraTeams = 1;
		
		Team.sortByStats(thirdTeams, table);
		Team[] thirdTeamsQualified = new Team[extraTeams];

		extraIndexes = new int[extraTeams];
		for(int i = 0; i < extraTeams; i++) {
			extraIndexes[i] = table[i];
			groups[table[i]].setColor(place, RankingTable.yellow);
			thirdTeamsQualified[i] = thirdTeams[table[i]];
		}
		
		for(int i = 0; i < length; i++) {
			extraTable.setTeam(i, thirdTeams[table[i]]);
		}
		
		return thirdTeamsQualified;
	}
}
