package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Stock extends SuperObject{

	GamePanel gP;
	
	public Stock(GamePanel gP) {
		
		name = "Stock";
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream("/object/stock.png"));
			tool.scaleImage(image, gP.tileSize, gP.tileSize);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}