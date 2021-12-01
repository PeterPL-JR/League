package com.peterpl.league.game.gui;

import java.awt.*;

import javax.swing.*;

public class Header extends JLabel {
	private static final long serialVersionUID = 1L;
	
	public static final int Basic = 45;
	
	public Header(String text, Container panel, int fontSize) {
		
		int width = (fontSize * 2) + text.length() * (int)(fontSize * 0.6);
		setBounds(panel.getWidth() / 2 - width / 2, 21, width, (int)(fontSize * 1.5));
		
		setText(text);
		setOpaque(true);
		setBackground(Color.LIGHT_GRAY);
		setFont(new Font("Times New Roman", Font.BOLD, fontSize));
		setHorizontalAlignment(SwingConstants.CENTER);
	}
}
