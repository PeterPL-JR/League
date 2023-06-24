package com.peterpl.league.game.rounds;

import java.io.*;

import com.peterpl.league.*;
import com.peterpl.league.game.*;
import com.peterpl.league.game.gui.*;
import com.peterpl.league.methods.*;

public class GroupsMenu extends GamePanel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public TableSetup[] groups;
	public Buttons draw;
	
	public static final int TableWidth = 200;
	public static final int TableHeight = 25;

	public GroupsMenu(int width, int height) {
		super("Groups", Header.Basic, width, height);

		groups = new TableSetup[League.groupsCount];
		for (int i = 0; i < groups.length; i++) {
			groups[i] = new TableSetup("Group " + ((char) ('A' + i)), League.teamsInGroup, League.flagsMode, TableWidth, TableHeight);
			add(groups[i]);
		}
		
		leftArrow.setLocation(25, leftArrow.getY());
		rightArrow.setVisible(false);
		
		button.setAction(this::buttonEvent);
		button.setVisible(true);

		draw = new Buttons("Draw");
		draw.setLocation(button.getX(), button.getY());
		draw.setAction(this::drawEvent);
		draw.setVisible(false);
		add(draw);
		
		leftEvent = () -> { League.setupFrame.setPots(); };
		rightEvent = () -> {};
		
		for (int i = 0; i < League.groupsCount; i++) {
			this.groups[i].setTextEvents(this);
		}
	}

	private void buttonEvent() {
		
		// Create Frames & Panels
		League.gameFrame = new GameFrame();
		League.gameFrame.setTitle(League.leagueName);
		
		League.createGame.createGroupsRound();
		League.groupsHandler = new GroupsHandler(League.groupRound, League.allTeams);
		League.createGame.createKnockoutRound();
		
		String[][] teamsNames = new String[League.groupsCount][League.teamsInGroup];
		String[] allTeams = new String[League.teamsInGroup * League.groupsCount];
		
		for (int i = 0; i < League.groupsCount; i++) {
			String[] groupNames = groups[i].getText();
			for (int j = 0; j < League.teamsInGroup; j++) {
				
				teamsNames[i][j] = groupNames[j];
				if(Methods.isStringEmpty(teamsNames[i][j])) return;
			}
		}
		
		for(int i = 0; i < League.teamsInGroup; i++) {
			for(int j = 0; j < League.groupsCount; j++) {
				allTeams[i + j * League.teamsInGroup] = teamsNames[j][i];
			}
		}

		if(!Methods.isArrayUnique(allTeams))
			return;

		int[][] potsNumbers = new int[League.groupsCount][League.teamsInGroup];
		if(League.potsMode) {
			for(int i = 0; i < League.groupsCount; i++) {
				for(int j = 0; j < League.teamsInGroup; j++) {
					String team = League.groupsTeamsNames[i][j];
					

					for(int p = 0; p < League.teamsInGroup; p++) {
						String[] potArray = League.potsTeamsNames[p];
						if(Methods.indexOf(potArray, team) != -1) {
							potsNumbers[i][j] = p;
							break;
						}
					}
				}
			}
		}

		for (int i = 0; i < League.groupsCount; i++) {
			for (int j = 0; j < League.teamsInGroup; j++) {
				int pot = League.potsMode ? potsNumbers[i][j] : 0;

				Team team = new Team(teamsNames[i][j], i, pot);
				
				League.allTeams[i][j] = team;
				League.groupRound[i].group.setTeam(j, team);
			}
			League.groupRound[i].matchesTable.setMatches(League.allTeams[i], i);
		}
		
		League.setupFrame.dispose();
		League.gameFrame.setVisible(true);
		League.gameFrame.setGroup(0);
		
		League.groupsHandler.startRound();
		League.createKnockout.createPairs();
		
		League.groupRound[League.groupsCount - 1].rightArrow.setVisible(true);
	}

	private void drawEvent() {	
		League.drawing.draw(League.potsTeamsNames, League.groupsTeamsNames);

		for (int i = 0; i < League.allTeams.length; i++)
			for (int j = 0; j < League.allTeams[i].length; j++)
				groups[i].setText(League.groupsTeamsNames[i]);

		draw.setVisible(false);
		button.setVisible(true);
	}
}
