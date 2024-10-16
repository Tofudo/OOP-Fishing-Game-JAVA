package main;

import entity.Crab;
import object.Fire;

//used to set assets on the map in game

public class AssetSetter {
	
	GamePanel gP;
	
	public AssetSetter(GamePanel gP) {
		
		this.gP = gP;
		
		
	}
	
	public void setObject () { //sets the object on the map 
		
		gP.obj[0] = new Fire(gP);
		gP.obj[0].worldX = 15 * gP.tileSize; //x axis value of where the object will be placed
		gP.obj[0].worldY = 11 * gP.tileSize; //y axis value of where the object will be placed
		
	}
	
	public void setMonster () { //sets the monster on the map 
		
		gP.monster[0] = new Crab(gP);
		gP.monster[0].worldX = 10 * gP.tileSize; //x axis value of where the object will be placed
		gP.monster[0].worldY = 11 * gP.tileSize; //y axis value of where the object will be placed
		
		gP.monster[1] = new Crab(gP);
		gP.monster[1].worldX = 20 * gP.tileSize; //x axis value of where the object will be placed
		gP.monster[1].worldY = 11 * gP.tileSize; //y axis value of where the object will be placed
		
		gP.monster[2] = new Crab(gP);
		gP.monster[2].worldX = 15 * gP.tileSize; //x axis value of where the object will be placed
		gP.monster[2].worldY = 7 * gP.tileSize; //y axis value of where the object will be placed
		
		gP.monster[3] = new Crab(gP);
		gP.monster[3].worldX = 15 * gP.tileSize; //x axis value of where the object will be placed
		gP.monster[3].worldY = 15 * gP.tileSize; //y axis value of where the object will be placed
		
	}
	
}
