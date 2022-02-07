package com.peterpl.league.game.gui.create;

import java.awt.*;

import javax.swing.*;

import com.peterpl.league.*;
import com.peterpl.league.game.gui.*;

public class FilesCreatePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Buttons loadLeague;
	private Buttons addRanking;
	private JLabel filesLabel;

	public FilesCreatePanel() {
		setLayout(null);
		setSize(CreateFrame.Width / 2, 200);
		createFiles();
	}

	public void createFiles() {
		// Hosts Label
		filesLabel = new JLabel("Files");
		filesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		filesLabel.setFont(new Font("Verdana", Font.PLAIN, 22));
		filesLabel.setBounds(17, 0, 260, 50);
		add(filesLabel);

		// Load League Button
		loadLeague = new Buttons("Load League");
		loadLeague.setBounds(17, 50, 267, 50);
		loadLeague.setAction(this::loadLeagueEvent);
		add(loadLeague);

		// Add Ranking Button
		addRanking = new Buttons("Add Ranking");
		addRanking.setBounds(17, 110, 267, 50);
		addRanking.setAction(this::addRankingEvent);
		add(addRanking);
	}
	
	private void loadLeagueEvent() {
		new LoadFrame(League.createFrame, League.leagueFile.loadFiles(), "load");
	}
	
	private void addRankingEvent() {
		
	}
}
