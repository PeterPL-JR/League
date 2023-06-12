package com.peterpl.league.game.gui.create;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.peterpl.league.*;
import com.peterpl.league.methods.*;

public class ModeCreatePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JComboBox<String> modeChoose;
	private JComboBox<String> knockChoose;
	private JLabel modeLabel;
	
	private String[] modeNames = {
		" 32 Teams, 8 Groups",
		" 24 Teams, 6 Groups",
		" 16 Teams, 4 Groups",
		" 12 Teams, 3 Groups",
	};
	
	private String[] knockNames = {
		" Knock-out Teams - 16",
		" Knock-out Teams - 8",
		" Knock-out Teams - 4"
	};
	
	public ModeCreatePanel() {
		setLayout(null);
		setSize(CreateFrame.Width / 2, 200);
		createMode();
		Methods.alignRight(this);
	}
	
	private void createMode() {
		// Mode Label
		modeLabel = new JLabel("Mode");
		modeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		modeLabel.setFont(new Font("Verdana", Font.PLAIN, 22));
		modeLabel.setBounds(17, 0, 260, 50);
		add(modeLabel);
		
		// Mode Choose Box
		modeChoose = new JComboBox<>();
		modeChoose.setFont(new Font("Verdana", Font.PLAIN, 17));
		modeChoose.setBounds(17, 50, 260, 50);
		add(modeChoose);
		
		for(String mode : modeNames) {
			modeChoose.addItem(mode);
		}
		modeChoose.setSelectedIndex(1);
		modeChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchKnock(getModeSelect());
			}
		});
		
		// Knock Choose Box
		knockChoose = new JComboBox<>();
		knockChoose.setFont(new Font("Verdana", Font.PLAIN, 17));
		knockChoose.setBounds(17, 117, 260, 50);
		add(knockChoose);
		
		knockChoose.addItem(knockNames[0]);
		knockChoose.addItem(knockNames[1]);
		knockChoose.setSelectedIndex(0);
	}
	
	private void switchKnock(int mode) {
		knockChoose.removeAllItems();
		
		if(mode >= 0 && mode < 2) {
			knockChoose.addItem(knockNames[0]);
			knockChoose.addItem(knockNames[1]);
		}
		if(mode >= 2 && mode < 4) {
			knockChoose.addItem(knockNames[1]);
			knockChoose.addItem(knockNames[2]);
		}
		if(mode == 3) {
			knockChoose.setSelectedIndex(1);
		}
	}
	
	public void setProperties(int mode, int knockout) {
		setTeamsMode(mode);
		setKnockoutMode(knockout);
	}
	
	public void setTeamsMode(int index) {		
		modeChoose.setSelectedIndex(index);
	}
	
	public void setKnockoutMode(int index) {
		knockChoose.setSelectedIndex(index);
	}
	
	public int getModeSelect() {
		return modeChoose.getSelectedIndex();
	}
	
	public int getKnockSelect() {
		return knockChoose.getSelectedIndex();
	}
}
