package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class Crab extends GameEntities{

	
	public Crab(GamePanel gP) {
		super(gP);
		
		direction = "down";
		speed = 3;
		
		//solid area used for crab monster
		solidArea = new Rectangle();
        solidArea.x = 0;
        solidArea.y = 19;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 45;
        solidArea.height = 41;
		//
        
		getMonsterImage();
		
	}
	
	public void getMonsterImage(){ //monster images
        
    	up1 = setup("/monster/crab1");
        up2 = setup("/monster/crab2");
        down1 = setup("/monster/crab1");
        down2 = setup("/monster/crab2");
        left1 = setup("/monster/crab1");
        left2 = setup("/monster/crab2");
        right1 = setup("/monster/crab1");
        right2 = setup("/monster/crab2");
        
    }
	
	public void setAction() { //direction of monster
		
		actionLockCounter++;
		
		if(actionLockCounter == 120) { //changes direction every 2 seconds (120 / 60 = 2)
			
			Random random = new Random();
			int i = random.nextInt(100)+1;
			
			if(i <= 25) {
				direction = "up";
			}
			if(i > 25 && i <= 50) {
				direction = "down";
			}
			if(i > 50 && i <= 75) {
				direction = "left";
			}
			if(i > 75 && i <= 100) {
				direction = "right";
			}
			
			actionLockCounter = 0;
		}
	}
	
	
}
