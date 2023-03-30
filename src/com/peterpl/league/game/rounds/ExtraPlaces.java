package com.peterpl.league.game.rounds;

import com.peterpl.league.*;
import com.peterpl.league.game.gui.*;
import com.peterpl.league.methods.*;

public class ExtraPlaces extends GamePanel {
	private static final long serialVersionUID = 1L;

	public RankingTable table;
	
	public ExtraPlaces() {
		super("3rd Places", Header.Basic, GameFrame.Width, GameFrame.Height);
		
		table = new RankingTable(League.groupsCount);
		table.setLocation(Game.centerX(table, this), 120);
		table.setSize(table.getWidth(), League.groupsCount * 55 + 56);
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
}
