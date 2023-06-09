package com.peterpl.league.game.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import com.peterpl.league.*;
import com.peterpl.league.files.*;

public class FinalRankingTable extends RankingTable {
	private static final long serialVersionUID = 1L;
	
	private JLabel thirdPlaces;
	
	public FinalRankingTable() {
		super(League.teamsCount);

		rankingParts[0].place.setText("");
		rankingParts[1].place.setText("");
		
		rankingParts[0].place.setIcon(GraphicsImage.placeGold);
		rankingParts[1].place.setIcon(GraphicsImage.placeSilver);
		
		setRowColor(0, gold);
		setRowColor(1, silver);
		setRowColor(2, brown);
		
		thirdPlaces = new JLabel();
		thirdPlaces.setIcon(GraphicsImage.placeBrown);
		thirdPlaces.setHorizontalAlignment(SwingConstants.CENTER);
		thirdPlaces.setOpaque(true);
		thirdPlaces.setBackground(brown);
		thirdPlaces.setBounds(0, 157, 58, 52);
		thirdPlaces.setBorder(new LineBorder(Color.black));
		add(thirdPlaces);
		
		thirdPlacesMatch(false);
		
		for(JPanel part : parts)
			add(part);
	}
	
	public void thirdPlacesMatch(boolean bool) {
		if(bool) {
			thirdPlaces.setBounds(0, 147, 58, 50);
			setRowColor(3, normal);
		} else {
			thirdPlaces.setBounds(0, 147, 58, 99);
			setRowColor(3, brown);
		}
	}

	public RankingTablePart getRankingPart(int index) {
		return rankingParts[index];
	}
	public int getTableSize() {
		return rankingParts.length;
	}
}
