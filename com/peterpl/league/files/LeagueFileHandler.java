package com.peterpl.league.files;

import java.io.*;
import java.util.*;

public class LeagueFileHandler {

	public static final String userName;
	public static final String leagueDirectory;

	private int day, month, year;
	private String fileName;

	static {
		userName = System.getProperty("user.name");
		leagueDirectory = "C:\\Users\\" + userName + "\\league";
	}

	public void saveFile(LeagueFile leagueFile) {
		try {
			
			createDirectory(leagueDirectory);
			createDirectory(leagueDirectory + "\\saved");
			createFile();
			
			leagueFile.fileName = fileName;
			
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(fileName));
			stream.writeObject(leagueFile);
			stream.close();
			
		} catch (Exception e) {
		}
	}
	
	public void saveFile(LeagueFile leagueFile, String fileName) {
		try {
			
			File file = new File(fileName);
			if(file.exists()) return;
			
			file.createNewFile();
			leagueFile.fileName = fileName;
			
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(fileName));
			stream.writeObject(leagueFile);
			stream.close();
			
		} catch(Exception e) {
		}
	}
	
	public LeagueFile loadFile(File file) {
		try {

			ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file.getPath()));
			LeagueFile leagueFile = (LeagueFile) stream.readObject();
			stream.close();
			
			return leagueFile;
			
		} catch(Exception e) {
			return null;
		}
	}
	
	public LeagueFile[] loadFiles() {
		File directory = new File(leagueDirectory + "\\saved");
		if(!directory.exists()) {
			createDirectory(leagueDirectory);
			createDirectory(leagueDirectory + "\\saved");
		}
		File[] files = directory.listFiles();
		
		int notNull = 0;
		for (int i = 0; i < files.length; i++)
			if(loadFile(files[i]) != null) 
				notNull++;
		
		LeagueFile[] leagueFiles = new LeagueFile[notNull];
		for(int i = 0; i < leagueFiles.length; i++) {
			LeagueFile leagueFile = loadFile(files[i]);
			if(leagueFile != null) {
				leagueFiles[leagueFiles.length - 1 - i] = leagueFile;
			}
		}
		
		return leagueFiles;
	}
	
	private void createFile() throws IOException {
		
		Calendar date = new GregorianCalendar();
		day = date.get(Calendar.DAY_OF_MONTH);
		month = date.get(Calendar.MONTH) + 1;
		year = date.get(Calendar.YEAR);
		
		int counter = 0;

		String dayStr = day + "_";
		String monthStr = month + "_";
		String yearStr = year + "_";

		if (day < 10)
			dayStr = "0" + dayStr;
		if (month < 10)
			monthStr = "0" + monthStr;

		String basicFileName = "league" + dayStr + monthStr + yearStr;
		String fileName = basicFileName + counter + ".lig";
		
		String fullFileName = leagueDirectory + "\\saved\\" + fileName;
		this.fileName = fullFileName;
		
		File file = new File(fullFileName);

		if (file.exists()) {
			do {
				counter++;
				fileName = basicFileName + counter + ".lig";
				fullFileName = leagueDirectory + "\\saved\\" + fileName;
				this.fileName = fullFileName;
				
				file = new File(fullFileName);
				
			} while(file.exists());
			
			file.createNewFile();
			
		} else {
			file.createNewFile();
		}
	}
	
	private void createDirectory(String path) {
		
		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdir();
		}
	}
}
