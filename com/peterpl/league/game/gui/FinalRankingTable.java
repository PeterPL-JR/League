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

		//parts[0].place.setText(null);
		//parts[1].place.setText(null);
		
		//parts[0].place.setIcon(GraphicsImage.placeGold);
		//parts[1].place.setIcon(GraphicsImage.placeSilver);
		
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
			thirdPlaces.setBounds(0, 157, 58, 52);
			setRowColor(3, normal);
		} else {
			thirdPlaces.setBounds(0, 157, 58, 103);
			setRowColor(3, brown);
		}
	}
}
