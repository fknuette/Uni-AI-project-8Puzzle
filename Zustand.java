package versuch1;

public class Zustand{
	private String code; // Repräsentativer Wert
	private Zustand previous;
	private int[][] numeric; // Tatsächlicher Wert
	private int kosten;
	private int runde;
	private int manhatten;
	// private boolean expanded;
	
	public Zustand(int[][] num) {
		this.numeric = num;
		for(int[] zeile : num) {
			for(int spalte : zeile) {
				code += Integer.toString(spalte);
			}
		}
	}
	
	
	// Hier die Getter
	public String getCode() {
		return this.code;
	}
	public Zustand getDavor() {
		return this.previous;
	}
	public int[][] getNumeric(){
		return this.numeric;
	}
	public int getKosten() {
		return this.kosten;
	}
	public int getRunde() {
		return this.runde;
	}
	public int getManhatten() {
		return this.manhatten;
	}
//	public boolean wasExpanded() {
//		
//	}
	
	// Hier die Setter
	public void setKosten(int k) {
		this.kosten = k;
	}
	public void setManhatten(int m) {
		this.manhatten = m;
	}
	public void setRef(Zustand s) {
		this.previous = s;
	}
	public void setRunde(int num) {
		this.runde = num;
	}
	
	@Override
	public String toString() {
		StringBuffer sbuf = new StringBuffer();
		for(int i = 0; i < numeric.length; i++) {
			for(int j = 0; j < numeric[i].length; j++){
				if(numeric[i][j] == 0) {
					sbuf.append("| |");
					continue;
				}
				sbuf.append("|" + numeric[i][j] + "|");
			}
			if(i != 2) {
				sbuf.append("\n=========\n");
			}
		}
		sbuf.append("\n");
		return sbuf.toString();
	}
}
