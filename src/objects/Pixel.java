package objects;

import java.awt.Color;

public class Pixel {
	
	private Color my_color;
	
	private int x,y;
	
	public Pixel(Color c, int a, int b){
		my_color = c;
		x = a;
		y = b;
	}
	
	public Color getColor() {
		return my_color;
	}

	public void setColor(Color c) {
		my_color = c;
	}

	public int getX() {
		return x;
	}

	public void setX(int a) {
		x = a;
	}

	public int getY() {
		return y;
	}

	public void setY(int b) {
		y = b;
	}

}
