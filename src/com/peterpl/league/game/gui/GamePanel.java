package com.peterpl.league.game.gui;

import javax.swing.*;

import com.peterpl.league.methods.*;

public abstract class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public Events leftEvent;
	public Events rightEvent;
	
	protected int width;
	protected int height;
	
	protected Header logo;
	public Buttons button;
	public ArrowButton leftArrow;
	public ArrowButton rightArrow;

	public GamePanel(String name, int logoFontSize, int width, int height) {
		this.width = width;
		this.height = height;
		
		setLayout(null);
		setSize(width, height);

		logo = new Header(name, this, logoFontSize);
		add(logo);

		button = new Buttons("OK");
		button.setLocation(Game.centerX(button, this), height - 120);
		button.setVisible(false);
		add(button);

		leftArrow = new ArrowButton("left");
		leftArrow.setLocation(40, 281);
		leftArrow.addAction(this::left);
		add(leftArrow);

		rightArrow = new ArrowButton("right");
		rightArrow.setLocation(width - 70, 281);
		rightArrow.addAction(this::right);
		add(rightArrow);
		
		leftEvent = () -> {};
		rightEvent = () -> {};
	}

	public final void left() {
		leftEvent.event();
	}

	public final void right() {
		rightEvent.event();
	}
}
