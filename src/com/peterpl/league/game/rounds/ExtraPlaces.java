package com.peterpl.league.game.rounds;


import com.peterpl.league.*;
import com.peterpl.league.game.gui.*;
import com.peterpl.league.methods.*;

public class ExtraPlaces extends GamePanel {
	private static final long serialVersionUID = 1L;

	public RankingTable table;
	
	public ExtraPlaces() {
		super(createExtraName(), Header.Basic, GameFrame.Width, GameFrame.Height);
		
		table = new RankingTable(League.groupsCount);
		table.setLocation(Game.centerX(table, this), 120);
		table.setSize(table.getWidth(), League.groupsCount * 49 + 50);
		add(table);

		for(int i = 0; i < League.groupsCount; i++)
			table.setRowColor(i, RankingTable.red);
		
		leftEvent = () -> { League.gameFrame.setGroup(League.groupsCount - 1); };
		rightEvent = () -> {};
	}
	
	public void setQualifiedTeams(int teams) {
		for(int i = 0; i < teams; i++)
			table.setRowColor(i, RankingTable.yellow);
	}
	
	private static String createExtraName() {
		int extraPlace = (League.teamsInKnockout - League.extraTeamsQualified) / League.groupsCount + 1;
		String sign = "st";
		if(extraPlace == 2) sign = "nd";
		if(extraPlace == 3) sign = "rd";
		
		return extraPlace + sign + " Places";
	}
}
