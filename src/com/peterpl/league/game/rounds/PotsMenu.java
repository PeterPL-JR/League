package com.peterpl.league.game.rounds;

import java.io.*;

import com.peterpl.league.*;
import com.peterpl.league.game.gui.*;
import com.peterpl.league.methods.*;

public class PotsMenu extends GamePanel implements Serializable {
	private static final long serialVersionUID = 1L;
	public TableSetup[] pots;
	
	public static final int TableWidth = 200;
	public static final int TableHeight = 25;

	public PotsMenu(int width, int height) {
		super("Teams", Header.Basic, width, height);
		League.potsMode = false;

		pots = new TableSetup[League.teamsInGroup];
		for (int i = 0; i < pots.length; i++) {
			pots[i] = new TableSetup("Pot " + (i + 1), League.groupsCount, League.flagsMode, TableWidth, TableHeight);
			add(pots[i]);
		}

		leftArrow.setVisible(false);

		button.setVisible(true);
		button.setAction(this::buttonEvent);

		leftEvent = () -> {
		};
		rightEvent = () -> {
			League.setupFrame.setGroups();
		};
	}

	private void buttonEvent() {
		League.potsMode = true;

		String[][] teamsNames = new String[League.teamsInGroup][League.groupsCount];
		String[] allTeams = new String[League.teamsInGroup * League.groupsCount];

		for (int i = 0; i < League.teamsInGroup; i++) {
			String[] potNames = pots[i].getText();
			for (int j = 0; j < League.groupsCount; j++) {
				
				teamsNames[i][j] = potNames[j];
				if (Methods.isStringEmpty(teamsNames[i][j]))
					return;
			}
		}

		for (int i = 0; i < League.teamsInGroup; i++) {
			for (int j = 0; j < League.groupsCount; j++) {
				allTeams[i + j * League.teamsInGroup] = teamsNames[i][j];
			}
		}

		if (!Methods.isArrayUnique(allTeams))
			return;
		
		if(League.flagsMode) {
			for(TableSetup table : pots) {
				for(FlagTextField flag : table.flagsTeams) {
					if(!flag.isFlag()) {
						return;
					}
				}
			}
		}

		for (int i = 0; i < League.teamsInGroup; i++)
			for (int j = 0; j < League.groupsCount; j++)
				League.potsTeamsNames[i][j] = teamsNames[i][j];

		for (int i = 0; i < League.teamsInGroup; i++)
			for (int j = 0; j < League.groupsCount; j++)
				pots[i].setEnabledAll(false);

		for (int i = 0; i < League.groupsCount; i++)
			for (int j = 0; j < League.teamsInGroup; j++)
				League.groups.groups[i].setEnabledAll(false);

		button.setVisible(false);
		League.pots.button.setVisible(false);
		League.groups.draw.setVisible(true);
		League.groups.button.setVisible(false);
	}
}
