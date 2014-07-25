package gorillaGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerPanel extends JPanel implements Observer{
	
	private Player my_player;
	
	private Board my_board;
	
	private JLabel my_score;
	
	private JLabel my_name;
	
	private Font my_score_font;
	
	private Font my_name_font;
	
	private int x,y;
	
	public PlayerPanel(final Board the_board, final Player the_player){
		
		super();
		
		my_player = the_player;
		
		my_board = the_board;
		
		my_score = new JLabel();
		
		my_name = new JLabel();
		
		my_name_font = new Font("Arial", Font.BOLD, 18);
		
		my_score_font = new Font("Arial", Font.PLAIN, 12);
				
		setup();
	}
	
	private void setup(){
		
		my_score.setFont(my_score_font);
		my_name.setFont(my_name_font);
		
		my_board.addObserver(this);
		
		my_name.setText(my_player.getPlayerName());
		
		my_score.setText("Score| " + my_player.getScore());
		
		add(my_name);
		
		add(my_score);
		
		setFocusable(false);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
	    setBackground(Color.CYAN);
	    
	    setPreferredSize(new Dimension(80, 200));
	    
	    setSize(80, 200); 
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		
	}

}
