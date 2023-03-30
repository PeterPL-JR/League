package com.peterpl.league.game.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import com.peterpl.league.methods.*;

public class Buttons extends JButton {
	private static final long serialVersionUID = 1L;
	private Events action;
	
	public static final Color color = new Color(0xe1e1e1);
	public static final LineBorder border = new LineBorder(new Color(0xadadad));
	public static final Font font = new Font("Tahoma", Font.PLAIN, 17);
	
	public static final int width = 205;
	public static final int height = 51;
	
	public Buttons(String text) {
		super(text);
		setSize(width, height);
		setFont(font);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setFocusPainted(false);
		setAction(() -> {});
		
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action.event();
			}
		});
	}

	public void setAction(Events action) {	
		this.action = action;
	}
}
