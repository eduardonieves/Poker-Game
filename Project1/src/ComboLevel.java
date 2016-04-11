import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 * Combo Level Class
 * 
 * @author Eduardo Nieves
 *
 */

public class ComboLevel extends StraightLevel implements ActionListener {
	static boolean royalFlush = false;
	static boolean straightFlush = false;
	static boolean fourOfAKind = false;
	static boolean fullHouse = false;
	static boolean flush = false;
	static boolean straight = false;
	static boolean trio = false;
	static boolean pass = false;
	static int passCounter = 0;

	//Panel and Buttons
	JPanel panel = new JPanel();
	JButton RFButton = new JButton("Royal Flush");
	JButton SFButton = new JButton("Straight Flush");
	JButton FKButton = new JButton("Four of a Kind");
	JButton FHButton = new JButton("Full House");
	JButton flushButton = new JButton("Flush");
	JButton straightButton = new JButton("Straight");
	JButton trioButton = new JButton("Three of a Kind");
	JButton passButton = new JButton("Pass");

	/**
	 * Constructor of ComboLevel
	 * @param validTurnTime
	 * @param points
	 * @param mainFrame
	 */
	public ComboLevel(TurnsTakenCounterLabel validTurnTime, Score points, JFrame mainFrame) {
		super(validTurnTime, points, mainFrame);
		super.turnsTakenCounter.setDifficultyModeLabel("Combo Level");
	}
	/**
	 * Overrided Method
	 */
	@Override
	protected boolean addToTurnedCardsBuffer(Card card) {
		// add the card to the list
		this.turnedCardsBuffer.add(card);
		System.out.println(card.getPosition());
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


			//Check for Royal Flush
			if(hasRoyalFlush()){
				System.out.println("Got Royal Flush");
				panel.add(RFButton);
				RFButton.addActionListener(new PickedRoyalFlush());

			}

			//Check for Straight Flush
			if(isFlush()&&hasStraight()&&!hasRoyalFlush()){
				System.out.println("Got Straight Flush");
				panel.add(SFButton);
				SFButton.addActionListener(new PickedStraightFlush());
			}

			//Check for Four of a Kind
			if(isFourOfaKind()){
				System.out.println("Got Four of a Kind");
				panel.add(FKButton);
				FKButton.addActionListener(new PickedFourOfaKind());
			}

			//Check for Full House
			if(hasFullHouse()){
				System.out.println("Got Full House");
				panel.add(FHButton);
				FHButton.addActionListener(new PickedFullHouse());
			}

			//Check for Flush
			if(isFlush()){
				System.out.println("Got Flush");
				panel.add(flushButton);
				flushButton.addActionListener(new PickedFlush());
			}

			//Check for Straight
			if(hasStraight()){
				System.out.println("Got Straight");
				panel.add(straightButton);
				straightButton.addActionListener(new PickedStraight());
				panel.add(trioButton);
				straightButton.addActionListener(new PickedTrio());
			}
			
			//Check for Trio
			if(isTrio()){
				System.out.println("Got Trio");
			}

			//Check for any hand possible
			if(hasRoyalFlush()||(isFlush()&&hasStraight()&&!hasRoyalFlush())||isFourOfaKind()||hasFullHouse()||isFlush()||hasStraight()||isTrio()){
				panel.add(passButton);
				passButton.addActionListener(new PickedPass());
				JOptionPane.showMessageDialog(this.mainFrame,panel
						, "Choose a Hand:", JOptionPane.PLAIN_MESSAGE);
				resetCards();
			}
			else{
				resetCards();
			}

		}
		//Check if bigger than 3 to end game
		else if(passCounter>=3){
			endGame();
		}
		//Awards points for Royal Flush(End Game)
		else if(royalFlush){
			this.scoreCounter.increaseRoyalFlush();
			this.turnedCardsBuffer.clear();
			endGameRF();
		}
		//Awards points for Straight Flush
		else if(straightFlush){
			this.scoreCounter.increaseStraightFlush();
			this.turnedCardsBuffer.clear();
			straightFlush = false;

		}
		//Awards points for Four of a Kind
		else if(fourOfAKind){
			fourOfAKind = false;
			this.turnedCardsBuffer.clear();
			this.scoreCounter.increaseFourOfaKind();
		}
		//Awards points for Full House
		else if(fullHouse){
			fullHouse = false;
			this.turnedCardsBuffer.clear();
			this.scoreCounter.increaseFullHouse();
		}
		//Awards points for Flush
		else if(flush){
			flush = false;
			this.scoreCounter.incrementFlush(cardsRanks);
			this.turnedCardsBuffer.clear();
		}
		//Awards points for Straight
		else if(straight){
			this.scoreCounter.incrementStraight(cardsRanks);
			this.turnedCardsBuffer.clear();
			straight = false;
		}
		//Awards points for Trio
				else if(trio){
					this.scoreCounter.incrementTrio(otherCard1.getRank());
					this.turnedCardsBuffer.clear();
					straight = false;
				}
		//Passes
		else if(pass){
			resetCards();
			pass = false;
		}
		return true;
	}
	/**
	 * Resets Cards
	 */
	public void resetCards(){
		this.scoreCounter.decrease();
		this.turnDownTimer.start();
	}
	/**
	 * Royal Flush
	 * @return
	 */
	public boolean hasRoyalFlush(){
		//RoyalFlush
		cardsRanks[0] = otherCard1.getRank();
		cardsRanks[1] = otherCard2.getRank();
		cardsRanks[2] = otherCard3.getRank();
		cardsRanks[3] = otherCard4.getRank();	
		cardsRanks[4] = otherCard5.getRank();

		boolean hasA = false;
		boolean hasK = false;
		boolean hasQ = false;
		boolean hasJ = false;
		boolean hasT = false;

		if(isFlush()&& hasStraight()){
			for(int i = 0; i<cardsRanks.length;i++){
				if(cardsRanks[i].equals("a")){
					hasA = true;
				}
				else if(cardsRanks[i].equals("k")){
					hasK = true;
				}
				else if(cardsRanks[i].equals("q")){
					hasQ = true;
				}
				else if(cardsRanks[i].equals("j")){
					hasJ = true;
				}
				else if(cardsRanks[i].equals("t")){
					hasT = true;
				}
			}
			if(hasA&&hasK&&hasQ&&hasJ&&hasT){
				System.out.println("Player has a Royal Flush");
				return true;
			}
		}
		return false;
	}
	/**
	 * Straight
	 */
	@Override
	public boolean hasStraight(){
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
		return false;
	}
	/**
	 * Four Of A Kind
	 * @return
	 */
	public boolean isFourOfaKind(){
		//Checks for all possible combinations
		if(!(otherCard4.getRank().equals(otherCard5.getRank()))&&otherCard1.getRank().equals(otherCard2.getRank())&&(otherCard2.getRank().equals(otherCard3.getRank())&& otherCard3.getRank().equals(otherCard4.getRank()))){
			return true;
		}
		if(!(otherCard3.getRank().equals(otherCard1.getRank()))&&otherCard1.getRank().equals(otherCard2.getRank())&&(otherCard2.getRank().equals(otherCard4.getRank())&& otherCard4.getRank().equals(otherCard5.getRank()))){
			return true;
		}
		if(!(otherCard1.getRank().equals(otherCard2.getRank()))&&otherCard1.getRank().equals(otherCard2.getRank())&&(otherCard2.getRank().equals(otherCard3.getRank())&& otherCard3.getRank().equals(otherCard4.getRank()))){
			return true;
		}

		return false;
	}
	/**
	 * Full House
	 * @return
	 */
	public boolean hasFullHouse(){
		//Checks for all possible combinations
		if((otherCard1.getRank().equals(otherCard5.getRank()))&&(otherCard2.getRank().equals(otherCard3.getRank())&& otherCard3.getRank().equals(otherCard4.getRank()))){
			return true;
		}
		else if((otherCard1.getRank().equals(otherCard4.getRank()))&&(otherCard2.getRank().equals(otherCard3.getRank())&& otherCard3.getRank().equals(otherCard5.getRank()))){
			return true;
		}
		else if((otherCard1.getRank().equals(otherCard3.getRank()))&&(otherCard2.getRank().equals(otherCard4.getRank())&& otherCard4.getRank().equals(otherCard5.getRank()))){
			return true;
		}
		else if((otherCard1.getRank().equals(otherCard2.getRank()))&&(otherCard3.getRank().equals(otherCard4.getRank())&& otherCard4.getRank().equals(otherCard5.getRank()))){
			return true;
		}
		else if((otherCard2.getRank().equals(otherCard5.getRank()))&&(otherCard1.getRank().equals(otherCard3.getRank())&& otherCard3.getRank().equals(otherCard4.getRank()))){
			return true;
		}
		else if((otherCard2.getRank().equals(otherCard4.getRank()))&&(otherCard1.getRank().equals(otherCard3.getRank())&& otherCard3.getRank().equals(otherCard5.getRank()))){
			return true;
		}
		else if((otherCard2.getRank().equals(otherCard3.getRank()))&&(otherCard1.getRank().equals(otherCard4.getRank())&& otherCard4.getRank().equals(otherCard5.getRank()))){
			return true;
		}
		else if((otherCard3.getRank().equals(otherCard5.getRank()))&&(otherCard1.getRank().equals(otherCard2.getRank())&& otherCard2.getRank().equals(otherCard4.getRank()))){
			return true;
		}
		else if((otherCard3.getRank().equals(otherCard4.getRank()))&&(otherCard1.getRank().equals(otherCard2.getRank())&& otherCard2.getRank().equals(otherCard5.getRank()))){
			return true;
		}
		else if((otherCard4.getRank().equals(otherCard5.getRank()))&&(otherCard1.getRank().equals(otherCard2.getRank())&& otherCard2.getRank().equals(otherCard3.getRank()))){
			return true;
		}
		return false;
	}
	/**
	 * Ends game for having a Royal Flush
	 */
	protected void endGameRF(){
		JOptionPane.showMessageDialog(this.mainFrame, "Congratulations! You Win!"
				, "Game Over", JOptionPane.PLAIN_MESSAGE);
		makeDeck();
	}

	//Action Listeners for the buttons
	static class PickedRoyalFlush implements ActionListener {
		@Override
		public void actionPerformed (ActionEvent e){
			System.out.println("You have made an Royal Flush. You Win!");
			royalFlush = true;
		}
	}
	static class PickedStraightFlush implements ActionListener {
		@Override
		public void actionPerformed (ActionEvent e){
			System.out.println("You have made an Straight Flush");
			straightFlush = true;
		}
	}
	static class PickedFourOfaKind implements ActionListener {
		@Override
		public void actionPerformed (ActionEvent e){
			System.out.println("You have made a Four of a Kind");
			fourOfAKind = true;
		}
	}
	static class PickedFullHouse implements ActionListener {
		@Override
		public void actionPerformed (ActionEvent e){
			System.out.println("You have made a Full House");
			fullHouse = true;

		}
	}
	static class PickedFlush implements ActionListener {
		@Override
		public void actionPerformed (ActionEvent e){
			System.out.println("You have made a Flush");
			flush = true;
		}
	}
	static class PickedStraight implements ActionListener {
		@Override
		public void actionPerformed (ActionEvent e){
			System.out.println("You have made a Straight");
			straight = true;
		}
	}
	static class PickedTrio implements ActionListener {
		@Override
		public void actionPerformed (ActionEvent e){
			System.out.println("You have made a Three of a Kind");
			trio = true;
		}
	}
	static class PickedPass implements ActionListener {
		@Override
		public void actionPerformed (ActionEvent e){
			System.out.println("You have passed");
			passCounter++;
			pass = true;	
		}
	}
}
