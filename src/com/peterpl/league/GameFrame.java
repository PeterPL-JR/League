package com.peterpl.league;

import javax.swing.*;

import com.peterpl.league.game.gui.*;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public static final int Width = 960;
	public static final int Height = 830;

	public int activeGroup = 0;
	
	public GameFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setSize(Width, Height);
		setTitle(League.title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	public void setGroup(int index) {
		if(index < 0 || index > League.groupsCount - 1) return;
		
		setPanel(League.groupRound[index]);
		if(League.groupsHandler != null && League.groupsHandler.getGroup() == index) {
			
			int match = League.groupsHandler.getMatchRound();
			League.groupRound[index].matchesTable.setMatchFocus(match);
		}
		activeGroup = index;
	}
	
	public void setKnockout(int index) {
		if(index < 0 || index > League.knockout.length - 1) return;
		
		setPanel(League.knockout[index]);
		int match = League.knockout[index].getMatch();
	
		League.knockout[index].setMatchFocus(match);
	}
	
	public void setPanel(GamePanel panel) {
		setContentPane(panel);
	}
	
	public void setPanelNext(GamePanel panel) {
		panel.rightArrow.requestFocusInWindow();
	}
	
	public void setTitle(String leagueName) {
		super.setTitle(League.title + " (" + leagueName + ")");
	}
}
