import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MemoryGame implements ActionListener {

	public static boolean DEBUG = true;
	private JFrame mainFrame;					// top level window
	private Container mainContentPane;			// frame that holds card field and turn counter
	private TurnsTakenCounterLabel turnCounterLabel;
	private Score scoreCounter;
	private GameLevel difficulty;

	/**
	 * Make a JMenuItem, associate an action command and listener, add to menu
	 */
	private static void newMenuItem(String text, JMenu menu, ActionListener listener)
	{
		JMenuItem newItem = new JMenuItem(text);
		newItem.setActionCommand(text);
		newItem.addActionListener(listener);
		menu.add(newItem);
	}

	/**
	 * Default constructor loads card images, makes window
	 * @throws IOException 
	 */
	public MemoryGame () throws IOException
	{
		// Make top level window
		this.mainFrame = new JFrame("Mr Fresh Memory Game");
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setSize(800,700);
		this.mainContentPane = this.mainFrame.getContentPane();
		this.mainContentPane.setLayout(new BoxLayout(this.mainContentPane, BoxLayout.PAGE_AXIS));
	
		// Menu bar
		JMenuBar menuBar = new JMenuBar();
		this.mainFrame.setJMenuBar(menuBar);

		// Game menu
		JMenu gameMenu = new JMenu("Memory");
		menuBar.add(gameMenu);
		newMenuItem("Exit", gameMenu, this);

		// Difficulty menu
		JMenu difficultyMenu = new JMenu("New Game");
		menuBar.add(difficultyMenu);
		newMenuItem("Easy Level", difficultyMenu, this);
		newMenuItem("Equal Pair Level", difficultyMenu, this);
		newMenuItem("Same Rank Trio Level", difficultyMenu, this);
		newMenuItem("Flush Level", difficultyMenu, this);
		newMenuItem("Straight Level", difficultyMenu, this);
		newMenuItem("Combo Level", difficultyMenu, this);
		
		// Help menu
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		newMenuItem("How To Play", helpMenu, this);
		newMenuItem("About", helpMenu, this);

		//this.leaderBoard = new ScoreLeaderBoard("EasyMode");
	}


	/**
	 * Handles menu events.  Necessary for implementing ActionListener.
	 *
	 * @param e object with information about the event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		dprintln("actionPerformed " + e.getActionCommand());
		try {
			if(e.getActionCommand().equals("Easy Level")) newGame("easy");
			else if(e.getActionCommand().equals("Equal Pair Level")) newGame("medium");
			else if(e.getActionCommand().equals("Same Rank Trio Level")) newGame("trio");
			else if(e.getActionCommand().equals("Flush Level")) newGame("flush");
			else if(e.getActionCommand().equals("Straight Level")) newGame("straight");
			else if(e.getActionCommand().equals("Combo Level")) newGame("combo");
			else if(e.getActionCommand().equals("How To Play")) showInstructions();
			else if(e.getActionCommand().equals("About")) showAbout();
			else if(e.getActionCommand().equals("Exit")) System.exit(0);
		} catch (IOException e2) {
			e2.printStackTrace(); throw new RuntimeException("IO ERROR");
		}
	}


	/**
	 * Prints debugging messages to the console
	 *
	 * @param message the string to print to the console
	 */
	static public void dprintln( String message )
	{
		if (DEBUG) System.out.println( message );
	}

	public JPanel showCardDeck()
	{
		// make the panel to hold all of the cards
		JPanel panel = new JPanel(new GridLayout(difficulty.getRowsPerGrid(),difficulty.getCardsPerRow()));
		panel.setBackground(new Color(6, 21, 62));

		// this set of cards must have their own manager
		this.difficulty.makeDeck();

		for(int i= 0; i<difficulty.getGrid().size();i++){
			panel.add(difficulty.getGrid().get(i));
		}
		return panel;
	}

	/**
	 * Prepares a new game (first game or non-first game)
	 * @throws IOException 
	 */
	public void newGame(String difficultyMode) throws IOException
	{
		// reset the turn counter to zero
		this.turnCounterLabel = new TurnsTakenCounterLabel();
		
		this.scoreCounter = new Score();

		// make a new card field with cards, and add it to the window

		if(difficultyMode.equalsIgnoreCase("easy")) {
			this.difficulty = new EasyLevel(this.turnCounterLabel,this.scoreCounter, this.mainFrame);
		}
		else if(difficultyMode.equalsIgnoreCase("medium")){
			this.difficulty = new EqualPairLevel(this.turnCounterLabel,this.scoreCounter, this.mainFrame);
		}

		else if(difficultyMode.equalsIgnoreCase("trio")){
			this.difficulty = new RankTrioLevel(this.turnCounterLabel,this.scoreCounter, this.mainFrame);
		}
		else if(difficultyMode.equalsIgnoreCase("flush")){
			this.difficulty = new FlushLevel(this.turnCounterLabel,this.scoreCounter, this.mainFrame);
		}
		else if(difficultyMode.equalsIgnoreCase("straight")){
			this.difficulty = new StraightLevel(this.turnCounterLabel,this.scoreCounter, this.mainFrame);
		}
		else if(difficultyMode.equalsIgnoreCase("combo")){
			this.difficulty = new ComboLevel(this.turnCounterLabel,this.scoreCounter, this.mainFrame);
		}
		else {
			throw new RuntimeException("Illegal Game Level Detected");
		}

		this.turnCounterLabel.reset();
		this.scoreCounter.reset();

		// clear out the content pane (removes turn counter label and card field)
		this.mainContentPane.removeAll();

		this.mainContentPane.add(showCardDeck());

		// add the turn counter label back in again
		this.mainContentPane.add(this.turnCounterLabel);
		
		//add the score counter label back in again
		this.mainContentPane.add(this.scoreCounter);

		// show the window (in case this is the first game)
		this.mainFrame.setVisible(true);
	}

	public boolean gameOver() throws FileNotFoundException, InterruptedException{
		return difficulty.gameOver();
	}

	/**
	 * Shows an instructional dialog box to the user
	 */
	private void showInstructions()
	{
		dprintln("MemoryGame.showInstructions()");
		//final String HOWTOPLAYTEXT = 
		JTextArea textArea = new JTextArea("How To Play\r\n" +
						"\r\n" +
						"\bEQUAL PAIR Level\r\n"+
						"The game consists of 8 pairs of cards.  At the start of the game,\r\n"+
						"every card is face down.  The object is to find all the pairs and\r\n"+
						"turn them face up.\r\n"+
						"\r\n"+
						"Click on two cards to turn them face up. If the cards are the \r\n"+
						"same, then you have discovered a pair.  The pair will remain\r\n"+
						"turned up.  If the cards are different, they will flip back\r\n"+
						"over automatically after a short delay.  Continue flipping\r\n"+
						"cards until you have discovered all of the pairs.  The game\r\n"+
						"is won when all cards are face up.\r\n"+
						"\r\n"+
						"Each time you flip two cards up, the turn counter will\r\n"+
						"increase. Try to win the game in the fewest number of turns!\r\n"+
						"\r\n"+
						"\bSAME RANK TRIO Level\r\n"+
						"\r\n"+
						"The game consists of a grid of distinct cards.  At the start of the game,\r\n"+
						"every card is face down.  The object is to find all the trios \r\n"+
						"of cards with the same rank and turn them face up.\r\n"+
						"\r\n"+
						"Click on three cards to turn them face up. If the cards have the \r\n"+
						"same rank, then you have discovered a trio.  The trio will remain\r\n"+
						"turned up.  If the cards are different, they will flip back\r\n"+
						"over automatically after a short delay.  Continue flipping\r\n"+
						"cards until you have discovered all of the pairs.  The game\r\n"+
						"is won when all cards are face up.\r\n"+
						"\r\n"+
						"\bFLUSH Level\r\n"+
						"\r\n"+
						"This game consists of a grid of distinct cards. At the start of\r\n"+
						"the game, every card is face down. The object is to find five cards\r\n"+
						"with the same suit and turn them face up.\r\n"+
						"\r\n"+
						"Click on five cards to turn them face up. If the cards have the \r\n"+
						"same suit, then you have discovered a flush. The flush will remain\r\n"+
						"turned up. If the suits are different, they will flip back\r\n"+
						"over automatically after a short delay.  Continue flipping\r\n"+
						"cards until you have discovered all of the flushes. The game\r\n"+
						"is won when all cards are face up.\r\n"+
						"\r\n"+
						"\bSTRAIGHT Level\r\n"+
						"\r\n"+
						"This game consists of a grid of distinct cards. At the start of\r\n"+
						"every card is face down. The object is to find five cards \r\n"+
						"the game, in sequence with at least two distinct suits.\r\n"+
						"\r\n"+
						"Click on five cards to turn them face up. If the cards are in \r\n"+
						"sequence and have at least two different suits, then you have\r\n"+
						"made a straight.The straight will remain turned up. If the suits are\r\n"+
						"they will flip back over automatically after a short delay. Continue flipping\r\n"+
						"different, cards until you have made all of the straights possibles.\r\n"+
						"The game is won when all cards are face up.\r\n"+
						"\r\n"+
						"\bCOMBO Level\r\n"+
						"\r\n"+
						"This game consist on a mix of all of the previous games before plus four\r\n"+
						"additional types of hands. At the start of the game,every card is face down.\r\n"+
						"Your objective is to make hands and get the higgest score possible. The four\r\n"+
						"additionals hands you can make are:\r\n"+
						"ROYAL FLUSH:\r\n"+
						"Consists of a straight flush of the ace, king, queen, jack and ten of a suit.\r\n"+
						"If you make this hand, you win instantly.\r\n"+
						"STRAIGHT FLUSH\r\n"+
						"Consist of a straight and a flush combined. Second highest scoring hand\r\n"+
						"FOUR OF A KIND:\r\n"+
						"Consits on four cards of the same rank. Third highest scoring hand\r\n"+
						"FULL HOUSE:\r\n"+
						"Consits of three cards of the same rank and other two of a different,\r\n"+
						"matching rank\r\n"+
						"\r\n"+
						"The game is over when you pass three times or when you get a Royal Flush");
		
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(450,600));
		JOptionPane.showMessageDialog(this.mainFrame,scrollPane
				, "How To Play", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Shows an dialog box with information about the program
	 */
	private void showAbout()
	{
		dprintln("MemoryGame.showAbout()");
		final String ABOUTTEXT = "Game Customized at UPRM. Originally written by Mike Leonhard";

		JOptionPane.showMessageDialog(this.mainFrame, ABOUTTEXT
				, "About Memory Game", JOptionPane.PLAIN_MESSAGE);
	}


}





