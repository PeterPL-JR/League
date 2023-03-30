package com.peterpl.league.game.gui;

import java.awt.event.*;
import javax.swing.*;

import static com.peterpl.league.files.GraphicsImage.*;

public class CheckSquare extends JPanel {
	private static final long serialVersionUID = 1L;
	private boolean checked = false;
	private String text;
	
	private JLabel square;
	private JLabel label;
	
	public CheckSquare(String text) {
		this.text = text;
		
		setSize(260, 50);
		setBackground(Buttons.color);
		setBorder(Buttons.border);
		setLayout(null);
		createLabels();
		
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				checked = !checked;
				square.setIcon(checked ? squareChecked : squareDefault);
			}
		});
	}
	
	private void createLabels() {
		square = new JLabel(squareDefault);
		square.setSize(getHeight(), getHeight());
		square.setLocation(0, 0);
		add(square);
		
		label = new JLabel(text);
		label.setSize(getWidth() - getHeight(), getHeight());
		label.setLocation(getHeight(), 0);
		label.setFont(Buttons.font);
		add(label);
	}
	
	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
