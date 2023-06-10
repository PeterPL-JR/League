package com.peterpl.league.game.gui;

import javax.swing.*;
import javax.swing.border.*;

import com.peterpl.league.*;
import com.peterpl.league.files.*;
import com.peterpl.league.methods.*;

import java.awt.*;
import java.util.*;

public class WinnerScreen extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final int FlagWidth = FlagTeam.Width;
	public static final int FlagHeight = FlagTeam.Height;

	public static ArrayList<FlagTeam> flagTeams = FlagTeam.getAllScaledFlags(FlagWidth, FlagHeight, true);
	private FlagTeam flagTeam;

	private JLabel title;
	private JLabel cup;
	private JPanel winner;
	
	private JLabel winnerName;
	private JLabel winnerFlag;
	
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
		cup = new JLabel(GraphicsImage.cup);
		cup.setSize(185, 185);
		cup.setLocation(Game.centerX(cup, this), 66);
		add(cup);
		
		winner = new JPanel();
		winner.setSize(getWidth(), 85);
		winner.setLocation(0, 255);
		add(winner);
		
		if(League.flagsMode) {
			winnerFlag = new JLabel();
			winnerFlag.setSize(FlagWidth, FlagHeight);
			winner.add(winnerFlag);
		}
		
		winnerName = new JLabel();
		winnerName.setForeground(Color.DARK_GRAY);
		winnerName.setFont(new Font("Verdana", Font.BOLD, 42));
		winnerName.setHorizontalAlignment(SwingConstants.CENTER);
		winnerName.setBorder(new EmptyBorder(0, 15, 0, 15));
		winner.add(winnerName);
	}
	
	public void setWinner(String name) {
		title.setVisible(true);
		winnerName.setText(name);
		
		if(League.flagsMode) {
			FlagTeam.findCountry(name, flagTeam, winnerFlag, winner, flagTeams);
		}
	}
}
