package com.peterpl.league.game.gui;

import java.util.*;
import java.awt.event.*;

import javax.swing.*;

import com.peterpl.league.*;
import com.peterpl.league.files.*;
import com.peterpl.league.methods.*;

public class FlagTextField extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final int FlagWidth = 40;
	public static final int FlagHeight = 27;
	
	public static ArrayList<FlagTeam> flagTeams = FlagTeam.getAllScaledFlags(FlagWidth, FlagHeight, false);
	private FlagTeam flagTeam;
	private JLabel flagLabel;
	public FlagText text;
	private boolean isFlag = false;
	
	public FlagTextField(int textWidth, int textHeight) {
		flagTeam = null;
		
		setSize(textWidth, textHeight);
		setLayout(null);
		setOpaque(false);
		
		// Flag Label
		flagLabel = new JLabel();
		flagLabel.setSize(FlagWidth, FlagHeight);
		flagLabel.setLocation(0, Game.centerY(flagLabel, this));
		flagLabel.setIcon(GraphicsImage.defaultFlag);
		add(flagLabel);
		
		// Text Label
		text = new FlagText();
		text.setSize(textWidth - FlagWidth - 10, textHeight);
		text.setLocation(FlagWidth + 10, 0);
		add(text);
	}
	
	public boolean isFlag() {
		return isFlag;
	}
	
	protected class FlagText extends JTextField {
		private static final long serialVersionUID = 1L;
		
		public FlagText() {
			addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent e) {
					isFlag = FlagTeam.findCountry(text.getText(), flagTeam, flagLabel, null, flagTeams);
				}
			});
			addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent e) {
					isFlag = FlagTeam.findCountry(text.getText(), flagTeam, flagLabel, null, flagTeams);
				}
			});
		}
		
		public void setText(String string) {
			super.setText(string);
			isFlag = FlagTeam.findCountry(text.getText(), flagTeam, flagLabel, null, flagTeams);
		}
	}
}
