package com.peterpl.league.game.gui.create;

import java.awt.*;

import javax.swing.*;

import com.peterpl.league.*;
import com.peterpl.league.game.gui.*;

public class FilesCreatePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Buttons loadLeague;
	private JLabel filesLabel;
	private Buttons exportLeague, importLeague;

	public FilesCreatePanel() {
		setLayout(null);
		setSize(CreateFrame.Width / 2, 400);
		createFiles();
	}

	public void createFiles() {
		// Hosts Label
		filesLabel = new JLabel("Files");
		filesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		filesLabel.setFont(new Font("Verdana", Font.PLAIN, 22));
		filesLabel.setBounds(17, 0, 260, 50);
		add(filesLabel);
		
		// Export button
		exportLeague = new Buttons("Export File");
		exportLeague.setBounds(17, 50, 267, 50);
		exportLeague.setAction(this::exportLeagueEvent);
		add(exportLeague);
				
		// Import button
		importLeague = new Buttons("Import File");
		importLeague.setBounds(17, 110, 267, 50);
		importLeague.setAction(this::importLeagueEvent);
		add(importLeague);

		// Load League Button
		loadLeague = new Buttons("Load League");
		loadLeague.setBounds(17, 185, 267, 50);
		loadLeague.setAction(this::loadLeagueEvent);
		add(loadLeague);
	}
	
	private void exportLeagueEvent() {
		new LoadFrame(League.createFrame, League.leagueFile.loadFiles(), "choose");
	}
	private void importLeagueEvent() {
		League.filesHandler.importFile();
	}
	
	private void loadLeagueEvent() {
		new LoadFrame(League.createFrame, League.leagueFile.loadFiles(), "load");
	}
}
