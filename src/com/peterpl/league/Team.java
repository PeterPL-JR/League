package com.peterpl.league;

import java.io.*;

public class Team implements Serializable {
	private static final long serialVersionUID = 1L;

	public final String name;
	public final int group;
	public final int pot;

	public boolean host = false;
	private int advance = GroupPhase;

	private int points;
	private int goalsScored, goalsLost;
	private int goals;

	private int wins, draws, loses;
	private int matches;

	public static final int Points = 0;
	public static final int Wins = 1, Draws = 2, Loses = 3;
	public static final int GoalsScored = 4, GoalsLoses = 5, Goals = 6;
	public static final int Matches = 7;

	public static final int GroupPhase = 0;
	public static final int SecondRound = 1, QuarterFinals = 2;
	public static final int SemiFinals = 3, ThirdPlace = 4;
	public static final int Final = 5, Winner = 6;

	public Team(String name, int group, int pot) {
		this.name = name;
		this.group = group;
		this.pot = pot;

		points = 0;
		goalsScored = goalsLost = goals = 0;
		wins = draws = loses = 0;
		matches = 0;
	}

	public Team(Team team) {
		this.name = team.name;
		this.group = team.group;
		this.pot = team.pot;

		host = team.host;
		advance = team.advance;

		points = team.points;
		goalsScored = team.goalsScored;
		goalsLost = team.goalsLost;
		goals = team.goals;

		wins = team.wins;
		draws = team.draws;
		loses = team.loses;
		matches = team.matches;
	}

	public void playMatch(int goalsScored, int goalsLost) {
		matches++;

		this.goalsScored += goalsScored;
		this.goalsLost += goalsLost;
		goals = this.goalsScored - this.goalsLost;

		if (goalsScored > goalsLost) {
			wins++;
			points += 3;
		} else if (goalsScored < goalsLost) {
			loses++;
			points += 0;
		} else if (goalsScored == goalsLost) {
			draws++;
			points += 1;
		}
	}

	public int[] getStats() {
		
		int[] stats = { points, wins, draws, loses, goalsScored, goalsLost, goals, matches };
		return stats;
	}

	public int get(int stat) {
		int[] stats = getStats();
		return stats[stat];
	}
	
	public void setAdvance(int advance) {
		this.advance = advance;
	}
	public int getAdvance() {
		return advance;
	}

	public static void sortByAdvance(Team[] teams) {
		int length = teams.length;

		for (int i = 1; i < length; i++) {
			for (int j = length - 1; j >= i; j--) {

				// SORT BY ADVANCE
				if (teams[j - 1].getAdvance() < teams[j].getAdvance()) {

					Team buffer = teams[j - 1];
					teams[j - 1] = teams[j];
					teams[j] = buffer;
				}
			}
		}
	}

	public static void sortByStats(Team[] teams) {
		int length = teams.length;

		int[] table = new int[length];
		for (int i = 0; i < length; i++)
			table[i] = i;

		Team[] teamsBuffer = new Team[length];
		System.arraycopy(teams, 0, teamsBuffer, 0, length);

		sortByStats(teamsBuffer, table);

		for (int i = 0; i < length; i++)
			teams[i] = teamsBuffer[table[i]];
	}

	public static void sortByStats(Team[] teams, int[] table) {
		int length = teams.length;

		for (int i = 1; i < length; i++) {
			for (int j = length - 1; j >= i; j--) {

				// SORT BY POINTS
				if (teams[table[j - 1]].get(Team.Points) < teams[table[j]].get(Team.Points)) {

					int buffer = table[j - 1];
					table[j - 1] = table[j];
					table[j] = buffer;

				} else if (teams[table[j - 1]].get(Team.Points) == teams[table[j]].get(Team.Points)) {

					// SORT BY GOALS
					if (teams[table[j - 1]].get(Team.Goals) < teams[table[j]].get(Team.Goals)) {

						int buffer = table[j - 1];
						table[j - 1] = table[j];
						table[j] = buffer;

					} else if (teams[table[j - 1]].get(Team.Goals) == teams[table[j]].get(Team.Goals)) {

						// SORT BY GOALS SCORED
						if (teams[table[j - 1]].get(Team.GoalsScored) < teams[table[j]].get(Team.GoalsScored)) {

							int buffer = table[j - 1];
							table[j - 1] = table[j];
							table[j] = buffer;
						}
					}
				}
			}
		}
	}

	public static Team[] sortByGroup(Team[] teams, int mode) {
		int length = teams.length;

		int[] groups = new int[length];
		int[] indexes = new int[length];

		for (int i = 0; i < length; i++)
			groups[i] = teams[i].group;
		for (int i = 0; i < length; i++)
			indexes[i] = i;

		for (int i = 1; i < length; i++) {
			for (int j = length - 1; j >= i; j--) {
				boolean condition;

				if (mode == 0) {
					condition = (groups[j] < groups[j - 1]);
				} else {
					condition = (groups[j] > groups[j - 1]);
				}

				if (condition) {
					int buffer = groups[j - 1];
					groups[j - 1] = groups[j];
					groups[j] = buffer;

					int buffer2 = indexes[j - 1];
					indexes[j - 1] = indexes[j];
					indexes[j] = buffer2;
				}
			}
		}

		Team[] newTeams = new Team[length];
		for (int i = 0; i < groups.length; i++)
			newTeams[i] = teams[indexes[i]];
		return newTeams;
	}
	
	public static Team getTheSame(Team teamNeedle, Team[][] teamsArray) {
		for(Team[] teams : teamsArray) {
			for(Team team : teams) {
				if(team.name == teamNeedle.name && team.group == teamNeedle.group) {
					return team;
				}
			}
		}
		return null;
	}
}
