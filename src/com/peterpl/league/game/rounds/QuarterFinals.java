package com.peterpl.league.game.rounds;

import com.peterpl.league.*;
import com.peterpl.league.game.gui.*;

public class QuarterFinals extends Knockout {
	private static final long serialVersionUID = 1L;
	
	public QuarterFinals() {
		super("Quarter-Finals", 4, 80, Header.Basic + 10, 20, 1);

		for (int i = 0; i < matches.length; i++) {
			for (int j = 0; j < 2; j++) {
				int winnerName = i * 2 + j + 1;
				matches[i].setText(j, "Winner " + winnerName);
			}
		}

		for(int i = 0; i < matches.length; i++) {
			matches[i].setLocation(matches[i].getX(), matches[i].getY() + 65 + 30 * i);
		}
	}
	
	public void startRound() {
		super.startRound();
	}

	public void endRound() {
		super.endRound();
		setTeamsAdvance(Team.QuarterFinals);
		League.semiFinals.startRound();
	}
}
