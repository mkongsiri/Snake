import java.awt.Color;
import java.awt.Graphics;

public class SnakeHead extends GameObj {
	
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	public static final int SIZE = 10;
	
	public SnakeHead(int courtWidth, int courtHeight, int x, int y) {
		super(INIT_VEL_X, INIT_VEL_Y, x, y, SIZE, SIZE, courtWidth,
				courtHeight);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(pos_x, pos_y, width, height);
	}

}