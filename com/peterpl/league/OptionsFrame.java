package com.peterpl.league;

import javax.swing.*;

import com.peterpl.league.game.gui.*;
import com.peterpl.league.methods.*;

public class OptionsFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public static final int Width = 700;
	public static final int Height = 600;
	
	private Header logo;
	private Buttons button;
	
	public OptionsFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		setSize(Width, Height);
		setTitle(League.title + " - Options");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);

		logo = new Header("Options", this, Header.Basic + 5);
		add(logo);
		
		button = new Buttons("OK");
		button.setLocation(Game.centerX(button, this), Height - 135);
		add(button);
	}
}
