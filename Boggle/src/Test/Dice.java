package Test;

public class Dice extends Board {

	private String face; 
	private String [] faces; 

	public Dice (String [] faces) {
		int random = (int)(Math.random()*6);
		this.faces = faces; 
		this.face = faces[random];
	}
}
