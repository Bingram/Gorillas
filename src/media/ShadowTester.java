package media;

import java.awt.Color;
import java.awt.Rectangle;

import objects.Shadow;

public class ShadowTester {
	
	private Shadow my_shadow;
	
	private int WIDTH, HEIGHT;
	
	
	public ShadowTester(){
		
		WIDTH = 600;
		HEIGHT = 600;
		
		my_shadow = new Shadow(WIDTH, HEIGHT);
		
		build();
		//System.out.print(my_shadow.toString());
		my_shadow.shadowPrint();
	}

	private void build(){
		for(int i = 10; i < 40; i++){
			for(int j = 10; j < 70; j++){
				my_shadow.setPixel(Color.BLACK, j, i);
			}
		}
		
		Rectangle test = new Rectangle(10,80,30,60);
		
		Rectangle test2 = new Rectangle(40,10,30,60);
		
		my_shadow.castShadow(test, 0, 0);
		
		System.out.println(my_shadow.collide(test));
		
		my_shadow.castShadow(test2, 0, 0);
		
		for(int i = HEIGHT/2 -3; i < HEIGHT-HEIGHT/6; i++){
			for(int j = WIDTH/2-3; j < WIDTH-WIDTH/3; j++){
				my_shadow.setPixel(Color.BLACK, j, i);
			}
		}
	}
	
	public static void main(String[] args){
	
		ShadowTester temp = new ShadowTester();
	}
}
