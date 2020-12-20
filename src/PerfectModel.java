/**
 * PerfectMaze Class inherit from AbstractMaze and implement buildMaze() in Interface.
 *
 * @author: Zihan
 * @created: 10-26-2020
 */
public class PerfectModel extends AbstractModel {

  /**
   * Constructor of PerfectMaze and all members are inherit from AbstractMaze.
   *
   * @param row row number of the maze
   * @param column column number of the maze
   * @param randomSeed Random() seed for setting golds and thieves
   * @param difficulty Difficulty
   */
  protected PerfectModel(int row, int column, int randomSeed, Difficulty difficulty, int numOfPlayer) {
    super(row, column, randomSeed, difficulty, numOfPlayer);
  }

  @Override
  public boolean buildMaze() {
    return buildPerfectMaze(randomSeed);
  }

}
