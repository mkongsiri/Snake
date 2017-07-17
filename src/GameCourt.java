import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.LinkedList;

import javax.swing.*;


/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {
	
	private PowerUp powerup;
	private int map[][] = new int[COURT_HEIGHT][COURT_WIDTH];
	private List<PowerUp> queue = new LinkedList<PowerUp>();
	private int length;	
	private GameObj tail;
	
	public static List<GameObj> snakeList = new LinkedList<GameObj>();
	public static List<Position> posList = new LinkedList<Position>();
	public static SnakeHead snake;
	private static int score;
	private static String name;

	public boolean playing = false; // whether the game is running
	public JLabel status; // Current status text (i.e. Running...)
	private JFrame frame;
	
	public boolean moving = false; // whether snake is moving

	// Game constants
	public static final int COURT_WIDTH = 300;
	public static final int COURT_HEIGHT = 300;
	public static final int SNAKE_VELOCITY = 10;
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 300;

	public GameCourt(JLabel status) {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// initial state
		powerup = new GrowOne(25, 25);
		length = 1;
		score = 0;
		refreshQueue();
		
		// instructions
		String instructions = "Welcome to Snake! \nTo play, use the arrow keys. \nEach powerup that you collect will change the length of your snake. \nYou get 10 points for each powerup collected, and the game ends if you run into yourself! \n\nBefore we get started, what is your name?";
		name = JOptionPane.showInputDialog(frame, instructions);
		if (name == "") {
			name = "Player 1";
		}
		
		// initialize map
		refreshMap();

		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		// This key listener allows the square to move as long
		// as an arrow key is pressed, by changing the square's
		// velocity accordingly. (The tick method below actually
		// moves the square.)
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT && snake.v_x != SNAKE_VELOCITY) {
					moving = true;
					snake.v_y = 0;
					snake.v_x = -SNAKE_VELOCITY;
					
//					if (snakeList.size() >= 1) {
//						for (int i = 1; i < snakeList.size(); i++) {
//							GameObj.moveTails(i, snakeList.get(i).getX(), snakeList.get(i).getY(), snakeList, Direction.LEFT);
//						}
//					}
					
//					for (GameObj obj : snakeList) {
//						obj.v_y = 0;
//						obj.v_x = -SNAKE_VELOCITY;
//					}
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT && snake.v_x != -SNAKE_VELOCITY) {
					moving = true;
					snake.v_y = 0;
					snake.v_x = SNAKE_VELOCITY;
					
//					if (snakeList.size() >= 1) {
//						for (int i = 1; i < snakeList.size(); i++) {
//							GameObj.moveTails(i, snakeList.get(i).getX(), snakeList.get(i).getY(), snakeList, Direction.RIGHT);
//						}
//					}
					
//					for (GameObj obj : snakeList) {
//						obj.v_y = 0;
//						obj.v_x = SNAKE_VELOCITY;
//					}
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN && snake.v_y != -SNAKE_VELOCITY) {
					moving = true;
					snake.v_x = 0;
					snake.v_y = SNAKE_VELOCITY;
					
//					if (snakeList.size() >= 1) {
//						for (int i = 1; i < snakeList.size(); i++) {
//							GameObj.moveTails(i, snakeList.get(i).getX(), snakeList.get(i).getY(), snakeList, Direction.DOWN);
//						}
//					}
					
//					for (GameObj obj : snakeList) {
//						obj.v_y = SNAKE_VELOCITY;
//						obj.v_x = 0;
//					}
				}
				else if (e.getKeyCode() == KeyEvent.VK_UP && snake.v_y != SNAKE_VELOCITY) {
					moving = true;
					snake.v_x = 0;
					snake.v_y = -SNAKE_VELOCITY;
					
//					if (snakeList.size() >= 1) {
//						for (int i = 1; i < snakeList.size(); i++) {
//							GameObj.moveTails(i, snakeList.get(i).getX(), snakeList.get(i).getY(), snakeList, Direction.UP);
//						}
//					}
					
//					for (GameObj obj : snakeList) {
//						obj.v_y = -SNAKE_VELOCITY;
//						obj.v_x = 0;
//					}
				}
			}		
		});

		this.status = status;
	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {
		snake = new SnakeHead(COURT_WIDTH, COURT_HEIGHT, 150, 150);
		snakeList.removeAll(snakeList);
		snakeList.add(snake);
		
		posList.removeAll(posList);
		posList.add(new Position(snake.getX(), snake.getY()));
		
		powerup = new GrowOne(25, 25);
		Highscores.updateScores(name, score);

		playing = true;
		moving = false;
		score = 0;
		length = 1;
		status.setText("Score: 0 \nLength: 1");

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}

	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {
		if (playing) {
			int headX = snake.getX();
			int headY = snake.getY();
			
			snake.move();
			
			posList.get(0).setX(headX);
			posList.get(0).setY(headY);
			
//			if (posList.size() >= 2) {
//				for (int i = 1; i < posList.size(); i++) {
//					posList.get(i).setX(posList.get(i - 1).getX());
//					posList.get(i).setY(posList.get(i - 1).getY());
//				}
//			}
			
			if (posList.size() >= 2) {
				for (int i = posList.size() - 1; i > 0; i--) {
					posList.get(i).setX(posList.get(i - 1).getX());
					posList.get(i).setY(posList.get(i - 1).getY());
				}
			}
			
//			for (GameObj obj : snakeList) {
//				if (obj != snake) {
//					obj.move();
//				}
//			}
			
			// check for game end conditions
			if (snake.intersectTail()) {
				playing = false;
				moving = false;
				status.setText("Game Over! Score: " + score);
				Highscores.updateScores(name, score);
			}
			
			// check for power up intersection
			if (snake.powerup(powerup)) {
				if (powerup instanceof GrowOne) {
					length += 1;
					
					tail = snakeList.get(snakeList.size() - 1);
					Direction dir = tail.getDirection();
					
					if (dir == Direction.LEFT) {
						SnakeTail tail1 = new SnakeTail(COURT_WIDTH, COURT_HEIGHT, tail.getX() + 11, tail.getY(), -SNAKE_VELOCITY, 0);
						snakeList.add(tail1);
						posList.add(new Position(snakeList.get(snakeList.size() - 1).getX(), snakeList.get(snakeList.size() - 1).getY()));
					}
					else if (dir == Direction.RIGHT) {
						SnakeTail tail1 = new SnakeTail(COURT_WIDTH, COURT_HEIGHT, tail.getX() - 11, tail.getY(), SNAKE_VELOCITY, 0);
						snakeList.add(tail1);
						posList.add(new Position(snakeList.get(snakeList.size() - 1).getX(), snakeList.get(snakeList.size() - 1).getY()));
					}
					else if (dir == Direction.UP) {
						SnakeTail tail1 = new SnakeTail(COURT_WIDTH, COURT_HEIGHT, tail.getX(), tail.getY() + 11, 0, -SNAKE_VELOCITY);
						snakeList.add(tail1);
						posList.add(new Position(snakeList.get(snakeList.size() - 1).getX(), snakeList.get(snakeList.size() - 1).getY()));
					}
					else if (dir == Direction.DOWN) {
						SnakeTail tail1 = new SnakeTail(COURT_WIDTH, COURT_HEIGHT, tail.getX(), tail.getY() - 11, 0, SNAKE_VELOCITY);
						snakeList.add(tail1);
						posList.add(new Position(snakeList.get(snakeList.size() - 1).getX(), snakeList.get(snakeList.size() - 1).getY()));
					}
				}
				else if (powerup instanceof GrowTwo) {
					length += 2;
					
					tail = snakeList.get(snakeList.size() - 1);
					Direction dir = tail.getDirection();
					
					if (dir == Direction.LEFT) {
						SnakeTail tail1 = new SnakeTail(COURT_WIDTH, COURT_HEIGHT, tail.getX() + 11, tail.getY(), -SNAKE_VELOCITY, 0);
						snakeList.add(tail1);
						posList.add(new Position(snakeList.get(snakeList.size() - 1).getX(), snakeList.get(snakeList.size() - 1).getY()));
						
						tail = snakeList.get(snakeList.size() - 1);
						dir = tail.getDirection();
						
						SnakeTail tail2 = new SnakeTail(COURT_WIDTH, COURT_HEIGHT, tail.getX() + 11, tail.getY(), -SNAKE_VELOCITY, 0);
						snakeList.add(tail2);
						posList.add(new Position(snakeList.get(snakeList.size() - 1).getX(), snakeList.get(snakeList.size() - 1).getY()));
					}
					else if (dir == Direction.RIGHT) {
						SnakeTail tail1 = new SnakeTail(COURT_WIDTH, COURT_HEIGHT, tail.getX() - 11, tail.getY(), SNAKE_VELOCITY, 0);
						snakeList.add(tail1);
						posList.add(new Position(snakeList.get(snakeList.size() - 1).getX(), snakeList.get(snakeList.size() - 1).getY()));
						
						tail = snakeList.get(snakeList.size() - 1);
						dir = tail.getDirection();
						
						SnakeTail tail2 = new SnakeTail(COURT_WIDTH, COURT_HEIGHT, tail.getX() - 11, tail.getY(), -SNAKE_VELOCITY, 0);
						snakeList.add(tail2);
						posList.add(new Position(snakeList.get(snakeList.size() - 1).getX(), snakeList.get(snakeList.size() - 1).getY()));
					}
					else if (dir == Direction.UP) {
						SnakeTail tail1 = new SnakeTail(COURT_WIDTH, COURT_HEIGHT, tail.getX(), tail.getY() + 11, 0, -SNAKE_VELOCITY);
						snakeList.add(tail1);
						posList.add(new Position(snakeList.get(snakeList.size() - 1).getX(), snakeList.get(snakeList.size() - 1).getY()));
						
						tail = snakeList.get(snakeList.size() - 1);
						dir = tail.getDirection();
						
						SnakeTail tail2 = new SnakeTail(COURT_WIDTH, COURT_HEIGHT, tail.getX(), tail.getY() + 11, -SNAKE_VELOCITY, 0);
						snakeList.add(tail2);
						posList.add(new Position(snakeList.get(snakeList.size() - 1).getX(), snakeList.get(snakeList.size() - 1).getY()));
					}
					else if (dir == Direction.DOWN) {
						SnakeTail tail1 = new SnakeTail(COURT_WIDTH, COURT_HEIGHT, tail.getX(), tail.getY() - 11, 0, SNAKE_VELOCITY);
						snakeList.add(tail1);
						posList.add(new Position(snakeList.get(snakeList.size() - 1).getX(), snakeList.get(snakeList.size() - 1).getY()));
						
						tail = snakeList.get(snakeList.size() - 1);
						dir = tail.getDirection();
						
						SnakeTail tail2 = new SnakeTail(COURT_WIDTH, COURT_HEIGHT, tail.getX(), tail.getY() - 11, -SNAKE_VELOCITY, 0);
						snakeList.add(tail2);
						posList.add(new Position(snakeList.get(snakeList.size() - 1).getX(), snakeList.get(snakeList.size() - 1).getY()));
					}
				}
				else {
					length -= 1;
					
					snakeList.remove(snakeList.size() - 1);
					posList.remove(posList.size() - 1);
				}
				queue.remove(0);
				placeItem(queue.get(0));
				score += 10;
				status.setText("Score: " + score + "\nLength: " + length);
			}
			
			// check state of powerup queue
			if (queue.size() == 1) {
				refreshQueue();
				refreshMap();
			}
			
			// update positions
			for (int i = 1; i < posList.size(); i++) {
				snakeList.get(i).setX(posList.get(i).getX());
				snakeList.get(i).setY(posList.get(i).getY());
			}

			// update the display
			repaint();
		}
	}
	
	public void refreshQueue() {
		while (queue.size() < 10) {
			double random = Math.random();
			
			if (random < 0.1) {
				queue.add(new GrowTwo(0, 0));
			}
			else if ((random >= 0.1) && (random < 0.2)) {
				queue.add(new ShrinkOne(0, 0));
			}
			else {
				queue.add(new GrowOne(0, 0));
			}
		}
	}
	
	public void refreshMap() {
		for (int h = 0; h < COURT_HEIGHT; h++) {
			for (int w = 0; w < COURT_WIDTH; w++) {
				map[h][w] = 0;
			}
		}
	}
	
	public void placeItem(PowerUp p) {
		int x = (int)(Math.random() * 280) + 1;
		int y = (int)(Math.random() * 280) + 1;
		
		if (map[x][y] == 0) {
			map[x][y] = 1;
			p.setX(x);
			p.setY(y);
			powerup = p;
		}
		else {
			placeItem(p);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		snake.draw(g);
		powerup.draw(g);
		
		if (posList.size() >= 2) {
			for (GameObj obj : snakeList) {
				if (obj != snake) {
					obj.draw(g);
				}
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}
