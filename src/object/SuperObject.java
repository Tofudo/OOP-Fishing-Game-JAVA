package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityTool;
import tile.TileManager;

//handles objects in the game

public class SuperObject {
	
	TileManager tM;
	
	public BufferedImage image;
	public String name;
	public boolean collision = true;
	
	public int worldX, worldY;
	
	public Rectangle solidArea = new Rectangle(0,0,48,48); 
	public int solidAreadefaultX = 0;
	public int solidAreadefaultY = 0; 
	
	UtilityTool tool = new UtilityTool();
	
	
	public void draw(Graphics2D g2, GamePanel gP) { //draws objectsc on screen depending their set location
		
		int screenX = worldX - gP.player.worldX + gP.player.screenX;
		int screenY = worldY - gP.player.worldY + gP.player.screenY;
		
		
		if(worldX + gP.tileSize > gP.player.worldX - gP.player.screenX &&
			worldX - gP.tileSize < gP.player.worldX + gP.player.screenX &&
			worldY + gP.tileSize > gP.player.worldY - gP.player.screenY &&
			worldY - gP.tileSize < gP.player.worldY + gP.player.screenY) {
		
			g2.drawImage(image, screenX, screenY, gP.tileSize, gP.tileSize, null);
			
		}
	}
}
