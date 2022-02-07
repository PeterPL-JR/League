package com.peterpl.league.files;

import com.peterpl.league.*;
import com.peterpl.league.game.*;
import com.peterpl.league.game.gui.*;
import com.peterpl.league.game.rounds.*;

public class LoadLeagueHandler {

	public static void loadGroupRound(LeagueFile file) {
		// Load Main Variables
		League.teamsCount = file.teamsCount;
		League.groupsCount = file.groupsCount;
		League.knockoutRounds = file.knockoutRounds;
		League.gameFrame.setTitle(League.title + " (" + file.leagueName + ")");
		League.gameMode = file.gameMode;
		League.thirdPlace = file.thirdPlace;
		League.extraTable = file.extraTeamsTable;

		int groupsCount = file.groupsCount;
		League.groupRound = new GroupRound[groupsCount];

		// Load League Elements
		Team[][] teams = file.groupTeams;
		Match[][] matches = file.groupMatches;

		for (int i = 0; i < groupsCount; i++) {
			League.groupRound[i] = new GroupRound(teams[i], matches[i], (char) ('A' + i) + "");
		}
		League.gameFrame.setGroup(0);
		League.groupRound[0].leftArrow.setVisible(false);

		League.groupsHandler = new GroupsHandler(League.groupRound, teams);
		League.ranking = new FinalRanking();
		League.ranking.table.thirdPlacesMatch(League.thirdPlace);
		League.extraPlacesTable = new ExtraPlaces();

		// Use Creating Methods
		switch (League.gameMode) {
		case "Teams32":
			League.groupsHandler.knockoutRound = League.secondRound;
			League.semiFinals.rightEvent = () -> League.gameFrame.setPanel(League.thirdPlaceMatch);
			League.finalMatch.leftEvent = () -> League.gameFrame.setPanel(League.thirdPlaceMatch);
			break;
		case "Teams24":
			League.groupsHandler.knockoutRound = League.secondRound;
			break;
		case "Teams16":
			League.groupsHandler.knockoutRound = League.quarterFinals;
			break;
		case "Teams12":
			League.groupsHandler.knockoutRound = League.semiFinals;
			break;
		}
	}

	public static void loadKnockout(LeagueFile file) {
		Match[][] knockout = file.knockoutMatches;
		League.knockout = new Knockout[League.knockoutRounds];

		Knockout round = League.groupsHandler.knockoutRound;
		for (int i = 0; i < League.knockoutRounds; i++) {
			League.knockout[i] = round;
			if (round == null) {
				continue;
			}
			round = round.getNextRound();
		}

		for (int i = 0; i < League.knockoutRounds; i++) {
			League.knockout[i].setMatches(knockout[i]);
			for (int j = 0; j < League.knockout[i].matchesCount; j++) {

				if (knockout[i][j].getPenalty()) {
					int penalty1 = knockout[i][j].getPenalty1();
					int penalty2 = knockout[i][j].getPenalty2();

					League.knockout[i].matches[j].playPenalty();
					League.knockout[i].matches[j].setPenalty(penalty1, penalty2);
					League.knockout[i].matches[j].endPenalty();
					League.knockout[i].matches[j].setWinner(knockout[i][j].winnerPenaltyIndex);

				} else {
					League.knockout[i].matches[j].setWinner(knockout[i][j].winnerMatchIndex);
				}
				if (knockout[i][j].getOvertime()) {
					League.knockout[i].matches[j].setOvertime(true, true);
				}
			}
		}
	}

	public static void loadTables(LeagueFile file) {
		League.finalMatch.rightArrow.setVisible(true);

		League.advancesTeams = file.rankingTeams;
		for (int i = 0; i < League.advancesTeams.length; i++) {
			League.ranking.table.setTeam(i, League.advancesTeams[i]);
		}

		if (League.extraTable) {

			Team[] extraTeams = file.extraTeams;
			for (int i = 0; i < extraTeams.length; i++) {
				League.extraPlacesTable.table.setTeam(i, extraTeams[i]);
			}

			int index = (League.gameMode.equals("Teams24")) ? 2 : 1;
			
			int[] extraIndexes = file.extraIndexes;
			for (int i = 0; i < League.groupsCount; i++) {
				League.groupRound[i].group.setRowColor(index, RankingTable.red);
			}
			for (int i = 0; i < extraIndexes.length; i++) {
				League.groupRound[extraIndexes[i]].group.setRowColor(index, RankingTable.yellow);
			}
		}

		League.finalMatch.winner.setWinner(League.advancesTeams[0].name);
	}
}
