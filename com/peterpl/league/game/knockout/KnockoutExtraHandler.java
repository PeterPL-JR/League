package com.peterpl.league.game.knockout;

import java.util.*;

import com.peterpl.league.*;
import com.peterpl.league.game.gui.*;

public class KnockoutExtraHandler {

	private KnockoutMatch[] matches;
	private Team[] teams;

	public KnockoutExtraHandler(KnockoutMatch[] matches, Team[] qualifiedTeams) {
		this.matches = matches;
		this.teams = qualifiedTeams;
	}

	public void setTeams16() {
		teams = Team.sortByGroup(teams, 1);

		Team[] firstTeams = { 
			matches[1].getTeam(0), 
			matches[3].getTeam(0),

			matches[5].getTeam(0), 
			matches[7].getTeam(0) 
		};
		int[] indexes = {
			1,3,5,7
		};

		for (int i = 0; i < teams.length; i++) {
			if (firstTeams[i].group == teams[i].group) {
				int otherIndex = (i == teams.length - 1) ? -1 : 1;

				Team buffer = teams[i + otherIndex];
				teams[i + otherIndex] = teams[i];
				teams[i] = buffer;
			}
		}
		
		for(int i = 0; i < teams.length; i++)
			matches[indexes[i]].setTeam(1, teams[i]);
	}

	public void setTeams8() {
		Team fTeam1 = matches[1].getTeam(0);
		Team fTeam2 = matches[3].getTeam(0);

		if (teams[0].group > teams[1].group) {
			Team buffer = teams[0];
			teams[0] = teams[1];
			teams[1] = buffer;
		}

		if ((fTeam1.group == teams[0].group) || (fTeam2.group == teams[1].group)) {
			matches[1].setTeam(1, teams[1]);
			matches[3].setTeam(1, teams[0]);
		} else {
			matches[1].setTeam(1, teams[0]);
			matches[3].setTeam(1, teams[1]);
		}
	}

	public void setTeams4() {
		ArrayList<Team> firstTeams = new ArrayList<>();

		for (int i = 0; i < League.groupsTeams.length; i++) {
			if (i == matches[0].getTeam(0).group)
				continue;
			firstTeams.add(League.groupsTeams[i][0]);
		}

		Team team = teams[0];
		matches[1].setTeam(1, team);

		if (team.group == firstTeams.get(1).group) {
			matches[0].setTeam(1, firstTeams.get(1));
			matches[1].setTeam(0, firstTeams.get(0));
		} else {
			matches[0].setTeam(1, firstTeams.get(0));
			matches[1].setTeam(0, firstTeams.get(1));
		}
	}
}
