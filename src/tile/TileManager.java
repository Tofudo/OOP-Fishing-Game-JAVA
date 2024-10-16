package tile;


import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;


public class TileManager {
	
	GamePanel gP;
	public Tile[] tile;
	public int mapTileNum[][];
	
	
	public TileManager(GamePanel gP) {
		
		
		this.gP = gP;
		
		tile = new Tile[10]; //max total number of tile for different kinds (grass,water,etc.)
		
		mapTileNum = new int[gP.maxWorldCol][gP.maxWorldRow];
		
		getTileImage(); //calls getTileImage() method
		loadMapTiles("/maps/map1.txt"); //loads the .txt file of the map in the res folder to read
		
	}
	
	public void getTileImage() {
		//sets up the tiles (index number, tile name, collision state)
		setup(0, "grass", false);
		setup(1, "water", true);
		setup(2, "sand", false);
		
	}
	
	
	public void setup(int index, String imagePath, boolean collision){
		
		UtilityTool tool = new UtilityTool();
		
		try {
			//gets image from tiles package
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
			tile[index].image = tool.scaleImage(tile[index].image, gP.tileSize, gP.tileSize);
			tile[index].collision = collision;
			
			     
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void loadMapTiles(String mapPath){ //loads map tiles
		
		try {
			
			InputStream is = getClass().getResourceAsStream(mapPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			
			int col = 0;
			int row = 0;
			
			while(col < gP.maxWorldCol && row < gP.maxWorldRow) {
				
				String line = br.readLine(); //reads single line
				
				while(col < gP.maxWorldCol) {
					
					String number[] = line.split(" "); //split numbers one by one
					
					int num = Integer.parseInt(number[col]); //change string to integer
					
					mapTileNum[col][row] = num;
					col++;
					
				}
				
				if(col == gP.maxWorldCol) {
					
					col = 0;
					row++;
					
				}
				
			}
			
			br.close();
			
		}catch(Exception e) {
		
		}
		
	}
	
	
	
	public void draw(Graphics2D g2) { //draws tiles
		
		
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gP.maxWorldCol && worldRow < gP.maxWorldRow) {
			
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gP.tileSize;
			int worldY = worldRow * gP.tileSize;
			int screenX = worldX - gP.player.worldX + gP.player.screenX;
			int screenY = worldY - gP.player.worldY + gP.player.screenY;
			
			
			if(worldX + gP.tileSize > gP.player.worldX - gP.player.screenX &&
				worldX - gP.tileSize < gP.player.worldX + gP.player.screenX &&
				worldY + gP.tileSize > gP.player.worldY - gP.player.screenY &&
				worldY - gP.tileSize < gP.player.worldY + gP.player.screenY) {
			
				g2.drawImage(tile[tileNum].image, screenX, screenY, gP.tileSize, gP.tileSize, null);
				
			}
			
			worldCol++;
			
			if(worldCol == gP.maxWorldCol) {
				
				worldCol = 0;
				worldRow++;
				
			}
			
		}
		
		
		//g2.drawImage(tile[0].image, 0, 0, gP.tileSize, gP.tileSize, null);
		//g2.drawImage(tile[1].image, 48, 0, gP.tileSize, gP.tileSize, null);
		
	}
	
}
