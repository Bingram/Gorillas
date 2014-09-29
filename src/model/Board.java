package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objects.Building;
import objects.Entity;
import objects.Hit;
import objects.Pixel;
import objects.Player;
import objects.Projectile;
import objects.Quadtree;
import objects.Shadow;
import objects.Sun;
import media.ImageExport;
import media.SoundPlayer;

public class Board extends Observable  {
	private Projectile projectile;
	
	private ArrayList<Hit> blastSites = new ArrayList<Hit>();
	
	private Building[] buildings = new Building[8];
	
	private List<Entity> allObjects = new ArrayList<Entity>();
	
	private Shadow my_shadow;
	
	public Shadow getMy_shadow() {
		return my_shadow;
	}



	private Sun sunny;
	
	private Player p1, p2;

	private Player targetPlayer;
	
	private Player currentPlayer;
	
	private SoundPlayer sound;
	
	private String IMPACT = "media/stomp.wav";
	
	private String KILL;
	
	private String WIN;
	
	private String HIT_SUNNY;
	
	private int[] yPoints;

	private boolean inFlight = false;
	
	private boolean inRound = true;
	
	
	
    public Board() {

    	//Default names
        p1 = new Player("");
        p1.setPlayerNumber(1);
        
        p2 = new Player("");
        p2.setPlayerNumber(2);
        
        //Default player
        currentPlayer = p1;
        
        
        //Default target
        targetPlayer = p2;
        
        
        
        p1.setImageName("gorilla_sml_p1_stand.png");
        
        p2.setImageName("gorilla_sml_p2_stand.png");

        sunny = new Sun();
        
        my_shadow = new Shadow();
        
        //Flag for if projectile in flight
        inFlight = false;
        
        //Flag for if currently in a round
        inRound = true;
        
        //calc random building heights
        yPoints = randHeight();

        /**
         * create buildings from array yPoints as heights
         * Assign one of three colors to each using mod 2, 3 or neither
         */
        for(int i = 0; i < 8; i++){
    		buildings[i] = new Building(yPoints[i]);
    		if(i%2 == 1){
   			buildings[i].setPaint(Color.BLUE);
    		} else if(i%3 == 1){
    			buildings[i].setPaint(Color.LIGHT_GRAY);
    		} else {
    			buildings[i].setPaint(Color.GRAY);
    		}
    	}
        
        //Set p1 inset from left
        p1.setX(210);
        //Set p1 height from second building height
    	p1.setY((800 - buildings[1].getHeight()) - p1.getHeight() + 2);
    	
    	//Set p2 inset from right
    	p2.setX(1280 - (250 + (p2.getBounds().width/2)));
    	//Set p2 height from second to last building
    	p2.setY((800 - buildings[6].getHeight()) - p2.getHeight() + 2);
    	
    	//Create projectile and set location to p1
    	projectile = new Projectile(currentPlayer.getX(), currentPlayer.getY());
    	
    	//sound player
    	sound = new SoundPlayer();
    	//pre-load impact sound, removes initial lag on first hit
    	sound.preLoad(IMPACT);
    	
    	
    	//TODO - Test Out JPEG
    	//my_shadow.shadowPrint();
                
    }
    
    public void printShadow(){
    	my_shadow.shadowPrint();
    }
    
    //casts a shadow for each object using its shadow Rectangle
    public void castShadows(){
    	int bldg_start_x = 0;
    	my_shadow.castShadow(p1.getShadow(), p1.getShadow().x, p1.getShadow().y);
    	my_shadow.castShadow(p2.getShadow(), p2.getShadow().x, p2.getShadow().y);
    	
    	my_shadow.castShadow(projectile.getShadow(), projectile.getShadow().x, projectile.getShadow().y);
    	
    	my_shadow.castShadow(sunny.getShadow(), sunny.getShadow().x, sunny.getShadow().y);
    	
    	for(Building b: buildings){
    		b.setxPos(bldg_start_x+160);
        	
    		b.setShadowX(bldg_start_x+160);
    		
    		my_shadow.castShadow(b.getShadow(), 0, 0);
    		
    		bldg_start_x += 160;
    	}
    	
    }
    
    /**
     * Runs a check against all objects and the projectile
     * Currently gets projectiles current coordinates and then
     * checks it against all buildings, players and sunny
     * If hit detected, return true otherwise return false
     * @return boolean of hit/no-hit
     */
    public boolean collisionCheck(){
    	
    	/**
    	 * SHADOW VERSION
    	 */
    	
    	//create shadows for each object

    	//collision when projectile hits shadow
    	////
    	//clear current palyer shadow
    	//check for target palyer shadow hit
    	//add point if player hit
    	//set flags
    	
    	Quadtree quad = new Quadtree(0, new Rectangle(0,0,1280,800));
    	
    	quad.clear();
    	
    	for (int i = 0; i < allObjects.size(); i++){
    		quad.insert(allObjects.get(i).getRectangle());
    	}
    	
    	List<Rectangle> returnObjects = new ArrayList<Rectangle>();
    	
    	returnObjects.clear();
    	
    	returnObjects.addAll(quad.retrieve(returnObjects, projectile.getRectangle()));
    	
    	for(int i = 0; i < returnObjects.size(); i++){
    		
    		//TODO Add collision detection here
    		
    	}
    	
    	////////////////////////////////////////////////
    	/**
    	 * OLD VERSION
    	 */
    	
    	//new rectangle using bounds of projectile
    	 Rectangle r3 = new Rectangle((int)projectile.getX(), (int)projectile.getY(), projectile.getWidth(), projectile.getHeight());

    	  
    	 //value to return, false unless intersection is true
    	 boolean value = false;
    	 
    	//hit target player
         if(r3.intersects(targetPlayer.getX(), targetPlayer.getY(), targetPlayer.getImage().getWidth(null)+5, targetPlayer.getImage().getHeight(null)+5)){
        	 
        	 Rectangle hit = r3.intersection(new Rectangle(targetPlayer.getX(), targetPlayer.getY(), targetPlayer.getImage().getWidth(null), targetPlayer.getImage().getHeight(null)));
        	
        	//turn off projectile
             projectile.setVis(false);
             projectile.reset();
			 
        	 //create new hit at location of intersection
        	 Hit h = new Hit(hit.x, hit.y);
        	 
        	 //set height and width of "explosion"
        	 h.setHeight(30);
        	 h.setWidth(30);
        	 
        	 //Set board in game true
        	 inRound = true;
        	 
        	 //Reset flight status false
             inFlight = false;
             
             //add the new hit
             blastSites.add(h);

             //collision true
             value = true;
             
             //Add to player score
             currentPlayer.setScore(1);
             
             //turn off player, they got hit
             targetPlayer.setVis(false);
             
             //Clear blast sites for new throw
             //blastSites = new ArrayList<Hit>();

         }

    	 
    	 //check against each building for collision
         for (Building b : buildings){

    		 if(r3.intersects(b.getxPos(), 800 - b.getyPos(), b.getWidth(), b.getHeight()) && projectile.isVisible()) {
            	 
    			 //play impact sound
    			 sound.play(IMPACT);
    			 
    			 //create intersection rectangle
    			 Rectangle hit = r3.intersection(new Rectangle(b.getxPos(), 800 - b.getyPos(), b.getWidth(), b.getHeight()));
            	
    			 //turn off projectile
                 projectile.setVis(false);
                 projectile.reset();
    			 
            	 //create new hit at location of intersection
            	 Hit h = new Hit(hit.x, hit.y);
            	 
            	 //set height and width of "explosion"
            	 h.setHeight(30);
            	 h.setWidth(30);
            	 
            	 //Set board in game true
            	 inRound = true;
            	 
            	 //Reset flight status false
                 inFlight = false;
                 
                 //add the new hit
                 blastSites.add(h);           
                 

                 //collision true
                 value = true;

             } 
             
         }
         
         //who hit sunny?
         if(r3.intersects(sunny.getX(), sunny.getY(), sunny.getImage().getWidth(null) - 10, sunny.getImage().getHeight(null) - 10)){

        	 //new rectangle denoting intersection of projectile and building
        	 Rectangle hit = r3.intersection(new Rectangle(sunny.getX(), sunny.getY(), sunny.getImage().getWidth(null), sunny.getImage().getHeight(null)));
        	 
        	 //turn off projectile
             projectile.setVis(false);
             projectile.reset();
			 
        	 //create new hit at location of intersection
        	 Hit h = new Hit(hit.x, hit.y);
        	 
        	 //set height and width of "explosion"
        	 h.setHeight(30);
        	 h.setWidth(30);
        	 
        	 //Set board in game true
        	 inRound = true;
        	 
        	 //Reset flight status false
             inFlight = false;
             
             //add the new hit
             blastSites.add(h);
             
             //collision true
             value = true;
             
             //change graphic of sun to show hit
             sunny.gotHit(true);

         }
         
                  
         //Detects when projectile goes off screen
    	 if(r3.getX() > 1280 || r3.getX() < 0 || r3.getY() > 800){
    		 
    		 projectile.reset();
    		 
    		 //turn off projectile
    		 projectile.setVis(false);
    		 
    		 //still in game
    		 inRound = true;
    		 
    		 //projectile is not flying
             inFlight = false;
             
             //"hit" detected off screen
             value = true;
 

    	}
         
    	 //If hit detected change places
    	 if(value){ //change current player to current target
             changePlaces();
         }
    	 
    	 //Boolean if hit detected
         return value;
    }

    //Generate new set of heights for buildings
	private int[] randHeight(){
    	
		//Number of buildings
		int num = 8;
		
		//Array of ints for height of buildings
    	int[] temp = new int[num];
    	
    	for(int i = 0; i < 8; i++){
    		
    		//Each building has base height of 300
    		//Add random value from 0 to 301
    		temp[i] = 300 + (int)(Math.random() * 301);
    	}
    	
    	return temp;
    }
    
	//Swap target and current players
    private void changePlaces(){
    	//If current player is P1
    	if(currentPlayer.getPlayerName().equalsIgnoreCase(p1.getPlayerName())){
    		currentPlayer = p2;
    		targetPlayer = p1;
    		//If current Player is P2
    	} else if(currentPlayer.getPlayerName().equalsIgnoreCase(p2.getPlayerName())){
    		currentPlayer = p1;
    		targetPlayer = p2;
    	}
    	
    	
    	//System.out.print(my_shadow.toString());
    	//System.out.println();
    	//System.out.println(point());
    	
    	
    	
    	//Make new projectile at new current player
    	projectile = new Projectile(currentPlayer.getX(), currentPlayer.getY());
    }
    
    private String point(){
    	return pointMain(my_shadow.getPixel(0, 0));
    }
    
    private String pointMain(Pixel p){

    	if(p.getColor().equals(Color.BLACK)){
    		return (p.getX() + "," +  p.getY()) + "";
    	} else{
    		return pointMain(my_shadow.getPixel(p.getX()+1, p.getY()));
    	}
    }
    
    //Find Sunny
	public Sun getSunny() {
		return sunny;
	}
	
	//Set a place for Sunny
	public void setSunny(Sun suny) {
		sunny = suny;
	}
    
	//Get Player 2
    public Player getP2() {
		return p2;
	}

    //Set Player 2
	public void setP2(Player p) {
		p2 = p;
	}

	//Get Player 1
	public Player getP1() {
		return p1;
	}

	//Set Player 1
	public void setP1(Player p) {
		p1 = p;
	}
	
	//Get current player
	public Player getCurrentPlayer(){
		return currentPlayer;
	}
	
	//Get current target
	public Player getTargetPlayer(){
		return targetPlayer;
	}

	//Get  current projectile
	public Projectile getProjectile() {
		return projectile;
	}

	//Set current projectile
	public void setProjectile(Projectile p) {
		projectile = p;
	}
	
	//Return the current list of blast points <Not Sand People>
	public ArrayList<Hit> getBlastSites() {
		return blastSites;
	}


	//Set blast sites for this board
	public void setBlastSites(ArrayList<Hit> blasts) {
		blastSites = blasts;
	}


	//Get the buildings for this board
	public Building[] getBuildings() {
		return buildings;
	}


	//Set buildings array for this board
	public void setBuildings(Building[] b) {
		buildings = b;
	}


	//Check if board has projectile in flight
	public boolean isFlight() {
		return inFlight;
	}


	//Set projectile flight status
	public void setFlight(boolean flight) {
		inFlight = flight;
	}


	//Check in game status
	public boolean isIngame() {
		return inRound;
	}


	//Sets game status: true = in round, false = not in round
	public void setIngame(boolean ingame) {
		inRound = ingame;
	}


    
}
