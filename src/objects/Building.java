package objects;

import java.awt.Color;
import java.awt.Rectangle;


public class Building extends Entity {

	private Color paint;
	
	private Rectangle myShape;
	
	private Rectangle shadow;
	
	
	
	
	public Building(int yPos){
		
		type = "BUILDING";
			
		type = "BUILDING";
		y = yPos;
		width = 160;
		height = yPos;
		myShape = new Rectangle(x, 800 - getHeight(), width, yPos);
		shadow =  new Rectangle(x, 800 - getHeight(), this.getWidth(), this.getHeight());
		
	}

	public int getyPos() {
		return y;
	}

	public void setyPos(int yPos) {
		this.y = yPos;
	}

	public Color getPaint() {
		return paint;
	}

	public void setPaint(Color paint) {
		this.paint = paint;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int w) {
		width = w;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int h) {
		height = h;
	}

	public int getxPos() {
		return x;
	}

	public void setxPos(int xPos) {
		x = xPos;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, 800 - height);
	}
	

	public Rectangle getShadow() {
		return new Rectangle(x, y, width, height);
	}
	
	public void setShadowX(int width){
		x = width;
	}

	public Rectangle drawMe(){
		
        	//y1 = 300 + (int)(Math.random() * ((600 - 300) + 1));
        	
		 return myShape;
        
	}

}
