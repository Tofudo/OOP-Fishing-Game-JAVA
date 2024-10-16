package main;

import entity.GameEntities;

//this is used for player colliding with other objects in the game

public class CollisionChecker {
	
	GamePanel gP;
	
	
	public CollisionChecker(GamePanel gP) {
		
		this.gP = gP;
		
	}
	
	public void checkTile(GameEntities entity) { //checks tile and their collision state to the player
		
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gP.tileSize;
		int entityRightCol = entityRightWorldX/gP.tileSize;
		int entityTopRow = entityTopWorldY/gP.tileSize;
		int entityBottomRow = entityBottomWorldY/gP.tileSize;
		
		int tileNum1, tileNum2;
		
		
		switch(entity.direction) {
			case "up":
				entityTopRow = (entityTopWorldY - entity.speed)/gP.tileSize;
				tileNum1 = gP.tM.mapTileNum[entityLeftCol][entityTopRow];
				tileNum2 = gP.tM.mapTileNum[entityRightCol][entityTopRow];
				if(gP.tM.tile[tileNum1].collision == true || gP.tM.tile[tileNum2].collision == true) {
					entity.collision = true;
				}
				break;
			case "down":
				entityBottomRow = (entityBottomWorldY + entity.speed)/gP.tileSize;
				tileNum1 = gP.tM.mapTileNum[entityLeftCol][entityBottomRow];
				tileNum2 = gP.tM.mapTileNum[entityRightCol][entityBottomRow];
				if(gP.tM.tile[tileNum1].collision == true || gP.tM.tile[tileNum2].collision == true) {
					entity.collision = true;
				}
				break;
			case "left":
				entityLeftCol = (entityLeftWorldX - entity.speed)/gP.tileSize;
				tileNum1 = gP.tM.mapTileNum[entityLeftCol][entityTopRow];
				tileNum2 = gP.tM.mapTileNum[entityLeftCol][entityBottomRow];
				if(gP.tM.tile[tileNum1].collision == true || gP.tM.tile[tileNum2].collision == true) {
					entity.collision = true;
				}
				break;
			case "right":
				entityRightCol = (entityRightWorldX + entity.speed)/gP.tileSize;
				tileNum1 = gP.tM.mapTileNum[entityRightCol][entityTopRow];
				tileNum2 = gP.tM.mapTileNum[entityRightCol][entityBottomRow];
				if(gP.tM.tile[tileNum1].collision == true || gP.tM.tile[tileNum2].collision == true) {
					entity.collision = true;
				}
			break;
		}
		
		
	}
	
	
	public int checkOBJ(GameEntities entity, boolean player) {
		
		
		int index = 999;
		
		for(int i = 0; i < gP.obj.length; i++) {
			
			
			if(gP.obj[i] != null) {
				
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				
				gP.obj[i].solidArea.x = gP.obj[i].worldX + gP.obj[i].solidArea.x;
				gP.obj[i].solidArea.y = gP.obj[i].worldY + gP.obj[i].solidArea.y;
				
				
				switch(entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					if(entity.solidArea.intersects(gP.obj[i].solidArea)) {
						if(gP.obj[i].collision == true) {
							entity.collision = true;
						}
						
						if(player == true) {
							index = i;
						}
					}
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					if(entity.solidArea.intersects(gP.obj[i].solidArea)) {
						if(gP.obj[i].collision == true) {
							entity.collision = true;
						}
						
						if(player == true) {
							index = i;
						}
					}
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					if(entity.solidArea.intersects(gP.obj[i].solidArea)) {
						if(gP.obj[i].collision == true) {
							entity.collision = true;
						}
						
						if(player == true) {
							index = i;
						}
					}
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(gP.obj[i].solidArea)) {
						if(gP.obj[i].collision == true) {
							entity.collision = true;
						}
						
						if(player == true) {
							index = i;
						}
					}
					break;
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gP.obj[i].solidArea.x = gP.obj[i].solidAreadefaultX;
				gP.obj[i].solidArea.y = gP.obj[i].solidAreadefaultY;
			}
			
			
		}
			
		
		
		return index;
		
	}
	
	//checks monster collision
	public int checkEntity(GameEntities entity, GameEntities[] target) { //checks if crab monster collides with player entity 
		
		int index = 999;
		
		for(int i = 0; i < target.length; i++) {
			
			
			if(target[i] != null) {
				
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				
				target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
				target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;
				
				
				switch(entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					if(entity.solidArea.intersects(target[i].solidArea)) {
						entity.collision = true;
						index = i;
					}
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					if(entity.solidArea.intersects(target[i].solidArea)) {
						entity.collision = true;
						index = i;
					}
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					if(entity.solidArea.intersects(target[i].solidArea)) {
						entity.collision = true;
						index = i;
					}
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(target[i].solidArea)) {
						entity.collision = true;
						index = i;
					}
					break;
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;
			}
		}
		
		return index;
		
	}
	
	public void checkPlayer(GameEntities entity) { //checks if player collides with crab monster entity
		
		
		entity.solidArea.x = entity.worldX + entity.solidArea.x;
		entity.solidArea.y = entity.worldY + entity.solidArea.y;
		
		
		gP.player.solidArea.x = gP.player.worldX + gP.player.solidArea.x;
		gP.player.solidArea.y = gP.player.worldY + gP.player.solidArea.y;
		
		
		switch(entity.direction) {
			case "up":
				entity.solidArea.y -= entity.speed;
				if(entity.solidArea.intersects(gP.player.solidArea)) {
					entity.collision = true;
						
					gP.player.life-=gP.player.life;
					
				}
				break;
			case "down":
				entity.solidArea.y += entity.speed;
				if(entity.solidArea.intersects(gP.player.solidArea)) {
					entity.collision = true;
					
					gP.player.life-=gP.player.life;
					
				}
				break;
			case "left":
				entity.solidArea.x -= entity.speed;
				if(entity.solidArea.intersects(gP.player.solidArea)) {
					entity.collision = true;
					
					gP.player.life-=gP.player.life;
					
				}
				break;
			case "right":
				entity.solidArea.x += entity.speed;
				if(entity.solidArea.intersects(gP.player.solidArea)) {
					entity.collision = true;
					
					gP.player.life-=gP.player.life;
					
				}
			break;
		}
		
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gP.player.solidArea.x = gP.player.solidAreaDefaultX;
		gP.player.solidArea.y = gP.player.solidAreaDefaultY;
		
	}
	

}
