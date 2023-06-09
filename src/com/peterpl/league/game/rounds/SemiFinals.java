package com.peterpl.league.game.rounds;

import com.peterpl.league.*;
import com.peterpl.league.game.gui.*;

public class SemiFinals extends Knockout {
	private static final long serialVersionUID = 1L;

	public SemiFinals() {
		super("Semi-Finals", 2, 120, Header.Basic + 15, 22, 1.7);
		
		for(int i = 0; i < matches.length; i++) {
			for(int j = 0; j < 2; j++) {
				int winnerName = i * 2 + j + 1;
				matches[i].setText(j, "Winner Q-Final " + winnerName);
			}
		}
		
		for(int i = 0; i < matches.length; i++) {
			matches[i].setLocation(matches[i].getX(), matches[i].getY() + 100 + 70 * i);
		}
	}

	protected void setNextRoundTeams() {
		if (nextRound != null) {
			for (int i = 0; i < nextRound.matchesCount; i++) {
				for (int j = 0; j < 2; j++) {
					int index = i * 2 + j;
					if (winners[index] == null)
						continue;
					if(League.thirdPlace) {
						League.thirdPlaceMatch.matches[i].setTeam(j, losers[index]);
						League.finalMatch.matches[i].setTeam(j, winners[index]);
					} else {
						nextRound.matches[i].setTeam(j, winners[index]);
					}
				}
			}
		}
	}
	
	public void startRound() {
		super.startRound();
	}

	public void endRound() {
		super.endRound();
		setTeamsAdvance(Team.SemiFinals);
		if(League.thirdPlace) {
			League.thirdPlaceMatch.startRound();
			return;
		}
		League.finalMatch.startRound();
	}
}
