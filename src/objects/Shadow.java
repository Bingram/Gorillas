package objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Shadow {
	
	Pixel[][] my_shadow; 
	
	private int WIDTH = 1280; //Columns
	private int HEIGHT = 800; //Rows
	
	public Shadow(){
		my_shadow = new Pixel[HEIGHT][WIDTH]; //new Pixel[Rows][Columns]
		setShadow();
	}
	
	public Shadow(int h, int w){
		HEIGHT = h;
		WIDTH = w;

		my_shadow = new Pixel[HEIGHT][WIDTH]; 
		setShadow();
	
	}
	
	private void setShadow(){
		
		for(int i = 0; i < HEIGHT; i++){
			for(int j = 0; j < WIDTH; j++){
				my_shadow[i][j] = new Pixel(Color.WHITE, i, j);
			}
		}
	}
	
	public void setPixel(Color c, int x, int y){
		
		try{

			my_shadow[x][y].setColor(c);
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println(e);
			System.out.println();
			System.out.println("X: " + x);
			System.out.println("Y: " + y);
		}
	}
	
	public Pixel getPixel(int x, int y){
		return my_shadow[x][y];
	}

	public void castShadow(Rectangle r, int x, int y){
		for(int i = r.y; i < r.y + r.height; i++){
			for(int j = r.x; j < r.x + r.width; j++){
				my_shadow[i][j].setColor(Color.BLACK);
			}
		}
	}
	
	public void clearShadow(Rectangle r, int x, int y){
		for(int i = r.y; i < r.y + r.height; i++){
			for(int j = r.x; j < r.x + r.width; j++){
				my_shadow[i][j].setColor(Color.WHITE);
			}
		}
	}
	
	public boolean hitCheck(Rectangle r){
		boolean value = false;
		//System.out.println("Hit Check");
		//System.out.println();
		//System.out.println("Rect Height: " + r.height);
		for(int i = r.y; i < r.y + r.height; i++){
			//System.out.println(i);
			for(int j = r.x; j < r.x + r.width; j++){
				//System.out.print(my_shadow[j][i].getColor().getAlpha() + " ");
				if(my_shadow[i][j].getColor().equals(Color.BLACK)){
					value = true;
					//System.out.println(value);
				}
			}
		}
		System.out.println();
		return value;
	}
	
	public boolean collide(Rectangle r){
		boolean value = true;
		
		for(int i = r.y; i < r.y + r.height; i++){
			for(int j = r.x; j < r.x + r.width; j++){ 
				my_shadow[i][j] = new Pixel(Color.WHITE, i, j);
			}
		}
		
		return value;
	}
	
	public void shadowPrint(){
	    //borrowed code: http://elsewhat.com/2006/08/17/converting-a-two-dimensional-array-of-ints-to-jpg-image-in-java/
	    
	    //setup int array
	    int[][] pixel = new int[HEIGHT][WIDTH];
	    //generate pattern of pixels from shadow values
	    for(int i = 0; i < HEIGHT; i++){
	    	for (int j = 0; j < WIDTH; j++){
	    		pixel[i][j] = getPixel(i, j).getColor().getRGB();
	    	}
	    }
	    
	    	//Create image
	        BufferedImage thing = convertRGBImageWithHeader(pixel,"Shadow Visual");
	       
	        //Write it to file
	        File file = new File("test4.jpg");
	        
	        try {
				ImageIO.write(thing, "jpg", file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
	    }
		
		//borrowed code: http://elsewhat.com/2006/08/17/converting-a-two-dimensional-array-of-ints-to-jpg-image-in-java/
	    public BufferedImage convertRGBImageWithHeader(int[][] rgbValue,String strHeader){
	        //We add extra pixels on top of the image for the strHeader
	        int headerHeight=13;
	        int height = rgbValue.length+headerHeight;
	        int width = rgbValue[0].length;

	        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	        //we either have to loop through all values, or convert to 1-d array
	        for(int y=headerHeight; y< height; y++){
	            for(int x=0; x< width; x++){
	                bufferedImage.setRGB(x,y,rgbValue[y-headerHeight][x]);  
	            }
	        }
	        //Draw the text
	        Graphics2D g=bufferedImage.createGraphics();
	        g.setFont(new Font("Monospaced", Font.BOLD, 14) );
	        g.setColor(Color.white);
	        g.drawString(strHeader,0,10);

	        return bufferedImage;  
	    }
	
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		
		
		for(int i = 0; i < HEIGHT; i++){
			for(int j = 0; j < WIDTH; j++){
				if(my_shadow[i][j].getColor().equals(Color.WHITE)){
					sb.append("\"");
				} else if(my_shadow[i][j].getColor().equals(Color.BLACK)){
					sb.append(".");
				}
			}
			sb.append("\n");
			
		}
		
		return sb.toString();
	}
	
}
