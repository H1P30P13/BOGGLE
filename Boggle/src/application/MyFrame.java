package application; 


import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import javax.swing.Timer;
import java.util.ArrayList;

/**
 * @author Jefferson Zeng 2024/06/12
 * @author Aidan Hama 2024/06/12
 */

/*
 * This class will create the GUI for a java interpretation of the board game
 * Boggle
 */

/**
 * Notes
 * 
 * Need to add pause feature need to add game over feature (point limit thing)
 * Add restriction so player cannot use scramble button on AI turn enhance
 * Features and GUI Check if everything works fine
 */
public class MyFrame extends JFrame implements ActionListener {

	boolean scrambleBoolean = false;
	
	
	// panels
	private JPanel buttonPanel;
	private JPanel wordPanel;
	private JPanel timerPanel;
	private JPanel messagePanel;
	private JPanel rulesPanel;
	private JPanel bookCover;
	
	private String chosenWord;
	private String AIChosenWord;

	
	
	// buttons
	private JButton buttonArray[][] = new JButton[5][5];
	private JButton submitButton;
	private JButton scrambleButton;
	private JButton restartButton;
	private JButton pauseButton;
	private JButton playerButton;
	private JButton AIButton;
	private JButton tournamentButton;
	private JButton infoButton;

	
	//Icons
	private ImageIcon boggleIcon;
	ImageIcon woodTexture = new ImageIcon(getClass().getResource("/application/woodtexture.png"));
	ImageIcon bigBoggleIcon = new ImageIcon(getClass().getResource("/application/DiceIcons.png"));

	//Player objects
	static Player player1;
	static Player player2;
	
	// labels
	private JLabel wordList;
	private JLabel timerLabel;
	private JLabel BoggleLabel;
	private JLabel turnLabel;
	private JLabel player1ScoreLabel;
	private JLabel player2ScoreLabel;
	private JLabel messageTitleLabel;
	private JLabel messageLabel;
	private JLabel backgroundLabel;
	private JLabel bigBoggleLabel;
	private JLabel infoLabel;
	private JLabel rulesLabel;

	// text fields
	private JTextField textField;
	private JTextField tournamentField;

	
	//Board Object
	private Board board;
	
	//integer variables
	private int scrambleCounter;
	private int wordListLengthCount;
	private int player1Points = 0;
	private int player2Points = 0;
	private int scrambleClick = 0;
	private int tournamentScore = 50;
	private int difficulty = 2; // set to 2 as default if user doesnt enter difficulty
	private int AITimeLeft=4;
	private int AIPauseTime=0;
	private int timeLeft = 15;
	
	
	private String turnStringLabel;
	private String listOfUsedWords = "";

	// LayeredPanels
	private JLayeredPane start = new JLayeredPane();
	private JLayeredPane game = new JLayeredPane();
	private JLayeredPane infoPanel = new JLayeredPane();
	

	// Creates Java timers and other variables needed
	private Timer timer;
	private Timer remainingTimeTimer;
	private Timer AITimer;

	

	private boolean checkPause = false;

	ArrayList<String> messageArray = new ArrayList<String>();


	private JDialog infoFrame;

	/*
	 * This is the constructor class to initialize and call all the components for
	 * the boggle GUI Frame
	 */
	MyFrame() throws FileNotFoundException {
		
		start.setBounds(0, 0, 700, 700);
		game.setBounds(0, 0, 700, 700);

		this.setTitle("Boggle");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700, 720);
		this.setLayout(new BorderLayout());
		this.setResizable(false);

		// Creates the start menu
		createStartMenu();
		this.setVisible(true);

	}

	/**
	 * This method will create the board in the form of a 5x5 button 
	 * 
	 * 
	 * @param nothing
	 * @return void 
	 * 
	 * Jefferson
	 */
	
	
	private void createButton() {
		buttonPanel = new JPanel(new GridLayout(5, 5));
		buttonPanel.setBounds(30, 125, 325, 325);
		for (int r = 0; r < 5; r++) {
			for (int c = 0; c < 5; c++) {
				buttonArray[r][c] = new JButton();
				buttonArray[r][c].setPreferredSize(new Dimension(50, 50));
				buttonPanel.add(buttonArray[r][c]);
			}
		}
		buttonPanel.setBackground(new Color(0, 153, 255));

		game.add(buttonPanel, JLayeredPane.PALETTE_LAYER);
	}

	
	
	
	
	
	/**
	 * Creates a message panel for the game give messages to the user
	 * @param nothing
	 * @return void 
	 * 
	 * Aidan
	 */
	private void createMessagePanel() {
		messageLabel = new JLabel();
		messageTitleLabel = new JLabel();

		messagePanel = new JPanel();
		game.setLayout(null); // Added layout manager

		messagePanel.setBackground(Color.white);
		messageTitleLabel.setBounds(50, 385, 300, 400);

		messagePanel.setBounds(30, 600, 300, 70);
		messageTitleLabel.setText("Message: ");
		messageTitleLabel.setFont(new Font("MONOSPACED", Font.BOLD, 20));
		messageLabel.setBounds(30, 600, 300, 100);

		game.add(messageTitleLabel, JLayeredPane.PALETTE_LAYER);
		messagePanel.add(messageLabel);
		game.add(messagePanel, JLayeredPane.PALETTE_LAYER);
		game.setSize(400, 500);
		game.setVisible(true);
	}

	
	

	
	
	/**
	 * Updates the message Panel with new messages and filters out older ones
	 * @param nothing
	 * @return void 
	 * 
	 * Aidan
	 */
	private void updateMessagePanel(String message) {
		if (messageArray.size() >= 4) {
			messageArray.remove(0);
		}
		messageArray.add(message);

		// Construct the HTML string for messages
		StringBuilder sb = new StringBuilder("<html>");
		for (String msg : messageArray) {
			sb.append(msg).append("<br/>");
		}
		sb.append("</html>");

		// Update the message JLabel's text
		messageLabel.setText(sb.toString());
	}

	
	
	
	
	
	
	/**
	 * Will create the start menu pane and the objects within it
	 * @param nothing
	 * @return void 
	 * 
	 * Aidan
	 */

	private void createStartMenu() {

		bigBoggleLabel = new JLabel();

		bigBoggleLabel.setBounds(25, 250, 650, 200);
		bigBoggleLabel.setIcon(bigBoggleIcon);
		start.add(bigBoggleLabel, JLayeredPane.PALETTE_LAYER);

		backgroundLabel = new JLabel(woodTexture);
		backgroundLabel.setBounds(0, 0, 700, 700);

		start.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

		playerButton = new JButton("Player");
		playerButton.setBounds(250, 135, 83, 40);
		playerButton.addActionListener(this);
		start.add(playerButton, JLayeredPane.PALETTE_LAYER);

		AIButton = new JButton("AI");
		AIButton.setBounds(400, 135, 83, 40);
		AIButton.addActionListener(this);
		start.add(AIButton, JLayeredPane.PALETTE_LAYER);

		tournamentButton = new JButton("Set Tournament Score");
		tournamentButton.setBounds(230, 500, 251, 40);
		tournamentButton.addActionListener(this);
		start.add(tournamentButton, JLayeredPane.PALETTE_LAYER);

		tournamentField = new JTextField();
		tournamentField.setBounds(230, 545, 251, 40);
		start.add(tournamentField, JLayeredPane.PALETTE_LAYER);

		infoButton = new JButton("INFO");
		infoButton.setBounds(310, 635, 83, 40);
		infoButton.addActionListener(this);
		start.add(infoButton, JLayeredPane.PALETTE_LAYER);

		this.add(start, BorderLayout.CENTER);
	}

	
	
	
	
	
	/**
	 * Creates the scramble, restart, and pause buttons
	 * 
	 * @param nothing
	 * @return void 
	 * 
	 * Jefferson
	 */
	private void createScrambleButton() {
		scrambleButton = new JButton("Scramble");
		scrambleButton.setBounds(425, 210, 83, 40);
		scrambleButton.addActionListener(this);
		game.add(scrambleButton, JLayeredPane.PALETTE_LAYER);
	}

	private void createRestartButton() {
		restartButton = new JButton("Restart");
		restartButton.setBounds(506, 210, 83, 40);
		restartButton.addActionListener(this);
		game.add(restartButton, JLayeredPane.PALETTE_LAYER);
	}

	private void createPauseButton() {
		pauseButton = new JButton("Pause");
		pauseButton.setBounds(593, 210, 83, 40);
		pauseButton.addActionListener(this);
		game.add(pauseButton, JLayeredPane.PALETTE_LAYER);
	}

	
	
	
	
	
	/**
	 * This method will create a text field so the player can enter and submit the
	 * words they find
	 * 
	 * @param nothing
	 * @return void 
	 * 
	 * Jefferson
	 */
	private void createTextField() {
		submitButton = new JButton("Submit");
		submitButton.setBounds(425, 260, 251, 40);
		submitButton.addActionListener(this);
		game.add(submitButton, JLayeredPane.PALETTE_LAYER);

		textField = new JTextField();
		textField.setBounds(425, 305, 251, 40);
		game.add(textField, JLayeredPane.PALETTE_LAYER);
	}

	
	
	
	
	
	/**
	 * Creates the scorelabel in the game panel and creates the specific font
	 * characteristics
	 * 
	 * @param nothing
	 * @return void 
	 * 
	 * Aidan
	 */
	private void createScoreLabel() {
		player1ScoreLabel = new JLabel();
		player1ScoreLabel.setBounds(440, -20, 350, 200);
		player1ScoreLabel.setText("Player 1: " + player1.score);
		player1ScoreLabel.setFont(new Font("MONOSPACED", Font.BOLD, 35));
		game.add(player1ScoreLabel, JLayeredPane.PALETTE_LAYER);

		player2ScoreLabel = new JLabel();
		player2ScoreLabel.setBounds(440, 55, 350, 200);
		player2ScoreLabel.setText("Player 2: " + player2.score);
		player2ScoreLabel.setFont(new Font("MONOSPACED", Font.BOLD, 35));
		game.add(player2ScoreLabel, JLayeredPane.PALETTE_LAYER);

	}

	
	
	
	
	/**
	 * Creates the timer and set the label to the start value
	 * 
	 * @param nothing
	 * @return void
	 * 
	 * Aidan
	 */
	private void createTimer() {
		timerPanel = new JPanel();
		timerPanel.setLayout(null);
		timerPanel.setBackground(Color.white);
		timerPanel.setBounds(30, 460, 251, 100);
		timerLabel = new JLabel();
		timerLabel.setFont(new Font("MONOSPACED", Font.BOLD, 20));
		timerLabel.setBounds(5, 45, 100, 50);
		timerLabel.setText("Timer " + "15");
		timerPanel.add(timerLabel);
		game.add(timerPanel, JLayeredPane.PALETTE_LAYER);
	}

	
	
	
	
	/**
	 * Creates the turn label and starts of with player1 this is important since
	 * this decides which player goes next
	 * 
	 * @param nothing
	 * @return void 
	 * 
	 * Aidan
	 */

	private void createTurnLabel() {
		turnStringLabel = "Player 1";
		turnLabel = new JLabel();
		turnLabel.setBounds(35, 385, 200, 200);
		turnLabel.setText(turnStringLabel);
		turnLabel.setFont(new Font("MONOSPACED", Font.BOLD, 40));
		game.add(turnLabel, JLayeredPane.PALETTE_LAYER);
	}

	
	
	
	
	/**
	 * method called to update turn label
	 * 
	 * If turnLabel is player1 it will change to player 2 and vice versa
	 * 
	 * @param nothing
	 * @return void 
	 * 
	 * Aidan
	 */
	public void updateTurnLabel() {

		if (turnLabel.getText().equals("Player 1")) {
			turnLabel.setText("Player 2");
		} else if (turnLabel.getText().equals("Player 2")) {
			turnLabel.setText("Player 1");
		}
	}

	
	
	
	
	
	
	
	/**
	 * Used to give the board new characters when scrambled
	 * 
	 * @param the new char array 
	 * @return void 
	 * 
	 * Jefferson
	 */
	public void setButtonChar(char[][] array) {
		for (int r = 0; r < 5; r++) {
			for (int c = 0; c < 5; c++) {

				buttonArray[r][c].setText(array[r][c] + "");
				buttonArray[r][c].setFont(new Font("MONOSPACED", Font.BOLD, 30));

			}
		}
	}
	
	
	
	
	
	
	

	/**
	 * This method will display the words the player has found
	 * Panel that has a JLabel that stores the list of words and adds it to game
	 * panel
	 * 
	 * @param nothing
	 * @return void 
	 * Aidan and Jefferson	
	*/ 
	private void wordsList() {
		wordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		wordPanel.setBackground(Color.white);
		wordPanel.setBounds(425, 348, 251, 300);
		wordList = new JLabel(listOfUsedWords, SwingConstants.LEFT);
		wordList.setBounds(425, 298, 251, 100);
		wordList.setText("");
		wordPanel.add(wordList, BorderLayout.NORTH);
		game.add(wordPanel, JLayeredPane.PALETTE_LAYER);
	}

	
	
	
	
	
	
	/**
	 * updates the wordsList and adds a new line for the word 
	 * 
	 * @param String newWord (used to add to do list)
	 * @return void 
	 * 
	 * Aidan
	 */

	public void updateWordsList(String newWord) {
		listOfUsedWords = listOfUsedWords + " " + newWord;
		wordListLengthCount += newWord.length();
		if (wordListLengthCount > 20) {
			listOfUsedWords = listOfUsedWords + "<br>";
			wordListLengthCount = 0;
		}
		wordList.setText("<html>" + listOfUsedWords + "</html>");
	}

	
	
	
	
	
	
	
	
	
	/**
	 * Creates the boggle label on the frame 
	 * 
	 * @param nothing
	 * @return void 
	 * 
	 * Aidan
	 */

	private void createBoggleLabel() {

		BoggleLabel = new JLabel();
		BoggleLabel.setBounds(5, -30, 400, 200);
		boggleIcon = new ImageIcon("/Users/aidan/eclipse-workspace/Boggle/src/application/DiceIconsSmall.png");
		BoggleLabel.setIcon(boggleIcon);
		game.add(BoggleLabel, JLayeredPane.PALETTE_LAYER);
	}

	
	
	
	/*
	 * This method will create the action listeners for the buttons
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		/*
		 * Checks for the playerButton on start menu sets the player 2 to be a player
		 * (by passing in 1) and creates the game frame/panel Aidan
		 */

		if (e.getSource() == playerButton) {
			createGame(1);
		}

		
		
		/*
		 * Checks for the playerButton on start menu sets the player 2 to be a AI (by
		 * passing in 2) and creates the game frame/panel Aidan
		 */

		if (e.getSource() == AIButton) {
			Object[] difficultyOptions = { "Easy", "Medium", "Hard" };

			int temp1 = JOptionPane.showOptionDialog(null, "Choose an difficulty", "Options",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, UIManager.getIcon("JOptionPane.questionIcon"), difficultyOptions, null);
			if (temp1 == 0) {
				difficulty = 1;
			} else if (temp1 == 1) {
				difficulty = 2;
			} else if (temp1 == 2) {
				difficulty = 2;
			}

			createGame(2);
		}

		
		
		
		/*
		 * Takes a string from the textfield. Tries to parse it into an integer. 
		 * Will take the integer and set the integer to the tournament score 
		 * 
		 * If the input is invalid, it will tell the user.
		 * 
		 * 
		 * Aidan
		 */
		if (e.getSource() == tournamentButton) {

			String temp = tournamentField.getText();

			String message;

			try {

				tournamentScore = Integer.parseInt(temp);
				message = "Tournament Score Set";

			} catch (Exception NumberFormat) {
				tournamentScore = 50;
				message = "Invalid response, Enter a number";
			}

			JOptionPane.showMessageDialog(null, message, "Tournament Score", JOptionPane.INFORMATION_MESSAGE);
		}

		
		
		
		
		
		//disable user input when paused
		// Yao
		if (e.getSource() == pauseButton) {
			if (checkPause == false) {
				checkPause = true;
				pauseButton.setText("Resume");
				submitButton.setEnabled(false);
				scrambleButton.setEnabled(false);
				restartButton.setEnabled(false);
				textField.setEnabled(false);
				buttonPanel.setVisible(false);
				timer.stop();
				AITimeLeft = 4 - AIPauseTime;
				
				if (player2 instanceof AI ) {
					AITimer.stop();
				}	
			}
			// when unpaused, able user to see again
			else if (checkPause == true) {
				checkPause = false;
				pauseButton.setText("Pause");
				submitButton.setEnabled(true);
				scrambleButton.setEnabled(true);
				restartButton.setEnabled(true);
				textField.setEnabled(true);
				buttonPanel.setVisible(true);

				timer.start();
				
				
				if (player2 instanceof AI) {
					AITimer.start();
				}
			}
		}

		
		
		
		
		
		//Jefferson
		if (e.getSource() == infoButton) {
			createINFOFrame();
		}

		
		
		
		
		
		
		/*
		 * Takes input from the textField and trims it and sets it to upperCase Also
		 * handles if entered blank First it checks if the word is in the board using
		 * dfs If PvP it will decide who goes by reading the turnLabel if PvAI than it
		 * will always go to player1 (first if statement) The word will then go through
		 * the select function and is compared to the valid words. If found it will
		 * return value greater than 0 which are the points
		 * 
		 * If zero returned means not in the validWords and notRealWord is set to true
		 * and will not change the turn label for a 2nd/multiple chance (notRealWord is
		 * not used but can be used to differntiate between inboard or not a word)
		 * 
		 * If the word is valid, it will add the points to player and will update the
		 * wordList on GUI and the score label
		 * 
		 * If player2 is AI it will restart the timers and start the AI Timer (used to
		 * simulate Ai thinking)
		 * 
		 * 
		 * player2 same thing as player 1
		 *
		 * Aidan
		 */

		if (e.getSource() == submitButton) {
			boolean inBoard = false;

			
			
			if (turnLabel.getText().equals("Player 2") && player2 instanceof AI) {
				updateMessagePanel("This is not your turn");
			} 
			else {

				scrambleBoolean = false;
				chosenWord = textField.getText().trim().toUpperCase();

				if (chosenWord.equals("")) {
					inBoard = false;
				} else {
					for (int i = 0; i < 5; i++) {
						for (int j = 0; j < 5; j++) {
							if (board.dfs(board.boardChar, chosenWord, i, j, 5, 5, 0)) {
								inBoard = true;
							}
						}
					}
				}
			}

			if (inBoard) {

				if (turnLabel.getText().equals("Player 1") || player2 instanceof AI) {
					player1Points = player1.select(chosenWord, board.validWords, board.usedWords);
					if (player1Points == 0) {
						updateMessagePanel("Not a real word");

					} else {

						updateTurnLabel();

						scrambleClick = 0;

						player1.setScore(player1Points);
						timeLeft = 15;

						timer.stop();
						timer.start();
						player1ScoreLabel.setText("Player 1: " + player1.score);
						remainingTimeTimer.stop(); 
						remainingTimeTimer.start();
						updateWordsList(chosenWord);
						scrambleCounter = 0;
						
						if (player2 instanceof AI) {

							submitButton.setEnabled(false);
							submitButton.setText("AI Thinking");

							timer.stop();
							timer.start();
							remainingTimeTimer.stop(); 
							remainingTimeTimer.start();

							AITimer.start();

						}
					}
					player1Points = 0;

					
				}

				else {

					player2Points = player2.select(chosenWord, board.validWords, board.usedWords);
					if (player2Points == 0) {
						updateMessagePanel("Not a real wold");

					} else {

						player2.setScore(player2Points);
						player2ScoreLabel.setText("Player 2: " + player2.score);
						scrambleClick = 0;

						timeLeft = 15;
						remainingTimeTimer.stop(); 
						remainingTimeTimer.start();
						timer.stop();
						timer.start();
						updateTurnLabel();

						updateWordsList(chosenWord);
						scrambleCounter = 0;

					}

					player2Points = 0;
				}

				/*
				 * When tournament score is passed call endOption
				 * 
				 * Aidan
				 */

				if (player2.score >= tournamentScore || player1.score >= tournamentScore) {
					try {
						endOption();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
			else {
				updateMessagePanel("Not in Board ");
			}
		}

		
		
		
		
		
		
		/*
		 * Checks if scrambleCounter is 4 or more
		 * 
		 * if so it will check if scrambleClick is 0 and player2 is a Player if these are
		 * true it means there needs to be a second click since there is a another
		 * player who needs to agree and the click was the first click
		 * 
		 * once 2nd click or player2 is Ai
		 * 
		 * If player 2 is an Ai it will randomly decide if it wants to scramble If it is
		 * true the scramble will happen and will check for used words in the new board
		 * and take them out (filterUsedWords) it will also reset the button char and
		 * set scramble click to zero There is also a check to makde sure the players
		 * doesnt click scrambleButton during AI Turn
		 * 
		 * if both players are players types than it will reset the board and do what is
		 * above Aidan
		 */

		if (scrambleCounter > 3 && e.getSource() == scrambleButton) {

			if (player2 instanceof AI && turnLabel.getText().equals("Player 2")) {
				updateMessagePanel("This is not your turn");

			} else {

				updateTurnLabel();

				timer.stop();
				timer.start();
				timeLeft = 15;

				if (scrambleClick == 0 && player2 instanceof Player && !(player2 instanceof AI)) {
					scrambleClick++;

				} else {
					if (player2 instanceof AI) {
						if (player2.decideScramble()) {
							updateMessagePanel("AI wants to scramble");
							updateTurnLabel();
							timer.stop();
							timer.start();
							remainingTimeTimer.stop(); 
							remainingTimeTimer.start();
							
							scrambleCounter = 0;
							board.Scramble();
							try {
								board.wordBoggle(board.dices, board.dictionaryFilePath);
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							}
							board.filterUsedWords(board.validWords, board.usedWords);
							setButtonChar(board.boardChar);
							scrambleClick = 0;
							scrambleBoolean = false;
						}
						else {
							
							updateMessagePanel("AI does not want to scramble");
							
							submitButton.setEnabled(false);
							submitButton.setText("AI Thinking");

							
							timer.stop();
							timer.start();
							remainingTimeTimer.stop(); 
							remainingTimeTimer.start();
							AITimer.start();
							
						}
					} else {
						scrambleCounter = 0;
						board.Scramble();
						try {
							board.wordBoggle(board.dices, board.dictionaryFilePath);
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
						board.filterUsedWords(board.validWords, board.usedWords);
						setButtonChar(board.boardChar);
						scrambleClick = 0;

					}
				}
			}

		} else if (scrambleCounter < 4 && e.getSource() == scrambleButton) {
			JOptionPane.showMessageDialog(null, "can't scramble", "title", JOptionPane.WARNING_MESSAGE);
		}

		
		
		
		/*
		 * call restart function program
		 * 
		 * Aidan
		 */

		if (e.getSource() == restartButton) {
			try {
				restart();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}

		}

	}
	

	
	

	/**
	 * Destroys Original frame and makes new one to restart
	 * @param nothing
	 * @return void
	 * 
	 * 
	 * Aidan
	 */
	public void restart() throws FileNotFoundException {
		this.dispose();
		MyFrame frame = new MyFrame();
	}

	
	
	/**
	 * Creates an optionPane with the options new game, setting/startmenu, and Exit
	 * 
	 * if new game is entered is pressed than it will reset all the values, labels,
	 * and timers
	 * 
	 * If setting/menu is selected than it will restart the game from the menu
	 * 
	 * If exit is pressed it will destroy frame
	 * 
	 * @param nothing
	 * @return void
	 * 
	 * Aidan
	 */

	public void endOption() throws FileNotFoundException {

		String playerName;

		// change back
		if (player1.score > 10) {
			playerName = "Player 1";
		}

		else if (player2.score > 10 && player2 instanceof AI) {
			playerName = "AI";

		}

		else {
			playerName = "Player 2";

		}

		Object[] options = { "New Game", "Setting/Start Menu", "Exit" };
		int result = JOptionPane.showOptionDialog(null, playerName + " won this game", "Options",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		if (result == 0) {
			scrambleCounter = 0;

			listOfUsedWords = "";
			wordList.setText(listOfUsedWords);

			player1.score = 0;
			player2.score = 0;

			player1ScoreLabel.setText("Player 1: " + player1.score);
			player2ScoreLabel.setText("Player 2: " + player2.score);

			turnLabel.setText("Player 1");
			;

			board.validWords.clear();
			board.usedWords.clear();

			timer.stop();
			timer.start();
			remainingTimeTimer.stop();
			remainingTimeTimer.start();

			board.Scramble();

			int type = 1;
			if (player2 instanceof AI) {
				type = 2;
			}
			this.revalidate();
			this.repaint();
		} else if (result == 1) {

			this.dispose();
			MyFrame frame = new MyFrame();

		} else if (result == 2) {
			this.dispose();
		}

	}

	
	
	
	/**
	 * Removes the start panel and Creates the board(the logic) and creates player1
	 * and player(will be Player or Ai depending on what was passed in) If player2
	 * is Ai it will create a timer for it with all the steps inside the game
	 * frame/panel is created with the methods the timers for the game label and the
	 * logic are created and started Aidan
	 * 
	 * 
	 * @param int decision (passed in to determine whether player 2 a Player or AI )
	 * @return void
	 * 
	 * Aidan
	 * 
	 */
	

	public void createGame(int decision) {
		this.remove(start);
		try {
			board = new Board();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		player1 = new Player();
		if (decision == 1) {
			player2 = new Player();
		} else if (decision == 2) {
			player2 = new AI(difficulty);

			AITimer = new Timer(AITimeLeft *1000, new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					/*
					 * After timer is done Does the AI select method
					 * 
					 * if the AI find nothing it will add to scramble counter and restart the timers
					 * and end AI Timer
					 * 
					 * If it finds a word it will increment the points, remove the word from the
					 * list, and update word list on GUI
					 * 
					 * Either way the code will restart the timers and add the score to player2
					 * Aidan
					 */
					AIChosenWord = ((AI) player2).select(board.validWords, board.usedWords);

					if (AIChosenWord.equals(" ")) {
						updateMessagePanel("AI did not find anything");
						scrambleCounter++;
						if (scrambleCounter >= 4) {
							updateMessagePanel("Scramble Available");
						}
					} else {

						player2Points = player2.incrementPoints(AIChosenWord);
						player2.setScore(player2Points);
						player2ScoreLabel.setText("Player 2: " + player2.score);
						player2.removeWord(AIChosenWord, board.validWords, board.usedWords);
						updateWordsList(AIChosenWord);
						scrambleClick = 0;
						scrambleCounter = 0;
					}

					player2.setScore(player2Points);
					player2Points = 0;

					updateTurnLabel();

					AITimeLeft= 0; 
					timeLeft = 15;
					submitButton.setText("Submit");
					submitButton.setEnabled(true);					
					
					timer.stop();
					timer.start();
					remainingTimeTimer.stop(); 
					remainingTimeTimer.start();
					AITimer.stop();
				}
			});
			
			
			
		}

		game.setLayout(null);

		createButton();
		createTextField();
		createBoggleLabel();
		createTurnLabel();
		wordsList();
		createScrambleButton();
		createRestartButton();
		createPauseButton();
		createTimer();
		setButtonChar(board.boardChar);
		createScoreLabel();
		createMessagePanel();
		backgroundLabel.setBounds(0, 0, 700, 700);

		game.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

		this.add(game, BorderLayout.CENTER);


		timer = new Timer(timeLeft * 1000, new ActionListener() {

			@Override

			/**
			 * If the timer finishes it mean the player took too long adds to scramble
			 * counter and updates turn label depending on whos turn and if player 2 is an
			 * AI it will restart the remaining timer
			 * 
			 * Aidan
			 */
			public void actionPerformed(ActionEvent e) {
				scrambleCounter++;
				if (scrambleCounter >= 4) {
					updateMessagePanel("Scramble Available");

				}

				updateTurnLabel();

				timer.stop();
				timer.start();
				timeLeft = 15;

				if (player2 instanceof AI) {

					submitButton.setEnabled(false);
					submitButton.setText("AI Thinking");
					
					timer.stop();
					timer.start();
					remainingTimeTimer.stop(); 
					remainingTimeTimer.start();

					AITimer.start();
					updateMessagePanel("Your turn finished");
				}

			}
		});

		remainingTimeTimer = new Timer(1000, new ActionListener() {

			/**
			 * specify an action listener to be notified when the timer object created
			 * "goes off". The actionPerformed method in this listener should contain the
			 * code for whatever task you need to be performed. When you create the timer,
			 * you also specify the number of milliseconds between timer firings. To start
			 * the timer, call its start method. To suspend it, call stop. 
			 * 
			 * Aidan & Yao
			 */
			public void actionPerformed(ActionEvent e) {
				updateTimer();

			}
		});

		remainingTimeTimer.start();

		timer.start();

		this.setVisible(true);
	}

	
	
	
	
	
	
	/**
	 * when timer is paused, freeze text, otherwise continue to countdown
	 * 
	 * @param nothing
	 * @return void 
	 */
	public void updateTimer() {
		if (!checkPause) {
			if (timeLeft > 0) {
				timeLeft--;
			}
			timerLabel.setText("Timer " + timeLeft);

		}
	}
	
	
	
	
	
	/**
	 * Creates a new frame with a book graphic that explains the rules and how the game works. 
	 * 
	 * A new frame is created instead of editing/switching the panel of the original frame so the user can reference it while playing
	 * 
	 * @param nothing
	 * @return void 
	 * 
	 * Jefferson and Aidan 
	 */

	private void createINFOFrame() {
		
		
		//creates the GUI Objects
		infoFrame = new JDialog();
		infoFrame.setResizable(false);
		infoFrame.setSize(700, 700);
		infoFrame.setLayout(null);

		infoPanel = new JLayeredPane();
		infoPanel.setBounds(0, 0, 700, 700);
		infoFrame.add(infoPanel);

		infoLabel = new JLabel("INFO");
		infoLabel.setBounds(250, 10, 200, 100);
		infoLabel.setFont(new Font("MONOSPACED", Font.BOLD, 40));
		infoLabel.setHorizontalAlignment(JLabel.CENTER);
		infoLabel.setVerticalAlignment(JLabel.TOP);
		infoPanel.add(infoLabel, JLayeredPane.PALETTE_LAYER);

		rulesLabel = new JLabel("RULES");
		rulesLabel.setFont(new Font("MONOSPACED", Font.BOLD, 30));
		rulesLabel.setBounds(155, 100, 200, 50);
		infoPanel.add(rulesLabel, JLayeredPane.MODAL_LAYER);

		JPanel crease = new JPanel();
		crease.setBackground(Color.black);
		crease.setBounds(350, 100, 5, 530);
		infoPanel.add(crease, JLayeredPane.POPUP_LAYER);

		bookCover = new JPanel();
		bookCover.setBackground(Color.red);
		bookCover.setBounds(40, 90, 620, 550);
		infoPanel.add(bookCover, JLayeredPane.PALETTE_LAYER);

		rulesPanel = new JPanel();
		rulesPanel.setBackground(Color.white);
		rulesPanel.setBounds(50, 100, 600, 530);
		rulesPanel.setLayout(null); 
		infoPanel.add(rulesPanel, JLayeredPane.MODAL_LAYER);

		
		
		
		//Creates the rules and will connect them in the panel. This is done for organization
		String ruleConnection = "<html> Find words on the board using <br>the dices. As long as <html> "
				+ "the dice that is being connected is adjacent horizontlly, vertically, or diagonaly "
				+ "and not being used it is a legal connection. Words have to be at least 3 characters";
		String ruleTurn = "<html> Each player has 15 seconds to enter a word "
				+ "(The AI will pick faster. If the player does not pick in time his turn will be passed";
		String ruleScramble = "<html> If each player misses their turn twice (4 misses in a row) than option "
				+ "(both players have to agree) to scramble the board is given which creates a new board with "
				+ "new words to select. However, words found on the previous board cannot be selected";
		String ruleSubmitting = "<html> When you find the word on the board, type it into the text field to submit it";

		
		JLabel rulePage1 = new JLabel(ruleConnection + "<html>" + "<br>" + "<html>" + "<br>" + ruleTurn + "<html>"
				+ "<br>" + "<html>" + "<br>" + ruleScramble + "<html>" + "<br>" + "<html>" + "<br>" + ruleSubmitting);
		rulePage1.setBounds(20, -50, 270, 670);
		rulePage1.setFont(new Font("MONOSPACED", Font.BOLD, 13));
		rulesPanel.add(rulePage1);

		
		
		
		
		
		String ruleScore = "<html> By default, the player who reaches 50 points first wins, but this goal"
				+ " can be changed in the start menu. To get points you select words. Word with 3-4 "
				+ "character are worth 1 point. 5 characters is 2 points. 6 characters is 3 points. "
				+ "7 characters is 5 points. Anything above is 11 points<br>";
		String rulePause = "<html> There is also the option to pause the game. The board will be"
				+ " hidden to prevent cheating/stalling time";
		String ruleMessages = "<html> In the left bottom corner, there is a messages box "
				+ "that will give you any info you would need such as errors/invalid inputs or if scramble is possible";
		String ruleWordList = "<html> A list of words that are used is given under the submit button";
		String ruleEnd = "<html> When the game ends, there is the option to create a new game, "
				+ "go back to start menu, or just close the game entirely.";

		JLabel rulePage2 = new JLabel(ruleScore + "<html>" + "<br>" + "<html>" + "<br>" + rulePause + "<html>" + "<br>"
				+ "<html>" + "<br>" + ruleMessages + "<html>" + "<br>" + "<html>" + "<br>" + ruleWordList + "<html>"
				+ "<br>" + "<html>" + "<br>" + ruleEnd);
		rulePage2.setBounds(320, -65, 270, 670);
		rulePage2.setFont(new Font("MONOSPACED", Font.BOLD, 13));
		rulesPanel.add(rulePage2);
		
		
		
		
		

		infoPanel.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
		infoFrame.setVisible(true);

	}

}
