import java.awt.Color;
import java.awt.Graphics;

public class SnakeTail extends GameObj {

	public static final int SIZE = 10;
	
	public SnakeTail(int courtWidth, int courtHeight, int x, int y, int vel_x, int vel_y) {
		super(vel_x, vel_y, x, y, SIZE, SIZE, courtWidth,
				courtHeight);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(pos_x, pos_y, width, height);
	}
	
}
