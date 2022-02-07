package com.peterpl.league.files;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

public class GraphicsImage extends ImageIcon {
	private static final long serialVersionUID = 1L;

	// Arrow Buttons
	public static GraphicsImage arrowLeft = new GraphicsImage("arrow_left.graph");
	public static GraphicsImage arrowRight = new GraphicsImage("arrow_right.graph");

	// GUI Elements
	public static GraphicsImage squareDefault = new GraphicsImage("square_default.graph");
	public static GraphicsImage squareChecked = new GraphicsImage("square_checked.graph");
	public static GraphicsImage delete = new GraphicsImage("delete.graph");
	
	// Winner Screen Elements
	public static GraphicsImage placeGold = new GraphicsImage("place_gold.graph");
	public static GraphicsImage placeSilver = new GraphicsImage("place_silver.graph");
	public static GraphicsImage placeBrown = new GraphicsImage("place_brown.graph");
	
	public static GraphicsImage cup = new GraphicsImage("cup.graph");
	public static GraphicsImage defaultFlag = new GraphicsImage("default_flag.graph");

	public GraphicsImage(String path) {
		super(GraphicsImage.class.getResource("/textures/" + path));
	}
	
	public GraphicsImage(Image image) {
		super(image);
	}
	
	public BufferedImage getImage() {
		Image image = super.getImage();
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		
		BufferedImage bImage = new BufferedImage(width, height, 2);
		Graphics graphics = bImage.createGraphics();
		graphics.drawImage(image, 0, 0, null);
		graphics.dispose();
		
		return bImage;
	}
	
	public int getWidth() {
		return super.getIconWidth();
	}

	public int getHeight() {
		return super.getIconHeight();
	}
}
