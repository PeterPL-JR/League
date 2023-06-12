package com.peterpl.league;

import java.awt.*;

import javax.swing.*;

import com.peterpl.league.files.*;
import com.peterpl.league.game.gui.*;
import com.peterpl.league.game.gui.create.*;
import com.peterpl.league.methods.*;

public class CreateFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public static final int Width = 802;
	public static final int Height = 850;

	public ModeCreatePanel modeCreate;
	public HostCreatePanel hostCreate;
	public OptionsCreatePanel optionsCreate;
	public FilesCreatePanel filesCreate;

	private Header logo;
	private Buttons button;
	private JPanel namePanel;

	private JLabel nameLabel;
	private TextFieldString name;

	public CreateFrame() {
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
		setLayout(null);

		logo = new Header("Create league", this, Header.Basic + 5);
		add(logo);

		button = new Buttons("Create");
		button.setLocation(298, 715);
		button.setAction(this::buttonEvent);
		add(button);

		createPanels();
		createLeagueName();
	}

	private void createLeagueName() {
		// League Name Label
		nameLabel = new JLabel("Tournament Name");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setFont(new Font("Verdana", Font.PLAIN, 22));
		nameLabel.setSize(350, 35);
		nameLabel.setLocation(Methods.centerX(nameLabel, namePanel), 0);
		namePanel.add(nameLabel);

		// League Name Text
		name = new TextFieldString("Enter a name");
		name.setSize(415, 50);
		name.setLocation(Methods.centerX(name, namePanel), 42);
		namePanel.add(name);
	}

	private void createPanels() {
		// Name Create Panel
		namePanel = new JPanel();
		namePanel.setBounds(0, 150, Width, 100);
		namePanel.setLayout(null);
		add(namePanel);

		// Mode Create Panel
		modeCreate = new ModeCreatePanel();
		modeCreate.setLocation(0, 257);
		add(modeCreate);

		// Host Create Panel
		hostCreate = new HostCreatePanel();
		hostCreate.setLocation(401, 257);
		add(hostCreate);

		// Options Create Panel
		optionsCreate = new OptionsCreatePanel();
		optionsCreate.setLocation(0, 460);
		add(optionsCreate);

		// Files Create Panel
		filesCreate = new FilesCreatePanel();
		filesCreate.setLocation(401, 440);
		add(filesCreate);
	}

	public void setNameText(String text) {
		name.setText(text);
	}
	public String getNameText() {
		return name.getText();
	}

	public void loadLeague(LeagueFile leagueFile) {
		setVisible(false);
		League.gameFrame = new GameFrame();
		League.gameFrame.setVisible(true);

		LoadLeagueHandler.loadLeague(leagueFile);
	}

	private void buttonEvent() {
		if (!League.createGame.getLeagueValues())
			return;
		League.createGame.createGameValues();
		League.createGame.createSetupRound();

		// Show Setup Frame
		League.createFrame.dispose();
		League.setupFrame.setVisible(true);
	}
}
