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
	
	public static final int BasicWidth = (int) (GameFrame.Width * 0.71);
	public static final int BasicHeight = 60;

	private Knockout knockout;
	private JLabel[] flagLabels;
	private FlagTeam[] flags;
	
	private boolean whileMatch = false;
	private boolean overtime = false;
	private boolean penaltyRound = false;

	private JLabel scoreLabel;
	private JLabel[] team;
	private ScoreSquare[] score;

	private PenaltySquare[] penalty;
	private Team[] teams;
	public Team winner;
	
	public final int teamSize = (BasicWidth - 120) / 2;
	public int height;
	public int fontSize;
	public final int flagWidth, flagHeight;
	private final int flagOffset = 7;
	private final GraphicsImage defaultFlag;
	
	public KnockoutMatch(Knockout knockout, int height, int fontSize, int flagWidth, int flagHeight) {
		this.knockout = knockout;
		this.height = height;
		teams = new Team[2];
		flags = new FlagTeam[2];
		this.fontSize = fontSize;
		
		this.flagWidth = flagWidth;
		this.flagHeight = flagHeight;
		defaultFlag = GraphicsImage.defaultFlag.getScaledImage(flagWidth, flagHeight, 1);
		
		setLayout(null);
		setSize(BasicWidth, height);
		setBackground(SystemColor.controlHighlight);
		setBorder(new LineBorder(Color.black));

		createLabel();
		createScores();
		createTeams();
		createPenalties();
		
//		team[0].setOpaque(true);
//		team[0].setBackground(Color.red);
		
//		team[1].setOpaque(true);
//		team[1].setBackground(Color.red);
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

		team[0].setLocation(team[0].getX() - 41, team[0].getY());
		team[1].setLocation(team[1].getX() + 38, team[1].getY());
		
		if(flagLabels != null) {			
			flagLabels[0].setLocation(flagLabels[0].getX() - 41, flagLabels[0].getY());
			flagLabels[1].setLocation(flagLabels[1].getX() + 38, flagLabels[1].getY());
		}

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
		if(League.flagsMode) {
			FlagTeam.findCountry(team.name, flags[index], flagLabels[index], this, knockout.flagTeams);			
		}
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
	
	public void setFocus() {
		if(penaltyRound()) {
			int penaltyIndex = penalty[0].getText().equals("") ? 0 : 1;
			penalty[penaltyIndex].requestFocusInWindow();
		} else {
			int scoreIndex = score[0].getText().equals("") ? 0 : 1;
			score[scoreIndex].requestFocusInWindow();			
		}
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
		
		penalty[0].setLocation(BasicWidth / 2 - 55 - 35, Methods.centerY(penalty[0], this) - 2);
		penalty[1].setLocation(BasicWidth / 2 + 55, Methods.centerY(penalty[1], this) - 2);
		
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
			flagLabels[i].setSize(flagWidth, flagHeight);
			flagLabels[i].setIcon(defaultFlag);
//			flagLabels[i].setBorder(new LineBorder(Color.red));
			add(flagLabels[i]);
		}
		
		int flag1X = teamSize - flagWidth;
		int flag2X = BasicWidth - teamSize + (flagWidth - FlagTextField.FlagWidth);
		
		flagLabels[0].setLocation(flag1X, Methods.centerY(flagLabels[0], this));
		flagLabels[1].setLocation(flag2X, Methods.centerY(flagLabels[1], this));
	}
	
	// CREATE 'TEAMS LABELS'
	private void createTeams() {
		team = new JLabel[2];

		for (int i = 0; i < team.length; i++) {
			team[i] = new JLabel();
			team[i].setFont(new Font("Verdana", Font.PLAIN, fontSize));
//			team[i].setBorder(new LineBorder(Color.green));
			add(team[i]);
		}

		if (League.flagsMode) {
			createFlags();
		}
		
		int tWidth = teamSize;
		int team2X = BasicWidth - teamSize;
		
		final int offset = flagOffset + (flagWidth - FlagTextField.FlagWidth);
		
		if(League.flagsMode) {
			tWidth = teamSize - flagWidth - offset;
			team2X = BasicWidth - teamSize + flagWidth + offset;
		}
		
		team[0].setSize(tWidth, height);
		team[0].setLocation(0, 0);
		
		team[1].setSize(tWidth, height);
		team[1].setLocation(team2X, 0);
		
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

		score[0].setLocation(teamSize + 13, Methods.centerY(score[0], this));
		score[1].setLocation(BasicWidth - teamSize - 13 - ScoreSquare.Size, Methods.centerY(score[1], this));

		score[0].setRightEvent(score[1]);
		score[1].setLeftEvent(score[0]);
	}
}
