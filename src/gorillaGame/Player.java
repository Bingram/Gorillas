package gorillaGame;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Player {

	private String imageName;
	
	private String playerName;
	
	private int playerNumber, width, height, x, y, score;
	
	private Rectangle shadow;
	
	private ImageIcon ii;

	private boolean visible;
	
	public Player(String name){
		setPlayerName(name);
		
		imageName = "gorilla_sml_p1.png";
		
		ii = new ImageIcon(this.getClass().getResource(imageName));
		
		width = getImage().getWidth(null);
		height = getImage().getHeight(null);
		
		//shadow is a rectangle half the width of player icon, and centered
		shadow = new Rectangle(x + (this.getWidth()/4), y, this.getWidth()/2, this.getHeight());
		
	}

	public int getScore() {
		return score;
	}

	public void setScore(int s) {
		score += s;
	}

	public void setWidth(int w) {
		width = w;
	}

	public void setHeight(int h) {
		height = h;
	}
	
	public void setShadow(Rectangle r){
		shadow = r;
	}
	
	public Rectangle getShadow() {
		return shadow;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int i){
		x = i;
	}
	
	public void setY(int i){
		y = i;
	}
	
	public void setVis(boolean vis){
		setVisible(vis);
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x, y, width, height);
	}

	public Image getImage() {
		return ii.getImage();
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String p) {
		playerName = p;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int p) {
		playerNumber = p;
	}
	
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
		
		ii = new ImageIcon(this.getClass().getResource(imageName));
		
		width = ii.getIconWidth();
		
		height = ii.getIconHeight();
	}
	
	
}
