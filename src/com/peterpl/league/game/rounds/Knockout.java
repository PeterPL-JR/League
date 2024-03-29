package com.peterpl.league.game.rounds;

import java.util.*;

import java.awt.*;

import com.peterpl.league.*;
import com.peterpl.league.game.gui.*;
import com.peterpl.league.methods.*;

public abstract class Knockout extends GamePanel {
	private static final long serialVersionUID = 1L;
	
	public static final int BasicFlagWidth = 30;
	public static final int BasicFlagHeight = 20;
	
	public ArrayList<FlagTeam> flagTeams;
	public final String name;
	public final int matchesCount;
	public final int matchHeight;
	public final int flagWidth, flagHeight;
	protected int match = 0;

	public KnockoutMatch[] matches;

	protected Knockout nextRound;
	public Team[] winners;
	public Team[] losers;

	public Knockout(String name, int matchesCount, int matchHeight, int logoFontSize, int matchFontSize, double flagScale) {
		super(name, logoFontSize, GameFrame.Width, GameFrame.Height);
		
		this.flagWidth = (int) (BasicFlagWidth * flagScale);
		this.flagHeight = (int) (BasicFlagHeight * flagScale);
		flagTeams = FlagTeam.getAllScaledFlags(flagWidth, flagHeight, true);
		
		this.name = name;
		this.matchesCount = matchesCount;
		this.matchHeight = matchHeight;
		
		matches = new KnockoutMatch[matchesCount];
		for (int i = 0; i < matchesCount; i++) {
			matches[i] = new KnockoutMatch(this, matchHeight, matchFontSize, flagWidth, flagHeight);
			matches[i].setLocation(Methods.centerX(matches[i], this), 120 + i * (int) (matches[i].getHeight() * 1.15));
			add(matches[i]);
		}

		winners = new Team[matchesCount];
		losers = new Team[matchesCount];
	}

	public void setNextRound(Knockout nextRound) {
		this.nextRound = nextRound;
	}
	
	public Knockout getNextRound() {
		return nextRound;
	}
	
	public int getMatch() {
		return match;
	}
	
	public void setTeamsAdvance(int advance) {
		for(int i = 0; i < matchesCount; i++) {
			for(int j = 0; j < 2; j++) {
				matches[i].getTeam(j).setAdvance(advance);
			}
		}
	}

	protected void setNextRoundTeams() {
		if (nextRound != null) {
			for (int i = 0; i < nextRound.matchesCount; i++) {
				for (int j = 0; j < 2; j++) {
					int index = i * 2 + j;
					if (winners[index] == null)
						continue;
					nextRound.matches[i].setTeam(j, winners[index]);
				}
			}
		}
	}

	public void setScore(int match, int score1, int score2) {
		matches[match].setScore(score1, score2);
	}
	
	public void setPenalty(int match, int score1, int score2) {
		matches[match].setPenalty(score1, score2);
	}

	public void setSize(int matchHeight, Font font) {
		for (KnockoutMatch match : matches) {
			match.setSize(match.getWidth(), matchHeight);
		}
	}
	
	public void setMatch(int index, Match match) {
		matches[index].setTeam(0, match.team1);
		matches[index].setTeam(1, match.team2);
		
		matches[index].setScore(match.getScore1(), match.getScore2());
	}
	public void setMatches(Match[] matches) {
		if(matches.length != matchesCount) return;
		
		for(int i = 0; i < matchesCount; i++) {
			setMatch(i, matches[i]);
		}
	}

	public void startRound() {
		button.setAction(this::endMatch);
		playMatch();
	}
	
	public void endRound() {
		rightArrow.requestFocusInWindow();

		for(int i = 0; i < League.knockoutMatches.length; i++) {
			if(League.knockoutMatches[i] == null) {
				League.knockoutMatches[i] = new Match[matchesCount];
				for(int j = 0; j < matchesCount; j++) {
					Team team1 = matches[j].getTeam(0);
					Team team2 = matches[j].getTeam(1);
					
					Match match = new Match(team1, team2);
					match.play(matches[j].getScore(0), matches[j].getScore(1), matches[j].overtime());
					
					if(matches[j].penaltyRound()) {
						match.playPenalty(matches[j].getPenalty(0), matches[j].getPenalty(1));
					}
					
					League.knockoutMatches[i][j] = match;
				}
				break;
			}
		}
	}
	
	public void setMatchFocus(int index) {
		if(index < 0 || index >= matches.length) return;
		matches[index].setFocus();
	}
	
	private void playMatch() {
		matches[match].play();
		button.setVisible(true);
		button.setAction(this::endMatch);
	}

	private void playPenalty() {
		matches[match].playPenalty();
		button.setVisible(true);
		button.setAction(this::endPenalty);
	}

	private boolean scoreMatch() {
		int score1;
		int score2;
		
		try {
			score1 = matches[match].getScore(0);
			score2 = matches[match].getScore(1);
			
		} catch(NumberFormatException e) {
			return false;
		}
		
		matches[match].getTeam(0).playMatch(score1, score2);
		matches[match].getTeam(1).playMatch(score2, score1);

		if (score1 > score2) {
			matches[match].setWinner(0);
			winners[match] = matches[match].getTeam(0);
			losers[match] = matches[match].getTeam(1);
			setNextRoundTeams();
			return true;
		}
		if (score1 < score2) {
			matches[match].setWinner(1);
			winners[match] = matches[match].getTeam(1);
			losers[match] = matches[match].getTeam(0);
			setNextRoundTeams();
			return true;
		}
		if (score1 == score2) {
			playPenalty();
		}
		return false;
	}

	private boolean scorePenalty() {
		
		if (matches[match].getPenalty(0) > matches[match].getPenalty(1)) {
			matches[match].setWinner(0);
			winners[match] = matches[match].getTeam(0);
			losers[match] = matches[match].getTeam(1);
			setNextRoundTeams();
			return true;
		}
		if (matches[match].getPenalty(0) < matches[match].getPenalty(1)) {
			matches[match].setWinner(1);
			winners[match] = matches[match].getTeam(1);
			losers[match] = matches[match].getTeam(0);
			setNextRoundTeams();
			return true;
		}
		return false;
	}

	// TO PRIVATE
	public void endMatch() {
		if (!scoreMatch()) {
			return;
		}

		matches[match].end();
		button.setVisible(false);
		match++;

		if (match >= matchesCount) {
			endRound();
			return;
		}

		playMatch();
	}

	// TO PRIVATE
	public void endPenalty() {
		try {
			if (!scorePenalty()) {
				return;
			}
		} catch (NumberFormatException e) {
			return;
		}

		matches[match].endPenalty();
		matches[match].end();
		button.setVisible(false);
		match++;

		if (match >= matchesCount) {
			endRound();
			return;
		}

		playMatch();
	}
}
