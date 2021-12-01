package com.peterpl.league.game.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.peterpl.league.methods.*;

public class TextFieldString extends JPanel {
	private static final long serialVersionUID = 1L;
	private static double fontTimes = 0.46;
	
	private JTextField text;
	private JLabel label;

	public TextFieldString(String altString) {
		setLayout(null);
		setCursor(new Cursor(Cursor.TEXT_CURSOR));
		
		label = new JLabel(altString);
		label.setLocation(0, 0);
		label.setForeground(Color.lightGray);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVisible(true);
		add(label);

		text = new JTextField();
		text.setLocation(0, 0);
		text.setHorizontalAlignment(SwingConstants.CENTER);
		add(text);
		createText();
	}
	
	private void createText() {
		text.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if(text.isEditable()) {
					label.setVisible(false);
				}
			}

			public void focusLost(FocusEvent e) {
				if(Game.isStringEmpty(text.getText())) {
					if(text.isEditable()) {
						label.setVisible(true);
					}
				}
			}
		});
	}
	
	public void setText(String str) {
		text.setText(str);
		label.setVisible(false);
	}
	
	public String getText() {
		return text.getText();
	}
	
	public void setAltString(String str) {
		label.setText(str);
	}
	
	public void setEditable(boolean editable) {
		text.setEditable(editable);
		label.setVisible(editable);
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
	}
	
	public void setActive(boolean active) {
		if(text.isEditable()) {
			label.setVisible(active);
		}
	}
	
	public boolean requestFocusInWindow() {
		return text.requestFocusInWindow();
	}
	
	public void setSize(int width, int height) {
		super.setSize(width, height);
		label.setSize(width, height);
		text.setSize(width, height);
		
		label.setFont(new Font("Rubik", Font.PLAIN, (int) (getHeight() * fontTimes)));
		text.setFont(new Font("Rubik", Font.PLAIN, (int) (getHeight() * fontTimes)));
	}

	public void setLocation(int x, int y) {
		super.setLocation(x, y);
	}

	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		label.setSize(width, height);
		text.setSize(width, height);
		
		label.setFont(new Font("Rubik", Font.PLAIN, (int) (getHeight() * fontTimes)));
		text.setFont(new Font("Rubik", Font.PLAIN, (int) (getHeight() * fontTimes)));
	}
}