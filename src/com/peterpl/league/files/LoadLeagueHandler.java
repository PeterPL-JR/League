package com.peterpl.league.files;


import com.peterpl.league.*;
import com.peterpl.league.game.*;
import com.peterpl.league.game.gui.*;
import com.peterpl.league.game.rounds.*;

import static com.peterpl.league.League.*;

public class LoadLeagueHandler {
	
	public static void loadLeague(LeagueFile file) {
		// Load Main Variables
		League.leagueName = file.leagueName;
		League.gameFrame.setTitle(leagueName);

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
		
		loadTables(file);
	}
	
	private static void loadGroupRound(LeagueFile file) {
		Team[][] teams = file.groupTeams;
		Match[][] matches = file.groupMatches;

		League.groupRound = new GroupRound[groupsCount];
		League.groupsHandler = new GroupsHandler(League.groupRound, teams);
		
		League.teamsInGroup = teamsCount / groupsCount;
		League.teamsInKnockout = (int) Math.pow(2, (League.knockoutRounds - (thirdPlace ? 1 : 0)));
		
		League.createGame.createGroupsRound();
		League.gameFrame.setGroup(0);

		for(int i = 0; i < groupsCount; i++) {
			League.groupRound[i].group.setTeams(teams[i]);
			League.groupRound[i].matchesTable.setMatches(matches[i]);
		}
		
		if(file.extraIndexes != null) {
			for(GroupRound groupRound : League.groupRound) {
				groupRound.group.setRowColor(groupsTeamsQualified, RankingTable.red);
			}
			for(int index : file.extraIndexes) {
				groupRound[index].group.setRowColor(groupsTeamsQualified, RankingTable.yellow);
			}
		}
	}
	
	private static void loadKnockoutRound(LeagueFile file) {
		League.createGame.createKnockoutRound();

		Match[][] knockMatches = file.knockoutMatches;
		
		for(int i = 0; i < knockoutRounds; i++) {
			Match[] knockRound = knockMatches[i];
			Knockout knockout = League.knockout[knockoutRounds - i - 1];
			
			for(int j = 0; j < knockRound.length; j++) {
				Match match = knockRound[j];
				knockout.setMatch(j, match);
				
				if(match.getPenalty()) {
					int penalty1 = match.getPenalty1();
					int penalty2 = match.getPenalty2();
					
					knockout.matches[j].playPenalty();
					knockout.matches[j].setPenalty(penalty1, penalty2);
					knockout.matches[j].endPenalty();
					knockout.matches[j].setWinner(match.winnerPenaltyIndex);
				} else {
					knockout.matches[j].setWinner(match.winnerMatchIndex);
				}
				if(match.getOvertime()) {
					knockout.matches[j].setOvertime(true, true);
				}
			}
		}
	}
	
	private static void loadTables(LeagueFile file) {
		League.finalMatch.rightArrow.setVisible(true);
		
		// Final ranking table
		League.advancesTeams = file.rankingTeams;
		for (int i = 0; i < League.advancesTeams.length; i++) {
			League.ranking.table.setTeam(i, League.advancesTeams[i]);
		}
		
		// Extra teams table
		Team[] extraTeams = file.extraTeams;

		if(League.extraTable && extraTeams != null) {
			for (int i = 0; i < extraTeams.length; i++) {
				League.extraPlacesTable.table.setTeam(i, extraTeams[i]);
			}
		}
		// Winner screen
		League.finalMatch.winner.setWinner(League.advancesTeams[0].name);
	}
}
