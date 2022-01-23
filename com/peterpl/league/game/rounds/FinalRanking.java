package com.peterpl.league.game.rounds;

import java.awt.*;

import javax.swing.*;

import com.peterpl.league.*;
import com.peterpl.league.game.gui.*;

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
		
		addTableDivide(4);
	}
	
	public void addTableDivide(int index) {
		for(int i = index; i < table.parts.length; i++) {
			table.parts[i].setLocation(table.parts[i].getX(), table.parts[i].getY() + 20);
		}
		table.setSize(table.getWidth(), table.getHeight() + 20);
		panel.setPreferredSize(new Dimension(table.getWidth(), table.getHeight()));
	}
}
