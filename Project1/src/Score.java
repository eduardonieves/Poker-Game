import javax.swing.JLabel;
import javax.swing.SwingConstants;
/**
 * Score<p>
 *  
 * Class that contains all the score methods for the game.
 * @author Eduardo Nieves
 *
 */

public class Score extends JLabel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//data fields
	private long scoreLabel = 0;
	private  String DESCRIPTION;


	/**
	 * Constructor of the Score Class.
	 * 
	 */
	public Score()
	{
		super();
		reset();
	}

	/**
	 * Set the score on the JPanel.
	 * 
	 * @param points
	 */
	public void setScore(String points){
		DESCRIPTION = "Score: ";
		setHorizontalTextPosition(SwingConstants.RIGHT);
	}
	/**
	 * Gets the score.
	 * 
	 * @return
	 */
	public long getScore(){
		return this.scoreLabel;
	}
	/**
	 *  Update the score label with the current counter value
	 */
	private void update()
	{
		setText(DESCRIPTION + Long.toString(this.scoreLabel));
		setHorizontalTextPosition(SwingConstants.RIGHT);
	}
	/**
	 * Increments the score for the EqualPair Level.
	 */
	public void increment()
	{
		this.scoreLabel += 50;
		update();
	}
	/**
	 * Increments the Score of the Rank Trio Level.
	 * 
	 * @param rank
	 */
	public void incrementTrio(String rank){
		Long value;
		if(rank.equals("j")){
			value = (long) 11;
		}
		else if (rank.equals("q")){
			value = (long) 12;
		}
		else if (rank.equals("k")){
			value = (long) 13;
		}
		else if (rank.equals("a")){
			value = (long) 20;
		}
		else if (rank.equals("t")){
			value = (long) 10;
		}
		else{
			value = Long.valueOf(rank);
		}
		this.scoreLabel += 100 + value*3 ;
		update();
	}

	/**
	 * Increments the score for the Flush Level.
	 * 
	 * @param rank
	 */
	public void incrementFlush(String[] rank){
		Long value = (long)0;
		Long totalValue = (long)0;

		for(int i=0;i<rank.length;i++){
			if(rank[i].equals("j")){
				value = (long) 11;
			}
			else if (rank[i].equals("q")){
				value = (long) 12;
			}
			else if (rank[i].equals("k")){
				value = (long) 13;
			}
			else if (rank[i].equals("a")){
				value = (long) 20;
			}
			else if (rank[i].equals("t")){
				value = (long) 10;
			}
			else{
				value = Long.valueOf(rank[i]);
			}
			totalValue += value;
		}
		this.scoreLabel += 700 + totalValue;
		update();
	}
	/**
	 * Increments the score for the Straight Level.
	 * 
	 * @param rank
	 */
	public void incrementStraight(String[] rank){
		Long value = (long)0;
		boolean isK = false;
		boolean isQ = false;
		boolean isJ = false;
		boolean isLetter = false;
		Long biggest = (long)0;
		for(int i=0;i<rank.length;i++){
			if (rank[i].equals("a")){
				value = (long) 20*100;
				i =rank.length+1; // Highest rank so no need to keep looking.
			}
			else if (rank[i].equals("k")){
				value = (long) 13*100;
				isLetter = true;
				isK = true;
			}
			else if (rank[i].equals("q")){
				if(!isK){
					value = (long) 12*100;
					isLetter = true;
				}

			}
			else if(rank[i].equals("j")){
				if(!isK && !isQ){
					value = (long) 11*100;
					isLetter = true;
					isJ = true;
				}
				else if (rank[i].equals("t")){
					if(!isK && !isQ && !isJ){
						value = (long) 10*100;
						isLetter = true;
					}
				}
				else{
					if(!isLetter && Long.valueOf(rank[i])>biggest){
						biggest = Long.valueOf(rank[i]);
						value = biggest;
					}
				}
			}
		}
		this.scoreLabel += 1000 + value;
		update();
	}
	public void increaseRoyalFlush(){
		this.scoreLabel += 10000;
		update();
	}
	public void increaseStraightFlush(){
		this.scoreLabel += 7000;
		update();
	}
	public void increaseFullHouse(){
		this.scoreLabel += 5000;
		update();
	}
	public void increaseFourOfaKind(){
		this.scoreLabel += 2000;
		update();
	}
	/**
	 * Decreases the score.
	 */
	public void decrease(){
		this.scoreLabel -= 5;
		update();
	}
	/**
	 * Resets the score to 0.
	 */
	public void reset()
	{
		this.scoreLabel = 0;
		update();
	}
}
