package com.peterpl.league.game.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class Table extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public final int collumns, rows;
	private int[] xSizes, ySizes;
	protected TablePart[] parts;
	protected boolean created = false;
	
	public Table(int collumns, int rows) {
		this.collumns = collumns;
		this.rows = rows;
		setLayout(null);
	}
	
	public void setSizes(int[] xSizes, int[] ySizes) {
		this.xSizes = xSizes;
		this.ySizes = ySizes;
	}
	
	public void createTable() {
		if(created) return;
		
		parts = new TablePart[rows];
		if(collumns != xSizes.length || rows != ySizes.length)
			return;
		created = true;
		
		for(int i = 0; i < rows; i++) {
			parts[i] = new TablePart(i);
			
			int previousHeight = 0;
			for(int j = 0; j < i; j++) 
				previousHeight += (ySizes[j] - 1);
			
			parts[i].setLocation(0, previousHeight);
			add(parts[i]);
		}
		int allHeight = 1;
		for(int size : ySizes)
			allHeight += (size - 1);
		
		setSize(parts[0].getWidth(), allHeight);
	}
	
	public void setCollumnColor(int index, Color color) {
		for(int i = 0; i < rows; i++) {
			parts[i].parts[index].setBackground(color);
		}
	}
	
	public void setRowColor(int index, Color color) {
		for(int i = 0; i < collumns; i++) {
			parts[index].parts[i].setBackground(color);
		}
	}
	
	public void setColor(int xIndex, int yIndex, Color color) {
		parts[yIndex].parts[xIndex].setBackground(color);
	}
	
	protected class TablePart extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private int width;
		protected final JPanel[] parts;
		
		public TablePart(int index) {
			setLayout(null);
			
			width = 1;
			for(int size : xSizes)
				width += (size - 1);
			setSize(width, ySizes[index]);
			
			parts = new JPanel[collumns];
			for(int i = 0; i < parts.length; i++) {
				parts[i] = new JPanel();
				parts[i].setSize(xSizes[i], ySizes[index]);
				parts[i].setBorder(new LineBorder(Color.black));
				
				int previousWidth = 0;
				for(int j = 0; j < i; j++) 
					previousWidth += (xSizes[j] - 1);
				
				parts[i].setLocation(previousWidth, 0);
				add(parts[i]);
			}
		}
	}
}
