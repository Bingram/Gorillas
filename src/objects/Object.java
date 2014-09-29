package objects;

import java.awt.Rectangle;

public class Object {
	//This objects x,y coords as well as width/height
	protected int x,y,width,height;
	
	protected String type;
	
	public String getMyType(){
		return type;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x, y, width, height);
	}
	
	//This objects shadow for collision
	private Shadow myShadow;
	
	
	
	

}
