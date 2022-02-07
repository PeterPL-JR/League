package com.peterpl.league;

import java.util.*;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

import com.peterpl.league.files.*;
import com.peterpl.league.methods.*;

public class FlagTeam {
	public static ArrayList<FlagTeam> flags = new ArrayList<>();
	public static ExceptionFlag[] exceptionFlags;

	public static final int Width = 100;
	public static final int Height = 64;

	static {
		ExceptionFlag[] exceptions = { 
			new ExceptionFlag("Switzerland", 1.25), 
			new ExceptionFlag("Denmark", 1.1),
			new ExceptionFlag("Norway", 1.1), 
			new ExceptionFlag("Iceland", 1.1),
			new ExceptionFlag("Faroe Islands", 1.1), 
			new ExceptionFlag("Israel", 1.1),
			new ExceptionFlag("San Marino", 1.1), 
		};
		FlagTeam.exceptionFlags = exceptions;
	}

	public final GraphicsImage flag;
	public final String[] names;
	public final String landName;

	public FlagTeam(GraphicsImage flag, String[] names, String landName) {
		this.flag = flag;
		this.names = names;
		this.landName = landName;
	}

	public FlagTeam getScaledFlag(int width, int height, boolean exceptions) {
		double size = 1;

		if (exceptions) {
			for (ExceptionFlag e : exceptionFlags) {
				if (e.name.equals(this.names[0])) {
					size = e.size;
					break;
				}
			}
		}

		Image img = flag.getImage().getScaledInstance((int) (width * size), (int) (height * size), Image.SCALE_DEFAULT);
		BufferedImage bufImage = new BufferedImage((int) (img.getWidth(null) * size),
				(int) (img.getHeight(null) * size), 2);

		Graphics2D graphics = bufImage.createGraphics();
		graphics.drawImage(img, 0, 0, null);
		return new FlagTeam(new GraphicsImage(img), names, landName);
	}

	public static boolean findCountry(String string, FlagTeam team, JLabel label, JPanel panel,
			ArrayList<FlagTeam> flags) {
		for (FlagTeam flag : flags) {
			for (String name : flag.names) {
				if (string.equals(name)) {
					team = flag;

					if (panel != null) {
						int width = flag.flag.getIconWidth();
						int height = flag.flag.getIconHeight();

						int oldX = label.getX();
						int oldWidth = label.getWidth();
						int middleX = oldX + (oldWidth / 2);

						int oldY = label.getY();
						int oldHeight = label.getHeight();
						int middleY = oldY + (oldHeight / 2);

						label.setSize(width, height);
						label.setLocation(middleX - width / 2, middleY - height / 2);
					}

					label.setIcon(team.flag);
					label.repaint();
					return true;
				}
			}
		}
		label.setIcon(GraphicsImage.defaultFlag);
		return false;
	}

	public static ArrayList<FlagTeam> getAllScaledFlags(int width, int height, boolean exceptions) {

		ArrayList<FlagTeam> flagTeams = new ArrayList<>();
		for (FlagTeam flag : FlagTeam.flags) {
			flagTeams.add(flag.getScaledFlag(width, height, exceptions));
		}
		return flagTeams;
	}

	public static class ExceptionFlag {
		public final String name;
		public final double size;

		public ExceptionFlag(String name, double size) {
			this.name = name;
			this.size = size;
		}
	}
}
