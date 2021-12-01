package com.peterpl.league.game;

import java.io.*;

import java.awt.event.*;

import com.peterpl.league.*;
import com.peterpl.league.game.rounds.*;
import com.peterpl.league.methods.*;

public class GroupsHandler implements Serializable {
	private static final long serialVersionUID = 1L;
	public Knockout knockoutRound;

	private int round = 0;
	private int group = 0;
	private int match = 0;
	private int matchRound = 0;
	private boolean finished = false;

	private GroupRound[] groups;
	private Team[][] teams;
	private int[][] orderTables;

	private Team[] thirdTeams;
	private Team[] secondTeams;

	public GroupsHandler(GroupRound[] groups, Team[][] teams) {
		this.groups = groups;
		this.teams = teams;

		orderTables = new int[League.groupsCount][League.teamsInGroup];
		for (int i = 0; i < League.groupsCount; i++) {
			orderTables[i] = new int[League.teamsInGroup];
			for (int j = 0; j < League.teamsInGroup; j++) {
				orderTables[i][j] = j;
			}
		}
	}

	public void startRound() {

		for (int i = 0; i < groups.length; i++) {
			groups[i].button.setAction(this::endMatch);
		}
		playMatch();
	}

	public void endRound() {
		finished = true;
		if (League.extraTable) {
			
			League.groupRound[League.groupsCount - 1].rightArrow.requestFocusInWindow();
			League.groupRound[League.groupsCount - 1].rightEvent = () -> {
				
				League.gameFrame.setPanel(League.extraPlacesTable);
				League.gameFrame.setPanelNext(League.extraPlacesTable);
			};
			
			League.extraTeams = new Team[League.groupsCount];
			for (int i = 0; i < League.groupsCount; i++) {
				League.extraTeams[i] = new Team(League.extraPlacesTable.table.getTeam(i));
			}
		} else {
			League.groupRound[League.groupsCount - 1].rightArrow.requestFocusInWindow();
			League.groupRound[League.groupsCount - 1].rightEvent = () -> {
				
				League.gameFrame.setPanel(knockoutRound);
				knockoutRound.setMatchFocus(0);
			};
		}

		for (int i = 0; i < League.groupsCount; i++) {
			League.groupsTeams[i] = League.groupRound[i].getTeams();
		}
		knockoutRound.startRound();
	}

	private void playMatch() {
		groups[group].matchesTable.playMatch(matchRound);
		groups[group].button.setVisible(true);
	}

	private void scoreMatch() {

		int score1 = groups[group].matchesTable.getScore1(matchRound);
		int score2 = groups[group].matchesTable.getScore2(matchRound);

		int team1 = groups[group].matchesTable.getMatchTeam1(matchRound);
		int team2 = groups[group].matchesTable.getMatchTeam2(matchRound);

		teams[group][team1].playMatch(score1, score2);
		teams[group][team2].playMatch(score2, score1);

		League.groupsMatches[group][matchRound].play(score1, score2);

		orderTables[group] = League.handlingGroups.sort(groups[group].group, teams[group]);

		/*
		if (League.gameMode == "Teams24")
			thirdTeams = League.handlingGroups.extraTeams(League.extraPlacesTable.table, groups, teams, orderTables, 2);

		if (League.gameMode == "Teams12")
			secondTeams = League.handlingGroups.extraTeams(League.extraPlacesTable.table, groups, teams, orderTables, 1);
		*/
	}

	// TO PRIVATE
	public void endMatch() {
		try {
			scoreMatch();
		} catch (NumberFormatException e) {
			return;
		}

		groups[group].matchesTable.endMatch(matchRound);
		groups[group].rightArrow.requestFocusInWindow();
		groups[group].button.setVisible(false);

		match++;
		matchRound = match + round * 2;

		if (match > 1 && round >= 2) {
			League.createKnockout.setGroup(group, teams, orderTables);
		}

		// THE BUG WERE HERE
		if (group >= League.groupsCount - 1 && match > 1 && round >= 2) {
			//if (League.gameMode == "Teams24");
				//League.createKnockout.setThirdPlaces(thirdTeams);

			//if (League.gameMode == "Teams12") {
				//League.createKnockout.setSecondPlaces(secondTeams);
				//League.createKnockout.setFirstPlaces(teams, orderTables);
			//}

			endRound();
			return;
		}

		if (group >= League.groupsCount - 1 && match > 1) {
			match = 0;
			group = 0;
			round++;
			matchRound = match + round * 2;
			
			League.gameFrame.requestFocusInWindow();
			League.gameFrame.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						League.gameFrame.setGroup(0);
						League.gameFrame.removeKeyListener(this);
					}
				}
			});
		}

		if (match > 1) {
			match = 0;
			group++;
			matchRound = match + round * 2;
		}

		playMatch();
	}
	
	public int getRound() {
		return round;
	}
	
	public int getGroup() {
		return group;
	}
	
	public int getMatch() {
		return match;
	}
	
	public int getMatchRound() {
		return matchRound;
	}
	
	public boolean isFinished() {
		return finished;
	}
}
