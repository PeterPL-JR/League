package com.peterpl.league.game.gui;

import javax.swing.*;

import com.peterpl.league.*;
import com.peterpl.league.methods.*;

import java.awt.*;
import java.awt.event.*;

public class TableSetup extends JPanel {
	private static final long serialVersionUID = 1L;
	public JTextField[] textFields;
	private final int teamsCount;
	
	private final int textWidth; // 175
	private final int textHeight; // 25

	private final int textMargin;
	private final int textFontSize;

	private JLabel title;
	private JTextField[] teams;
	public FlagTextField[] flagsTeams;
	private JLabel[] hosts;

	public TableSetup(String name, int teamsCount, boolean flagsMode, int textWidth, int textHeight) {
		this.teamsCount = teamsCount;

		this.textWidth = textWidth;
		this.textHeight = textHeight;

		this.textMargin = (int) (textHeight * 1.5);
		this.textFontSize = (int) (textHeight * 0.5);
		
		setLayout(null);
		setSize(textWidth + 80, 100 + teamsCount * textMargin);
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
		textFields[index].setEnabled(false);
		textFields[index].setDisabledTextColor(Color.black);
		textFields[index].setBackground(new Color(240, 240, 240));
	}

	public void setEnabledAll(boolean enabled) {
		for (int i = 0; i < textFields.length; i++) {
			textFields[i].setEnabled(false);
			textFields[i].setDisabledTextColor(Color.black);
			textFields[i].setBackground(new Color(240, 240, 240));
		}
	}

	public void setText(String string, int index) {
		textFields[index].setText(string);
	}

	public void setText(String[] strings) {
		for (int i = 0; i < teams.length; i++) {
			textFields[i].setText(strings[i]);
		}
	}

	public String[] getText() {
		String[] strings = new String[teams.length];
		for (int i = 0; i < strings.length; i++)
			strings[i] = textFields[i].getText();
		return strings;
	}
	
	public void setTextEvents(GamePanel panel) {
		for(int i = 0; i < teams.length; i++) {
			textFields[i].addKeyListener(new KeyAdapter() {
				
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
			hosts[i].setLocation(textWidth + teams[i].getX() - (League.flagsMode ? 12 : 20), 78 + i * textMargin);
			hosts[i].setFont(new Font("Verdana", Font.PLAIN, (int) (textFontSize * 1.6)));
			hosts[i].setHorizontalAlignment(SwingConstants.CENTER);
			hosts[i].setVisible(false);
			add(hosts[i]);
		}
	}

	private void teams() {
		flagsTeams = new FlagTextField[teamsCount];
		textFields = new JTextField[teamsCount];
		
		teams = new JTextField[teamsCount];
		for (int i = 0; i < teams.length; i++) {

			teams[i] = new JTextField();
			teams[i].setSize(textWidth - 26, textHeight);
			teams[i].setLocation(Game.centerX(teams[i], this), 78 + i * textMargin);
			teams[i].setFont(new Font("Verdana", Font.PLAIN, textFontSize));
			teams[i].setMargin(new Insets(0, 7, 0, 0));
			teams[i].setBackground(Color.white);
			textFields[i] = teams[i];
		}
		
		for(int i = 0; i < flagsTeams.length; i++) {
			flagsTeams[i] = new FlagTextField(textWidth, textHeight);
			flagsTeams[i].setLocation(Game.centerX(flagsTeams[i], this), 78 + i * textMargin);
			flagsTeams[i].text.setFont(new Font("Verdana", Font.PLAIN, textFontSize));
			flagsTeams[i].text.setMargin(new Insets(0, 7, 0, 0));
			if(League.flagsMode) textFields[i] = flagsTeams[i].text;
		}
		
		for(int i = 0; i < textFields.length; i++)
			Game.setFocusSwitches(textFields, i);
		
		if(League.flagsMode) {
			for(int i = 0; i < teamsCount; i++)
				add(flagsTeams[i]);
		} else {
			for(int i = 0; i < teamsCount; i++)
				add(teams[i]);
		}
	}
}
