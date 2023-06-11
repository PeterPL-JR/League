package com.peterpl.league.game;

import java.util.*;

import com.peterpl.league.*;
import com.peterpl.league.methods.*;

public class DrawHandler {
	private LandsDrawHandler landsDraw = new LandsDrawHandler();
	private Random random = new Random();
	
	static final int Team = 0, GroupIndex = 1, PotIndex = 2;

	private String[][] pots;
	private String[][] groups;

	private int potsN;
	private int groupsN;

	public void draw(String[][] pots, String[][] groups) {
		this.potsN = pots.length;
		this.groupsN = groups.length;
		
		this.pots = pots;
		this.groups = groups;
		landsDraw.init(pots, potsN * groupsN, groupsN);
		
		drawPot1();
		
		for(int i = 1; i < potsN; i++) {
			drawPot(pots, i);
		}
		for(int i = 0; i < groupsN; i++) {
			drawPositions(groups, i);
		}
	}

	private void drawPot(String[][] pots, int potIndex) {
		String[] randomTeams = Game.shuffleArray(pots[potIndex]);
		
		for(int i = 0; i < groupsN; i++) {
			String team = randomTeams[i];
			String[] group = groups[i];
			
			if(League.flagsMode) {
				if(landsDraw.checkLands(group, team, i, potIndex)) {
					continue;
				}
			}
			group[potIndex] = team;
		}

		if(landsDraw.countTeams(groups, potIndex) != groupsN) {
			for(int i = 0; i < groupsN; i++) {
				groups[i][potIndex] = null;
			}
			drawPot(pots, potIndex);
		}
	}
	
	private void drawPositions(String[][] groups, int groupIndex) {
		String[] teams = new String[potsN - 1];
		
		for(int i = 0; i < potsN - 1; i++) {
			teams[i] = groups[groupIndex][i + 1];
		}
		teams = Game.shuffleArray(teams);
		for(int i = 0; i < teams.length; i++) {
			groups[groupIndex][i + 1] = teams[i];
		}
	}

	private void drawPot1() {

		int drawsLength = groupsN - League.hostsCount;
		int[] draws = new int[drawsLength];

		for (int i = 0; i < draws.length; i++)
			draws[i] = -1;

		for (int i = 0; i < draws.length; i++) {

			int drawGroup;
			do {
				drawGroup = random.nextInt(drawsLength);
			} while (draws[drawGroup] != -1);
			draws[drawGroup] = i;
		}

		createHosts(draws, drawsLength);
	}
	
	private void createHosts(int[] draws, int drawsLength) {
		if (League.hostsCount == 0)
			for (int i = 0; i < drawsLength; i++)
				groups[i][0] = pots[0][draws[i]];

		if (League.hostsCount == 1) {
			for (int i = 0; i < drawsLength; i++)
				groups[i + League.hostsCount][0] = pots[0][draws[i] + League.hostsCount];
			groups[0][0] = pots[0][0];
		}

		if (League.hostsCount == 2) {
			int count = groupsN / 2 - 1;

			if (League.gameMode.equals("Teams12")) {
				groups[2][0] = pots[0][2];
			} else {
				for (int i = 0; i < count; i++)
					groups[i + 1][0] = pots[0][draws[i] + League.hostsCount];
				for (int i = count; i < count + count; i++)
					groups[i + 2][0] = pots[0][draws[i] + League.hostsCount];
			}

			groups[0][0] = pots[0][0];
			groups[groupsN / 2][0] = pots[0][1];
		}
	}
}
