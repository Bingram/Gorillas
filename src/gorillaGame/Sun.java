package gorillaGame;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Sun {

	private ImageIcon ii;
	
	private int x,y;
	
	private Rectangle shadow;
	
	public Sun(){
		ii = new ImageIcon(this.getClass().getResource("sun_happy_sml.png"));
		
		y = 0;
		x = 640 - (ii.getIconWidth()/2);
		
		shadow = new Rectangle(x,y, ii.getIconWidth()/2,ii.getIconWidth()/2);
	
	}
	
	public void gotHit(boolean hit){
		if(hit){
			
			ii = new ImageIcon(this.getClass().getResource("sun_sad_sml.png"));
		}
		
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Image getImage(){
		return ii.getImage();
	}

	public Rectangle getShadow() {
		return shadow;
	}

	public void setShadow(Rectangle s) {
		shadow = s;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x,y, ii.getIconWidth()/2, ii.getIconHeight()/2);
	}
	
}
