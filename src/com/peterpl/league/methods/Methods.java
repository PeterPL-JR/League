package com.peterpl.league.methods;

import static com.peterpl.league.League.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.peterpl.league.game.gui.*;

public interface Methods {

	public static boolean isStringEmpty(String string) {
		boolean empty = true;

		for (int i = 0; i < string.length(); i++) {
			char ch = string.charAt(i);
			if (ch != ' ' && ch != '\t') {
				empty = false;
				return empty;
			}
		}
		return empty;
	}

	public static boolean isArrayUnique(String[] array) {
		boolean unique = true;

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				if (i == j)
					continue;
				if (array[i].equals(array[j])) {
					unique = false;
					return unique;
				}
			}
		}
		return true;
	}
	
	public static <T> int indexOf(T[] hayStack, T needle) {
		for(int i = 0; i < hayStack.length; i++) {
			T elem = hayStack[i];
			if(elem.equals(needle)) {
				return i;
			}
		}
		return -1;
	}

	public static void setFocusSwitches(JComponent[] objects, int index) {

		int previousIndex = index - 1;
		int nextIndex = index + 1;

		Events actionPrevious = () -> {
			objects[previousIndex].requestFocus();
		};
		Events actionNext = () -> {
			objects[nextIndex].requestFocus();
		};

		if (previousIndex >= 0)
			Events.setKeyAction(objects[index], actionPrevious, KeyEvent.VK_UP);
		if (nextIndex < objects.length)
			Events.setKeyAction(objects[index], actionNext, KeyEvent.VK_DOWN);
	}

	public static int centerX(JComponent object, Container panel) {
		return panel.getWidth() / 2 - object.getWidth() / 2;
	}

	public static int centerY(JComponent object, Container panel) {
		return panel.getHeight() / 2 - object.getHeight() / 2;
	}

	public static char[] createCharsArray(String str) {

		char[] chars = new char[str.length()];
		for (int i = 0; i < chars.length; i++)
			chars[i] = str.charAt(i);

		return chars;
	}
	
	public static void alignRight(Container panel) {
		
		Component[] elems = panel.getComponents();
		int[] coordsX = new int[elems.length];
		
		for(int i = 0; i < coordsX.length; i++) {
			coordsX[i] = elems[i].getX();
		}
		
		for(int i = 0; i < elems.length; i++) {
			int panelW = panel.getWidth();
			int elemW = elems[i].getWidth();
			
			int xCoord = panelW - elemW - coordsX[i];
			int yCoord = elems[i].getY();
			
			elems[i].setLocation(xCoord, yCoord);
		}
	}
	
	public static void setTablesPos(TableSetup[] tables, int mode) {
		int lengthValue = tables.length;
		
		if (mode == 0) {
			int tableWidth = tables[0].getWidth() + 20;
			int absWidth = lengthValue * tables[0].getWidth() + (lengthValue - 1) * 20;
			int width = setupFrame.getWidth() / 2 - absWidth / 2;

			for (int i = 0; i < tables.length; i++) {
				tables[i].setLocation(width + i * tableWidth, 140);
			}
		}
		if (mode == 1) {
			int tableWidth = tables[0].getWidth() + 20;
			int absWidth = (lengthValue / 2) * tables[0].getWidth() + (lengthValue / 2 - 1) * 20;
			int width = setupFrame.getWidth() / 2 - absWidth / 2;
			
			for(int i = 0; i < tables.length / 2; i++) {
				tables[i].setLocation(width + i * tableWidth, 140);
				tables[i + lengthValue / 2].setLocation(width + i * tableWidth, 140 + tables[0].getHeight() + 20);
			}
		}
	}
}
