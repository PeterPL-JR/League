package com.peterpl.league.game.gui.create;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.peterpl.league.*;
import com.peterpl.league.game.gui.*;

public class HostCreatePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JComboBox<String> hostChoose;
	private TextFieldString[] hosts;
	private JLabel hostLabel;
	
	private String[] hostNames = {
		" (None)",
		" One Host",
		" Two Hosts"
	};
	
	public HostCreatePanel() {
		setLayout(null);
		setSize(CreateFrame.Width / 2, 200);
		createHosts();
	}
	
	public String[] getHostNames() {
		String[] names = new String[2];
		for(int i = 0; i < names.length; i++)
			names[i] = hosts[i].getText();
		return names;
	}
	
	private void createHosts() {
		// Hosts Label
		hostLabel = new JLabel("Host");
		hostLabel.setHorizontalAlignment(SwingConstants.CENTER);
		hostLabel.setFont(new Font("Verdana", Font.PLAIN, 22));
		hostLabel.setBounds(17, 0, 260, 50);
		add(hostLabel);
		
		// Hosts Text Fields
		hosts = new TextFieldString[2];
		for(int i = 0; i < hosts.length; i++) {
			hosts[i] = new TextFieldString("Host name");
			hosts[i].setBounds(17, 50 + i * 42, 260, 35);
			add(hosts[i]);
		}
		hosts[1].setVisible(false);
		
		//
		// hosts[0].setText("Katar");
		//
		
		// Hosts Choose Box
		hostChoose = new JComboBox<>();
		hostChoose.setFont(new Font("Verdana", Font.PLAIN, 15));
		hostChoose.setBounds(17, 92, 260, 33);
		add(hostChoose);
		
		for(String host : hostNames) {
			hostChoose.addItem(host);
		}
		
		hostChoose.setSelectedIndex(1);
		hostChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int selectedItem = getHostSelect();
				switch(selectedItem) {
				case 0:
					setHost(0, true, false);
					setHost(1, false, false);
					
					hosts[0].setText(null);
					hosts[1].setText(null);
					hostChoose.setLocation(hostChoose.getX(), 92);
					break;
				case 1:
					setHost(0, true, true);
					setHost(1, false, false);
					hostChoose.setLocation(hostChoose.getX(), 92);
					break;
				case 2:
					setHost(0, true, true);
					setHost(1, true, true);
					
					hosts[0].requestFocusInWindow();
					hosts[1].requestFocusInWindow();
					hostChoose.requestFocusInWindow();
					
					hostChoose.setLocation(hostChoose.getX(), 134);
					break;
				}
			}
		});
	}
	
	private void setHost(int index, boolean visible, boolean active) {
		hosts[index].setVisible(visible);
		
		hosts[index].setActive(active);
		hosts[index].setEditable(active);
	}
	
	public int getHostSelect() {
		return hostChoose.getSelectedIndex();
	}
}
