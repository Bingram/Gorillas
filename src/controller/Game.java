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

    
    private Boolean aiming = false;

	
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
            //System.out.println(me);
            

			my_board_panel.setAimStart(me.getPoint());

           
          } 
          
        

    	@Override
    	public void mouseDragged(MouseEvent me){

			my_board_panel.setAimFinish(me.getPoint());


          }
          
    	@Override
          public void mouseReleased(MouseEvent me){
			my_board_panel.setAimFinish(me.getPoint());
			//TODO

			/**
			 * When mouse released take angle
			 * and length to calculate distance
			 * and velocity
			 */
			my_board_panel.setAimLine(new Line2D.Double(my_board_panel.getAimStart(), my_board_panel.getAimFinish()));


			double shotYDiff, shotXDiff;

			shotYDiff = my_board_panel.getAimLine().getY2()-my_board_panel.getAimLine().getY1();
			shotXDiff = my_board_panel.getAimLine().getX2()-my_board_panel.getAimLine().getX1();


			System.out.println("Input Angle: "+Math.atan2(shotYDiff, shotXDiff));

			//find angle of shot
			my_board_panel.setAimAngle(Math.toDegrees(Math.atan2(shotYDiff, shotXDiff))*-1);

			my_board.getProjectile().setTheta(my_board_panel.getAimAngle().intValue());
			my_board.getProjectile().setVelo(lineLength()/6);

			System.out.println("Projectile Speed: "+my_board.getProjectile().getVelo());
			System.out.println("Projectile Angle: "+my_board.getProjectile().getTheta());

			//my_board_panel.repaint();

			//aimStart = aimFinish = null;



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
        double x=Math.pow((my_board_panel.getAimFinish().x - my_board_panel.getAimStart().x), 2);
        double y=Math.pow((my_board_panel.getAimFinish().y - my_board_panel.getAimStart().y), 2);
        length_in_pixel = (int)Math.sqrt(x+y);
		
		return length_in_pixel;
	}

	//Method to draw an arrow based on the start and finish points of a user clicking
    private void makeArrow() {
		// TODO Auto-generated method stub

		/*if(aimStart != null && aimFinish != null){

    		int width = 16;

    		nPoints = 4;

    		Point aLeft = moveLeft(aimStart, width/2);
    		Point aRight = moveRight(aimStart, width/2);

    		Point bLeft = moveLeft(aimFinish, width/2);
    		Point bRight = moveRight(aimFinish, (width/2)+(lineLength()/3));

    		aimXPoints = new int[]{aimStart.x, aLeft.x, bLeft.x, aimFinish.x, bRight.x, aRight.x, aimStart.x};

    		aimYPoints = new int[]{aimStart.y, aLeft.y, bLeft.y, aimFinish.y, bRight.y, aRight.y, aimStart.y};



    	}*/
		
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
