import javax.swing.JFrame;
/**
 * StraightLevel<p>
 * 
 * The Straight Level of the game.<p>
 * @author Eduardo Nieves
 *
 */
public class StraightLevel extends FlushLevel {
	//protected variables
	protected String cardRank;	
	protected int straightCounter;
	
	/**
	 * Constructor
	 * @param validTurnTime
	 * @param points
	 * @param mainFrame
	 */
	public StraightLevel(TurnsTakenCounterLabel validTurnTime, Score points, JFrame mainFrame) {
		super(validTurnTime, points, mainFrame);
		super.turnsTakenCounter.setDifficultyModeLabel("Straight Level");
	}

	/**
	 * Overrides Method
	 */
	@Override
	protected boolean addToTurnedCardsBuffer(Card card) {
		// add the card to the list
		this.turnedCardsBuffer.add(card);
		System.out.println(card.getPosition());
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
			if(hasStraight()){
				this.scoreCounter.incrementStraight(cardsRanks);
				this.turnedCardsBuffer.clear();
				straightCounter++;
				if(straightCounter >=8){
					endGame();
				}
			}else 
			{
				// The cards do not match, so start the timer to turn them down
				this.scoreCounter.decrease();
				this.turnDownTimer.start();
			}
		}
		return true;
	}
	/**
	 * Straight Method
	 * @return
	 */
	public boolean hasStraight(){
		if(!otherCard5.getSuit().equals(otherCard1.getSuit())||!otherCard5.getSuit().equals(otherCard2.getSuit())||!otherCard5.getSuit().equals(otherCard3.getSuit())||!otherCard5.getSuit().equals(otherCard4.getSuit())){
			System.out.println("Card1: "+cardsID[otherCard1.getPosition()]);
			System.out.println("Card2: "+(cardsID[otherCard2.getPosition()]));
			
			
			if((cardsID[otherCard1.getPosition()]-1)==(cardsID[otherCard2.getPosition()])){ //Checks if the second card is the next in sequence.
				if((cardsID[otherCard2.getPosition()]-1)== cardsID[otherCard3.getPosition()]){//Checks if the third card is the next in sequence.
					if((cardsID[otherCard3.getPosition()]-1)== cardsID[otherCard4.getPosition()]){//Checks if the fourth card is the next in sequence.
						if(((cardsID[otherCard4.getPosition()]-1)== cardsID[otherCard5.getPosition()])){//Checks if the fifth card is the next in sequence.
							cardsRanks[0] = otherCard1.getRank();
							cardsRanks[1] = otherCard2.getRank();
							cardsRanks[2] = otherCard3.getRank();
							cardsRanks[3] = otherCard4.getRank();
							cardsRanks[4] = otherCard5.getRank();
							return true;
						}
					}
				}
			}
		}
		return false;

	}
}
