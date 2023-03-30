package com.peterpl.league.game.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import com.peterpl.league.*;
import com.peterpl.league.files.*;
import com.peterpl.league.game.rounds.*;
import com.peterpl.league.methods.*;

public class KnockoutMatch extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final int BasicWidth = 580;
	public static final int BasicHeight = 60;

	private Knockout knockout;
	private JLabel[] flagLabels;
	private FlagTeam[] flags;
	
	private boolean whileMatch = false;
	private boolean overtime = false;
	private boolean penaltyRound = false;

	private JLabel scoreLabel;
	private JLabel[] team;
	public ScoreSquare[] score;

	private PenaltySquare[] penalty;
	private Team[] teams;
	public Team winner;
	public int height;
	
	public KnockoutMatch(Knockout knockout, int height) {
		this.knockout = knockout;
		this.height = height;
		teams = new Team[2];
		flags = new FlagTeam[2];

		setLayout(null);
		setSize(BasicWidth, height);
		setBackground(SystemColor.controlHighlight);
		setBorder(new LineBorder(Color.black));

		createLabel();
		createTeams();
		createScores();
		createPenalties();
	}

	public void setScore(int score1, int score2) {
		score[0].setScore(score1);
		score[1].setScore(score2);
	}

	public void setPenalty(int score1, int score2) {
		penalty[0].setScore(score1);
		penalty[1].setScore(score2);
	}

	public void play() {
		score[0].setActive(true);
		score[1].setActive(true);

		whileMatch = true;
		setBackground(SystemColor.scrollbar);
		for (ScoreSquare score : this.score)
			score.setEnabled(true);
	}

	public void end() {
		score[0].setActive(false);
		score[1].setActive(false);

		whileMatch = false;
		setBackground(SystemColor.controlHighlight);
		for (ScoreSquare score : this.score)
			score.setEnabled(false);

		knockout.setMatchFocus(knockout.getMatch() + 1);
	}

	public void setWinner(int index) {
		if (index < 0 || index > 1)
			return;

		Font font = team[index].getFont();
		team[index].setFont(new Font(font.getName(), Font.BOLD, (int) (font.getSize() * 1.05)));

		winner = teams[index];
	}

	public void playPenalty() {
		setOvertime(false, false);
		penaltyRound = true;

		for (int i = 0; i < 2; i++) {
			score[i].setEnabled(false);

			penalty[i].setVisible(true);
			penalty[i].setActive(true);
		}

		team[0].setLocation(-41, team[0].getY());
		team[1].setLocation(388, team[1].getY());

		penalty[0].requestFocusInWindow();
	}

	public void endPenalty() {

		for (int i = 0; i < 2; i++) {
			penalty[i].setActive(false);
		}
	}

	public void setOvertime(boolean overtime, boolean loaded) {
		if (!whileMatch && !loaded)
			return;
		if (penaltyRound)
			return;

		this.overtime = overtime;

		scoreLabel.setText(overtime ? "E" : "-");
	}

	public void setTeam(int index, Team team) {
		if (index < 0 || index > 1)
			return;

		this.team[index].setText(team.name);

		teams[index] = team;
		FlagTeam.findCountry(team.name, flags[index], flagLabels[index], this, knockout.flagTeams);
	}

	public void setText(int index, String text) {
		if (index < 0 || index > 1)
			return;

		this.team[index].setText(text);
	}

	public Team getTeam(int index) {
		return teams[index];
	}

	public int getScore(int index) {
		return Integer.parseInt(score[index].getScore());
	}

	public int getPenalty(int index) {
		return Integer.parseInt(penalty[index].getScore());
	}

	public boolean whileMatch() {
		return whileMatch;
	}

	public boolean overtime() {
		return overtime;
	}

	public boolean penaltyRound() {
		return penaltyRound;
	}

	private void createPenalties() {

		penalty = new PenaltySquare[2];
		for (int i = 0; i < penalty.length; i++) {
			penalty[i] = new PenaltySquare();
			penalty[i].setVisible(false);
			//penalty[i].setBorder(new LineBorder(Color.blue));
			penalty[i].enterKeyEvent = () -> {
				knockout.button.doClick();
			};
			add(penalty[i]);
		}

		penalty[0].setLocation(200, Game.centerY(penalty[0], this) - 2);
		penalty[1].setLocation(345, Game.centerY(penalty[1], this) - 2);
		
		penalty[0].setRightEvent(penalty[1]);
		penalty[1].setLeftEvent(penalty[0]);
	}

	// CREATE 'SCORE LABEL'
	private void createLabel() {
		scoreLabel = new JLabel("-");
		scoreLabel.setSize(40, height);
		scoreLabel.setLocation(BasicWidth / 2 - 20, 0);
		scoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(scoreLabel);

		scoreLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setOvertime(overtime ? false : true, false);
			}
		});
	}

	private void createFlags() {
		if(flagLabels != null) {
			remove(flagLabels[0]);
			remove(flagLabels[1]);
		}
		
		flagLabels = new JLabel[2];
		for(int i = 0; i < flagLabels.length; i++) {
			flagLabels[i] = new JLabel();
			flagLabels[i].setSize(FlagTextField.FlagWidth, FlagTextField.FlagHeight);
			flagLabels[i].setIcon(GraphicsImage.defaultFlag);
			//flagLabels[i].setBorder(new LineBorder(Color.red));
			add(flagLabels[i]);
		}
		
		flagLabels[0].setLocation(30, Game.centerY(flagLabels[0], this));
		flagLabels[1].setLocation(getWidth() - 80, Game.centerY(flagLabels[1], this));
	}
	
	// CREATE 'TEAMS LABELS'
	private void createTeams() {
		team = new JLabel[2];

		for (int i = 0; i < team.length; i++) {
			team[i] = new JLabel();
			team[i].setFont(new Font("Verdana", Font.PLAIN, 15));
			//team[i].setBorder(new LineBorder(Color.green));
			add(team[i]);
		}

		if (League.flagsMode) {
			createFlags();
		}
		
		team[0].setSize(200, height);
		team[0].setLocation(0, 0);
		
		team[1].setSize(200, height);
		team[1].setLocation(347, 0);

		team[0].setHorizontalAlignment(SwingConstants.RIGHT);
		team[1].setHorizontalAlignment(SwingConstants.LEFT);
	}

	// CREATE 'SCORES'
	private void createScores() {
		score = new ScoreSquare[2];
		for (int i = 0; i < score.length; i++) {

			score[i] = new ScoreSquare(this);
			score[i].enterKeyEvent = () -> {
				knockout.button.doClick();
			};
			add(score[i]);
		}

		score[0].setLocation(243, Game.centerY(score[0], this));
		score[1].setLocation(311, Game.centerY(score[1], this));

		score[0].setRightEvent(score[1]);
		score[1].setLeftEvent(score[0]);
	}
}