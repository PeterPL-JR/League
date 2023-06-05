package com.peterpl.league.game.rounds;

import java.awt.*;

import javax.swing.*;

import com.peterpl.league.*;
import com.peterpl.league.game.gui.*;
import com.peterpl.league.game.gui.RankingTable.*;
import com.peterpl.league.methods.*;

public class FinalRanking extends GamePanel {
	private static final long serialVersionUID = 1L;

	public FinalRankingTable table;
	private JPanel panel;
	private JScrollPane scroll;
	
	public FinalRanking() {
		super("Ranking", Header.Basic + 10, GameFrame.Width, GameFrame.Height);
		
		leftEvent = () -> {
			League.gameFrame.setPanel(League.finalMatch);
		};
		rightArrow.setVisible(false);
		
		table = new FinalRankingTable();
		panel = new JPanel();
		scroll = new JScrollPane(panel);
		
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(table.getWidth(), table.getHeight()));
		panel.add(table);

		scroll.setBounds(width / 2 - table.getWidth() / 2, 140, table.getWidth() + 17, 606);
		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(10);
		add(scroll);
		
		table.thirdPlacesMatch(League.thirdPlace);
		
		addTableDivide(4);
		
		if(League.teamsInKnockout == 16) {
			addTableDivide(8);
			addTableDivide(16);
		}
		if(League.teamsInKnockout == 8) {
			addTableDivide(8);
		}
	}
	
	public void addTableDivide(int index) {
		for(int i = index; i < table.getTableSize(); i++) {
			RankingTablePart part = table.getRankingPart(i);
			part.setLocation(part.getX(), part.getY() + 20);
			part.repaint();
		}
		table.setSize(table.getWidth(), table.getHeight() + 20);
		panel.setPreferredSize(new Dimension(table.getWidth(), table.getHeight()));
	}
}
