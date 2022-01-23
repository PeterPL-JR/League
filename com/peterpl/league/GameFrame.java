package com.peterpl.league;

import javax.swing.*;

import com.peterpl.league.game.gui.*;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public static final int Width = 802;
	public static final int Height = 850;

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
	
	public void setGroup(int group) {
		if(group < 0 || group > League.groupsCount - 1) return;
		
		setPanel(League.groupRound[group]);
		if(League.groupsHandler != null && League.groupsHandler.getGroup() == group) {
			
			int match = League.groupsHandler.getMatchRound();
			League.groupRound[group].matchesTable.setMatchFocus(match);
		}
		activeGroup = group;
	}
	
	public void setPanel(GamePanel panel) {
		setContentPane(panel);
	}
	
	public void setPanelNext(GamePanel panel) {
		panel.rightArrow.requestFocusInWindow();
	}
}
