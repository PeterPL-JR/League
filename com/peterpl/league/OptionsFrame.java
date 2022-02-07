package com.peterpl.league;

import java.awt.event.*;

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
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
		setVisible(true);
		requestFocus();

		logo = new Header("Options", this, Header.Basic + 5);
		add(logo);
		
		button = new Buttons("OK");
		button.setLocation(Game.centerX(button, this), Height - 135);
		add(button);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				League.menuFrame.setVisible(true);
			}
		});
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
					League.menuFrame.setVisible(true);
				}
			}
		});
	}
}
