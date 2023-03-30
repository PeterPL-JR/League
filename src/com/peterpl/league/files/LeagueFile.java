package com.peterpl.league.files;

import java.io.*;
import java.util.*;

import com.peterpl.league.*;

public class LeagueFile implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String leagueName;
	public String fileName;
	public String gameMode;
	
	public int teamsCount;
	public int groupsCount;
	public int knockoutRounds;
	public boolean thirdPlace;
	public boolean extraTeamsTable;
	
	public int day, month, year;
	public int hour, minute, second;
	public String dateText, hourText;
	
	public Team[][] groupTeams;
	public Match[][] groupMatches;
	public Match[][] knockoutMatches;
	
	public Team[] rankingTeams;
	public Team[] extraTeams;
	public int[] extraIndexes;
	
	public LeagueFile() {
		leagueName = League.leagueName;
		gameMode = League.gameMode;
		
		teamsCount = League.teamsCount;
		groupsCount = League.groupsCount;
		knockoutRounds = League.knockoutRounds;
		thirdPlace = League.thirdPlace;
		extraTeamsTable = League.extraTable;
		
		groupTeams = League.groupsTeams;
		groupMatches = League.groupsMatches;
		knockoutMatches = League.knockoutMatches;
		
		rankingTeams = League.advancesTeams;
		extraTeams = League.extraTeams;
		extraIndexes = League.handlingGroups.extraIndexes;
		createDate();
	}
	
	private void createDate() {
		
		Calendar date = new GregorianCalendar();
		day = date.get(Calendar.DAY_OF_MONTH);
		month = date.get(Calendar.MONTH) + 1;
		year = date.get(Calendar.YEAR);
		
		hour = date.get(Calendar.HOUR_OF_DAY);
		minute = date.get(Calendar.MINUTE);
		second = date.get(Calendar.SECOND);
		
		String dayText = (day < 10 ? "0" : "") + day;
		String monthText = (month < 10 ? "0" : "") + month;
		String yearText = "" + year;
		
		String hourText = (hour < 10 ? "0" : "") + hour;
		String minuteText = (minute < 10 ? "0" : "") + minute;
		String secondText = (second < 10 ? "0" : "") + second;
		
		dateText = dayText + "." + monthText + "." + yearText;
		hourText = hourText + ":" + minuteText + ":" + secondText;
	}
	
	public static void sortByDate(LeagueFile[] files, int[] table) {
		int length = files.length;
		
		for(int i = 1; i < length; i++) {
			for(int j = length - 1; j >= i; j--) {
				
				// SORT BY YEAR
				if(files[table[j - 1]].year < files[table[j]].year) {
					
					int buffer = table[j - 1];
					table[j - 1] = table[j];
					table[j] = buffer;
					
				} else if(files[j - 1].year == files[table[j]].year) {
					
					// SORT BY MONTH
					if(files[table[j - 1]].month < files[table[j]].month) {
						
						int buffer = table[j - 1];
						table[j - 1] = table[j];
						table[j] = buffer;
						
					} else if(files[j - 1].month == files[table[j]].month) {
						
						// SORT BY DAY
						if(files[table[j - 1]].day < files[table[j]].day) {
							
							int buffer = table[j - 1];
							table[j - 1] = table[j];
							table[j] = buffer;
							
						} else if(files[j - 1].day == files[table[j]].day) {
							
							// SORT BY HOUR
							if(files[table[j - 1]].hour < files[table[j]].hour) {
								
								int buffer = table[j - 1];
								table[j - 1] = table[j];
								table[j] = buffer;
							}
						}
					}
				}
			}
		}
	}
}
