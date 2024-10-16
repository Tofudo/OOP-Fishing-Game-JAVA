package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;


/**
 *
 * @author Shem Cardoza
 */


public class GameEntities { //sets variables need for entities like player in the game
    
	GamePanel gP;
    public int worldX,worldY;
    public int speed, life, maxLife, stock;
    
    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2,fishLeft,fishRight,none;
    public String direction;
    
    public int spriteCounter = 0;
    public int spriteNum = 1;
    
    
    public Rectangle solidArea = new Rectangle(0 , 0, 48, 48); //default solid area for all entities
    public int solidAreaDefaultX, solidAreaDefaultY; 
    public boolean collision = false;
    public int actionLockCounter = 0;
    
    public GameEntities(GamePanel gP) {
    	this.gP = gP;
    }
    
    public void setAction(){}
    
    public void update() {
    	
    	setAction();
    	
    	collision = false;
    	
    	gP.cChecker.checkTile(this);
    	gP.cChecker.checkOBJ(this, false); //if not player then collision is false
    	gP.cChecker.checkPlayer(this);
    	
    	if(collision == false) { 
    		
        	switch(direction){
            
            case "up":
            	worldY = worldY - speed;
            	break;
            case "down":
            	worldY = worldY + speed;
            	break;
            case "left":
            	worldX = worldX - speed;
            	break;   
            case "right":
            	worldX = worldX + speed;
            	break;
			}
        	
        }
        
	    spriteCounter++;
	    
	    if(spriteCounter > 6) {
	        if(spriteNum == 1) {
	        	spriteNum = 2;
	        }
	        else if(spriteNum == 2) {
	        	spriteNum = 1;
	        }
	        	spriteCounter = 0;
	    }

    	
    }
    
    public void draw(Graphics2D g2) {
    	
    	BufferedImage image = null;
    	
    	int screenX = worldX - gP.player.worldX + gP.player.screenX;
		int screenY = worldY - gP.player.worldY + gP.player.screenY;
		
		
		if(worldX + gP.tileSize > gP.player.worldX - gP.player.screenX &&
			worldX - gP.tileSize < gP.player.worldX + gP.player.screenX &&
			worldY + gP.tileSize > gP.player.worldY - gP.player.screenY &&
			worldY - gP.tileSize < gP.player.worldY + gP.player.screenY) {
		       
		       switch(direction){
		           
		            case "up":
		            	if(spriteNum == 1) {
		            		image = up1;
		            	}
		            	if(spriteNum == 2) {
		            		image = up2;
		            	}
		            	break;
		            case "down":
		            	if(spriteNum == 1) {
		            		image = down1;
		            	}
		            	if(spriteNum == 2) {
		            		image = down2;
		            	}
		            	break;
		            case "left":
		            	if(spriteNum == 1) {
		            		image = left1;
		            	}
		            	if(spriteNum == 2) {
		            		image = left2;
		            	}
		            	break;   
		            case "right":
		            	if(spriteNum == 1) {
		            		image = right1;
		            	}
		            	if(spriteNum == 2) {
		            		image = right2;
		            	}
		        	break;
		            	
		       }
			
		
			g2.drawImage(image, screenX, screenY, gP.tileSize, gP.tileSize, null);
			
		}
    	
    	
    }
    
    
    public BufferedImage setup(String imagePath) { //for image setup in Game
    	
    	
    	UtilityTool tool = new UtilityTool();
    	BufferedImage image = null;
		
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = tool.scaleImage(image, gP.tileSize, gP.tileSize);
			
			     
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return image;
    	
    	
    }
    
}
