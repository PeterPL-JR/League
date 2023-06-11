package com.peterpl.league.files;

import com.peterpl.league.*;
import com.peterpl.league.game.*;
import com.peterpl.league.game.gui.*;
import com.peterpl.league.game.rounds.*;
import com.peterpl.league.methods.*;

import static com.peterpl.league.League.*;

public class LoadLeagueHandler {
	
	public static void loadLeague(LeagueFile file) {
		// Load Main Variables
		League.gameFrame.setTitle(League.title + " (" + file.leagueName + ")");

		League.teamsCount = file.teamsCount;
		League.groupsCount = file.groupsCount;
		League.knockoutRounds = file.knockoutRounds;
		
		League.gameMode = file.gameMode;
		League.flagsMode = file.flagsMode;
		
		League.thirdPlace = file.thirdPlace;
		League.extraTable = file.extraTeamsTable;
		
		// Create league elements
		League.ranking = new FinalRanking();
		League.ranking.table.thirdPlacesMatch(League.thirdPlace);
		League.extraPlacesTable = new ExtraPlaces();
		
		loadGroupRound(file);
		loadKnockoutRound(file);
	}
	
	private static void loadGroupRound(LeagueFile file) {
		League.groupRound = new GroupRound[groupsCount];
		
		Team[][] teams = file.groupTeams;
		Match[][] matches = file.groupMatches;
		
		for(int i = 0; i < groupsCount; i++) {
			League.groupRound[i] = new GroupRound(teams[i], matches[i], (char) ('A' + i) + "");
		}
		
		League.gameFrame.setGroup(0);
		League.groupRound[0].leftArrow.setVisible(false);

		League.groupsHandler = new GroupsHandler(League.groupRound, teams);
	}
	
	private static void loadKnockoutRound(LeagueFile file) {
		
	}
}
