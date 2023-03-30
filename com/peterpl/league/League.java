package com.peterpl.league;

import java.awt.*;

import com.peterpl.league.files.*;
import com.peterpl.league.game.*;
import com.peterpl.league.game.knockout.*;
import com.peterpl.league.game.rounds.*;

public class League {
	public static final String title = "League";
	
	public static String leagueName;
	public static String gameMode;
	public static int gameModeInt;
	
	public static int hostsCount;
	public static boolean thirdPlace;
	public static boolean extraTable;
	public static boolean flagsMode;

	public static MenuFrame menuFrame = new MenuFrame();
	public static CreateFrame createFrame = new CreateFrame();
	public static SetupFrame setupFrame;
	public static GameFrame gameFrame;

	public static int teamsCount;
	public static int groupsCount;
	public static int teamsInKnockout;
	public static int teamsInGroup;
	public static int knockoutRounds;
	public static int groupsTeamsQualified;
	public static int extraTeamsQualified;

	public static PotsMenu pots;
	public static GroupsMenu groups;

	public static GroupRound[] groupRound;
	public static Knockout[] allKnockoutRounds;
	public static Knockout[] knockout;
	public static ExtraPlaces extraPlacesTable;

	public static SecondRound secondRound;
	public static QuarterFinals quarterFinals;
	public static SemiFinals semiFinals;
	public static ThirdPlaceMatch thirdPlaceMatch;
	public static Final finalMatch;
	public static FinalRanking ranking;

	public static int[][] teamsPotsNumbers;
	public static String[][] potsTeamsNames;
	public static String[][] groupsTeamsNames;

	public static Match[][] groupsMatches;
	public static Match[][] knockoutMatches;

	public static Team[][] groupsTeams;
	public static Team[][] allTeams;
	public static Team[] advancesTeams;
	public static Team[] extraTeams;

	public static GameCreateHandler createGame = new GameCreateHandler(createFrame);
	public static DrawHandler drawing = new DrawHandler();
	public static GroupsHandler groupsHandler;
	public static TableHandler handlingGroups = new TableHandler();
	public static ScoresDrawHandler drawingScores = new ScoresDrawHandler();
	public static KnockoutCreateHandler createKnockout;
	
	public static LeagueFileHandler leagueFile = new LeagueFileHandler();
	public static CountriesFileHandler countriesFile = new CountriesFileHandler();
	public static FilesHandler filesHandler = new FilesHandler();

	public static void initGroupsRound() {
		
		// Tables of Teams Names
		teamsPotsNumbers = new int[groupsCount][teamsInGroup];
		potsTeamsNames = new String[teamsInGroup][groupsCount];
		groupsTeamsNames = new String[groupsCount][teamsInGroup];
		
		// Tables of "Team" Objects
		groupsTeams = new Team[groupsCount][];
		allTeams = new Team[groupsCount][teamsInGroup];
		advancesTeams = new Team[teamsCount];
		groupsMatches = new Match[groupsCount][6];
	}

	public static void initKnockoutRound() {
		
		secondRound = new SecondRound();
		quarterFinals = new QuarterFinals();
		semiFinals = new SemiFinals();
		thirdPlaceMatch = new ThirdPlaceMatch();
		finalMatch = new Final();
		
		knockout = new Knockout[knockoutRounds];
		knockoutMatches = new Match[knockoutRounds][];
		ranking = new FinalRanking();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				FlagsSheet.loadFlagsSheets();
				menuFrame.setVisible(true);

				/*
				menuFrame.startEvent();
				createFrame.optionsCreate.setNamesMode(1);
				
				createFrame.modeCreate.setTeamsMode(1);
				createFrame.modeCreate.setKnockoutMode(0);
				createFrame.buttonEvent();
				pots.buttonEvent();
				groups.drawEvent();
				groups.buttonEvent();
				
				League.drawingScores.createScores(groupRound);
				League.drawingScores.createScores(secondRound);
				League.gameFrame.setContentPane(quarterFinals);
				//*/
				
				/*
				
				League.gameFrame.setContentPane(knockout[knockout.length - 2]);
				/*
				*/
			}
		});
	}
}
