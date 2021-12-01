package com.peterpl.league.game.gui;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.Color;
import java.awt.Font;

public class WinnerScreen extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel title;
	private JLabel cup;
	private JLabel winner;
	
	public WinnerScreen() {
		setLayout(null);
		setSize(300, 336);
		
		title = new JLabel("Winner");
		title.setBounds(16, 0, 268, 52);
		title.setBackground(new Color(230, 230, 230));
		title.setForeground(Color.DARK_GRAY);
		title.setFont(new Font("Verdana", Font.BOLD, 25));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBorder(new LineBorder(Color.gray, 1));
		title.setOpaque(true);
		title.setVisible(false);
		add(title);
		
		cup = new JLabel(Images.cup);
		cup.setBounds(57, 66, 185, 185);
		add(cup);
		
		winner = new JLabel();
		winner.setForeground(Color.DARK_GRAY);
		winner.setFont(new Font("Verdana", Font.BOLD, 40));
		winner.setHorizontalAlignment(SwingConstants.CENTER);
		winner.setBounds(0, 251, 300, 85);
		add(winner);
	}
	
	public void setWinner(String name) {
		title.setVisible(true);
		winner.setText(name);
	}
}
