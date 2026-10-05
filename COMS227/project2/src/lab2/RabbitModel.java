package lab2;

/**
 * A RabbitModel is used to simulate the growth
 * of a population of rabbits. 
 */
public class RabbitModel
{
  // TODO - add instance variables as needed
  private int currentPop;
  private int lastYear;
  private int yearBefore;
  
  /**
   * Constructs a new RabbitModel.
   */
  public RabbitModel()
  {
    // TODO
	lastYear = 1;
	yearBefore = 0;
	currentPop = lastYear + yearBefore;
  }  
 
  /**
   * Returns the current number of rabbits.
   * @return
   *   current rabbit population
   */
  public int getPopulation()
  {
    // TODO - returns a dummy value so code will compile
	return currentPop;
  }
  
  /**
   * Updates the population to simulate the
   * passing of one year.
   */
  public void simulateYear()
  {
    // TODO
	  yearBefore = lastYear;
	  lastYear = currentPop;
	  currentPop = yearBefore + lastYear;
  }
  
  /**
   * Sets or resets the state of the model to the 
   * initial conditions.
   */
  public void reset()
  {
    // TODO
	lastYear = 1;
	yearBefore = 0;
	currentPop = lastYear + yearBefore;
  }
}
