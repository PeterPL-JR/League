package com.peterpl.league.game.rounds;

import com.peterpl.league.*;
import com.peterpl.league.game.gui.*;

public class ThirdPlaceMatch extends Knockout {
	private static final long serialVersionUID = 1L;

	public ThirdPlaceMatch() {
		super("Third Place", 1, 140, Header.Basic + 20, 25, 1.8);
		
		for(int i = 0; i < matches.length; i++) {
			for(int j = 0; j < 2; j++) {
				int loser = i * 2 + j + 1;
				matches[i].setText(j, "Loser S-Final " + loser);
			}
		}
		
		for (int i = 0; i < matches.length; i++) {
			matches[i].setLocation(matches[i].getX(), matches[i].getY() + 100);
		}
		logo.setLocation(logo.getX(), 30);
	}
	
	public void setNextRoundTeams() {
	}
	
	public void startRound() {
		super.startRound();
	}

	public void endRound() {
		super.endRound();
		matches[0].winner.setAdvance(Team.ThirdPlace);
		League.finalMatch.startRound();
	}
}
