package com.peterpl.league.game.rounds;

import com.peterpl.league.*;
import com.peterpl.league.files.*;
import com.peterpl.league.game.gui.*;
import com.peterpl.league.methods.*;

public class Final extends Knockout {
	private static final long serialVersionUID = 1L;

	public final WinnerScreen winner;

	public Final() {
		super("Final", 1, 140, Header.Basic + 30, 25, 1);

		for (int i = 0; i < matches.length; i++) {
			for (int j = 0; j < 2; j++) {
				int winnerName = i * 2 + j + 1;
				matches[i].setText(j, "Winner S-Final " + winnerName);
			}
		}

		for (int i = 0; i < matches.length; i++) {
			matches[i].setLocation(matches[i].getX(), matches[i].getY() + 100);
		}

		rightArrow.setVisible(false);
		logo.setLocation(logo.getX(), 30);

		winner = new WinnerScreen();
		winner.setLocation(width / 2 - winner.getWidth() / 2, 400);
		add(winner);

		leftEvent = () -> { League.gameFrame.setPanel(League.semiFinals); };
		rightEvent = () -> { League.gameFrame.setPanel(League.ranking); };
	}

	public void startRound() {
		super.startRound();
	}

	public void endRound() {
		super.endRound();
		setTeamsAdvance(Team.Final);
		winner.setWinner(matches[0].winner.name);
		matches[0].winner.setAdvance(Team.Winner);

		for (int i = 0; i < League.groupsCount; i++)
			for (int j = 0; j < League.teamsInGroup; j++)
				League.advancesTeams[j + i * League.teamsInGroup] = League.allTeams[i][j];
		
		Team.sortByStats(League.advancesTeams);
		Team.sortByAdvance(League.advancesTeams);

		// DELETE THIS
//		LeagueFile leagueFile = new LeagueFile();
//		League.leagueFile.saveFile(leagueFile);
		
		League.ranking.table.update(League.advancesTeams);
		rightArrow.setVisible(true);
	}
}
