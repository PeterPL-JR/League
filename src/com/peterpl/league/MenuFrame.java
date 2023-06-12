package com.peterpl.league;

import javax.swing.*;

import com.peterpl.league.game.gui.*;
import com.peterpl.league.methods.*;

import java.awt.*;

public class MenuFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public static final int width = 600;
	public static final int height = 500;

	private JPanel menu;
	private JLabel logo;
	private Buttons[] buttons;

	public MenuFrame() {
		
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
		
		menu = new JPanel();
		menu.setLayout(null);
		setContentPane(menu);
		
		logo();
		buttons();
	}
	
	private void logo() {
		logo = new JLabel("League");
		logo.setOpaque(true);
		logo.setBackground(Color.LIGHT_GRAY);
		logo.setFont(new Font("Times New Roman", Font.BOLD, 45));
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
		logo.setBounds(162, 11, 269, 81);
		menu.add(logo);
	}
	
	private void buttons() {
		buttons = new Buttons[3];
		buttons[0] = new Buttons("Start");
		buttons[1] = new Buttons("Options");
		buttons[2] = new Buttons("Quit");
		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].setLocation(194, 167 + i * 62);
			add(buttons[i]);
			
			Methods.setFocusSwitches(buttons, i);
		}
		
		buttons[0].setAction(this::startEvent);
		buttons[1].setAction(this::optionsEvent);
		buttons[2].setAction(this::quitEvent);
	}
	
	private void startEvent() {
		League.menuFrame.setVisible(false);
		League.createFrame.setVisible(true);
	}
	
	private void optionsEvent() {
	}
	
	private void quitEvent() {
		System.exit(0);
	}
}
