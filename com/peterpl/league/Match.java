package com.peterpl.league;

import java.io.*;

public class Match implements Serializable {
	private static final long serialVersionUID = 1L;

	public final Team team1, team2;
	private int score1 = -1, score2 = -1;
	private boolean played = false;

	private boolean overtime = false;
	private int penalty1 = -1, penalty2 = -1;
	private boolean penalty = false;
	
	public int winnerMatchIndex = -1;
	public int winnerPenaltyIndex = -1;

	public Match(Team team1, Team team2) {
		this.team1 = team1;
		this.team2 = team2;
	}

	public void play(int score1, int score2) {
		if (played)
			return;
		played = true;

		this.score1 = score1;
		this.score2 = score2;
		
		winnerMatchIndex = score1 > score2 ? 0 : 1;
	}

	public void play(int score1, int score2, boolean overtime) {
		this.play(score1, score2);
		this.overtime = overtime;
	}

	public void playPenalty(int penalty1, int penalty2) {
		if (penalty)
			return;
		penalty = true;

		this.penalty1 = penalty1;
		this.penalty2 = penalty2;
		
		winnerPenaltyIndex = penalty1 > penalty2 ? 0 : 1;
	}

	public int getScore1() {
		return score1;
	}

	public int getScore2() {
		return score2;
	}

	public boolean getOvertime() {
		return overtime;
	}

	public boolean getPenalty() {
		return penalty;
	}

	public int getPenalty1() {
		return penalty1;
	}

	public int getPenalty2() {
		return penalty2;
	}
}
