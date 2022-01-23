package com.peterpl.league.game.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class PenaltySquare extends ScoreSquare {
	private static final long serialVersionUID = 1L;
	
	public static final int size = 36;
	private JLabel[] brackets;
	
	private Font textFont;
	private Font bracketsFont;

	public PenaltySquare() {
		textFont = new Font("Verdana", Font.PLAIN, (int)(size * 0.5));
		bracketsFont = new Font("Verdana", Font.PLAIN, (int)(size * 0.6));
		
		setSize(size, size);
		setLayout(null);
		setOpaque(false);
		setBorder(null);
		
		createLabel();
		createBrackets();
	}
	
	protected void createFocusEvents() {
		
		focusListener = new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				focused = true;
				
				if(active) {
					bracketsFont = bracketsFont.deriveFont(Font.BOLD);
					
					for(int i = 0; i < brackets.length; i++)
						brackets[i].setFont(bracketsFont);
				}
			}

			public void focusLost(FocusEvent e) {
				focused = false;
				
				if(active) {
					bracketsFont = bracketsFont.deriveFont(Font.PLAIN);
					
					for(int i = 0; i < brackets.length; i++)
						brackets[i].setFont(bracketsFont);
				}
			}
		};
	}
	
	private void createLabel() {
		label = new JLabel();
		label.setBounds(0, 0, size, size);
		label.setFont(textFont);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setOpaque(false);
		label.setBorder(null);
		label.setForeground(new Color(0x505050));
		add(label);
	}
	
	private void createBrackets() {
		brackets = new JLabel[2];
		for(int i = 0; i < brackets.length; i++) {
			brackets[i] = new JLabel();
			brackets[i].setVerticalAlignment(SwingConstants.CENTER);
			brackets[i].setBounds(i * size / 2, 0, size / 2, size);
			brackets[i].setFont(bracketsFont);
			brackets[i].setForeground(new Color(0x505050));
			add(brackets[i]);
		}
		
		brackets[0].setText("(");
		brackets[1].setText(")");
		
		brackets[0].setHorizontalAlignment(SwingConstants.LEFT);
		brackets[1].setHorizontalAlignment(SwingConstants.RIGHT);
	}
	
	public void setActive(boolean active) {
		this.active = active;
		if(!active) {
			
			bracketsFont = bracketsFont.deriveFont(Font.PLAIN);
			
			for(int i = 0; i < brackets.length; i++)
				brackets[i].setFont(bracketsFont);
		}
	}
}
