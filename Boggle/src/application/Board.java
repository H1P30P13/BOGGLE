package application;


import java.io.*;
import java.util.*;

import javax.swing.JLabel;

public class Board {
    Dice[][] dices;
    String dictionaryFilePath;
    public static ArrayList<String> validWords = new ArrayList <String> (); 
    public static ArrayList<String> usedWords = new ArrayList <String> ();
    char [][] boardChar = new char [5][5];
    
    
    
    /*
     * Creates the dices
     * and find all the words in the boggle board using wordBoggle
     * 
     * Aidan
     */
    public Board() throws FileNotFoundException {
    	//this.timer = timer; 
    	dices = new Dice[5][5];
        createDice();
        dictionaryFilePath = "/Users/aidan/eclipse-workspace/Boggle/src/application/dictionary.txt";

        
        try {
        	wordBoggle(dices, dictionaryFilePath);
        }
        catch (Exception e) {
        	System.out.println("The file path was not found. Please enter Board.java and change the dictionaryFilePath at line ____ to direct file path of your dictionary.txt file");
        	System.exit(1);
        }

    }
    
    
    
    
    
    
    
    
    // DFS function to check if the word can be found starting from (i, j)
    //Referenced Code 
    
    /*
     * @param is the board array, the word, the starting locations (i and j refer to word boggle to understand)
     * the boarder n and m, and the idx which represents how many times this has happened for each non-recursive call
     * 
     * @returns boolean (true if in the board and false if not in the board)
     * 
     * checks if the i and j is in bounds of board, if not return false
     * 
     * if the char of the word does not equal to the board index than return false
     * 
     * if amount of times it has recurses is equal to the word's length -1 than return true
     * (I think its not checking if the word is the same per se but checks if the word hasn't been eliminated yet from the above)
     * 
     * it will recurse with the index around the starting location 
     * 
     * returns boolean 
     * 
     * Aidan 
     * 
     * 
     * Referenced code and unedited
     */
    


	boolean dfs(char[][] boardChar, String word, int i, int j, int n, int m, int idx) {
        if (i < 0 || i >= n || j < 0 || j >= m) { // Checks if out of bounds and returns false 
            return false;
        }
        if (word.charAt(idx) !=boardChar[i][j]) { // if the char on boardChar does not equal the char of word than false 
        
            return false;
        }
        if (idx == word.length() - 1) { // if index equals the length of the words returns true to keep word
        	return true;				// doesnt need to check if all the chars is the same since it would already return false if not
        }
        char temp = boardChar[i][j];
        boardChar[i][j] = '*'; // marks taken places

        // recurses but changed the location of the i and j for a new place in the board 
        boolean a = dfs(boardChar, word, i, j + 1, n, m, idx + 1);
        boolean b = dfs(boardChar, word, i, j - 1, n, m, idx + 1);
        boolean c = dfs(boardChar, word, i + 1, j, n, m, idx + 1);
        boolean d = dfs(boardChar, word, i - 1, j, n, m, idx + 1);
        boolean e = dfs(boardChar, word, i + 1, j + 1, n, m, idx + 1);
        boolean f = dfs(boardChar, word, i - 1, j + 1, n, m, idx + 1);
        boolean g = dfs(boardChar, word, i + 1, j - 1, n, m, idx + 1);
        boolean h = dfs(boardChar, word, i - 1, j - 1, n, m, idx + 1);

        boardChar[i][j] = temp;
        return a || b || c || e || f || g || h || d;
    }
    
	
	
	
	
	
	
   
    /* Function to check all words in the dictionary
    * 
    * Parameters 2d Dices array representing the board and file path 
    * 
    * first takes the size of board which is 5x5
    * than it takes the chars from the dices and creates a 2d array which chars 
    * 
    * Next it will take a word from the dictionary 
    * than check if it is above 2 characters and change it to uppercase(for it to be compared with uppercase board)
    * 
    * it will then repeatedly use the dfs function and for each word it repeats 25(or n*m) times 
    * which is for every spot on the board
    * 
    * if the word is already in validWords than it wont add it (to prevente duplciates since same word can be found multiple times )
    * 
    * Referenced Code but edited to work within the program 
    * Aidan
	*/  
    
	void wordBoggle(Dice[][] dices, String dictionaryFilePath) throws FileNotFoundException {
        int n = dices.length;
        int m = dices[0].length;
        
        
        // Print the boardChar for debugging
        // Takes the faces/char from the dice and puts it into a char array 
        
        
        
        for (int o = 0; o < n; o++) {
            for (int p = 0; p < m; p++) {
        		boardChar [o][p] = dices[o][p].getFace();
            }
        }

        //Scan each word in the dictionary 
        Scanner scan = new Scanner (new File (dictionaryFilePath));
            String word = null;
            while (scan.hasNextLine()) {
            	//turns the word into an uppercase so it can be compared with capitalized chars 
            	word= scan.nextLine().toUpperCase();  	
            	if (word.length() > 2) { // filters words taken to make sure length is long enough 
            			for (int i = 0; i < n; i++) {
                			for (int j = 0; j < m; j++) {  
                				if (dfs(boardChar, word, i, j, n, m, 0)) {
                					if (!validWords.contains(word)){
                						validWords.add(word); // stored the words in the list 
                					}
	                			}
	                		}
	            		}
	            	}
	            }
         
         System.out.println("valid words for you to test");
         for (int i = 0; i  < validWords.size(); i++)
            	System.out.println(validWords .get(i));
        
    	}

	
	
	
	
    // Method to scramble the boardChar
    // Aidan 
    public void Scramble() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                dices[i][j].newFaces();
            }
        }
    }

    
    
    
    
    
    
    /**
     * Method to create and initialize the dice on the boardChar
     * @param nothing
     * @return void 
     * 
     * Aidan
     */
    
    public void createDice() {
        dices[0][0] = new Dice("AAAFRS"); 
        dices[0][1] = new Dice("AEEGMU");
        dices[0][2] = new Dice("CEIILT");
        dices[0][3] = new Dice("DHHNOT");
        dices[0][4] = new Dice("FIPRSY");

        dices[1][0] = new Dice("AAEEEE");
        dices[1][1] = new Dice("AEGMNN");
        dices[1][2] = new Dice("CEILPT");
        dices[1][3] = new Dice("DHLNOR");
        dices[1][4] = new Dice("GORRVW");

        dices[2][0] = new Dice("AAFIRS");
        dices[2][1] = new Dice("AEGMNN");
        dices[2][2] = new Dice("CEIPST");
        dices[2][3] = new Dice("EIITTT");
        dices[2][4] = new Dice("HIPRRY");

        dices[3][0] = new Dice("ADENNN");
        dices[3][1] = new Dice("BJKQXZ");
        dices[3][2] = new Dice("DDLNOR");
        dices[3][3] = new Dice("EMOTTT");
        dices[3][4] = new Dice("NOOTUW");

        dices[4][0] = new Dice("AEEEEM");
        dices[4][1] = new Dice("CCNSTW");
        dices[4][2] = new Dice("DHHLOR");
        dices[4][3] = new Dice("ENSSSU");
        dices[4][4] = new Dice("OOOTTU");
    }
    
    
    
    
    
    
    
    /*
     * Paramters is the validWordsList and the usedWordsList
     * 
     * will compare the words in the useWords to the validWord
     * If a word in usedWords is found in validWords than it will delete/remove it 
     * from the validWords to avoid repeats 
     * 
     * this can happen when the board is rescrambled and there are words in both board
     * 
     * Aidan 
     */
    public static void filterUsedWords(List<String> validWords, List<String> usedWords) {
        
    	if (usedWords !=  null) {
	    	for (String word : usedWords) {
	            int index = binarySearch(validWords, word, 0 , validWords.size()-1);
	            if (index >= 0) {
	                validWords.remove(index);
	            }
	        }
    	}
    }

    
    
    
    
    
    /**
     *  simple binary search for the filterUsedWord function, if -1 returned means not in 
     * @param list (list of the words to check)
     * @param target (the word that is being checked)
     * @param left 
     * @param right
     * @return integer (the index of where to remove)
     */
    // Aidan 
    
    private static int binarySearch(List<String> list, String target, int left, int right) {
        if (left > right) {
            return -1; 
        }

        int mid = left + (right - left) / 2;
        int comparison = list.get(mid).compareTo(target);

        if (comparison == 0) {
            return mid; 
        } else if (comparison < 0) {
            return binarySearch(list, target, mid + 1, right); 
        } else {
            return binarySearch(list, target, left, mid - 1); 
        }
    }
    
    
   
}

