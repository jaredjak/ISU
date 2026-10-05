package hw2;

/**
 * Models a simplified baseball-like game called Fuzzball.
 * 
 * @author Jared Krug
 */
public class FuzzballGame {
  /**
   * Number of strikes causing a player to be out.
   */
  public static final int MAX_STRIKES = 2;

  /**
   * Number of balls causing a player to walk.
   */
  public static final int MAX_BALLS = 5;

  /**
   * Number of outs before the teams switch.
   */
  public static final int MAX_OUTS = 3;
  
  /**
   * Given number of innings inputed.
   */
  private int givenNumInnings;
  
  /**
   * The initial inning count. Begins at 1.
   */
  private int inningCount;
  
  /**
   * Tells whether it is the top or bottom of the inning.
   */
  private boolean inningTopBot;
  
  /**
   * Holds the current number of balls.
   */
  private int balls;
  
  /**
   * Holds the current number of strikes.
   */
  private int strikes;
  
  /**
   * Holds the current number of outs.
   */
  private int outs;
  
  /**
   * Tells whether there is a runner on first base or not.
   */
  private int base1;
  
  /**
   * Tells whether there is a runner on second base or not.
   */
  private int base2;
  
  /**
   * Tells whether there is a runner on third base or not.
   */
  private int base3;
  
  /**
   * Holds the number of team 0's score.
   */
  private int team0Score;
  
  /**
   * Holds the number of team 1's score.
   */
  private int team1Score;

  
  /**
   * Constructs a game that has the given number of innings and starts at the top of inning 1.
   * @param givenNumInnings - number of innings for the game
   */
  public FuzzballGame(int givenNumInnings) {
	  
	  this.givenNumInnings = givenNumInnings;
	  
	  inningCount = 1;
	  inningTopBot = true;
	  balls = 0;
	  strikes = 0;
	  outs = 0;
	  base1 = 0;
	  base2 = 0;
	  base3 = 0;
	  team0Score = 0;
	  team1Score = 0;
	  
  }
  
  /**
   * Returns true if the game is over, false otherwise.
   * @return true if the game is over, false otherwise
   */
  public boolean gameEnded() {
	  if (inningCount > givenNumInnings) {
		  return true;
	  }
	  return false;
  }
  
  /**
   * Returns true if there is a runner on the indicated base, fails otherwise.
   * Returns false if the given argument is anything other than 1, 2, or 3.
   * @param which - base number to check
   * @return true if there is a runner on the indicated base
   */
  public boolean runnerOnBase(int which) {
	  if (which < 1 || which > 3) {
		  return false;
	  }
	  
	  if (which == base1 || which == base2 || which == base3) {
		  return true;
	  }
	  return false;
  }
  
  /**
   * Returns the current inning. Innings are numbered starting at 1.
   * When the game is over, this method returns the game's total number of innings, plus one.
   * @return current inning, or the number of innings plus one in case the game is over
   */
  public int whichInning() {
	  int inningsPlus1 = givenNumInnings + 1;
	  if (gameEnded()) {
		  return inningsPlus1;
	  }
	  return inningCount;
  }
  
  /**
   * Returns true if it's the first half of the inning (team 0 is at bat).
   * @return true if it's the first half of the inning, false otherwise.
   */
  public boolean isTopOfInning() {
	  if (inningTopBot) {
		  return true;
	  }
	  return false;
  }
  
  /**
   * Returns the number of outs for the team currently at bat.
   * @return current number of outs
   */
  public int getCurrentOuts() {
	  return outs;
  }
  
  /**
   * Returns the number of called strikes for the current batter.
   * @return current number of strikes
   */
  public int getCalledStrikes() {
	  return strikes;
  }
  
  /**
   * Returns the count of balls for the current batter.
   * @return current number of balls
   */
  public int getBallCount() {
	  return balls;
  }
  
  /**
   * Returns the score for team 0.
   * @return score for team 0
   */
  public int getTeam0Score() {
	  return team0Score;
  }

  /**
   * Returns the score for team 1.
   * @return score for team 1
   */
  public int getTeam1Score() {
	  return team1Score;
  }
  
  /**
   * Method called to indicate a strike for the current batter.
   * If the swung parameter is true, the batter is immediately out.
   * Otherwise, 1 is added to the batters current count of called strikes (possibly resulting in the batter being out).
   * This method does nothing if the game has ended.
   * @param swung - true if the batter swung at the pitch, false if it's a "called" strike
   */
  public void strike(boolean swung) {
	  
	  if (gameEnded()) {
		  return;
	  }
	  
	  if (swung) {
		  outsCounter();
	  } 
	  else {
		  strikes += 1;
		  if (strikes == MAX_STRIKES) {
			  outsCounter();
		  }
	
	  }
  }
  
  /**
   * Method called to indicate that the batter is out due to a caught fly.
   * This method does nothing if the game has ended.
   */
  public void caughtFly() {
	  
	  if (gameEnded()) {
		  return;
	  }
	  
	  outsCounter();
  }
  
  /**
   * Method called to indicate a bad pitch at which the batter did not swing.
   * This method adds 1 to the batter's count of balls, possibly resulting in a walk.
   * This method does nothing if the game has ended
   */
  public void ball() {
	  
	  if (gameEnded()) {
		  return;
	  }
	  
	  balls += 1;
	  if (balls == MAX_BALLS) {
		  shiftRunnersWalk();
	  }
  }
  
  /**
   * Method called to indicate that the batter hit the ball. The interpretation of the distance parameter is as follows:
   *  - Less than 15: the hit is a foul and the batter is immediately out.
   *  
   *  - At least 15, but less than 150: the hit is a single.
   *    An imaginary runner advances to first base, and all other runners on base advance by 1.
   *    If there was a runner on third base, the score increases by 1.
   *    
   *  - At least 150, but less than 200: the hit is a double.
   *    An imaginary runner advances to second base, and all other runners on base advance by 2.
   *    If there were runners on second or third base, the score increases by 1 or 2.
   *    
   *  - At least 200, but less than 250: the hit is a triple.
   *    An imaginary runner advances to third base, and all other runners on base advance by 3.
   *    If there were runners on first, second, or third base, the score is increased by 1, 2, or 3.
   *    
   *  - At least 250: the hit is a home run.
   *    All imaginary runners currently on base advance to home.
   *    The score is increased by 1 plus the number of runners on base.
   * @param distance - distance the ball travels (possibly negative)
   */
  public void hit(int distance) {
	  
	  if (gameEnded()) {
		  return;
	  }
	  
	  if (distance < 15) {
		  outsCounter();
	  }
	  else if (distance >= 15 && distance < 150) {
		  shiftRunners(1);
	  }
	  else if (distance >= 150 && distance < 200) {
		  shiftRunners(2);
	  }
	  else if (distance >= 200 && distance < 250) {
		  shiftRunners(3);
	  }
	  else {
		  shiftRunners(4);
	  }
  }
  
  /**
   * HELPER METHOD
   * 
   * I definitely did not do this right. Way too much repetition.
   * 
   * Method called to indicate runners shifting bases based on what the batter hits.
   * @param baseMoves - the amount of bases that the imaginary players will move
   */
  private void shiftRunners(int baseMoves) {
	  resetBatter();
	  /*
	   * Represents a single along with all possible outcomes.
	   */
	  if (baseMoves == 1) {
		  if (base3 == 3) {
			  base3 = 0;
			  scoring();
		  }
		  if (base2 == 2) {
			  base2 = 0;
			  base3 = 3;
		  }
		  if (base1 == 1) {
			  base1 = 0;
			  base2 = 2;
		  }
		  base1 = 1;
	  }
	  /* 
	   * Represents a double along with all possible outcomes.
	   */
	  else if (baseMoves == 2) {
		  if (base3 == 3) {
			  base3 = 0;
			  scoring();
		  }
		  if (base2 == 2) {
			  base2 = 0;
			  scoring();
		  }
		  if (base1 == 1) {
			  base1 = 0;
			  base3 = 3;
		  }
		  base2 = 2;
	  }
	  /*
	   * Represents a triple along with all possible outcomes.
	   */
	  else if (baseMoves == 3) {
		  if (base3 == 3) {
			  base3 = 0;
			  scoring();
		  }
		  if (base2 == 2) {
			  base2 = 0;
			  scoring();
		  }
		  if (base1 == 1) {
			  base1 = 0;
			  scoring();
		  }
		  base3 = 3;
	  }
	  /*
	   * Represents a home run. All runners in play score.
	   */
	  else if (baseMoves == 4) {
		  if (base3 == 3) {
			  base3 = 0;
			  scoring();
		  }
		  if (base2 == 2) {
			  base2 = 0;
			  scoring();
		  }
		  if (base1 == 1) {
			  base1 = 0;
			  scoring();
		  }
		  scoring();
	  }
  }
  
  /**
   * HELPER METHOD
   * Method called to indicate which runners walk when the batter walks.
   */
  private void shiftRunnersWalk() {
	  balls = 0;
	  resetBatter();
	  
	  if (base3 == 3 && base2 == 2 && base1 == 1) {
		  scoring();
	  }
	  else if (base3 == 0 && base2 == 2 && base1 == 1) {
		  base3 = 3;
		  base2 = 2;
	  }
	  else if (base2 == 0 && base1 == 1) {
		  base2 = 2;
	  }
	  else if (base1 == 0) {
		  base1 = 1;
	  }
  }
  
  /**
   * HELPER METHOD
   * Method called to indicate that the current batter is out.
   * Adds 1 out for the current batting team.
   * 3 outs signals a change in sides.
   */
  private void outsCounter() {
	  resetBatter();
	  outs += 1;
	  
	  if (outs == MAX_OUTS && inningTopBot) {
			  inningTopBot = false;
			  outs = 0;
			  resetBases();
	  }
	  else if (outs == MAX_OUTS && !inningTopBot){
		  inningTopBot = true;
		  outs = 0;
		  resetBases();
		  inningCount += 1;
	  }
  }
  
  /**
   * HELPER METHOD
   * Method called to simulate either team0 or team1 scoring.
   */
  private void scoring() {
	  if (inningTopBot) {
		  team0Score += 1;
	  }
	  else {
		  team1Score += 1;
	  }
  }
  
  /**
   * HELPER METHOD
   * Method called when teams switch sides.
   * Resets all bases to be empty.
   */
  private void resetBases() {
	  base1 = 0;
	  base2 = 0;
	  base3 = 0;
  }
  
  /**
   * HELPER METHOD
   * Method called when batter is either out or hits the ball.
   */
  private void resetBatter() {
	  strikes = 0;
	  balls = 0;
  }
  
  // The methods below are provided for you and you should not modify them.
  // The compile errors will go away after you have written stubs for the
  // rest of the API methods.
  /**
   * Returns a three-character string representing the players on base, in the
   * order first, second, and third, where 'X' indicates a player is present and
   * 'o' indicates no player. For example, the string "oXX" means that there are
   * players on second and third but not on first.
   * 
   * @return three-character string showing players on base
   */
  public String getBases()
  {
    return (runnerOnBase(1) ? "X" : "o") + (runnerOnBase(2) ? "X" : "o")
        + (runnerOnBase(3) ? "X" : "o");
  }

  /**
   * Returns a one-line string representation of the current game state. The
   * format is:
   * <pre>
   *      ooo Inning:1 [T] Score:0-0 Balls:0 Strikes:0 Outs:0
   * </pre>
   * The first three characters represent the players on base as returned by the
   * <code>getBases()</code> method. The 'T' after the inning number indicates
   * it's the top of the inning, and a 'B' would indicate the bottom. The score always
   * shows team 0 first.
   * 
   * @return a single line string representation of the state of the game
   */
  public String toString()
  {
    String bases = getBases();
    String topOrBottom = (isTopOfInning() ? "T" : "B");
    String fmt = "%s Inning:%d [%s] Score:%d-%d Balls:%d Strikes:%d Outs:%d";
    return String.format(fmt, bases, whichInning(), topOrBottom, getTeam0Score(),
        getTeam1Score(), getBallCount(), getCalledStrikes(), getCurrentOuts());
  }
}
