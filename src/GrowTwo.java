import java.awt.Color;
import java.awt.Graphics;

public class GrowTwo extends PowerUp {
	
	public GrowTwo(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(getX(), getY(), 10, 10);
	}

}
