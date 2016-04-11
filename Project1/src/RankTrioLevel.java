import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 * RankTrioLevel <p>
 * 
 * The Rank Trio Level of the game. <p>
 * The goal is to find, on each turn, three cards with the same rank.
 * 
 * @author Eduardo Nieves
 *
 */
public class RankTrioLevel extends EqualPairLevel {
	
	protected Card otherCard1;
	protected Card otherCard2;
	protected Card otherCard3;
	
	// TRIO LEVEL: The goal is to find, on each turn, three cards with the same rank
	
	int[] cardsID = new int[50];
	private int trioCompleted = 0;
	
	/**
	 * Constructor of the RankTrioLevel.
	 * @param validTurnTime
	 * @param points
	 * @param mainFrame
	 */
	protected RankTrioLevel(TurnsTakenCounterLabel validTurnTime,Score points, JFrame mainFrame) {
		super(validTurnTime,points, mainFrame);
		super.turnsTakenCounter.setDifficultyModeLabel("Trio Level");
		super.scoreCounter.setScore("0");
		cardsToTurnUp = 3;
		cardsPerRow = 10;
		rowsPerGrid = 5;
	}

	@Override
	protected void makeDeck() {
		// In Trio level the grid consists of distinct cards, no repetitions
		ImageIcon cardIcon[] = this.loadCardIcons();

		//back card
		ImageIcon backIcon = cardIcon[TotalCardsPerDeck];

		int cardsToAdd[] = new int[getRowsPerGrid() * getCardsPerRow()];
		for(int i = 0; i < (getRowsPerGrid() * getCardsPerRow()); i++)
		{
			cardsToAdd[i] = i;
		
		}

		// randomize the order of the deck
		this.randomizeIntArray(cardsToAdd);

		// make each card object
		for(int i = 0; i < cardsToAdd.length; i++)
		{
			// number of the card, randomized
			int num = cardsToAdd[i];
			// make the card object and add it to the panel
			String rank = cardNames[num].substring(0, 1);
			String suit = cardNames[num].substring(1, 2);
			int position = i;
			this.grid.add( new Card(this, cardIcon[num], backIcon, num, rank, suit,position));
			
			if (rank.equals("2")){
				cardsID[i] = 2;
			}
			else if (rank.equals("3")){
				cardsID[i] = 3;
			}
			else if (rank.equals("4")){
				cardsID[i] = 4;
			}
			else if (rank.equals("5")){
				cardsID[i] = 5;
			}
			else if (rank.equals("6")){
				cardsID[i] = 6;
			}
			else if (rank.equals("7")){
				cardsID[i] = 7;
			}
			else if (rank.equals("8")){
				cardsID[i] = 8;
			}
			else if (rank.equals("9")){
				cardsID[i] = 9;
			}
			else if (rank.equals("t")){
				cardsID[i] = 10;
			}
			else if (rank.equals("j")){
				cardsID[i] =11;
			}
			else if (rank.equals("q")){
				cardsID[i] = 12;
			}
			else if (rank.equals("k")){
				cardsID[i] = 13;
			}
			else if (rank.equals("a")){
				cardsID[i] = 14;
			}
			System.out.println("pos"+ (i+1) +": "+cardsID[i]);
		}
	}
	/**
	 * Shows the user a message that there aren't any moves left.
	 */
	@Override
	protected void endGame(){
		JOptionPane.showMessageDialog(this.mainFrame, "There are no more moves left"
				, "Game Over", JOptionPane.PLAIN_MESSAGE);
		//new RankTrioLevel(this.turnCounterLabel,this.scoreCounter, this.mainFrame);
}
	@Override
	protected boolean addToTurnedCardsBuffer(Card card) {
		// add the card to the list
		this.turnedCardsBuffer.add(card);
		System.out.println(turnedCardsBuffer.size());
		if(this.turnedCardsBuffer.size() == getCardsToTurnUp())
		{
			// We are uncovering the last card in this turn
			// Record the player's turn
			this.turnsTakenCounter.increment();
			// get the other card (which was already turned up)
			 otherCard1 = this.turnedCardsBuffer.get(0);
			 otherCard2 = this.turnedCardsBuffer.get(1);
			if(isTrio()){
				// Three cards match, so remove them from the list (they will remain face up)
				this.scoreCounter.incrementTrio(card.getRank());
				this.turnedCardsBuffer.clear();
				
				trioCompleted++;
				if (trioCompleted==12){
					endGame();
				}
			}
			else 
			{
				// The cards do not match, so start the timer to turn them down
				this.scoreCounter.decrease();
				this.turnDownTimer.start();
			}
		}
		return true;
	}
	public boolean isTrio(){
		
		otherCard1 = this.turnedCardsBuffer.get(0);
		otherCard2 = this.turnedCardsBuffer.get(1);
		otherCard3 = this.turnedCardsBuffer.get(1);
		if((otherCard3.getRank().equals(otherCard1.getRank())) && (otherCard3.getRank().equals(otherCard2.getRank()))) {
			// Three cards match, so remove them from the list (they will remain face up)
			this.scoreCounter.incrementTrio(otherCard3.getRank());
			this.turnedCardsBuffer.clear();
			trioCompleted++;
			if (trioCompleted==12){
				endGame();
			}
		}
		
		
		return false;
		
	}
}
