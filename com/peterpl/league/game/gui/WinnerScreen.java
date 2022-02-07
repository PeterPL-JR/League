package com.peterpl.league.game.gui;

import javax.swing.*;
import javax.swing.border.*;

import com.peterpl.league.methods.*;

import java.awt.Color;
import java.awt.Font;

public class WinnerScreen extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel title;
	private JLabel cup;
	private JLabel winner;
	
	public WinnerScreen() {
		setLayout(null);
		setSize(700, 336);
		create();
		
		title = new JLabel("Winner");
		title.setSize(268, 52);
		title.setLocation(Game.centerX(title, this), 0);
		title.setBackground(new Color(230, 230, 230));
		
		title.setForeground(Color.DARK_GRAY);
		title.setFont(new Font("Verdana", Font.BOLD, 25));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBorder(new LineBorder(Color.gray, 1));
		title.setOpaque(true);
		
		title.setVisible(false);
		add(title);
	}
	
	private void create() {
		cup = new JLabel();
		cup.setSize(185, 185);
		cup.setLocation(Game.centerX(cup, this), 66);
		add(cup);
		
		winner = new JLabel();
		winner.setForeground(Color.DARK_GRAY);
		winner.setFont(new Font("Verdana", Font.BOLD, 40));
		winner.setHorizontalAlignment(SwingConstants.CENTER);
		winner.setSize(getWidth(), 85);
		winner.setLocation(0, 251);
		add(winner);
	}
	
	public void setWinner(String name) {
		title.setVisible(true);
		winner.setText(name);
	}
}
