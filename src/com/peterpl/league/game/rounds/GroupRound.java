package com.peterpl.league.game.rounds;

import com.peterpl.league.*;
import com.peterpl.league.game.gui.*;
import com.peterpl.league.methods.*;

public class GroupRound extends GamePanel {
	private static final long serialVersionUID = 1L;
	private final int groupPosX = 120;
	private final int matchesPosX = 400;
	
	public RankingTable group;
	public MatchesTable matchesTable;

	public GroupRound(String name) {
		super("Group " + name, Header.Basic, GameFrame.Width, GameFrame.Height);
		create();
	}
	
	public GroupRound(Team[] teams, Match[] matches, String name) {
		super("Group " + name, Header.Basic, GameFrame.Width, GameFrame.Height);
		create();
		
		for(int i = 0; i < 4; i++) {
			group.setTeam(i, teams[i]);
		}
		for(int i = 0; i < 6; i++) {
			matchesTable.setMatches(matches);
		}
	}
	
	private void create() {
		group = new RankingTable(4);
		group.setLocation(Methods.centerX(group, this), groupPosX);
		add(group);
		
		matchesTable = new MatchesTable(this);
		matchesTable.setLocation(Methods.centerX(matchesTable, this), matchesPosX);
		add(matchesTable);
		
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
