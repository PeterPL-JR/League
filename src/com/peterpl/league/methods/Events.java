package com.peterpl.league.methods;

import java.awt.event.*;
import javax.swing.*;

public interface Events {
	void event();

	public static void setKeyAction(JComponent object, Events event, int key) {
		object.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == key) event.event();
			}
		});
	}

	public static void setFocusAction(JComponent object, Events event, boolean focusActive) {
		if (focusActive)
			object.addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					event.event();
				}
			});
		else
			object.addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent e) {
					event.event();
				}
			});
	}
}