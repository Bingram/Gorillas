package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Menu extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel player_name_1;
	
	private JLabel player_name_2;
	
	private JLabel rounds;
	
	private JTextField player_name_1_entry;
	
	private JTextField player_name_2_entry;
	
	private JTextField rounds_entry;
	
	private JButton start_button;
	
	private JButton cancel_button;
	
	private String p1_name;
	private String p2_name;
	private int wins;
	
	
	public Menu (){
		
        super();
        
        player_name_1 = new JLabel("Player 1 Name: ");
        player_name_2 = new JLabel("Player 2 Name: ");
        
        player_name_1_entry = new JTextField(20);
        player_name_2_entry = new JTextField(20);
        
        rounds = new JLabel("Points to Win: ");
        rounds_entry = new JTextField(2);
        
        start_button = new JButton("Start");
        cancel_button = new JButton("Cancel");
        
        setup();
        
        
	}
	
	private void setup(){
				
		add(player_name_1);
		add(player_name_2);
		add(player_name_1_entry);
		add(player_name_2_entry);
		add(rounds);
		add(rounds_entry);
		
		add(start_button);
		add(cancel_button);
		
		start_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				p1_name = player_name_1_entry.getText();
				p2_name = player_name_2_entry.getText();
				
				wins = Integer.parseInt(rounds_entry.getText());
			}
		}
				
				);
		
		setFocusable(true);
		setLayout(new FlowLayout());
		
		setBackground(Color.GRAY);
		
		setPreferredSize(new Dimension(640, 480));
		
		setSize(640, 480);
		
		
	}

	public String getP1_name() {
		return p1_name;
	}

	public void setP1_name(String p1_name) {
		this.p1_name = p1_name;
	}

	public String getP2_name() {
		return p2_name;
	}

	public void setP2_name(String p2_name) {
		this.p2_name = p2_name;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	
	
	

}
