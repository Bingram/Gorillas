package controller;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Board;
import view.BoardView;


public class Game extends JFrame {

	//serial just because
	private static final long serialVersionUID = 1L;
	
	private Double aimAngle;
    
    private Line2D aimLine;
    
    private Point aimStart, aimFinish;
    
    private Boolean aiming = false;

	private int[] aimXPoints;

	private int[] aimYPoints;

	private int nPoints;
	
	CustomMouseMotionListener aimer;
	
	//the board
	private Board my_board;
	
	//panel for board
	private BoardView my_board_panel;

	public Game() {
		aimer = new CustomMouseMotionListener();

		my_board = new Board();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
        setTitle("Gorillas");
        
        setResizable(false);
        
        setVisible(true);
        
        startGame();

        
       // my_board.castShadows();
        
        //my_board_panel.printMyShadow();
        
    }
	
	/**
	 * Prompts for player names and passes info to board
	 */
	private void startGame(){
		
		
		
		/**
		 
		//set speed scale *TEMP*
		my_board_panel.setScale(Double.parseDouble(JOptionPane.showInputDialog(
	               this,
	              "Scale?",
	               JOptionPane.PLAIN_MESSAGE)));
	               
	     */
		
		
        
		
		//prompt for P1 Name
		String p1_name = (String)JOptionPane.showInputDialog(
                this,
                "Set Player Name"
                + "\"Default is \"Player 1\"\"",
                "Player 1",
                JOptionPane.PLAIN_MESSAGE);
		
		//check for blank entry
		if(p1_name.equals("")){
			p1_name = "Player 1";
		}
		
		//prompt for P2 Name
		String p2_name = (String)JOptionPane.showInputDialog(
                this,
                "Set Player Name"
                + "\"Default is \"Player 2\"\"",
                "Player 1",
                JOptionPane.PLAIN_MESSAGE);
		
		//check for blank entry
		if(p2_name.equals("")){//may want to run a better filter
			p2_name = "Player 2";
		}
		
		//set names
		my_board.getP1().setPlayerName(p1_name);
		my_board.getP2().setPlayerName(p2_name);
		
		//add the board
		my_board_panel = new BoardView(my_board);
		
		//set size of board panel
		my_board_panel.setSize(1280, 800);
		
		//set size to match menu
		setPreferredSize(my_board_panel.getSize());
		
		my_board_panel.addMouseMotionListener(aimer);
		my_board_panel.addMouseListener(aimer);
			
        
        //add game panel
        add(my_board_panel);
        
        //TODO Clear Game Screen away for testing
        //setVisible(true);
        
        
        
        pack();
        
        my_board.setIngame(true);
        
        
		
	}

    public static void main(String[] args) {
        
    	new Game();    	
    
    }
    
    private  class CustomMouseMotionListener implements MouseMotionListener, MouseListener{
    	@Override
    	public void mouseMoved(MouseEvent e) {
    		// TODO Auto-generated method stub
    		
    	}
    	
    	@Override
    	public void mousePressed(MouseEvent me) { 
            System.out.println(me); 
            
            if(aimStart == null){
            	aimStart = me.getPoint();
            	makeArrow();
            } 
            
            my_board_panel.repaint();
           
          } 
          
        

    	@Override
    	public void mouseDragged(MouseEvent me){
			aimFinish = me.getPoint();

			makeArrow();

		    my_board_panel.repaint();
          }
          
    	@Override
          public void mouseReleased(MouseEvent me){
        	  aimFinish = me.getPoint();
        	  //TODO
        	          	  
        	  /**
        	   * When mouse released take angle
        	   * and length to calculate distance
        	   * and velocity
        	   */
        	  aimLine = new Line2D.Double(aimStart, aimFinish);
        	  
        	  double shotYDiff, shotXDiff;
        	  
        	  shotYDiff = aimLine.getY2()-aimLine.getY1();
        	  shotXDiff = aimLine.getX2()-aimLine.getX1();
        	  
        	  //find angle of shot
        	  aimAngle = Math.atan2(shotYDiff, shotXDiff);
        	  
        	  my_board.getProjectile().setTheta(aimAngle.intValue());
    		  my_board.getProjectile().setVelo(lineLength()/3);
    		  
    		  my_board_panel.repaint();
        	  
        	  aimStart = aimFinish = null;
        	  
        	  
        	  
        	  aiming = false;
        	  
        	  
        	  my_board.setFlight(true);
          }

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
    }
    
    private int lineLength(){
		
		int length_in_pixel;
        double x=Math.pow((aimFinish.x - aimStart.x), 2);
        double y=Math.pow((aimFinish.y - aimStart.y), 2);
        length_in_pixel = (int)Math.sqrt(x+y);
		
		return length_in_pixel;
	}
    
    private void makeArrow() {
		// TODO Auto-generated method stub
    	if(aimStart != null && aimFinish != null){  		
    		
    		int width = 16;
    		
    		nPoints = 4;
    		
    		Point aLeft = moveLeft(aimStart, width/2);
    		Point aRight = moveRight(aimStart, width/2);
    		    		
    		Point bLeft = moveLeft(aimFinish, width/2);
    		Point bRight = moveRight(aimFinish, (width/2)+(lineLength()/3));
    		
    		aimXPoints = new int[]{aimStart.x, aLeft.x, bLeft.x, aimFinish.x, bRight.x, aRight.x, aimStart.x};
    		
    		aimYPoints = new int[]{aimStart.y, aLeft.y, bLeft.y, aimFinish.y, bRight.y, aRight.y, aimStart.y};
    		
    		
    		
    	}
		
	}
    
    private Point moveLeft(Point p, int distance){
    	Point temp = p;
    	
    	temp.x -= distance;
    	temp.y -= distance;
    	
    	return temp;
    }
    
    private Point moveRight(Point p, int distance){
    	Point temp = p;
    	
    	temp.x += distance;
    	temp.y += distance;
    	
    	return temp;
    }
    
    private Point moveForward90(Point p, int distance){
    	Point temp = p;
    	
    	temp.x += distance;
    	temp.y -= distance;
    	
    	return temp;
    }
    
    private Point moveBack90(Point p, int distance){
    	Point temp = p;
    	
    	temp.x += distance;
    	temp.y -= distance;
    	
    	return temp;
    }
    
	
}
