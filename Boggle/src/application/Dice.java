package application;

public class Dice {
	private String faces; 
	private char face; 

/*
 * Passes in the string faces with all the String of the dice
 * face is a char which hold the character of the face that is up 
 * 
 * Aidan 
 */
	public Dice(String faces){
		this.faces = faces;
		//this.face = faces.charAt((int) (Math.random()*6));
		this.face = newFaces();

	}
	
	/*
	 * Selects a character from the string to be represented as the face-up side
	 * @param nothing
	 * @param nothing
	 * 
	 * Aidan 
	 */
	
	public char newFaces() {
		
		this.face = faces.charAt((int) (Math.random()*6));
		return face; 
	}
	
	
	/*
	 * getter method for the face-up side
	 * 
	 * @param nothing
	 * @return char face (up-side one)
	 * 
	 * Aidan 
	 */
	public char getFace() {
		return face; 
	}
	
	/*
	 * setter method to change the upside face
	 * 
	 * @param new char face (up-side one)
	 * @return nothing
	 *
	 * Aidan 
	 */
	public void setFace(char face) {
		this.face = face; 
	}
	
}
