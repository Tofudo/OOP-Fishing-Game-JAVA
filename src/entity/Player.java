package entity;

import main.GamePanel;
import main.KeyInputs;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Shem Cardoza
 */

public class Player extends GameEntities {

    KeyInputs keyIn;
    
    public final int screenX;
    public final int screenY;
    
    private long startTime;
    private int secondsPassed;
    
    private int fishingValue;
    public int valueToAdd = 0;
    private boolean fishValueGenerated = false;
    
    private boolean cook = false;
    public boolean fishCaught = false;
    
    public String fishName;
    

    public Player(GamePanel gP, KeyInputs keyIn) {
    	
    	super(gP);
    	
        this.keyIn = keyIn;
        
        
        screenX = gP.screenWidth/2 - (gP.tileSize/2);
        screenY = gP.screenHeight/2 - (gP.tileSize/2);
        
        
        setDefaultValues();
        getPlayerImage();
        
        //solid area used for player
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        //
        
        startTime = System.currentTimeMillis();
        secondsPassed = 0;
    }
    

    public void setDefaultValues() {
    	//default player position and speed
    	maxLife = 100;
    	life = maxLife;
        worldX = gP.tileSize * 13;
        worldY = gP.tileSize * 9;
        speed = 3;
        direction = "right";
    }
    
    
    public void getPlayerImage(){ //this method uses setup method in GameEntities for player images
        
    	up1 = setup("/player/left1");
        up2 = setup("/player/right1");
        down1 = setup("/player/left1");
        down2 = setup("/player/right1");
        left1 = setup("/player/left1");
        left2 = setup("/player/left2");
        right1 = setup("/player/right1");
        right2 = setup("/player/right2");
        fishLeft = setup("/player/fish1left");
        fishRight = setup("/player/fish1right");
        none = setup("/player/none");
        
    }
    
    
    public void update() { //used if the user presses a key function
        
    	if(keyIn.upKey == true || keyIn.downKey == true || keyIn.leftKey == true || keyIn.rightKey == true || keyIn.fishKey == true) {
    	
    	
	        if (keyIn.upKey == true) {
	            direction = "up";
	            
	        } else if (keyIn.downKey == true) {
	            direction = "down";
	            
	        } else if (keyIn.leftKey == true) {
	            direction = "left";
	            
	        } else if (keyIn.rightKey == true) {
	            direction = "right";
	            
	        } else if (keyIn.fishKey == true && worldX <= 10 *gP.tileSize) {
	            direction = "fishLeft";
	            getFish(); //calls getFish function
	            
	        } else if (keyIn.fishKey == true && worldX >= 20 *gP.tileSize) {
	            direction = "fishRight";
	            getFish(); //calls getFish function
	            
	        } else if (keyIn.fishKey == true && worldY <= 7 * gP.tileSize) {
	        	if(worldX < 15 * gP.tileSize) {
	        		direction = "fishLeft";
	        	}
	        	if(worldX >= 15 * gP.tileSize) {
	        		direction = "fishRight";
	        	}
	            getFish(); //calls getFish function
	            
	        } else if (keyIn.fishKey == true && worldY >= 14 * gP.tileSize) {
	        	if(worldX < 15 * gP.tileSize) {
	        		direction = "fishLeft";
	        	}
	        	if(worldX >= 15 * gP.tileSize) {
	        		direction = "fishRight";
	        	}
	            getFish(); //calls getFish function
	            
	        }
        
        
	        //checks if the player collides with solid tiles
	        collision = false;
	        gP.cChecker.checkTile(this);
	        
	        //checks if the player collides with objects in game
	        int objIndex = gP.cChecker.checkOBJ(this, true);
	        cook(objIndex);
	        
	        //checks if monster and player collides
	        int monsterIndex = gP.cChecker.checkEntity(this, gP.monster); //player and the monster as target
	        interactMonster(monsterIndex);
	        
	        
	        //for player movement
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
	        	case "fishLeft":
	        		break;
	        	case "fishRight":
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
	    	//
	    	
	    if (keyIn.fishKey == false) {
	    	
	    	resetTime(); //calls resetTime method
	           
	    }
	    	
	    if(keyIn.upKey == false && keyIn.downKey == false && keyIn.leftKey == false && keyIn.rightKey == false && keyIn.fishKey == false){
	    		
	    	direction = "none"; //displays static player image if no key is pressed
	    }
	    	
	    
    }
    
    
    public void cook(int i) { //method used when player consumes a fish (touches fire)
    	
    	if(i!=999) { //checks if the player collides with fire
    		
    		if(cook == true) {
    			
	    		gP.startTime = System.currentTimeMillis();
	        	gP.nowTime = System.currentTimeMillis();
	        	
	        	cook = false;
    		}
    		
    		while(life < life + stock && stock >= 0 && life < maxLife) { //adds HP and decreases stock when player consumes a fish
    			
    			life ++;
    			stock --;

    		}
    	}
    	
    	
    }
    
    
    public void interactMonster(int i) { //checks if player touches monster
    	
    	if(i != 999) {
    		
    		life -= life; //deducts life when collided with monster
    		
    	}
    	
    }
    
    
    public void resetTime() { //resets fishing after being used
    	
    	startTime = System.currentTimeMillis();
        secondsPassed = 0;
        
        fishValueGenerated = false;
    	
    }
    
    
    public void getFish() { //player gets a fish after some seconds after holding f key
    	
    	long currentTime = System.currentTimeMillis();
    	
    	int elapsedSeconds = (int)((currentTime - startTime) / 1000);
    	
    	
    	if(!fishValueGenerated) { //checks if the fishValueGenerated is false to avoid duplication
    		
    		fishingValue = genNum(); //calls genNum method in GameEntities
    		
    		fishValueGenerated = true; //sets it to true
    		
    	}
    	
    	
    	if(elapsedSeconds > secondsPassed) {
    		
    		secondsPassed = elapsedSeconds;
    		
    		
    		if(secondsPassed == fishingValue) { //checks if fishing time and random value (fishValue) is equal
    			
    			cook = true;
    			fishCaught = true;
	    		
	    		Fish f = new Fish();
	    		f.genFish(); //calls genFish function in Fish
	    		
	    		
	    		valueToAdd = f.passFishValue();
	    		fishName = f.passFishName();
	    		
	    		stock += Math.min(valueToAdd, 100 - stock);
	            
    		}
    		
    	}
    	
    	
    	
    }
    
    
    public void draw(Graphics2D g2) { //draws player image
    	  
    	
        BufferedImage image = null;
        
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
             case "fishLeft":
             	
             	image = fishLeft;
             	
             	break;
             case "fishRight":
             	
         		image = fishRight;
         	
         	break;
             case "none":
             	
         		image = none;
         	
         	break;
             	
        }
        
        g2.drawImage(image, screenX, screenY, gP.tileSize, gP.tileSize, null);
        
     }
    
    
    public int genNum() { //generates a number to match player fishing
    	
    	int num = 0;
    	
    	int max = 15;
    	int min = 5;
    	
    	num = (int)Math.floor(Math.random() * (max - min + 1) + min);
    	
    	return num;
    	
    }

}
