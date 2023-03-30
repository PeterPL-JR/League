package com.peterpl.league.game.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import com.peterpl.league.files.*;
import com.peterpl.league.methods.*;

public class ArrowButton extends JLabel implements Serializable {
	private static final long serialVersionUID = 1L;
	private Events event;
	
	public ArrowButton(String type) {
		setIcon(type == "right" ? GraphicsImage.arrowRight : GraphicsImage.arrowLeft);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setSize(36, 50);
		addAction(() -> {});
		
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				event.event();
			}
		});
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					event.event();
				}
			}
		});
	}
	
	public void addAction(Events event) {
		this.event = event;
	}
}
