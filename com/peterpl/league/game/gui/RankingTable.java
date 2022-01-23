package com.peterpl.league.game.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import com.peterpl.league.*;

public class RankingTable extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final Color normal = new Color(0xe3e3e3);

	public static final Color green = new Color(0xbbffbb);
	public static final Color yellow = new Color(0xffffbb);
	public static final Color red = new Color(0xffbbbb);

	public static final Color gold = new Color(0xffdd00);
	public static final Color silver = new Color(0xc0c0c0);
	public static final Color brown = new Color(0xcc9966);

	protected TableHeader title;
	public TablePart[] parts;

	public Team[] teams;
	protected final int teamsCount;

	public RankingTable(int teams) {
		this.teams = new Team[teams];
		teamsCount = teams;

		setLayout(null);
		setSize(580, 56 + teams * 55);
		setBackground(SystemColor.controlHighlight);
		setBorder(new LineBorder(Color.black));

		title = new TableHeader(56);
		title.setLocation(0, 0);
		add(title);

		String[] titles = { "Pos", "Team", "Pts", "W", "D", "L", "+", "-", "+/-", "M" };

		title.place.setText(titles[0]);
		title.place.setFont(new Font("Tahoma", Font.BOLD, 14));

		title.teamL.setText(titles[1]);
		title.teamL.setFont(new Font("Tahoma", Font.BOLD, 14));

		for (int i = 0; i < title.stats.length; i++) {
			title.stats[i].setFont(new Font("Tahoma", Font.BOLD, 14));
		}
		for (int i = 0; i < title.stats.length; i++) {
			title.stats[i].setText(titles[i + 2]);
		}

		parts = new TablePart[teams];
		for (int i = 0; i < parts.length; i++) {
			parts[i] = new TablePart(56);
			parts[i].setLocation(0, 55 + i * (parts[i].getThisHeight() - 1));
			add(parts[i]);
		}
		for (int i = 0; i < parts.length; i++) {
			parts[i].place.setText((i + 1) + ".");
		}
	}

	public void setPartsHeight(int height) {
		for (int i = 0; i < parts.length; i++) {
			parts[i].setHeight(height);
			parts[i].setLocation(0, 55 + i * (parts[i].getThisHeight() - 1));
		}
		setSize(getWidth(), 56 + (height - 1) * teamsCount);
	}

	public void setColor(int index, Color color) {
		if (index < 0 || index >= parts.length)
			return;
		parts[index].setBackground(color);
	}

	public void setTeam(int index, Team team) {
		if (index < 0 || index >= parts.length)
			return;
		parts[index].teamName.setText(" " + team.name);
		teams[index] = team;

		int[] stats = team.getStats();

		for (int i = 0; i < parts[index].stats.length; i++) {
			parts[index].stats[i].setText(stats[i] + "");
		}
	}
	
	public Team getTeam(int index) {
		if(index < 0 || index >= teamsCount)
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

	protected class TableHeader extends TablePart {
		private static final long serialVersionUID = 1L;

		protected TableHeader(int height) {
			super(height);
			setBackground(SystemColor.scrollbar);
			teamName.setVisible(false);
		}
	}

	public class TablePart extends JPanel {
		private static final long serialVersionUID = 1L;

		protected JLabel place;
		protected JLabel teamL;

		protected JLabel[] stats;
		protected JLabel teamName;

		private int height;

		protected TablePart(int height) {
			this.height = height;

			setLayout(null);
			setSize(580, height);
			setBackground(SystemColor.controlHighlight);

			labels();
			team();
		}

		protected void setHeight(int height) {
			this.height = height;

			setSize(getWidth(), height);

			place.setSize(place.getWidth(), height);
			teamL.setSize(teamL.getWidth(), height);

			teamName.setSize(teamName.getWidth(), (int) (height * 0.6));
			teamName.setLocation(teamName.getX(), (int) (height / 2 - (height * 0.6 / 2)));

			for (int i = 0; i < stats.length; i++)
				stats[i].setSize(stats[i].getWidth(), height);
		}

		private void labels() {

			place = new JLabel();
			place.setBounds(0, 0, 58, height);
			place.setFont(new Font("Tahoma", Font.PLAIN, 16));
			place.setBorder(new LineBorder(Color.black));
			place.setHorizontalAlignment(SwingConstants.CENTER);
			add(place);

			teamL = new JLabel();
			teamL.setBounds(57, 0, 200, height);
			teamL.setFont(new Font("Tahoma", Font.PLAIN, 14));
			teamL.setBorder(new LineBorder(Color.black));
			teamL.setHorizontalAlignment(SwingConstants.CENTER);
			add(teamL);

			stats = new JLabel[8];
			for (int i = 0; i < stats.length; i++) {
				stats[i] = new JLabel();
				stats[i].setFont(new Font("Tahoma", Font.PLAIN, 14));
				stats[i].setBorder(new LineBorder(Color.black));
				stats[i].setHorizontalAlignment(SwingConstants.CENTER);
				add(stats[i]);
			}

			stats[0].setBounds(256, 0, 65, height);
			stats[0].setFont(new Font("Tahoma", Font.PLAIN, 17));

			for (int i = 1; i < stats.length; i++) {
				stats[i].setBounds(320 + (i - 1) * 37, 0, 38, height);
			}
		}

		private void team() {
			int height = (int) (this.height * 0.6);

			teamName = new JLabel();
			teamName.setBounds(78, (this.height / 2) - height / 2, 160, height);
			teamName.setBorder(new LineBorder(new Color(100, 100, 100)));
			teamName.setOpaque(true);
			teamName.setBackground(new Color(247, 247, 247));
			teamName.setFont(new Font("Verdana", Font.PLAIN, 13));
			add(teamName);
		}

		protected int getThisHeight() {
			return height;
		}
	}
}
