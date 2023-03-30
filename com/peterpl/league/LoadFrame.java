package com.peterpl.league;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import com.peterpl.league.files.*;
import com.peterpl.league.game.gui.*;
import com.peterpl.league.methods.*;

public class LoadFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private LeagueFile[] files;
	private ArrayList<LeagueFile> deleteFiles;

	private int checkedIndex = -1;
	private JFrame parentFrame;

	private FilePanel[] filePanels;
	private JPanel panel;
	private JScrollPane scroll;

	private JLabel label;
	private JLabel altLabel;
	private Buttons button;

	public static final int Width = 600;
	public static final int Height = 500;

	public static final int NoWidth = 450;
	public static final int NoHeight = 280;

	public LoadFrame(JFrame parentFrame, LeagueFile[] files, String mode) {

		LeagueFile[] newFiles = new LeagueFile[files.length];
		int[] table = new int[files.length];
		for (int i = 0; i < table.length; i++) {
			table[i] = i;
		}

		LeagueFile.sortByDate(files, table);
		for (int i = 0; i < table.length; i++) {
			newFiles[i] = files[table[i]];
		}

		this.files = newFiles;
		this.parentFrame = parentFrame;

		setTitle(League.title + ((mode == "load") ? " - Load League" : " - Export File"));
		create(mode);

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
				}
			}
		});
	}

	private void create(String mode) {

		if (files.length != 0)
			setFilesList();
		else
			setNoFiles();

		parentFrame.setEnabled(false);
		setResizable(false);
		setLayout(null);
		setVisible(true);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		deleteFiles = new ArrayList<>();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				parentFrame.setEnabled(true);
			}
		});

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(450, 55 * files.length));
		panel.setLayout(null);

		filePanels = new FilePanel[files.length];
		for (int i = 0; i < filePanels.length; i++) {
			filePanels[i] = new FilePanel(i, mode);
			panel.add(filePanels[i]);

			String leagueName = files[i].leagueName;
			String teamsCount = " (" + files[i].teamsCount + ")";

			filePanels[i].name.setText(leagueName + teamsCount);
			filePanels[i].date.setText(files[i].dateText);
		}

		label = new JLabel("League Files");
		label.setBounds(0, 20, getWidth(), 50);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Verdana", Font.PLAIN, 35));
		label.setForeground(new Color(0x464646));
		add(label);

		scroll = new JScrollPane(panel);
		scroll.setBounds(getWidth() / 2 - 468 / 2, getHeight() / 2 - 306 / 2, 468, 254);
		scroll.getVerticalScrollBar().setUnitIncrement(10);
		scroll.setBorder(null);
		add(scroll);

		altLabel = new JLabel("(No files)");
		altLabel.setBounds(0, 85, getWidth(), 30);
		altLabel.setFont(new Font("Verdana", Font.PLAIN, 25));
		altLabel.setHorizontalAlignment(SwingConstants.CENTER);
		altLabel.setForeground(new Color(0x767676));
		altLabel.setVisible(files.length != 0 ? false : true);
		add(altLabel, 0);

		button = new Buttons("OK");
		button.setLocation(Game.centerX(button, this), getHeight() - 120);
		button.setAction((mode == "load") ? this::loadButtonEvent : this::chooseButtonEvent);
		add(button, 0);
	}

	private void loadButtonEvent() {

		for (int i = 0; i < deleteFiles.size(); i++) {
			File file = new File(deleteFiles.get(i).fileName);
			file.delete();
		}

		for (int i = 0; i < filePanels.length; i++) {
			if (filePanels[i].checked()) {
				checkedIndex = i;
				break;
			}
			checkedIndex = -1;
		}

		dispose();
		if (checkedIndex != -1) {
			League.createFrame.loadLeague(files[checkedIndex]);
		}
	}

	private void chooseButtonEvent() {
		for (int i = 0; i < filePanels.length; i++) {
			if (filePanels[i].checked()) {
				checkedIndex = i;
				break;
			}
			checkedIndex = -1;
		}

		parentFrame.setEnabled(true);
		dispose();
		
		if (checkedIndex != -1) {
			League.filesHandler.exportFile(files[checkedIndex]);
		}
	}

	private void setFilesList() {
		setSize(Width, Height);
		setLocationRelativeTo(null);
	}

	private void setNoFiles() {
		setSize(NoWidth, NoHeight);
		setLocationRelativeTo(null);
	}

	public void dispose() {
		parentFrame.setEnabled(true);
		super.dispose();
	}

	private class FilePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private boolean checked = false;

		private final Color colorDefault = new Color(0xe7e7e7);
		private final Color colorActive = new Color(0xd8d8d8);
		private final Color colorChecked = new Color(0xc8c8c8);

		private final LineBorder borderDefault = new LineBorder(new Color(0xc0c0c0));
		private final LineBorder borderChecked = new LineBorder(new Color(0xb0b0b0), 2);

		public JLabel name;
		public JLabel date;
		public JLabel delete;

		public FilePanel(int index, String mode) {
			setSize(450, 50);
			setLocation(0, index * 51);
			setLayout(null);
			setBackground(colorDefault);
			setBorder(new LineBorder(new Color(0xc0c0c0)));
			setCursor(new Cursor(Cursor.HAND_CURSOR));

			addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					setBackground(checked ? colorChecked : colorActive);
				}

				public void mouseExited(MouseEvent e) {
					setBackground(checked ? colorChecked : colorDefault);
				}

				public void mouseClicked(MouseEvent e) {
					checked = !checked;
					setBackground(checked ? colorChecked : colorActive);
					setChecked(checked);
					if (mode == "load")
						delete.setVisible(checked);
				}
			});

			name = new JLabel();
			name.setBounds(20, 0, 320, 50);
			name.setFont(new Font("Verdana", Font.PLAIN, 18));
			add(name);

			date = new JLabel();
			date.setBounds(340, 0, 100, 50);
			date.setFont(new Font("Verdana", Font.PLAIN, 15));
			date.setHorizontalAlignment(SwingConstants.CENTER);
			add(date);

			delete = new JLabel(GraphicsImage.delete);
			delete.setBounds(280, 0, 50, 50);
			delete.setVisible(false);
			add(delete);

			delete.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {

					deleteFiles.add(files[index]);
					filePanels[index].setChecked(false);
					panel.remove(filePanels[index]);

					for (int i = index + 1; i < filePanels.length; i++) {
						filePanels[i].setLocation(0, filePanels[i].getY() - 51);
					}
					panel.repaint();

					if (filePanels.length == deleteFiles.size()) {
						altLabel.setVisible(true);
						scroll.setVisible(false);
					}
				}
			});
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
			setBorder(checked ? borderChecked : borderDefault);
			if (checked) {
				for (int i = 0; i < filePanels.length; i++) {
					if (filePanels[i] == this) {
						continue;
					}
					if (filePanels[i].checked()) {
						filePanels[i].setChecked(false);
						filePanels[i].delete.setVisible(false);
					}
				}
			} else {
				setBackground(colorDefault);
			}
		}

		public boolean checked() {
			return checked;
		}
	}
}
