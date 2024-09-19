package versuch1;

import java.util.Hashtable;

public class Heuristik {
	
	private static final Hashtable<Integer, Tuple> genesis = new Hashtable<Integer, Tuple>(); // Richtige Anordnung der Zahlen in 3 x 3
	
	static {
		for(int i = 1; i <= 8; i++) {
			genesis.put(i, new Tuple(i / 3, i % 3));
		}
	}
	
	// Hier kann man die verschiedenen Heuristiken anlegen
	public static void heu1(Zustand z, int runde) {
		// Hier wird die Manhatten-Heuristik implementiert
		int[][] now = z.getNumeric();
		int sum = 0;
		Tuple distanz;
		
		// Kosten werden hier zusammengerechnet
		for(int i = 0; i < now.length; i++) {
			for(int j = 0; j < now[i].length; j++) {
				if(now[i][j] == 0) {
					continue;
				}
				distanz = genesis.get(now[i][j]);
				sum += Math.abs(i - distanz.getX());
				sum += Math.abs(j - distanz.getY());
			}
		}
		z.setRunde(runde);
		z.setManhatten(sum);
		z.setKosten(sum + runde);
	}
	
}
