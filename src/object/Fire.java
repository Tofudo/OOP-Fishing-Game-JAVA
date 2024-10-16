package object;

import main.GamePanel;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Fire extends SuperObject{
	
	GamePanel gP;
	
	public Fire(GamePanel gP) {
		
		name = "Fire";
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream("/object/Fire.png"));
			tool.scaleImage(image, gP.tileSize, gP.tileSize);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
