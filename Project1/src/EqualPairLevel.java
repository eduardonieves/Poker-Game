import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class EqualPairLevel extends EasyLevel {
	private int pairsCompleted = 0;
	protected EqualPairLevel(TurnsTakenCounterLabel validTurnTime,Score points, JFrame mainFrame) {
		super(validTurnTime,points, mainFrame);
		super.turnsTakenCounter.setDifficultyModeLabel("Medium Level");
		super.scoreCounter.setScore("0");
		
	}
	@Override
	
	protected boolean addToTurnedCardsBuffer(Card card) {
		this.turnedCardsBuffer.add(card);
		if(this.turnedCardsBuffer.size() == getCardsToTurnUp())
		{
			// there are two cards faced up
			// record the player's turn
			this.turnsTakenCounter.increment();
			// get the other card (which was already turned up)
			Card otherCard = this.turnedCardsBuffer.get(0);
			// the cards match, so remove them from the list (they will remain face up)
			if( otherCard.getNum() == card.getNum()){
				System.out.println("The cards match");
				this.scoreCounter.increment();
				this.turnedCardsBuffer.clear();
				pairsCompleted++;
			}
			// the cards do not match, so start the timer to turn them down
			else{
				System.out.println("The cards do not match");
				this.scoreCounter.decrease();
				this.turnDownTimer.start();
			}
		}
		if(pairsCompleted == 8){
			endGame();
		}
		return true;
	}
	/**
	 * Shows the user a message that he won the game.
	 */
	@Override
	protected void endGame(){
		JOptionPane.showMessageDialog(this.mainFrame, "Congratulations! You Win!"
				, "Game Over", JOptionPane.PLAIN_MESSAGE);
		makeDeck();
}
	@Override
	protected boolean turnUp(Card card) {
		// the card may be turned
		if(this.turnedCardsBuffer.size() < getCardsToTurnUp()) 
		{
			return this.addToTurnedCardsBuffer(card);
		}
		// there are already the number of EasyMode (two face up cards) in the turnedCardsBuffer
		return false;
	}

	@Override
	protected String getMode() {
		// TODO Auto-generated method stub
		return "MediumMode";
	}



}
