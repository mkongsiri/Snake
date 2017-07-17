import javax.swing.JOptionPane;
import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class Highscores {
	
	private static Map<String, Integer> scores = new TreeMap<String, Integer>();
	private static int low;
	
	public static void updateScores(String name, int score) {
		try {
			FileReader r = new FileReader("highscores.txt");
			BufferedReader reader = new BufferedReader(r);
			String next = null;
			
			next = reader.readLine();
			while (next != null) {
				String item = next;
				String player = item.substring(0, item.indexOf(","));
				int points = Integer.valueOf(item.substring(item.indexOf(",") + 2));
				
				scores.put(player, points);
				
				next = reader.readLine();
			}
			
			low = Integer.MAX_VALUE;
			for (Map.Entry<String, Integer> i : scores.entrySet()) {
				if (i.getValue() < low) {
					low = i.getValue();
				}
			}
			
			if (scores.containsKey(name)) {
				if (scores.get(name) < score) {
					scores.put(name, score);
				}
			}		
			else if (scores.size() < 5) {
				scores.put(name, score);
			}
			else {
				String remove = "";
				for (Map.Entry<String, Integer> i : scores.entrySet()) {
					if (i.getValue() == low) {
						remove = i.getKey();
					}
				}
				
				scores.remove(remove);
				scores.put(name, score);
			}
			
			reader.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("highscores.txt not found");
		}
		catch (IOException e) {
			System.out.println("File IO error");
		}
		
		try {
			FileWriter f = new FileWriter("highscores.txt");
			BufferedWriter writer = new BufferedWriter(f);
			
			for (Map.Entry<String, Integer> i : scores.entrySet()) {
				writer.write(i.getKey() + ", " + i.getValue());
				writer.newLine();
			}
			
			writer.close();
		} catch (IOException e) {
			System.out.println("highscores.txt not found");
		}
	}
	
	public static void showScores() {
		String scores = "";
		
		try {
			FileReader r = new FileReader("highscores.txt");
			BufferedReader reader = new BufferedReader(r);
			String next = reader.readLine();
			
			while (next != null) {
				scores += next + "\n";
				
				next = reader.readLine();
			}
			
			reader.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("highscores.txt not found");
		}
		catch (IOException e) {
			System.out.println("File IO error");
		}
		
		JOptionPane.showMessageDialog(null, scores);
	}
}
