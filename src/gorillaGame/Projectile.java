package gorillaGame;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Projectile {

	private String imageName;
	
	private ImageIcon ii;
	
	private Rectangle shadow;
	
	private int x,y, dx, dy, width, height;
	private double time = 0;
	
	private int x0;
	private int y0;
	
	private double g = 9.81;
	private double velo = 0;
	private double theta = 0;
	
	private boolean visible;
	
	public Projectile(int initX, int initY){
		imageName = "banana_sml.png";
		
		ii = new ImageIcon(this.getClass().getResource(imageName));
		
		width = getImage().getWidth(null);
		height = getImage().getHeight(null);
		
		setVisible(true);
		
		x = x0 = initX;
		y = y0 = initY;
		
		shadow = new Rectangle(x,y, width, height/2);
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
	
	public double getG() {
		return g;
	}

	public void setG(double grav) {
		g = grav;
	}

	public double getVelo() {
		return velo;
	}

	public void setVelo(double v) {
		velo = v;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(int t) {
		theta = t * (Math.PI/180);
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
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
	
	public double getTime(){
		return time;
	}
	
	public void reset(){
		x = x0;
		y = y0;
		time = 0;
	}
	
	public void posCalc(int p){
		calcPos(p);
	}
	
	public void setVis(boolean vis){
		setVisible(vis);
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x, y, width, height);
	}
	
	//main method for projectile trajectory...
	private void calcPos(int player){
		
		double vX0 = velo * Math.cos(theta);
    	double vY0 = velo * Math.sin(theta);
    	
    	dx = (int) ( vX0);
    	dy = (int) (vY0 - ((g*Math.pow(time, 2))/2));
    	
    	if(player == 1){
    		x += dx;
    	} else if(player == 2){
    		x -= dx;
    	}
    	
    	y -= dy;
	}
	
	public void setTime(double t){
		time += t;
	}
	
	protected void changeAmmo(String name){
		imageName = name;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean vis) {
		visible = vis;
	}
	
}
