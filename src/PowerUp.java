import java.awt.Graphics;

public class PowerUp {
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	public static final int COURT_WIDTH = 300;
	public static final int COURT_HEIGHT = 300;
	public int max_x;
	public int max_y;
	
	private int x;
	private int y;
	
	public PowerUp(int pos_x, int pos_y) {
		x = pos_x;
		y = pos_y;
			
		// take the width and height into account when setting the 
		// bounds for the upper left corner of the object.
		max_x = COURT_WIDTH - WIDTH;
		max_y = COURT_HEIGHT - HEIGHT;

	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int n) {
		x = n;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int n) {
		y = n;
	}
	
	public void draw(Graphics g) {
	}
	
}
