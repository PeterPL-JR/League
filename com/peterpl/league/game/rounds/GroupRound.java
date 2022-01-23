package com.peterpl.league.game.rounds;

import com.peterpl.league.*;
import com.peterpl.league.game.gui.*;

public class GroupRound extends GamePanel {
	private static final long serialVersionUID = 1L;
	
	public RankingTable group;
	public MatchesTable matchesTable;

	public GroupRound(String name) {
		super("Group " + name, Header.Basic, GameFrame.Width, GameFrame.Height);
		
		group = new RankingTable(4);
		group.setLocation(111, 120);
		add(group);

		matchesTable = new MatchesTable(this);
		matchesTable.setLocation(111, 420);
		add(matchesTable);
		
		leftEvent = () -> { League.gameFrame.setGroup(League.gameFrame.activeGroup - 1); };
		rightEvent = () -> { League.gameFrame.setGroup(League.gameFrame.activeGroup + 1); };
	}
	
	public GroupRound(Team[] teams, Match[] matches, String name) {
		super("Group " + name, Header.Basic, GameFrame.Width, GameFrame.Height);
		
		group = new RankingTable(4);
		group.setLocation(111, 120);
		add(group);
		
		for(int i = 0; i < 4; i++) {
			group.setTeam(i, teams[i]);
		}
		
		matchesTable = new MatchesTable(this);
		matchesTable.setLocation(111, 420);
		add(matchesTable);
		
		for(int i = 0; i < 6; i++) {
			matchesTable.setMatches(matches);
		}
		
		leftEvent = () -> { League.gameFrame.setGroup(League.gameFrame.activeGroup - 1); };
		rightEvent = () -> { League.gameFrame.setGroup(League.gameFrame.activeGroup + 1); };
	}
	
	public Team[] getTeams() {
		
		Team[] oldTeams = group.getTeams();
		Team[] teams = new Team[4];
		
		for(int i = 0; i < teams.length; i++) {
			teams[i] = new Team(oldTeams[i]);
		}
		
		return teams;
	}
}
