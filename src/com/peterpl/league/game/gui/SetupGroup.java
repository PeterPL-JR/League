package com.peterpl.league.game.gui;

import javax.swing.*;

import com.peterpl.league.methods.*;

import java.awt.Color;
import java.awt.Font;

public class SetupGroup extends JPanel {
	private static final long serialVersionUID = 1L;
	private final int teamsCount;
	
	private JLabel title;
	public JTextField[] teams;
	public JLabel[] hosts;

	public SetupGroup(String name, int teamsCount) {
		this.teamsCount = teamsCount;
		
		setLayout(null);
		setSize(200, 96 + teamsCount * 31);
		setBackground(new Color(230, 230, 230));
		
		title = new JLabel("Group " + name);
		title.setFont(new Font("Tahoma", Font.BOLD, 18));
		title.setOpaque(true);
		title.setBackground(Color.LIGHT_GRAY);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(0, 0, 200, 48);
		add(title);
		
		labels();
		teams();
	}

	public void setHost(int index) {
		if(index < 0 || index >= teamsCount) return;
		
		hosts[index].setVisible(true);
		teams[index].setEditable(false);
	}
	
	private void labels() {
		hosts = new JLabel[teamsCount];
		
		for(int i = 0; i < teamsCount; i++) {
			hosts[i] = new JLabel("H");
			hosts[i].setFont(new Font("Tahoma", Font.PLAIN, 20));
			hosts[i].setBounds(171, 72 + i * 31, 29, 20);
			hosts[i].setVisible(false);
			add(hosts[i]);
		}
	}
	
	private void teams() {
		teams = new JTextField[teamsCount];
		for(int i = 0; i < teams.length; i++) {
			teams[i] = new JTextField();
			teams[i].setSize(116, 24);
			teams[i].setLocation(Methods.centerX(teams[i], this), 72 + i * 31);
			add(teams[i]);
			
			Methods.setFocusSwitches(teams, i);
		}
	}
}
