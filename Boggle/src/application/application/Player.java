package application;

import java.util.ArrayList;

public class Player {
    public int score;
    Player() {
        this.score = 0;
    }

    
    
    
    
    
    
    /**
     * Allows the user to input a String and increment their point counter
     * @param input The string that the user inputted.
     * @param validWords The list of valid words on the board.
     * 
     * Una
     */
   

    public int select(String input, ArrayList<String> validWords, ArrayList<String> usedWords) {
    	
    	input = input.toUpperCase();
        int points = 0; 
        int lindex = 0; int rindex = validWords.size()-1; // left index, right index
   	 	
        while (lindex <= rindex) {
            int mindex = lindex + (rindex - lindex)/2;
            if (validWords.get(mindex).compareTo(input) == 0) {

            	removeWord(validWords.get(mindex), validWords, usedWords);
            	points = incrementPoints(input); //edited - Aidan 
            	return points;
            } else if (validWords.get(mindex).compareTo(input) < 0) { // If the string comes after the middle index
                lindex = mindex + 1;
            } else {
                rindex = mindex - 1;
            }
        }

        return points; 
    }


    /**
     * Increment the points of the player depending on the string.
     * @param input The string that the player inputted.
     * @return The number of points that should be incremented.
     * 
     * Una
     */
    public int incrementPoints(String input) {
    	int length = input.length();
        if (length <= 4) {

            return 1;
        } else if (length == 5) {
            return 2;
        } else if (length == 6) {
            return 3;
        } else if (length == 7) {
            return 5;
        } else {
            return 11;
        }
    }
    
    
    /**
     * @param int added
     * @return void
     * 
     * used to add upon the totalScore of the player 
     * 
     * Aidan
     */
    
    
    public  boolean decideScramble() {
    	return true; 
    }
    
    
    
    public void setScore(int added) {
    	 score += added; 
    }
    
    /**
     * @param String word, ArrayList validWords, ArrayList usedWords 
     * @return void
     * will remove the word from validWords so it can be used again and will add it to usedWords
     * 
     * Aidan 
     */
    public void removeWord(String word, ArrayList <String> validWords, ArrayList <String> usedWords) {

    	usedWords.add(word);
    	validWords.remove(word);
    }
    
}