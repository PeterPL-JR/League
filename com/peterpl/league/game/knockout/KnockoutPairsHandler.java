package com.peterpl.league.game.knockout;

import java.util.*;

import com.peterpl.league.*;
import com.peterpl.league.game.rounds.*;

public class KnockoutPairsHandler {

	private char[] groups;
	MatchKnock[] matches;

	public KnockoutPairsHandler(Knockout knockout) {

		groups = new char[League.groupsCount];
		for (int i = 0; i < groups.length; i++)
			groups[i] = (char) ('A' + i);

		matches = new MatchKnock[knockout.matchesCount];
		for (int i = 0; i < matches.length; i++)
			matches[i] = new MatchKnock();
	}

	public void createPairsStandard() {
		int length = matches.length;
		
		if(League.teamsInKnockout == League.groupsCount) {
			
			for(int i = 0; i < length; i++) {
				matches[i].setTeam(0, groups[i * 2], '1');
				matches[i].setTeam(1, groups[i * 2 + 1], '1');
			}
		
		} else {
			
			for(int i = 0; i < length / 2; i++) {
				matches[i].setTeam(0, groups[i * 2], '1');
				matches[i].setTeam(1, groups[i * 2 + 1], '2');
				
				matches[i + length / 2].setTeam(0, groups[i * 2 + 1], '1');
				matches[i + length / 2].setTeam(1, groups[i * 2], '2');
			}
		}
	}
	
	public void createPairsExtra() {
		switch (matches.length) {
		case 8:
			createPairs16();
			break;
		case 2:
			createPairs4();
			break;
		case 4:
			if (League.groupsCount == 6)
				create1Pairs8();
			else if (League.groupsCount == 3)
				create2Pairs8();
			break;
		}
	}

	private void createPairs16() {
		// Create an array of 1st Places
		char[] teams1p = new char[League.groupsCount];
		System.arraycopy(groups, 0, teams1p, 0, League.groupsCount);
		teams1p = createRandArray(teams1p);

		// Create arrays of 2nd Places
		char[] teams2p = new char[League.groupsCount];
		System.arraycopy(teams1p, 0, teams2p, 0, League.groupsCount);

		char[] teams2p1 = new char[League.groupsCount / 2];
		char[] teams2p2 = new char[League.groupsCount / 2];

		{
			int length = teams1p.length / 2;
			for (int i = 0; i < length; i++) {
				teams2p1[i] = teams2p[i + length];
				teams2p2[i] = teams2p[i];
			}
		}

		teams2p1 = createRandArray(teams2p1);
		teams2p2 = createRandArray(teams2p2);

		// Set Matches Teams
		for (int i = 0; i < matches.length; i++)
			if (i % 2 == 1)
				matches[i].setText(1, "3rd Place");

		int length = matches.length / 2;
		int gLength = groups.length / 2;

		for (int i = 1; i < length; i++) {
			matches[i].setTeam(0, teams1p[i - 1], '1');
			matches[i + length].setTeam(0, teams1p[i + gLength - 1], '1');
		}

		matches[0].setTeam(0, teams2p1[0], '2');
		matches[0].setTeam(1, teams2p1[1], '2');
		matches[0].sortGroups();
		matches[2].setTeam(1, teams2p1[2], '2');

		matches[4].setTeam(0, teams2p2[0], '2');
		matches[4].setTeam(1, teams2p2[1], '2');
		matches[4].sortGroups();
		matches[6].setTeam(1, teams2p2[2], '2');
	}

	private void createPairs4() {
		char[] teams1p = new char[League.groupsCount];
		char[] otherTeams1p = new char[League.groupsCount - 1];
		
		System.arraycopy(groups, 0, teams1p, 0, League.groupsCount);
		teams1p = createRandArray(teams1p);

		otherTeams1p[0] = teams1p[1];
		otherTeams1p[1] = teams1p[2];
		sortArray(otherTeams1p);

		String text = otherTeams1p[0] + "1" + "/" + otherTeams1p[1] + "1";
		matches[0].setTeam(0, teams1p[0], '1');
		matches[0].setText(1, text);

		matches[1].setText(0, text);
		matches[1].setText(1, "2nd Place");
	}

	private void create1Pairs8() {
		char[] teams = new char[League.groupsCount];
		System.arraycopy(groups, 0, teams, 0, League.groupsCount);
		teams = createRandArray(teams);

		// Match 1
		matches[0].setTeam(0, teams[0], '1');
		matches[0].setTeam(1, teams[1], '1');
		matches[0].sortGroups();

		// Match 2
		matches[1].setTeam(0, teams[2], '1');
		matches[1].setText(1, "2nd Place");

		// Match 3
		matches[2].setTeam(0, teams[3], '1');
		matches[2].setTeam(1, teams[4], '1');
		matches[2].sortGroups();

		// Match 4
		matches[3].setTeam(0, teams[5], '1');
		matches[3].setText(1, "2nd Place");
	}

	private void create2Pairs8() {
		char[] teams1p = new char[League.groupsCount];
		System.arraycopy(groups, 0, teams1p, 0, League.groupsCount);
		teams1p = createRandArray(teams1p);

		char[] teams2p = new char[League.groupsCount];
		teams2p = createTurnedArray(teams1p);

		// Match 1
		matches[0].setTeam(0, teams2p[0], '2');
		matches[0].setTeam(1, teams2p[1], '2');

		// Match 2
		matches[1].setTeam(0, teams1p[0], '1');
		matches[1].setText(1, "2nd Place");

		// Match 3
		matches[2].setTeam(0, teams1p[1], '1');
		matches[2].setTeam(1, teams2p[2], '2');

		// Match 4
		matches[3].setTeam(0, teams1p[2], '1');
		matches[3].setText(1, "2nd Place");
	}

	private char[] createRandArray(char[] chars) {

		ArrayList<Character> charsList = new ArrayList<>();
		ArrayList<Character> newChars = new ArrayList<>();
		for (char ch : chars)
			charsList.add(ch);

		while (charsList.size() != 0) {
			int rand = new Random().nextInt(charsList.size());
			newChars.add(charsList.get(rand));
			charsList.remove(rand);
		}

		char[] finalArray = new char[chars.length];
		Object[] objArray = newChars.toArray();

		for (int i = 0; i < objArray.length; i++) {
			finalArray[i] = (char) objArray[i];
		}
		return finalArray;
	}

	private char[] createTurnedArray(char[] chars) {

		char[] newArray = new char[chars.length];
		int length = chars.length;

		for (int i = 0; i < length; i++) {
			newArray[i] = chars[length - i - 1];
		}
		return newArray;
	}

	private void sortArray(char[] array) {
		int length = array.length;

		for (int i = 1; i < length; i++) {
			for (int j = length - 1; j >= i; j--) {
				if (array[j] < array[j - 1]) {

					char buffer = array[j];
					array[j] = array[j - 1];
					array[j - 1] = buffer;
				}
			}
		}
	}
}
