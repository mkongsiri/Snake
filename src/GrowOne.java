import java.awt.Color;
import java.awt.Graphics;

public class GrowOne extends PowerUp {
	
	public GrowOne(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(getX(), getY(), 10, 10);
	}

}


