package com.peterpl.league.game.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import com.peterpl.league.*;

public class FinalRankingTable extends RankingTable {
	private static final long serialVersionUID = 1L;
	
	private JLabel thirdPlaces;
	
	public FinalRankingTable() {
		super(League.teamsCount);
		setPartsHeight(52);

		parts[0].place.setText(null);
		parts[1].place.setText(null);
		
		parts[0].place.setIcon(Images.placeGold);
		parts[1].place.setIcon(Images.placeSilver);
		
		setColor(0, gold);
		setColor(1, silver);
		setColor(2, brown);
		
		thirdPlaces = new JLabel();
		thirdPlaces.setIcon(Images.placeBrown);
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
			setColor(3, normal);
		} else {
			thirdPlaces.setBounds(0, 157, 58, 103);
			setColor(3, brown);
		}
	}
}
