package com.peterpl.league.game.gui;

import java.util.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import com.peterpl.league.*;
import com.peterpl.league.game.rounds.*;
import com.peterpl.league.methods.*;

public class MatchesTable extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final int FlagWidth = 30;
	public static final int FlagHeight = 20;
	
	public static ArrayList<FlagTeam> flagTeams = FlagTeam.getAllScaledFlags(FlagWidth, FlagHeight, true);
	private GroupRound group;
	private MatchPart[] matchesParts;
	private int[][] matchesOrder = { { 0, 1 }, { 2, 3 }, { 1, 3 }, { 0, 2 }, { 3, 0 }, { 1, 2 } };

	public MatchesTable(GroupRound group) {
		this.group = group;
		
		setLayout(null);
		setSize(580, 265);

		matchesParts = new MatchPart[6];
		for (int i = 0; i < matchesParts.length; i++) {
			matchesParts[i] = new MatchPart();
			matchesParts[i].setLocation(0, i * 44);
			add(matchesParts[i]);
		}
	}

	public void playMatch(int index) {
		matchesParts[index].score[0].setActive(true);
		matchesParts[index].score[1].setActive(true);
		setMatchFocus(index);
		
		matchesParts[index].setBackground(SystemColor.scrollbar);
		for (ScoreSquare score : matchesParts[index].score)
			score.setEnabled(true);
	}

	public void endMatch(int index) {
		matchesParts[index].score[0].setActive(false);
		matchesParts[index].score[1].setActive(false);
		
		matchesParts[index].setBackground(SystemColor.controlHighlight);
		for (ScoreSquare score : matchesParts[index].score)
			score.setEnabled(false);
	}

	public void setMatchFocus(int index) {
		if(index < 0 || index >= matchesParts.length) return;
		
		int scoreIndex = matchesParts[index].score[0].getText().equals("") ? 0 : 1;
		matchesParts[index].score[scoreIndex].requestFocusInWindow();
	}
	
	public void setMatches(Team[] group, int groupIndex) {

		for (int i = 0; i < matchesOrder.length; i++) {
			for (int j = 0; j < matchesOrder[i].length; j++) {
				matchesParts[i].setMatch(group, matchesOrder[i][0], matchesOrder[i][1]);
				
				Team team1 = group[matchesOrder[i][0]];
				Team team2 = group[matchesOrder[i][1]];
				League.groupsMatches[groupIndex][i] = new Match(team1, team2);
			}
		}
	}
	
	public void setMatches(Match[] matches) {
		for(int i = 0; i < matches.length; i++) {
			
			Match match = matches[i];
			matchesParts[i].setMatch(match.team1, match.team2, match.getScore1(), match.getScore2());
		}
	}

	public int getScore1(int match) {
		return Integer.parseInt(matchesParts[match].score[0].getScore());
	}

	public int getScore2(int match) {
		return Integer.parseInt(matchesParts[match].score[1].getScore());
	}

	public int getMatchTeam1(int match) {
		return matchesParts[match].team1i;
	}

	public int getMatchTeam2(int match) {
		return matchesParts[match].team2i;
	}

	public void setScore(int match, int score1, int score2) {
		matchesParts[match].score[0].setScore(score1);
		matchesParts[match].score[1].setScore(score2);
	}

	private class MatchPart extends JPanel {
		private static final long serialVersionUID = 1L;

		private int team1i, team2i;
		private JLabel scoreLabel;
		private JLabel[] team;
		private ScoreSquare[] score;
		
		private FlagTeam[] flagTeams;
		private JLabel[] flagLabels;
		
		public MatchPart() {
			
			setLayout(null);
			setSize(580, 45);
			setBackground(SystemColor.controlHighlight);
			setBorder(new LineBorder(Color.black));

			label();
			teams();
			scores();
			
			if(League.flagsMode)
				flags();
		}

		private void setMatch(Team team1, Team team2, int score1, int score2) {
			if(League.flagsMode) {
				FlagTeam.findCountry(team1.name, flagTeams[0], flagLabels[0], this, MatchesTable.flagTeams);
				FlagTeam.findCountry(team2.name, flagTeams[1], flagLabels[1], this, MatchesTable.flagTeams);
			}
			
			team[0].setText(team1.name);
			team[1].setText(team2.name);
			
			score[0].setScore(score1);
			score[1].setScore(score2);
		}
		
		private void setMatch(Team[] teams, int index1, int index2) {
			if(League.flagsMode) {
				FlagTeam.findCountry(teams[index1].name, flagTeams[0], flagLabels[0], this, MatchesTable.flagTeams);
				FlagTeam.findCountry(teams[index2].name, flagTeams[1], flagLabels[1], this, MatchesTable.flagTeams);
			}
			
			team[0].setText(teams[index1].name);
			team[1].setText(teams[index2].name);

			team1i = index1;
			team2i = index2;
		}

		private void label() {
			scoreLabel = new JLabel("-");
			scoreLabel.setBounds(269, 0, 41, 45);
			scoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
			scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
			add(scoreLabel);
		}

		private void teams() {
			int posRate = League.flagsMode ? 388 : 347;
			int widthRate = League.flagsMode ? 192 : 233;
			
			team = new JLabel[2];
			for (int i = 0; i < team.length; i++) {
				
				team[i] = new JLabel();
				team[i].setBounds(0 + posRate * i, 0, widthRate, 45);
				team[i].setFont(new Font("Tahoma", Font.PLAIN, 14));
				//team[i].setBorder(new LineBorder(Color.red));
				add(team[i]);
			}

			team[0].setHorizontalAlignment(SwingConstants.RIGHT);
			team[1].setHorizontalAlignment(SwingConstants.LEFT);
		}

		private void scores() {
			score = new ScoreSquare[2];
			for (int i = 0; i < score.length; i++) {
				
				score[i] = new ScoreSquare();
				score[i].setLocation(247 + i * 61, 11);
				score[i].enterKeyEvent = () -> { group.button.doClick(); };
				add(score[i]);
			}
			
			score[0].setRightEvent(score[1]);
			score[1].setLeftEvent(score[0]);
		}
		
		private void flags() {
			flagTeams = new FlagTeam[2];
			flagLabels = new JLabel[2];

			for(int i = 0; i < 2; i++) {
				flagTeams[i] = null;
				
				flagLabels[i] = new JLabel();
				flagLabels[i].setSize(FlagWidth, FlagHeight);
				flagLabels[i].setLocation(200 + 150 * i, Methods.centerY(flagLabels[i], this) + 1);
				add(flagLabels[i]);
			}
			
			flagTeams[0] = null;
			flagTeams[1] = null;
		}
	}
}
