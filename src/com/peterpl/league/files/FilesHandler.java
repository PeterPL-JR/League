package com.peterpl.league.files;

import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;

import com.peterpl.league.*;

public class FilesHandler {
	private JFileChooser fileHandler;
	
	public FilesHandler() {
		fileHandler = new JFileChooser();
		
		FileFilter[] filters = fileHandler.getChoosableFileFilters();
		fileHandler.removeChoosableFileFilter(filters[0]);
		fileHandler.setFileFilter(new FileNameExtensionFilter("League Files (.lig)", "lig"));
	}
	
	public void exportFile(LeagueFile leagueFile) {
		fileHandler.setDialogTitle("Export File");

		if (fileHandler.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File getFile = fileHandler.getSelectedFile();
			String fileName = getFile.getAbsolutePath();

			String fileEnd = fileName.substring(fileName.length() - 4, fileName.length());
			String fileExtension = fileEnd.equals(".lig") ? "" : ".lig";
			String fullFileName = fileName + fileExtension;

			File file = new File(fullFileName);
			String onlyFileName = fullFileName.substring(0, fullFileName.length() - 4);
			String newName = fullFileName;
			int counter = 0;

			while (file.exists()) {
				newName = onlyFileName + "_" + counter + ".lig";
				file = new File(newName);
				counter++;
			}
			fullFileName = newName;
			League.leagueFile.saveFile(leagueFile, fullFileName);
		}
	}
	
	public void importFile() {
		fileHandler.setDialogTitle("Import File");
		
		if(fileHandler.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = fileHandler.getSelectedFile();
			LeagueFile leagueFile = League.leagueFile.loadFile(file);
			
			if(leagueFile != null) {
				League.leagueFile.saveFile(leagueFile);
			}
		}
	}
}
