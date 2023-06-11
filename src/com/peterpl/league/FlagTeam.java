package com.peterpl.league;

import java.util.*;

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
			new ExceptionFlag("Iceland", 1.12),
			new ExceptionFlag("Faroe Islands", 1.1), 
			new ExceptionFlag("Israel", 1.1),
			new ExceptionFlag("San Marino", 1.1), 
			new ExceptionFlag("Slovakia", 1.05), 
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
		return new FlagTeam(flag.getScaledImage(width, height, size), names, landName);
	}
	
	public static FlagTeam getCountry(String name) {
		for(FlagTeam bufferTeam : flags) {
			if(Game.indexOf(bufferTeam.names, League.countriesFile.encodeString(name)) != -1) {
				return bufferTeam;
			}
		}
		return null;
	}

	public static boolean findCountry(String string, FlagTeam team, JLabel label, JPanel panel,
			ArrayList<FlagTeam> flags) {
		for (FlagTeam flag : flags) {
			for (String name : flag.names) {
				if (League.countriesFile.encodeString(string).equals(name)) {
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
	
	public static String getLand(String team) {
		for(FlagTeam flagTeam : FlagTeam.flags) {
			if(Game.indexOf(flagTeam.names, League.countriesFile.encodeString(team)) != -1) {
				return flagTeam.landName;
			}
		}
		return null;
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
