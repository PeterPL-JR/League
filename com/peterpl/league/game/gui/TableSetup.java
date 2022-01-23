package com.peterpl.league.game.gui;

import javax.swing.*;

import com.peterpl.league.methods.*;

import java.awt.*;
import java.awt.event.*;

public class TableSetup extends JPanel {
	private static final long serialVersionUID = 1L;
	private final int teamsCount;

	private final int textWidth; // 195
	private final int textHeight; // 25

	private final int textMargin;
	private final int textFontSize;

	private JLabel title;
	private JTextField[] teams;
	private FlagTextField[] flagsTeams;
	private JLabel[] hosts;

	public TableSetup(String name, int teamsCount, boolean flagsMode, int textWidth, int textHeight) {
		this.teamsCount = teamsCount;

		this.textWidth = textWidth;
		this.textHeight = textHeight;

		this.textMargin = (int) (textHeight * 1.5);
		this.textFontSize = (int) (textHeight * 0.5);
		
		setLayout(null);
		setSize(textWidth + 100, 100 + teamsCount * textMargin);
		setBackground(new Color(225, 225, 225));

		title = new JLabel(name);
		title.setFont(new Font("Verdana", Font.BOLD, 20));
		title.setOpaque(true);
		title.setBackground(new Color(190, 190, 190));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(0, 0, getWidth(), 50);
		add(title);

		teams();
		labels();
	}

	public void setHost(int index) {
		if (index < 0 || index >= teamsCount)
			return;

		hosts[index].setVisible(true);
		teams[index].setEnabled(false);
		teams[index].setDisabledTextColor(Color.black);
		teams[index].setBackground(new Color(240, 240, 240));
	}

	public void setEnabledAll(boolean enabled) {
		for (int i = 0; i < teams.length; i++) {
			teams[i].setEnabled(false);
			teams[i].setDisabledTextColor(Color.black);
			teams[i].setBackground(new Color(240, 240, 240));
		}
	}

	public void setText(String string, int index) {
		teams[index].setText(string);
	}

	public void setText(String[] strings) {
		for (int i = 0; i < teams.length; i++) {
			teams[i].setText(strings[i]);
		}
	}

	public String[] getText() {
		String[] strings = new String[teams.length];
		for (int i = 0; i < strings.length; i++)
			strings[i] = teams[i].getText();
		return strings;
	}
	
	public void setTextEvents(GamePanel panel) {
		for(int i = 0; i < teams.length; i++) {
			teams[i].addKeyListener(new KeyAdapter() {
				
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER)
						panel.button.doClick();
				}
			});
		}
	}

	private void labels() {
		hosts = new JLabel[teamsCount];
		for (int i = 0; i < teamsCount; i++) {

			hosts[i] = new JLabel("H");
			hosts[i].setSize(30, textHeight);
			hosts[i].setLocation(textWidth + teams[i].getX(), 78 + i * textMargin);
			hosts[i].setFont(new Font("Verdana", Font.PLAIN, (int) (textFontSize * 1.6)));
			hosts[i].setHorizontalAlignment(SwingConstants.CENTER);
			hosts[i].setVisible(false);
			add(hosts[i]);
		}
	}

	private void teams() {
		flagsTeams = new FlagTextField[teamsCount];
		for(int i = 0; i < flagsTeams.length; i++) {
			
			flagsTeams[i] = new FlagTextField(textWidth, textHeight);
		}
		
		teams = new JTextField[teamsCount];
		for (int i = 0; i < teams.length; i++) {

			teams[i] = new JTextField();
			teams[i].setSize(textWidth, textHeight);
			teams[i].setLocation(Game.centerX(teams[i], this), 78 + i * textMargin);
			teams[i].setFont(new Font("Verdana", Font.PLAIN, textFontSize));
			teams[i].setMargin(new Insets(0, 7, 0, 0));
			teams[i].setBackground(Color.white);
			add(teams[i]);

			Game.setFocusSwitches(teams, i);
		}
	}
}
