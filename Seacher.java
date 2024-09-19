package versuch1;

import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Seacher {
	static PriorityQueue<Zustand> todo = new PriorityQueue<Zustand>((z1, z2) -> Integer.compare(z1.getKosten(), z2.getKosten()));
	static Hashtable<String, Zustand> schonda = new Hashtable<String, Zustand>();
	
	// Der eigentliche Suchalgorithmus
	public static Zustand findeOptimum(Zustand s) {
		Zustand primo;
		
		// Bearbeitung von Anfangszustand
		s.setRef(null);
		Heuristik.heu1(s, 0);
		schonda.put(s.getCode(), s);
		todo.add(s);
		
		while(true) {
			if(todo.isEmpty()) {
				System.out.println("Fail, no solution found");
				return null;
			} else {
				primo = todo.poll();
				// Schaue ob die Manhattendistanz gleich 0 ist
				if(primo.getManhatten() == 0) {
					System.out.println("Solution found");
					return primo;
				} else {
					LinkedList<Zustand> expandedStates = expand(primo);
					todo.addAll(expandedStates);
				}
			}
		}
	}
	
	// Hier expandieren wir den Zustand richtig
	public static LinkedList<Zustand> expand(Zustand now){
		LinkedList<Zustand> back = new LinkedList<Zustand>();
		int[][] num = now.getNumeric();
		
		// Welche Moeglichkeiten gibt es?
		Tuple dieNull = null;
		A: for(int i = 0; i < num.length; i++) {
			for(int j = 0; j < num[i].length; j++) {
				if(num[i][j] == 0) {
					dieNull = new Tuple(i, j);
					break A;
				}
			}
		}
		
		// Die vier Kombinationen fuer das Tauschen testen und erstellen
		if(dieNull.getX() + 1 <= 2) {
			int[][] klon1 =  new int[num.length][];
			for(int i = 0; i < num.length; i++) {
				klon1[i] = num[i].clone();
			}
			klon1[dieNull.getX()][dieNull.getY()] = klon1[dieNull.getX() + 1][dieNull.getY()];
			klon1[dieNull.getX() + 1][dieNull.getY()] = 0;
			duplikat(now, new Zustand(klon1), back);
		}
		if(dieNull.getX() - 1 >= 0) {
			int[][] klon2 =  new int[num.length][];
			for(int i = 0; i < num.length; i++) {
				klon2[i] = num[i].clone();
			}
			klon2[dieNull.getX()][dieNull.getY()] = klon2[dieNull.getX() - 1][dieNull.getY()];
			klon2[dieNull.getX() - 1][dieNull.getY()] = 0;
			duplikat(now, new Zustand(klon2), back);
		}
		if(dieNull.getY() + 1 <= 2) {
			int[][] klon3 =  new int[num.length][];
			for(int i = 0; i < num.length; i++) {
				klon3[i] = num[i].clone();
			}
			klon3[dieNull.getX()][dieNull.getY()] = klon3[dieNull.getX()][dieNull.getY() + 1];
			klon3[dieNull.getX()][dieNull.getY() + 1] = 0;
			duplikat(now, new Zustand(klon3), back);
		}
		if(dieNull.getY() - 1 >= 0) {
			int[][] klon4 =  new int[num.length][];
			for(int i = 0; i < num.length; i++) {
				klon4[i] = num[i].clone();
			}
			klon4[dieNull.getX()][dieNull.getY()] = klon4[dieNull.getX()][dieNull.getY() - 1];
			klon4[dieNull.getX()][dieNull.getY() - 1] = 0;
			duplikat(now, new Zustand(klon4), back);
		}
		
		return back;
	}
	
	// Hier ueberpruefen wir ob es ein duplikat gibt und ob wir den Knoten zu expanded List hinzufuegen
	public static void duplikat(Zustand now, Zustand neu, LinkedList<Zustand> back) {
		Heuristik.heu1(neu, now.getRunde() + 1);
		neu.setRef(now);
		
		if(schonda.containsKey(neu.getCode())) {
			
			// Hier aktualisieren wir die Table um welches Element es sich handelt mit den geringsten Kosten
			if(neu.getKosten() < schonda.get(neu.getCode()).getKosten()) {
				schonda.put(neu.getCode(), neu);
				back.add(neu);
			}
		} else {
			schonda.put(neu.getCode(), neu);
			back.add(neu);
		}
	}
	 
	
	// Hier wird die graphische Ausgabe erzeugt
	public static void aufklappen(Zustand now) {
		LinkedList<Zustand> draw = new LinkedList<Zustand>();
		StringBuffer sbuf = new StringBuffer();
		
		if(now == null) {
			return;
		}
		while(now != null) {
			draw.add(now);
			now = now.getDavor();
		}
		
		Collections.reverse(draw);
		sbuf.append("How to Solve your Probleme :)\n");
		
		int i = 1;
		for(Zustand jz : draw) {
			if(jz.getManhatten() == 0) {
				sbuf.append("\nSchritt " + i +":\n");
				sbuf.append(jz.toString());
				break;
			}
			sbuf.append("\nSchritt " + i +":\n");
			sbuf.append(jz.toString());
			i++;
		}
		
		System.out.println(sbuf.toString());
	}
	public static void main(String[] args) {
		int [][] input = {{0, 2, 1}, {3, 4, 5}, {6, 7, 8}};
		Zustand neu = new Zustand(input);
		Zustand opt = findeOptimum(neu);
		aufklappen(opt);
	}
}
