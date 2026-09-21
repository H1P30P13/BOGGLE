package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class AI extends Player {
    private static int difficulty;
    Timer AITimer;
    public String word;

    AI(int difficulty) {
        this.difficulty = difficulty;
    }
    @Override
    public int select(String input, ArrayList<String> validWords, ArrayList<String> usedWords) {
        return super.select(input, validWords, usedWords); // Call the parent method if needed
    }

    
    /**
     * @param is validWordList which contains all the valid words for the user to get and the 
     * usedWordList which will be used to put the selected word in 
     * 
     * @return a String which is the word selected or blank 
     * 
     * Una  
     */
    public String select(ArrayList<String> validWords,  ArrayList<String> usedWords) {
    	/*
    	 * randomly decides not to find word and return blank 
    	 */
    	
    	
    	
    	return " ";
    	
    	/*
    	int findNothing = (int)(Math.random()*10);

    	if (findNothing <=2) {
    		return " ";
    	}
    	
    	
    	
       	for (int i=0; i< (int) Math.floor(validWords.size())*0.60; i++) {
            int index = (int) Math.floor(Math.random() * validWords.size());
            if (difficulty == 1) {
                if (validWords.get(index).length() <= 4) {
                	return validWords.get(index);

                }
            } else if (difficulty == 2) {
                if (validWords.get(index).length() == 5 || validWords.get(index).length() == 6) {
                	return validWords.get(index);

                }
            } else if (difficulty == 3) {
                if (validWords.get(index).length() >= 7) {
                	return validWords.get(index);
                }
            }
        }
            	
    		
    	return " ";
    	
    	*/
    }

    /*
     * returns boolean deciding whether to scramble or not 
     * 
     * @oaram nothing
     * @return return a boolean which is decided randomly
     * 
     * Aidan
     */
    public boolean decideScramble() {
    	
    	

    	/*
    	 * randomly decides not to do scramble
    	 * Aidan
    	 */
    	
    	
    	return false; 
    	
    	/*
    	
    	
    	int decision = (int) (Math.random()* 10);
    	
    	if (decision <= 3) {
    		return true;
    	}
    	else {
    		return false;
    	}
    	*/
    	
    }
    
}