import javax.swing.JFrame;

/**
 * FlushLevel<p>
 *  
 * The Flush Level of the game.<p>
 * Extends from the RankTrioLevel.
 * @author Eduardo Nieves
 *
 */
public class FlushLevel extends RankTrioLevel{

	protected Card otherCard1;
	protected Card otherCard2;
	protected Card otherCard3;
	protected Card otherCard4;
	protected Card otherCard5;
	protected String[] cardsRanks = new String[5];
	protected int flushCounter = 0;

	/**
	 * Constructor of the FlushLevel.
	 * @param validTurnTime
	 * @param points
	 * @param mainFrame
	 */
	protected FlushLevel(TurnsTakenCounterLabel validTurnTime, Score points, JFrame mainFrame) {
		super(validTurnTime, points, mainFrame);
		super.turnsTakenCounter.setDifficultyModeLabel("Flush Level");
		cardsToTurnUp = 5;
	}


	@Override
	protected boolean addToTurnedCardsBuffer(Card card) {
		// add the card to the list
		this.turnedCardsBuffer.add(card);
		//	System.out.println(turnedCardsBuffer.size());
		if(this.turnedCardsBuffer.size() == getCardsToTurnUp())
		{
			// We are uncovering the last card in this turn
			// Record the player's turn
			this.turnsTakenCounter.increment();
			// get the other card (which was already turned up)
			otherCard1 = this.turnedCardsBuffer.get(0);
			otherCard2 = this.turnedCardsBuffer.get(1);
			otherCard3 = this.turnedCardsBuffer.get(2);
			otherCard4 = this.turnedCardsBuffer.get(3);
			otherCard5 = this.turnedCardsBuffer.get(4);

			//The Suits are the same, successful hand.
			if(isFlush()){
				cardsRanks[0] = otherCard5.getRank();
				cardsRanks[1] = otherCard1.getRank();
				cardsRanks[2] = otherCard2.getRank();
				cardsRanks[3] = otherCard3.getRank();
				cardsRanks[4] = otherCard4.getRank();	
				
				this.scoreCounter.incrementFlush(cardsRanks);
				this.turnedCardsBuffer.clear();
				flushCounter++;
				if(flushCounter >=9){
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
	public boolean isFlush(){
		if((otherCard5.getSuit().equals(otherCard1.getSuit())) && (otherCard5.getSuit().equals(otherCard2.getSuit())&& (otherCard5.getSuit().equals(otherCard3.getSuit())))&& (otherCard5.getSuit().equals(otherCard4.getSuit()))){
			cardsRanks[0] = otherCard5.getRank();
			cardsRanks[1] = otherCard1.getRank();
			cardsRanks[2] = otherCard2.getRank();
			cardsRanks[3] = otherCard3.getRank();
			cardsRanks[4] = otherCard4.getRank();	
			return true;
		}
		return false;
	}
}
