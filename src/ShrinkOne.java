import java.awt.Color;
import java.awt.Graphics;

public class ShrinkOne extends PowerUp {
	
	public ShrinkOne(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(getX(), getY(), 10, 10);
	}

}
