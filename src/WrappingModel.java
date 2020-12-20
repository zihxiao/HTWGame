import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * WrappingMaze Class inherit from RoomMaze and override buildMaze() in super class by adding
 * boundaries connections based on room maze.
 *
 * @author: Zihan
 * @created: 10-29-2020
 */
public class WrappingModel extends RoomModel {
  private int numOfWrapping;

  /**
   * Constructor of WrappingMaze and all members are inherit from RoomMaze.
   *
   * @param row row number of the maze
   * @param column column number of the maze
   * @param randomSeed Random() seed for setting golds and thieves
   * @param numOfRemainingWalls the number of remaining walls
   * @param numOfWrapping the number of channels connecting boundaries
   */
  protected WrappingModel(int row, int column, int randomSeed, Difficulty difficulty,
                          int numOfPlayer, int numOfRemainingWalls, int numOfWrapping) {
    super(row, column, randomSeed, difficulty, numOfPlayer, numOfRemainingWalls);
    this.numOfWrapping = numOfWrapping;
  }

  @Override
  public boolean buildMaze() {
    if (buildRoomMaze(randomSeed, randomSeed + 500)) {
      int[][] pairs = new int[row + column][2];
      for (int i = 0; i < column; i++) {
        pairs[i][0] = i;
        pairs[i][1] = i + (row - 1) * column;
      }
      for (int i = 0; i < row; i++) {
        pairs[i + column][0] = i * column;
        pairs[i + column][1] = (i + 1) * column - 1;
      }
      Set<Integer> visitedPairs = new HashSet<>();
      int wrapping = numOfWrapping;
      Random rand = new Random();
      rand.setSeed(randomSeed);
      while (wrapping > 0) {
        int idx = rand.nextInt(pairs.length);
        if (visitedPairs.contains(idx)) {
          continue;
        }
        visitedPairs.add(idx);
        int[] curr = pairs[idx];
        connections.get(curr[0]).add(curr[1]);
        connections.get(curr[1]).add(curr[0]);
        numOfConnections++;
        wrapping--;
      }
      return numOfConnections == numOfWalls - numOfRemainingWalls + numOfWrapping;
    }
    return false;
  }
}
