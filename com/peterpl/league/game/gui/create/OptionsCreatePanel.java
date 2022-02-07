package com.peterpl.league.game.gui.create;

import java.awt.*;

import javax.swing.*;

import com.peterpl.league.*;
import com.peterpl.league.game.gui.*;
import com.peterpl.league.methods.*;

public class OptionsCreatePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private String[] teamsMode = {
		" Default Teams Mode",
		" National Teams (Flags)",
	};
	
	private JComboBox<String> teamsChoose;
	private CheckSquare matchSquare;
	private JLabel optionsLabel;

	public OptionsCreatePanel() {
		setLayout(null);
		setSize(CreateFrame.Width / 2, 200);
		createOptions();
		Game.alignRight(this);
	}

	private void createOptions() {
		// Files Label
		optionsLabel = new JLabel("Options");
		optionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		optionsLabel.setFont(new Font("Verdana", Font.PLAIN, 22));
		optionsLabel.setBounds(17, 0, 260, 50);
		add(optionsLabel);
		
		// Teams Mode Choose Box
		teamsChoose = new JComboBox<>();
		teamsChoose.setFont(new Font("Verdana", Font.PLAIN, 17));
		teamsChoose.setBounds(17, 50, 260, 50);
		add(teamsChoose);
		
		for(String teams : teamsMode) {
			teamsChoose.addItem(teams);
		}
		
		matchSquare = new CheckSquare("Third Place Match");
		matchSquare.setLocation(17, 110);
		add(matchSquare);
	}
	
	public void setNamesMode(int index) {
		teamsChoose.setSelectedIndex(index);
	}
	
	public int getTeamsSelect() {
		return teamsChoose.getSelectedIndex();
	}
	
	public boolean getThirdPlacesSelect() {
		return matchSquare.isChecked();
	}
}
