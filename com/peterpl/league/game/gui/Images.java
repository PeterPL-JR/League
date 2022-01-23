package com.peterpl.league.game.gui;

import javax.swing.*;

public class Images extends ImageIcon {
	private static final long serialVersionUID = 1L;
	
	public static Images arrowLeft = new Images("arrow_left.png");
	public static Images arrowRight = new Images("arrow_right.png");

	public static Images cup = new Images("cup.png");
	public static Images delete = new Images("delete.png");

	public static Images placeGold = new Images("place_gold.png");
	public static Images placeSilver = new Images("place_silver.png");
	public static Images placeBrown = new Images("place_brown.png");

	public static Images square_default = new Images("square_default.png");
	public static Images square_checked = new Images("square_checked.png");
	
	public Images(String path) {
		super(Images.class.getResource("/textures/" + path));
	}
	
	public int getWidth() {
		return super.getIconWidth();
	}
	
	public int getHeight() {
		return super.getIconHeight();
	}
}
