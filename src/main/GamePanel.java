package main;

import entity.Fish;
import entity.GameEntities;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author Shem Cardoza
 */

public class GamePanel extends JPanel implements Runnable{
	
    //screen
    final int origTileSize = 16; //default size of objects in the map
    final int scale = 3;
    
    public final int tileSize = origTileSize * scale; //scales value of tile by scale
    public final int maxScreenCol = 16; //max width
    public final int maxScreenRow = 12; //max height
    public final int screenWidth = tileSize * maxScreenCol; //size of gamescreen witdth in pixels
    public final int screenHeight = tileSize * maxScreenRow; //size of gamescreen witdth in pixels
    //
    
    //worldmap settings
    public final int maxWorldCol = 32;
    public final int maxWorldRow = 24;
    public final int worldWidth = tileSize + maxWorldCol;
    public final int worldHeight = tileSize + maxWorldRow;
    //
    
    public long startTime;
    public long nowTime;
    private int elapsedSeconds;
    
    public long clockStart;
    
    private int playerStartHp;
    
    
    int FPS = 60;
    
    TileManager tM = new TileManager(this); //Instantiate TileManager to this
    KeyInputs keyIn = new KeyInputs(this); //Instantiate KeyInputs to this
    
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;
    
    public Player player = new Player(this,keyIn);
    public Fish fish;
    public SuperObject obj[] = new SuperObject[10]; //slots for display objects
    public GameEntities monster[] = new GameEntities[20]; //slots for monster display
    
    
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int gameOverState = 2;
    
    private int hpMinus = 15; //seconds to minus player HP if no food is consumed
    
    
    public GamePanel(){
        //for display purposes
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); //sets size of the screen
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); //game rendering
        this.addKeyListener(keyIn); 
        this.setFocusable(true);
        //
        
    }
    
    
    public void setupGame() { //sets up the game 
    	
    	aSetter.setObject(); //sets the objects in the map
    	aSetter.setMonster(); //sets monster
    	
    	gameState = titleState; //sets the game to title state at the start
    	
    	playerStartHp = player.maxLife; //sets playerStartHp to maxLife
    	
    }
    
    
    public void startGameThread(){
        
        gameThread = new Thread(this); // passes Game class to this Thread
        gameThread.start(); // calls run method
        
    }
    
    
    @Override
    public void run() { //this method is executed when the game is opened
    	
        double drawInterval = 1000000000/FPS; //1B nanoseconds = 1 second
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        		
        		
        while(gameThread != null){ //checks if the game is running
            
        	
        	
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime) / drawInterval;
            
            lastTime = currentTime;
            
            if(delta >= 1){
                
                update();
                repaint();
                delta--;
                
            }
            
            
            if(gameState == titleState) {
            	
            	clockStart = System.currentTimeMillis();
            	
            	startTime = System.currentTimeMillis(); //starts again the timer for uses
            	
            	player.life = playerStartHp/2; //sets the player life during title state
            	player.stock = 0; //sets the player's stock during title state
            	
            	//resets player position
            	player.worldX = tileSize * 13; 
                player.worldY = tileSize * 9;
                player.direction = "none";
                
            }
            
            //for playtime calculation uses
            if(gameState == playState) { //checks if the game is in play state 
            	
	            nowTime = System.currentTimeMillis();
	            elapsedSeconds = (int)((nowTime - startTime) / 1000);
	            
	            
	            if(elapsedSeconds == hpMinus) { //used condition to minus player's HP when no food is consumed
	            	
	            	player.life -= 20; //deducts 5 on player's HP
	            	
	            	//resets the time again
	            	startTime = System.currentTimeMillis(); 
	            	nowTime = System.currentTimeMillis();
	            	//
	            }
	            
	            if(player.life <= 0) { //checks if the player's life is 0
	            	
	            	gameState = gameOverState; //puts the game state in game over if the condition is true
	            	
	            }
	            
            }
            
            if(gameState == gameOverState) {
            	aSetter.setMonster(); //calls and resets monster again
            	
            	//a fix to player's movement after holding a movement key right before game over
            	keyIn.upKey = false;
    	    	keyIn.downKey = false;
    	    	keyIn.rightKey = false;
    	    	keyIn.leftKey = false;
            	
            }
            
        }
    }
    

    
    public void update(){ //this updates the player's position and display when moving anywhere in the map
    	
        if(gameState == playState) {
        	
        	player.update(); //for player update during playState
        	
        	for(int i = 0; i < monster.length; i++) {
        		if(monster[i] != null) {
        			monster[i].update();
        		}
        	}
        }
        
    }
    
    public void paintComponent(Graphics g){ //for painting method or displaying
        
        super.paintComponent(g); //super = JPanel
        
        Graphics2D g2 = (Graphics2D)g;
        
        
        //Title State
        if(gameState == titleState) { //checks if the game is in title state
        	
        	ui.draw(g2); //uses draw method in UI

        }
        
        else if(gameState == gameOverState) { //checks if the game is over
        	
        	ui.draw(g2); //uses draw method in UI
        	
        }
        
        else { //else if the game is in play state
        	
        	
        	tM.draw(g2); //uses tile manager to draw tiles for the map in the game
            
        	
            for(int i = 0; i<obj.length; i++) { //draws objects
            	
            	if(obj[i]!= null) {
            		
            		obj[i].draw(g2, this);
            	}
            }
            
            
            player.draw(g2); //draws player
            
            
            for(int i = 0; i<monster.length; i++) { //draws monster
            	
            	if(monster[i]!= null) {
            		
            		monster[i].draw(g2);
            	}
            }

            
            ui.draw(g2); //draws other needed resources 
            
            //for ui fishing method display
            if(player.fishCaught && gameState != gameOverState) { //checks if the fishCaught in player is true
            	ui.drawFishCaught(); //displays drawFishCaught in UI
            	
	            Timer timer = new Timer(2000, new ActionListener() { //sets 5 seconds time to display drawFishCaught
	            	@Override
	            	public void actionPerformed(ActionEvent e) {
	            		player.fishCaught = false; //makes the fishCaught in player false after 5 seconds
	            	}
	            });
	            timer.setRepeats(false);
	            timer.start();
        	}
            //
            
        }
        
        
        g2.dispose(); //dispose 2d when drawing stops to save memory
        
    }
    
    
}
