package com.peterpl.league;

import javax.swing.*;

public class SetupFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public static final int Width1 = 1400;
	public static final int Width2 = 1100;
	
	public static final int Height = 820;
	
	public SetupFrame(int width, int height) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setSize(width, height);
		setTitle(League.title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	public void setPots() {
		setPanel(League.pots);
	}
	
	public void setGroups() {
		setPanel(League.groups);
	}
	
	public void setPanel(JPanel panel) {
		setContentPane(panel);
	}
}
