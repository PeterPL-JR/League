package com.peterpl.league.game;

import java.util.*;

import com.peterpl.league.*;

public class DrawHandler {
	private Random random = new Random();

	private String[][] pots;
	private String[][] groups;
	private int[][] numbers;

	private int potsN;
	private int groupsN;

	public void draw(String[][] pots, String[][] groups, int[][] numbers) {

		this.pots = pots;
		this.groups = groups;
		this.numbers = numbers;

		potsN = pots.length;
		groupsN = groups.length;

		drawPot1();

		for (int i = 1; i < groups.length; i++)
			for (int j = 1; j < groups[i].length; j++)
				groups[i][j] = null;

		for (int n = 1; n < potsN; n++) {

			int[] draws = new int[groupsN];
			for (int i = 0; i < draws.length; i++)
				draws[i] = -1;

			for (int i = 0; i < draws.length; i++) {

				int drawGroup;
				do {
					drawGroup = random.nextInt(groupsN);
				} while (draws[drawGroup] != -1);
				draws[drawGroup] = i;
			}

			for (int i = 0; i < groups.length; i++) {
				int position;
				do {
					position = random.nextInt(3) + 1;
				} while (groups[i][position] != null);
				groups[i][position] = pots[n][draws[i]];
				numbers[i][position] = n;
			}
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

		for (int i = 0; i < groupsN; i++)
			numbers[i][0] = 0;
	}
}
