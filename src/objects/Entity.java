package objects;

import java.awt.Rectangle;

public class Entity {
	//This objects x,y coords as well as width/height
	protected int x,y,width,height;
	
	protected String type;
	
	//This objects shadow for collision
	private Shadow myShadow;

	public Rectangle getRectangle() {
		// TODO Auto-generated method stub
		
		
		return new Rectangle(x,y,width,height);
	}	

}
