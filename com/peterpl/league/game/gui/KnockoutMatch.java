package com.peterpl.league.game.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import com.peterpl.league.*;
import com.peterpl.league.game.rounds.*;

public class KnockoutMatch extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final int BasicWidth = 580;
	public static final int BasicHeight = 60;

	private Knockout knockout;
	private boolean whileMatch = false;
	private boolean overtime = false;
	private boolean penaltyRound = false;

	private JLabel scoreLabel;
	private JLabel[] team;
	public ScoreSquare[] score;
	
	private PenaltySquare[] penalty;
	private Team[] teams;
	public Team winner;

	public KnockoutMatch(Knockout knockout) {
		this.knockout = knockout;
		teams = new Team[2];
		
		setLayout(null);
		setSize(BasicWidth, BasicHeight);
		setBackground(SystemColor.controlHighlight);
		setBorder(new LineBorder(Color.black));

		label();
		teams();
		scores();
		penalty();
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
		team[index].setFont(new Font(font.getName(), Font.BOLD, (int)(font.getSize() * 1.05)));
		
		winner = teams[index];
	}
	
	public void playPenalty() {
		setOvertime(false, false);
		penaltyRound = true;
			
		for(int i = 0; i < 2; i++) {
			score[i].setEnabled(false);
			
			penalty[i].setVisible(true);
			penalty[i].setActive(true);
		}
		
		team[0].setLocation(-41, team[0].getY());
		team[1].setLocation(388, team[1].getY());
		
		penalty[0].requestFocusInWindow();
	}
	
	public void endPenalty() {
		
		for(int i = 0; i < 2; i++) {
			penalty[i].setActive(false);
		}
	}

	public void setOvertime(boolean overtime, boolean loaded) {
		if (!whileMatch && !loaded)
			return;
		if(penaltyRound)
			return;
		
		this.overtime = overtime;
		
		scoreLabel.setText(overtime ? "E" : "-");
	}

	public void setTeam(int index, Team team) {
		if (index < 0 || index > 1)
			return;
		
		this.team[index].setText(team.name);
		
		teams[index] = team;
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
	
	private void penalty() {
		
		penalty = new PenaltySquare[2];
		for(int i = 0; i < penalty.length; i++) {
			penalty[i] = new PenaltySquare();
			penalty[i].setVisible(false);
			penalty[i].enterKeyEvent = () -> { knockout.button.doClick(); };
			add(penalty[i]);
		}
		
		penalty[0].setLocation(200, -2);
		penalty[1].setLocation(345, -2);
		
		penalty[0].setRightEvent(penalty[1]);
		penalty[1].setLeftEvent(penalty[0]);
	}

	// CREATE 'SCORE LABEL'
	private void label() {
		scoreLabel = new JLabel("-");
		scoreLabel.setBounds(BasicWidth / 2 - 20, 0, 40, BasicHeight);
		scoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(scoreLabel);

		scoreLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setOvertime(overtime ? false : true, false);
			}
		});
	}

	// CREATE 'TEAMS LABELS'
	private void teams() {
		team = new JLabel[2];
		
		for (int i = 0; i < team.length; i++) {
			team[i] = new JLabel();
			team[i].setFont(new Font("Verdana", Font.PLAIN, 15));
			add(team[i]);
		}
		team[0].setBounds(0, 0, 233, BasicHeight);
		team[1].setBounds(347, 0, 233, BasicHeight);
		
		team[0].setHorizontalAlignment(SwingConstants.RIGHT);
		team[1].setHorizontalAlignment(SwingConstants.LEFT);
	}

	// CREATE 'SCORES'
	private void scores() {
		score = new ScoreSquare[2];
		for (int i = 0; i < score.length; i++) {
			
			score[i] = new ScoreSquare(this);
			score[i].enterKeyEvent = () -> { knockout.button.doClick(); };
			add(score[i]);
		}
		
		score[0].setLocation(243, BasicHeight / 2 - 12);
		score[1].setLocation(311, BasicHeight / 2 - 12);
		
		score[0].setRightEvent(score[1]);
		score[1].setLeftEvent(score[0]);
	}

	public void setDimension(int height, Font font) {
		
		setSize(getWidth(), height);
		scoreLabel.setBounds(BasicWidth / 2 - 20, 0, 40, height);
		
		team[0].setBounds(0, 0, 233, height);
		team[1].setBounds(347, 0, 233, height);
		
		score[0].setBounds(243, height / 2 - 12, 25, 24);
		score[1].setBounds(311, height / 2 - 12, 25, 24);
		
		for(int i = 0; i < 2; i++) {
			team[i].setFont(font);
			
			int penaltyX = penalty[i].getX();
			int penaltyY = height / 2 - PenaltySquare.size / 2;
			penalty[i].setLocation(penaltyX, penaltyY);
		}
	}
}
