package gorillaGame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Game extends JFrame {

	//serial just because
	private static final long serialVersionUID = 1L;
	
	//the board
	private Board my_board;
	
	//panel for board
	private BoardPanel my_board_panel;

	public Game() {

		my_board = new Board();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
        setTitle("Gorillas");
        
        setResizable(false);
        
        setVisible(true);
        
        startGame();
        
        my_board.castShadows();
        
        my_board_panel.printMyShadow();
        
    }
	
	/**
	 * Prompts for player names and passes info to board
	 */
	private void startGame(){
		
		
		
		/**
		 
		//set scale *TEMP*
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
		if(p2_name.equals("")){
			p2_name = "Player 2";
		}
		
		//set names
		my_board.getP1().setPlayerName(p1_name);
		my_board.getP2().setPlayerName(p2_name);
		
		//add the board
		my_board_panel = new BoardPanel(my_board);
		
		//set size of board panel
		my_board_panel.setSize(1280, 800);
		
		//set size to match menu
		setPreferredSize(my_board_panel.getSize());
			
        
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
	
}
