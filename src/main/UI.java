package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.text.AttributedString;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import entity.Fish;

import object.Heart;
import object.Stock;

public class UI {
	
	GamePanel gP;
	Fish f;
	Graphics2D g2;
	Font arial_40;
	BufferedImage heartImage;
	BufferedImage stockImage;
	
	public int commandNum = 0;
	
	private int elapsedSeconds, min = 0, sec, passMin, passSec;
	
	private boolean updateFile = false, readFile = true; //for file reading and update
	
	private String seconds, mins;
	
	public UI(GamePanel gP) {
		
		this.gP = gP;
		
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		Heart heart = new Heart(gP);
		Stock stock = new Stock(gP);
		heartImage = heart.image;
		stockImage = stock.image;
		
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		
		if(gP.gameState == gP.titleState) {
			
			min = 0;
			updateFile = true;
			
			if(readFile!=false) { //to read highscore at the titlescreen
				
				try {
					
					File file = new File("File.txt");
					Scanner scan = new Scanner(file);
					
				    int newMinutes = scan.nextInt();
				    int newSeconds = scan.nextInt();
				    
				    passMin = newMinutes;
				    passSec = newSeconds;
				    
					scan.close();
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				}
				readFile = false;
			}
			
			drawTitleScreen();
			
		}
		
		if(gP.gameState == gP.playState) {
			
			drawHUD();
			drawInstruct();
			drawTime();
			
		}
		
		if(gP.gameState == gP.gameOverState) {
			
			//game progress storage
			if(updateFile == true) {
				
				try {
					
					sec = elapsedSeconds;
					
					File file = new File("File.txt");
					Scanner scan = new Scanner(file);
					
					if (scan.hasNextInt()) {
						
				        int newMinutes = scan.nextInt();
				        
				        if (scan.hasNextInt()) {
				        	
				            int newSeconds = scan.nextInt();

				            try (FileWriter fileW = new FileWriter(file)) {
				            	
				                // Write the updated time to the same file, maintaining the format
				            	if(min >= newMinutes) {
				            		
				            		if(sec >= newSeconds){
				            		
				            			fileW.write(min + "\n" + sec);
				            		}
				            		else{
					            		
				            			fileW.write(min + "\n" + newSeconds);
				            		}
				            		
				            	}
				            	
				            	else if(min < newMinutes){
				            		
				            		fileW.write(newMinutes + "\n" + newSeconds);
				            		
				            	}
				            	
				            } catch (IOException e) {
				                e.printStackTrace();
				            }
				        		            
				        } else {
				            System.out.println("File does not contain the seconds on the second line.");
				        }
				    } else {
				        System.out.println("File does not contain the minutes on the first line.");
				    }
					
					scan.close();
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				}
				
				updateFile = false;
				
			}
			//
			
			readFile = true;
			drawGameOver();
			
		}
		
		
	}
	
	public void drawTitleScreen() { //displays text at the start of the game (title, etc.)
		
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, gP.screenWidth, gP.screenHeight);

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70F));
		String text = "STRANDEAD";
		String selector = "->";
		int x = centerText(text) - 75;
		int y = (gP.tileSize * 4) - 30;


		g2.setColor(Color.WHITE);
		g2.drawString(text.substring(0, 5), x, y);


		Font bold = g2.getFont().deriveFont(Font.BOLD, 125F);
		AttributedString attributedText = new AttributedString(text.substring(5));
		attributedText.addAttribute(TextAttribute.FOREGROUND, Color.RED);
		attributedText.addAttribute(TextAttribute.SIZE, 90F);
		attributedText.addAttribute(TextAttribute.FONT, bold);
		g2.drawString(attributedText.getIterator(), x + g2.getFontMetrics().stringWidth("STRAN"), y);
		
		//HighScore
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
		g2.setColor(Color.WHITE);
		text = "Highscore:";
		x = centerText(text);
		y += gP.tileSize * 1.7;
		g2.drawString(text, x, y);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,38F));
		if(passMin == 0) {
			g2.setColor(Color.WHITE);
			text = "" + passSec + " sec";
			x = centerText(text);
			y += gP.tileSize * 1.2;
			g2.drawString(text, x, y);
		}
		else {
			g2.setColor(Color.WHITE);
			text = ""+ passMin + " min and " + passSec + " sec";
			x = centerText(text);
			y += gP.tileSize * 1.1;
			g2.drawString(text, x, y);
		}
		//

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));

		text = "PLAY";
		x = centerText(text);
		y += gP.tileSize * 2.8;
		g2.setColor(Color.WHITE);
		g2.drawString(text, x, y);
		if (commandNum == 0) {
		    g2.drawString(selector, x - 45, y);
		}

		text = "EXIT";
		x = centerText(text);
		y += gP.tileSize * 1.0;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
		    g2.drawString(selector, x - 52, y);
		}
	}
	
	
	public void drawHUD() { //draws player life and fish stock on screen
		
		g2.setFont(arial_40);
		
		g2.setColor(Color.BLACK);
		g2.drawImage(heartImage, gP.tileSize/2, gP.tileSize/3, gP.tileSize, gP.tileSize, null);
		g2.drawString(" " + gP.player.life, 75, 50);
		g2.drawImage(stockImage, gP.tileSize/2, (gP.tileSize * 2)-20, gP.tileSize, gP.tileSize, null);
		g2.drawString(" " + gP.player.stock, 75, 112);
		
	}
	
	
	public void drawTime() { //draws playtime game on screeen
		
		//clock logic idk but works
		long nowTime = System.currentTimeMillis();
        elapsedSeconds = (int)((nowTime - gP.clockStart) / 1000);
        
        if(elapsedSeconds > 59) {
        	min+=1;
        	elapsedSeconds = 0;
        	gP.clockStart = System.currentTimeMillis(); //resets clock in GamePanel
        }
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,45F));
		
        seconds = String.valueOf(elapsedSeconds); 
        mins = String.valueOf(min);
		g2.setColor(Color.BLACK);
		
		g2.drawString(mins + ":" + seconds, (gP.tileSize/2)+5, 190);
		
	}
	
	
	public void drawInstruct() { //draws instruction on screen
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20F));
		String text = "Press and hold 'F' key to fish";
		g2.drawString(text, (10*gP.tileSize)+20, (1*gP.tileSize)-10);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,22F));
		text = "Avoid the Crabs!";
		g2.drawString(text, (12*gP.tileSize)+10, (1*gP.tileSize)+25);
		
	}
	
	
	public void drawGameOver() { //displays when the game is over 
		
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, gP.screenWidth, gP.screenHeight);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,105F));
		String text = "GAME OVER";
		String selector = "->";
		int x = centerText(text); 
		int y = gP.tileSize*3;
		
		g2.setColor(Color.RED);
		g2.drawString(text, x, y);
		
		
		//total time the player survived
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));
		g2.setColor(Color.WHITE);
		text = "You survived for:";
		x = centerText(text);
		y += gP.tileSize * 1.6;
		g2.drawString(text, x, y);
		
		if(min == 0) {
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN,45F));
			g2.setColor(Color.WHITE);
			text = "" + seconds + " sec";
			x = centerText(text);
			y += gP.tileSize * 1.3;
			g2.drawString(text, x, y);
		}
		else {
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN,45F));
			g2.setColor(Color.WHITE);
			text = ""+ mins + " min and " + seconds + " sec";
			x = centerText(text);
			y += gP.tileSize * 1.3;
			g2.drawString(text, x, y);
		}
		
		
		//buttons
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,30F));
		g2.setColor(Color.WHITE);
		text = "PLAY AGAIN";
		x = centerText(text);
		y += gP.tileSize * 3.0;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(selector, x-45, y);
		}
		
		text = "QUIT";
		x = centerText(text);
		y += gP.tileSize * 1.0;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(selector, x-52, y);
		}
		
	}


	public void drawFishCaught() { //draws text on screen when the player catches a fish
		
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,30F));
		g2.setColor(Color.BLACK);
		
		String text = "You caught a " + gP.player.fishName;
		int x = centerText(text);
		int y = (gP.screenHeight/2) - 170;
		
		g2.drawString(text, x, y);
		
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,50F));
		g2.setColor(Color.BLACK);
		text = "+ " + gP.player.valueToAdd;
		x = centerText(text);
		y = (gP.screenHeight/2) - 110;
		
		g2.drawString(text, x, y);
		
		if(gP.player.stock >= 100) {
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,50F));
			g2.setColor(Color.BLACK);
			text = "Inventory is full";
			x = centerText(text);
			y = (gP.screenHeight/2) - 50;
			
			g2.drawString(text, x, y);
		}
		
	}
	
	
	public int centerText(String text) { //centers the text displayed
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gP.screenWidth/2 - length/2;
		
		return x;
	}
	
}
