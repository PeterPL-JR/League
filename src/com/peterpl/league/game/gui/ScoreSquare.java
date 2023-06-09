package com.peterpl.league.game.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import com.peterpl.league.methods.*;

public class ScoreSquare extends JTextField {
	private static final long serialVersionUID = 1L;
	
	public static final int Size = 25;
	
	protected boolean active = false;
	protected boolean focused = false;

	public Events enterKeyEvent;
	public Events leftEvent, rightEvent;

	protected final Border normalBorder = new LineBorder(new Color(0xa0a0a0));
	protected final Border focusBorder = new LineBorder(new Color(0x9d9d9d), 2);
	
	protected KeyAdapter keyListener;
	protected MouseAdapter mouseListener;
	protected FocusAdapter focusListener;

	public ScoreSquare() {
		create();
		
		createKeyEvents();
		createMouseEvents();
		createFocusEvents();
		
		addKeyListener(keyListener);
		addMouseListener(mouseListener);
		addFocusListener(focusListener);
	}

	public ScoreSquare(KnockoutMatch match) {
		create();
		
		createKeyEvents();
		createMouseEvents();
		createFocusEvents();
		
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_E) {
					match.setOvertime(match.overtime() ? false : true, false);
				}
			}
		});
		
		addKeyListener(keyListener);
		addMouseListener(mouseListener);
		addFocusListener(focusListener);
	}

	protected void create() {
		setSize(Size, Size);
		setFont(new Font("Verdana", Font.PLAIN, 15));
		setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(normalBorder);

		enterKeyEvent = () -> {};
		leftEvent = () -> {};
		rightEvent = () -> {};
	}

	protected void createKeyEvents() {

		keyListener = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (!active) return;

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					enterKeyEvent.event();
				}
				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					leftEvent.event();
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					rightEvent.event();
				}
			}
		};
	}

	protected void createMouseEvents() {
		
		mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (active)
					requestFocusInWindow();
			}
		};
	}
	
	protected void createFocusEvents() {

		focusListener = new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				focused = true;

				if (active) {
					setSize(27, 27);

					setLocation(getX() - 1, getY() - 1);
					setSize(27, 27);
					setBorder(focusBorder);
				}
			}

			public void focusLost(FocusEvent e) {
				focused = false;

				if (active) {
					setSize(25, 25);

					setLocation(getX() + 1, getY() + 1);
					setSize(25, 25);
					setBorder(normalBorder);
				}
			}
		};
	}

	public void setScore(int score) {
		setText(score + "");
	}

	public String getScore() {
		return getText();
	}

	public void setLeftEvent(ScoreSquare score) {
		leftEvent = () -> {
			score.requestFocusInWindow();
		};
	}

	public void setRightEvent(ScoreSquare score) {
		rightEvent = () -> {
			score.requestFocusInWindow();
		};
	}

	public void setActive(boolean active) {
		this.active = active;
		if (!active) {
			setBorder(normalBorder);
			if (focused) {
				setSize(25, 25);
				setLocation(getX() + 1, getY() + 1);
			}
		}
	}

	public boolean isActive() {
		return active;
	}
}
