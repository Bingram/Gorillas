package objects;

import java.awt.Color;
import java.awt.Rectangle;

public class Hit extends Object {

	private int xPos;
	private int yPos;
	private Color blastColor = Color.CYAN;

	private Rectangle shadow;
	
	private int width;
	private int height;
		
	public Hit(int x, int y){
		type = "HIT";
		
		xPos = x;
		yPos = y;
		
		width = 20;
		
		height = 20;
	}

	public int getYpos() {
		return yPos;
	}

	public void setYpos(int ypos) {
		this.yPos = ypos;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public Color getBlastColor() {
		return blastColor;
	}

	public void setBlastColor(Color blastColor) {
		this.blastColor = blastColor;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	private void setShadow(){
		
	}

}
