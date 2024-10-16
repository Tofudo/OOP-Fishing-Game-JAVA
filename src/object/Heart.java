package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Heart extends SuperObject{
	
	GamePanel gP;
	
	public Heart(GamePanel gP) {
		
		name = "Heart";
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream("/object/heart.png"));
			tool.scaleImage(image, gP.tileSize, gP.tileSize);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}