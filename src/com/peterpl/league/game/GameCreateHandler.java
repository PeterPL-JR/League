package com.peterpl.league.game;

import static com.peterpl.league.League.*;

import com.peterpl.league.*;
import com.peterpl.league.game.gui.*;
import com.peterpl.league.game.knockout.*;
import com.peterpl.league.game.rounds.*;
import com.peterpl.league.methods.*;

public class GameCreateHandler {
	private CreateFrame frame;

	private String leagueName;
	private String[] hostNames;

	private int leagueModeInt;
	private int knockTeamsInt;

	private int teamsModeInt;
	
	public GameCreateHandler(CreateFrame frame) {
		this.frame = frame;
	}

	public boolean getLeagueValues() {

		// League Name
		leagueName = frame.getNameText();
		if (Methods.isStringEmpty(leagueName))
			return false;

		// Hosts Names
		int hostsCount = frame.hostCreate.getHostSelect();
		hostNames = new String[hostsCount];

		String[] names = frame.hostCreate.getHostNames();
		for (int i = 0; i < hostsCount; i++) {
			hostNames[i] = names[i];
			if (Methods.isStringEmpty(hostNames[i]))
				return false;
		}

		if (hostsCount == 2 && hostNames[0].equals(hostNames[1]))
			return false;

		// League Modes
		leagueModeInt = frame.modeCreate.getModeSelect();
		knockTeamsInt = frame.modeCreate.getKnockSelect();

		// Teams Modes
		teamsModeInt = frame.optionsCreate.getTeamsSelect();
		League.thirdPlace = frame.optionsCreate.getThirdPlacesSelect();
		return true;
	}

	public void createGameValues() {

		int[] teamsCountArray = { 32, 24, 16, 12 };
		int[] groupsCountArray = { 8, 6, 4, 3 };
		int[] knockoutTeamsArray = { 16, 8, 4 };

		// League Main Variables
		League.leagueName = leagueName;
		League.gameMode = "Teams" + teamsCountArray[leagueModeInt];
		League.gameModeInt = leagueModeInt;

		// Teams & Groups Variables
		League.teamsCount = teamsCountArray[leagueModeInt];
		League.groupsCount = groupsCountArray[leagueModeInt];
		League.teamsInKnockout = knockoutTeamsArray[knockTeamsInt + (leagueModeInt >= 2 ? 1 : 0)];
		League.teamsInGroup = League.teamsCount / League.groupsCount;
		League.hostsCount = hostNames.length;

		// Other Variables
		int knockoutRounds = 0;
		for (int i = 2; i <= League.teamsInKnockout; i *= 2)
			knockoutRounds++;
		if (League.thirdPlace)
			knockoutRounds++;

		League.knockoutRounds = knockoutRounds;
		League.flagsMode = (teamsModeInt == 0) ? false : true;
	}

	public void createSetupRound() {
		int setupWidth = 0, setupHeight = 0;

		// Set Width & Height of Frame
		if (gameMode.equals("Teams32") || gameMode.equals("Teams24")) {
			setupWidth = SetupFrame.Width1;
			setupHeight = SetupFrame.Height;
		}
		if (gameMode.equals("Teams16") || gameMode.equals("Teams12")) {
			setupWidth = SetupFrame.Width2;
			setupHeight = SetupFrame.Height;
		}

		// Create Frame & Panels
		pots = new PotsMenu(setupWidth, setupHeight);
		groups = new GroupsMenu(setupWidth, setupHeight);

		setupFrame = new SetupFrame(setupWidth, setupHeight);
		setupFrame.setTitle(League.title + " (" + League.leagueName + ")");
		setupFrame.setPots();

		// Handle Tables on the Panels
		if (gameMode.equals("Teams32") || gameMode.equals("Teams24")) {
			Methods.setTablesPos(pots.pots, 0);
			Methods.setTablesPos(groups.groups, 1);
		}

		if (gameMode.equals("Teams16") || gameMode.equals("Teams12")) {
			Methods.setTablesPos(pots.pots, 1);
		}

		if (gameMode.equals("Teams16"))
			Methods.setTablesPos(groups.groups, 1);

		if (gameMode.equals("Teams12"))
			Methods.setTablesPos(groups.groups, 0);

		// Create Host in Tables
		for (int i = 0; i < League.hostsCount; i++) {
			String hostName = hostNames[i];

			League.pots.pots[0].setHost(i);
			League.pots.pots[0].setText(hostName, i);

			League.groups.groups[i * (League.groupsCount / 2)].setHost(0);
			League.groups.groups[i * (League.groupsCount / 2)].setText(hostName, 0);
		}
		League.initGroupsRound();
	}

	public void createGroupsRound() {

		League.groupRound = new GroupRound[groupsCount];
		for (int i = 0; i < League.groupRound.length; i++)
			League.groupRound[i] = new GroupRound((char) ('A' + i) + "");
		League.groupRound[0].leftArrow.setVisible(false);

		// Create Colors of Qualified Teams
		int qualified = teamsInKnockout / groupsCount;
		int extraQualified = teamsInKnockout - (qualified * groupsCount);

		League.groupsTeamsQualified = qualified;
		League.extraTeamsQualified = extraQualified;
		League.extraTable = (extraQualified == 0) ? false : true;

		for (int i = 0; i < groupsCount; i++) {
			for (int j = 0; j < teamsInGroup; j++)
				groupRound[i].group.setRowColor(j, RankingTable.red);

			for (int j = 0; j < qualified; j++) {
				groupRound[i].group.setRowColor(j, RankingTable.green);
			}
			if (extraQualified != 0)
				groupRound[i].group.setRowColor(qualified, RankingTable.yellow);
		}
	}

	public void createKnockoutRound() {
		League.initKnockoutRound();

		// Create Extra Teams Table
		if (League.extraTable) {

			extraPlacesTable = new ExtraPlaces();
			extraPlacesTable.setQualifiedTeams(extraTeamsQualified);

			groupRound[groupsCount - 1].rightEvent = () -> {
				gameFrame.setPanel(extraPlacesTable);
				extraPlacesTable.rightArrow.requestFocusInWindow();
			};
			extraPlacesTable.leftEvent = () -> {
				gameFrame.setGroup(groupsCount - 1);
			};
		}

		// Knock-out Rounds
		Knockout[] allKnockoutRounds = { finalMatch, semiFinals, quarterFinals, secondRound };
		Knockout[] thirdKnockoutRounds = { finalMatch, thirdPlaceMatch, semiFinals, quarterFinals, secondRound };
		League.allKnockoutRounds = allKnockoutRounds;

		for (int i = 0; i < knockoutRounds; i++)
			knockout[i] = (League.thirdPlace) ? thirdKnockoutRounds[i] : allKnockoutRounds[i];

		for (int i = 1; i < knockoutRounds; i++) {
			knockout[i].setNextRound(knockout[i - 1]);
		}

		League.groupsHandler.knockoutRound = knockout[knockoutRounds - 1];
		League.createKnockout = new KnockoutCreateHandler(knockout[knockoutRounds - 1]);

		// Event on Frame
		for (int i = 0; i < knockoutRounds; i++) {
			int previousIndex = i - 1;
			int nextIndex = i + 1;

			if (previousIndex >= 0)
				knockout[i].rightEvent = () -> {
					gameFrame.setKnockout(previousIndex);
				};
			if (nextIndex < knockoutRounds)
				knockout[i].leftEvent = () -> {
					gameFrame.setKnockout(nextIndex);
				};
		}

		GamePanel endGroupPanel = extraTable ? extraPlacesTable : groupRound[groupsCount - 1];
		GamePanel firstKnockPanel = knockout[knockout.length - 1];

		endGroupPanel.rightEvent = () -> {
			gameFrame.setKnockout(knockout.length - 1);
		};
		firstKnockPanel.leftEvent = () -> {
			gameFrame.setPanel(endGroupPanel);
		};
	}
}
