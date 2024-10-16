package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 *
 * @author Shem Cardoza
 */
//this contains key functions

public class KeyInputs implements KeyListener{
	
	GamePanel gP;

    public boolean upKey,downKey,leftKey,rightKey,fishKey;
    
    
    public KeyInputs(GamePanel gP) {
    	this.gP = gP;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); //returns the equivalent number of key pressed
        
        
        if(gP.gameState == gP.titleState) { //keys only used when the game is in titlestate
        	
        	if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){ // checks if user pressed w key
	            gP.ui.commandNum--;
	            if(gP.ui.commandNum < 0) {
	            	gP.ui.commandNum = 1;
	            }
	        }
	        
	        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){ // checks if user pressed S key
	            gP.ui.commandNum++;
	            if(gP.ui.commandNum > 1) {
	            	gP.ui.commandNum = 0;
	            }
	        }
	        
	        if(code == KeyEvent.VK_ENTER) {
	        	if(gP.ui.commandNum == 0) {
	        		gP.gameState = gP.playState;
	        	}
	        	if(gP.ui.commandNum == 1) {
	        		System.exit(0);
	        	}
	        }
        	
        }
        
        
        if(gP.gameState == gP.playState) { //keys used only when the game is in playstate
	        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){ // checks if user pressed w key
	            upKey = true;
	        }
	        
	        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){ // checks if user pressed S key
	            downKey = true;
	        }
	        
	        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){ // checks if user pressed A key
	            leftKey = true;
	        }
	        
	        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){ // checks if user pressed D key
	            rightKey = true;
	        }
	        
	        
	        if(code == KeyEvent.VK_F){ // checks if user pressed F key
	            fishKey = true;
	        }
        }
        
        
        if(gP.gameState == gP.gameOverState) { //keys used when the game is over and locks other keys
        	
        	if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){ // checks if user pressed w key
	            gP.ui.commandNum--;
	            if(gP.ui.commandNum < 0) {
	            	gP.ui.commandNum = 1;
	            }
	        }
	        
	        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){ // checks if user pressed S key
	            gP.ui.commandNum++;
	            if(gP.ui.commandNum > 1) {
	            	gP.ui.commandNum = 0;
	            }
	        }
	        
	        if(code == KeyEvent.VK_ENTER) {
	        	if(gP.ui.commandNum == 0) {
	        		gP.gameState = gP.titleState; //makes the game go over title state again
	        	}
	        	if(gP.ui.commandNum == 1) {
	        		System.exit(0); //exits the program
	        	}
	        }
	        
	        
	        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){ // checks if user pressed w key
	            upKey = false;
	        }
	        
	        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){ // checks if user pressed S key
	            downKey = false;
	        }
	        
	        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){ // checks if user pressed A key
	            leftKey = false;
	        }
	        
	        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){ // checks if user pressed D key
	            rightKey = false;
	        }
	        
	        
	        if(code == KeyEvent.VK_F){ // checks if user pressed F key
	            fishKey = false;
	        }
        	
        }
        
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
    
        int code = e.getKeyCode(); //returns the equivalent number of key pressed
        if(gP.gameState == gP.playState) {
	        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){ // checks if user pressed w key
	            upKey = false;
	        }
	        
	        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){ // checks if user pressed S key
	            downKey = false;
	        }
	        
	        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){ // checks if user pressed A key
	            leftKey = false;
	        }
	        
	        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){ // checks if user pressed D key
	            rightKey = false;
	        }
	        
	        if(code == KeyEvent.VK_F){ // checks if user pressed F key
	            fishKey = false;
	        }
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
    
    }
    
}
