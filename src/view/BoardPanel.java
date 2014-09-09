package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Board;
import objects.Building;
import objects.Hit;
import objects.Player;

public class BoardPanel extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	private Board game_board;
	
	private Color bg;
	
	private Thread animator;
	   
    private final int DELAY = 60;
	
	private JLabel p1_score;
	
	private JLabel p1_name;
	
	private JLabel p2_score;
	
	private JLabel p2_name;
	
	private int wins;

	private double scale = (1.00);
    
    private int bldg_height_start;
    
    private Boolean WINNER = false;
	
	public BoardPanel(Board the_board){
		
		super();
		
		setLayout(null);
		
        bg = Color.CYAN;
		
		setBackground(bg);

		p1_score = new JLabel();
		p2_score = new JLabel();
		p1_name = new JLabel();
		p2_name = new JLabel();
				
    	setDoubleBuffered(true);
		
		game_board = the_board;	

		setup();
		
	}
	
	
	private void setup(){
		
		p1_score.setText("Score| " + game_board.getP1().getScore());
		p2_score.setText("Score| " + game_board.getP2().getScore());
		p1_name.setText(game_board.getP1().getPlayerName());
		p2_name.setText(game_board.getP2().getPlayerName());
		
		p1_name.setFont(new Font("Arial", Font.BOLD, 30));
		p2_name.setFont(new Font("Arial", Font.BOLD, 30));
		
		p1_score.setFont(new Font("Arial", Font.BOLD, 14));
		p2_score.setFont(new Font("Arial", Font.BOLD, 14));
		
		add(p1_name);
		add(p2_name);
		
		add(p1_score);
		add(p2_score);
		
		/**
		 * Sets position for player info.
		 * Right now it's set by absolute position,
		 * future should less dependent on hard coding.
		 */
		p1_name.setBounds(30, 0, 200, 50);
		
		p1_score.setBounds(30, 50, 200, 30);
		
		
		p2_name.setBounds(1140, 0, 200, 50);
				
		p2_score.setBounds(1140, 50, 200, 30);
		
		setWins(3);
	
	}
	
	public void addNotify() {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }

    /**
     * Draws game_board.getProjectile() at current x,y coords
     */
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        
        if(true){
        	//base height to calc buildings
        	bldg_height_start = 0;
	        //draw sun
	        g2d.drawImage(game_board.getSunny().getImage(), game_board.getSunny().getX(), game_board.getSunny().getY(), this);
	        
	        //Draw city-scape 
	        for(Building b : game_board.getBuildings()){
	    	 
	        	//draw graphical shading of each building
	        	g2d.setColor(Color.BLACK);
	        	g2d.fillRect(bldg_height_start, 800 - b.getyPos(), b.getWidth(), b.getHeight());
	        	
	        	//draw each building
	        	g2d.setColor(b.getPaint());
	        	g2d.fillRect(bldg_height_start-1, 800 - b.getyPos()+1, b.getWidth()-1, b.getHeight()-1);
	        	
	        	b.setxPos(bldg_height_start);
	        	b.setShadowX(bldg_height_start);
	        	bldg_height_start += 160;
		        
	        }
	        
	        //draw the windows on buildings
	        for(Building b : game_board.getBuildings()){
	        	drawWindows(g2d, b.getBounds());
	        }
	        
	        //draw blast sites
	        for(Hit h : game_board.getBlastSites()){
	        	g2d.setColor(bg);
	        	g2d.fillOval(h.getxPos(), h.getYpos(), h.getHeight(), h.getWidth());
	        }	        
        	
	        //draw current player
	        g2d.drawImage(game_board.getCurrentPlayer().getImage(), game_board.getCurrentPlayer().getX(), game_board.getCurrentPlayer().getY(), this);
	        
	        //draw target player
	        g2d.drawImage(game_board.getTargetPlayer().getImage(), game_board.getTargetPlayer().getX(), game_board.getTargetPlayer().getY(), this);

	        //draw projectile when in flight
	        if(game_board.isFlight()){
		        
	        	spin(g2d, game_board.getProjectile().getBounds());
		        
	        }
	        
        } 
        
        
        Toolkit.getDefaultToolkit().sync();
        
        g.dispose();
    }
    
    private void drawWindows(Graphics2D copy, Rectangle r){
    	
    	int x = bldg_height_start = r.x + 10;
    	int y = 820 - (r.y);
    	
    	while (y <= 800){
    		
    		for (int i = 0; i < 4; i++){
    			copy.setColor(Color.black);
    			copy.fillRect(x, y, 27, 55);
    			copy.setColor(Color.blue);
    			copy.fillRect(x+2, y+2, 24, 52);
    			x += 37.5;
    		}
    		
    		y += 75;
    		x = bldg_height_start;
    	}
    	
    }
    
    /**
     * Copied from David Stevens @
     * http://www.coderanch.com/t/467940/GUI/java/Graphics-rotation-translation-affinetransform
     * 
     * Modified the calculation for rotation amount to calc based on time of flight
     * 
     * Works great!
     * 
     * @param copy G2D from paint()
     * @param r bounds of game_board.getProjectile() image
     */
    private void spin(Graphics2D copy, Rectangle r){
    	
    	AffineTransform at = new AffineTransform(); // create a new transform instance   
        double x = r.x + (r.width / 2); // get the centre point of the rectangle (this is where I got it wrong)  
        double y = r.y + (r.height / 2);  
        at.translate(x, y); // translate the axis point to the centre   
        at.rotate(Math.toRadians(120 * game_board.getProjectile().getTime())); // rotate the transform by whatever amount of degrees you want around the axis  
        at.translate(-x, -y); // move the axis point back to the original position  
        copy.setTransform(at); // set the transform to the graphics context 
        
        copy.drawImage(game_board.getProjectile().getImage(), game_board.getProjectile().getX(), game_board.getProjectile().getY(), this);
  
    }
    
    /**
     * Thread control for animation
     */
    public void run() {
    	
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();
        
        
        
        while(game_board.isIngame()){
        	
        	
        	if(game_board.getCurrentPlayer().getScore() == this.getWins()){
            	
            	game_board.setIngame(false);
            	game_board.setFlight(false);
            	
            	WINNER = true;

    			JOptionPane.showMessageDialog(this, game_board.getCurrentPlayer().getPlayerName() + " Wins!");
    			
    		} else if(game_board.getTargetPlayer().getScore() == this.getWins()){
    			
    			game_board.setIngame(false);
            	game_board.setFlight(false);
            	
            	WINNER = true;

    			JOptionPane.showMessageDialog(this, game_board.getTargetPlayer().getPlayerName() + " Wins!");	
    		}
        	
	    	if(!game_board.isFlight() && !WINNER){
	    	 	
	    		
		        game_board.getProjectile().setTheta(Integer.parseInt(JOptionPane.showInputDialog(
			               this,
			               game_board.getCurrentPlayer().getPlayerName() + ", Shot Angle? (Degree's)",
			               JOptionPane.PLAIN_MESSAGE)));
						
		        game_board.getProjectile().setVelo(Integer.parseInt(JOptionPane.showInputDialog(
			               this,
			               game_board.getCurrentPlayer().getPlayerName() + ", Shot Speed? (m/s)",
			               JOptionPane.PLAIN_MESSAGE)));
						
				game_board.setFlight(true);
				
				
	    	 }
			
				
	        if(game_board.isFlight()) {
	        	
	        	
	        	if(!game_board.collisionCheck()){
	        		
	        		Player p = game_board.getCurrentPlayer();
	        		int player = p.getPlayerNumber();
	        		
		            //game_board.getProjectile().setTime(scale);        	
			        //game_board.getProjectile().posCalc(player);
			        game_board.getProjectile().update(player);
	        	}
		        
	            	            
	        }
	        
	        
	        
	        
	        p1_score.setText("Score| " + game_board.getP1().getScore());
			p2_score.setText("Score| " + game_board.getP2().getScore());
	        
	       
	        //timeDiff = System.currentTimeMillis() - beforeTime;
	        //sleep = DELAY - timeDiff;
	
	        //if (sleep < 0)
	          //  sleep = 2;
			
			repaint();
			
	        try {
	            Thread.sleep(17);
	        } catch (InterruptedException e) {
	            System.out.println("interrupted");
	        }
	
	        beforeTime = System.currentTimeMillis();
        }
        
    }
    
    public void setScale(double d) {
		scale = d;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int w) {
		wins = w;
	}
	
	public void printMyShadow(){
		game_board.printShadow();
	}

}
