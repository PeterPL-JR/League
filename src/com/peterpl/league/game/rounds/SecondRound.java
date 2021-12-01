package com.peterpl.league.game.rounds;

import com.peterpl.league.*;
import com.peterpl.league.game.gui.*;

public class SecondRound extends Knockout {
	private static final long serialVersionUID = 1L;
	
	public SecondRound() {
		super("Round of 16", 8, Header.Basic);
	}

	public void startRound() {
		super.startRound();
	}
	
	public void endRound() {
		super.endRound();
		setTeamsAdvance(Team.SecondRound);
		League.quarterFinals.startRound();
	}
}
