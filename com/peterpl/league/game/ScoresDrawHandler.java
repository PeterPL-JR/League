package com.peterpl.league.game;

import java.util.*;

import com.peterpl.league.*;
import com.peterpl.league.game.rounds.*;

public class ScoresDrawHandler {

	private Random rand = new Random();

	public void createScores(GroupRound[] groups) {

		for (int i = 0; i < groups.length; i++) {
			for (int j = 0; j < 6; j++) {

				//if(i == groups.length - 1 && j == 5) continue;
				
				Team team1 = League.allTeams[i][groups[i].matchesTable.getMatchTeam1(j)];
				Team team2 = League.allTeams[i][groups[i].matchesTable.getMatchTeam2(j)];

				int[] scores = getRandomScores(team1, team2, "match");
				groups[i].matchesTable.setScore(j, scores[0], scores[1]);
			}
		}

		for (int j = 0; j < League.groupsCount * 6; j++) {
			League.groupsHandler.endMatch();
		}
	}

	public void createScores(Knockout round) {
		for (int i = 0; i < round.matchesCount; i++) {

			Team team1 = round.matches[i].getTeam(0);
			Team team2 = round.matches[i].getTeam(1);

			int[] scores = getRandomScores(team1, team2, "match");
			round.setScore(i, scores[0], scores[1]);
			
			Random random = new Random();
			int overtime = random.nextInt(5);
			if(overtime == 0) {
				round.matches[i].setOvertime(true, false);
			}
			
			if(scores[0] == scores[1]) {
				int[] penalties = getRandomScores(team1, team2, "penalty");
				round.setPenalty(i, penalties[0], penalties[1]);
				round.matches[i].playPenalty();
				round.endPenalty();
				team1.playMatch(scores[0], scores[1]);
				team2.playMatch(scores[1], scores[0]);
				continue;
			}
			round.endMatch();
		}
	}

	private int[] getRandomScores(Team team1, Team team2, String what) {
		
		int chance1 = League.teamsInGroup - team1.pot;
		int chance2 = League.teamsInGroup - team2.pot;

		int drawChance = League.teamsInGroup - Math.abs(chance1 - chance2);
		int count = chance1 + chance2 + drawChance;

		int winTeam1 = chance1;
		int winTeam2 = chance1 + chance2;
		int draw = count;

		int scoreGen = rand.nextInt(count);
		int score = 2;

		if (scoreGen >= 0 && scoreGen < winTeam1)
			score = 0;
		else if (scoreGen >= winTeam1 && scoreGen < winTeam2)
			score = 1;
		else if (scoreGen >= winTeam2 && scoreGen < draw)
			score = 2;

		int score1 = 0;
		int score2 = 0;
		
		int maxScore;
		
		if(what == "penalty") {
			
			maxScore = rand.nextInt(5) + 3;
			score = score == 2 ? rand.nextInt(2) : score;
			
		} else {
			maxScore = 4;
		}
		
		switch (score) {
		case 0:
			do {
				score1 = rand.nextInt(maxScore);
				score2 = rand.nextInt(maxScore);
			} while (score1 <= score2);
			break;
		case 1:
			do {
				score1 = rand.nextInt(maxScore);
				score2 = rand.nextInt(maxScore);
			} while (score1 >= score2);
			break;
		case 2:
			score1 = score2 = rand.nextInt(drawChance);
			break;
		}
		
		int[] scores = {
			score1,
			score2
		};
		
		return scores;
	}
}
