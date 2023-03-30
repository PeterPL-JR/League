package com.peterpl.league.game.gui;

import java.util.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import com.peterpl.league.*;
import com.peterpl.league.methods.*;

public class RankingTable extends Table {
	private static final long serialVersionUID = 1L;
	public static final int FlagWidth = 43;
	public static final int FlagHeight = 27;
	
	public static ArrayList<FlagTeam> flagTeams = FlagTeam.getAllScaledFlags(FlagWidth, FlagHeight, true);
	public static final Color normal = new Color(0xe3e3e3);

	public static final Color green = new Color(0xbbffbb);
	public static final Color yellow = new Color(0xffffbb);
	public static final Color red = new Color(0xffbbbb);

	public static final Color gold = new Color(0xffdd00);
	public static final Color silver = new Color(0xc0c0c0);
	public static final Color brown = new Color(0xcc9966);
	
	protected static final String[] titles = { "Pos", "Team", "Pts", "W", "D", "L", "+", "-", "+/-", "M" };
	protected static final int[] collumnsSizes = { 58, 280, 65, 38, 38, 38, 38, 38, 38, 38 };

	protected RankingTableHeader rankingHeader;
	protected RankingTablePart[] rankingParts;

	public Team[] teams;
	protected final int teamsCount;

	public RankingTable(int teams) {
		super(titles.length, teams + 1);
		this.teams = new Team[teams];
		teamsCount = teams;
		
		int[] rowsSizes = new int[teams + 1];
		for(int i = 0; i < rowsSizes.length; i++)
			rowsSizes[i] = 50;
		setSizes(collumnsSizes, rowsSizes);
		createTable();
		
		rankingHeader = new RankingTableHeader(parts[0]);
		rankingParts = new RankingTablePart[teams];
		
		for(int i = 0; i < rankingParts.length; i++) {
			rankingParts[i] = new RankingTablePart(i, parts[i + 1]);
		}
	}

	public void setRowColor(int index, Color color) {
		super.setRowColor(index + 1, color);
	}

	public void setTeam(int index, Team team) {
		if (index < 0 || index >= parts.length)
			return;
		//parts[index].teamName.setText(" " + team.name);
		teams[index] = team;

		rankingParts[index].setTeam(team);
	}

	public Team getTeam(int index) {
		if (index < 0 || index >= teamsCount)
			return null;
		return teams[index];
	}

	public Team[] getTeams() {
		return teams;
	}

	public void update(Team[] teams) {
		for (int i = 0; i < teams.length; i++) {
			setTeam(i, teams[i]);
		}
	}

	protected class RankingTableHeader extends TablePart {
		private static final long serialVersionUID = 1L;

		public RankingTableHeader(TablePart part) {
			super(11 - 11);
			JPanel[] parts = part.parts;
			
			for(int i = 0; i < parts.length; i++) {
				parts[i].setBackground(SystemColor.scrollbar);
				parts[i].setLayout(new BorderLayout());
				
				JLabel label = new JLabel(titles[i]);
				label.setFont(new Font("Tahoma", Font.BOLD, 15));
				label.setHorizontalAlignment(0);
				parts[i].add(label);
			}
		}
	}
	
	protected class RankingTablePart extends TablePart {
		private static final long serialVersionUID = 1L;
		private final int placeIndex;
		private TablePart thisPart;
		
		private JLabel flagLabel;
		private FlagTeam flag;
		
		private JLabel place;
		private JLabel team;
		private JLabel[] stats;
		
		public RankingTablePart(int index, TablePart part) {
			super(index);
			this.placeIndex = index + 1;
			thisPart = part;
			
			createLabels();
			
			if(League.flagsMode) {
				createFlag();
			} else {
				createTeam();
			}
		}
		
		private void createLabels() {
			thisPart.parts[0].setLayout(new BorderLayout());
			
			place = new JLabel(" " + placeIndex + ". ");
			place.setFont(new Font("Tahoma", 0, 16));
			place.setHorizontalAlignment(0);
			thisPart.parts[0].add(place);
			
			stats = new JLabel[8];
			for(int i = 0; i < stats.length; i++) {
				thisPart.parts[i + 2].setLayout(new BorderLayout());
				
				stats[i] = new JLabel("0");
				stats[i].setFont(new Font("Tahoma", 0, 14));
				stats[i].setHorizontalAlignment(0);
				thisPart.parts[i + 2].add(stats[i]);
			}
			stats[0].setFont(new Font("Tahoma", 0, 17));
		}
		
		private void createFlag() {
			JPanel part = thisPart.parts[1];
			part.setLayout(null);
			
			flagLabel = new JLabel();
			flagLabel.setSize(FlagWidth, FlagHeight);
			flagLabel.setLocation(15, Game.centerY(flagLabel, part));
			part.add(flagLabel);
			
			team = new JLabel();
			team.setFont(new Font("Verdana", 0, 17));
			team.setLocation(flagLabel.getWidth() + flagLabel.getX() - 2, 0);
			team.setSize(part.getWidth() - team.getX(), part.getHeight());
			part.add(team);
		}
		
		private void createTeam() {
			JPanel part = thisPart.parts[1];
			part.setLayout(null);
			
			team = new JLabel();
			team.setFont(new Font("Verdana", 0, 14));
			team.setSize(part.getWidth() - 60, 30);
			team.setLocation(Game.centerX(team, part), Game.centerY(team, part));
			team.setBorder(new LineBorder(Color.gray));
			team.setOpaque(true);
			part.add(team);
		}
		
		private void setTeam(Team team) {
			this.team.setText("  " + team.name);
			int[] stats = team.getStats();
			
			for (int i = 0; i < stats.length; i++) {
				this.stats[i].setText(stats[i] + "");
			}
			
			if(League.flagsMode) {
				FlagTeam.findCountry(team.name, flag, flagLabel, thisPart, flagTeams);
			}
		}
	}
}
