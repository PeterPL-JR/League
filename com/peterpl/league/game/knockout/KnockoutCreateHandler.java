package com.peterpl.league.game.knockout;

import com.peterpl.league.*;
import com.peterpl.league.game.rounds.*;

public class KnockoutCreateHandler {

	private Knockout knockoutRound;
	private MatchKnock[] matches;
	private char[] groups;

	private KnockoutPairsHandler pairsHandler;
	private KnockoutExtraHandler extraHandler;

	public KnockoutCreateHandler(Knockout knockout) {
		knockoutRound = knockout;

		groups = new char[League.groupsCount];
		for (int i = 0; i < groups.length; i++)
			groups[i] = (char) ('A' + i);

		matches = new MatchKnock[knockout.matchesCount];
		for (int i = 0; i < matches.length; i++)
			matches[i] = new MatchKnock();
		
		pairsHandler = new KnockoutPairsHandler(knockout);
	}

	public void createPairs() {

		if (!League.extraTable) {
			pairsHandler.createPairsStandard();
		} else {
			pairsHandler.createPairsExtra();
		}

		this.matches = pairsHandler.matches;
		for (int i = 0; i < matches.length; i++) {
			
			knockoutRound.matches[i].setText(0, matches[i].getTeam(0));
			knockoutRound.matches[i].setText(1, matches[i].getTeam(1));
		}
	}

	public void setGroup(int group, Team[][] teams, int[][] order) {

		for (int i = 0; i < matches.length; i++) {
			for (int j = 0; j < 2; j++) {

				if (matches[i].groupsInt[j] == group) {

					if (matches[i].placesInt[j] == 0)
						knockoutRound.matches[i].setTeam(j, teams[group][order[group][0]]);

					if (matches[i].placesInt[j] == 1)
						knockoutRound.matches[i].setTeam(j, teams[group][order[group][1]]);
				}
			}
		}
	}

	public void setExtraTeams(Team[] extraTeams) {
		extraHandler = new KnockoutExtraHandler(knockoutRound.matches, extraTeams);

		switch (matches.length) {
		case 8:
			extraHandler.setTeams16();
			break;
		case 2:
			extraHandler.setTeams4();
			break;
		case 4:
			extraHandler.setTeams8();
			break;
		}
	}
}