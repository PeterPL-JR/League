package com.peterpl.league.files;

import java.util.*;

import java.awt.image.*;

import com.peterpl.league.*;

public class FlagsSheet {
	private ArrayList<GraphicsImage> images;
	private ArrayList<String[]> langNames;
	public static final String[] landsNames = {"europe", "south_america", "oceania", "north_america", "africa", "asia"};

	private int width, height;
	private String landName;

	public FlagsSheet(String landName, int width, int height) {
		this.width = width;
		this.height = height;
		this.landName = landName;

		images = new ArrayList<>();
		loadImage();
		loadNames();

		for (int i = 0; i < images.size(); i++) {

			String[] names = new String[CountriesFileHandler.langs.length];
			for (int j = 0; j < names.length; j++)
				names[j] = langNames.get(j)[i];

			FlagTeam.flags.add(new FlagTeam(images.get(i), names, landName));
		}
	}

	private void loadImage() {

		// Load BufferedImage
		GraphicsImage graphImage = new GraphicsImage("flags/flags_" + landName + ".graph");
		BufferedImage image = graphImage.getImage();
		int width = (int) (image.getWidth());
		int height = (int) (image.getHeight());

		// Create Arrays
		images = new ArrayList<>();
		int[] pixelsRaw = new int[width * height];
		int[][] pixels = new int[width][height];
		image.getRGB(0, 0, width, height, pixelsRaw, 0, width);

		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				pixels[x][y] = pixelsRaw[x + y * width];

		// Offsets
		for (int yy = 0; yy < this.height; yy++) {
			for (int xx = 0; xx < this.width; xx++) {

				// Create Flags Arrays
				BufferedImage flagImage = new BufferedImage(FlagTeam.Width, FlagTeam.Height,
						BufferedImage.TYPE_INT_ARGB);
				for (int y = 0; y < FlagTeam.Height; y++)
					for (int x = 0; x < FlagTeam.Width; x++)
						flagImage.setRGB(x, y, pixels[x + xx * FlagTeam.Width][y + yy * FlagTeam.Height]);

				// Add flag to array list
				images.add(new GraphicsImage(flagImage));
			}
		}
	}

	private void loadNames() {
		langNames = new ArrayList<>();
		for (String lang : CountriesFileHandler.langs) {
			try {
				langNames.add(League.countriesFile.loadFile(landName, lang));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void loadFlagsSheets() {
		new FlagsSheet("europe", 11, 5);
		new FlagsSheet("south_america", 5, 2);
		
		new FlagsSheet("oceania", 4, 2);
		new FlagsSheet("north_america", 6, 4);
		
		new FlagsSheet("africa", 13, 4);
		new FlagsSheet("asia", 11, 4);
	}
}
