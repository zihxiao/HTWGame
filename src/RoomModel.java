import java.util.Random;

/**
 * RoomMaze Class inherit from PerfectMaze and override buildMaze() in super class by adding
 * additional connections based on the necessary connections which are created in PerfectMaze.
 *
 * @author: Zihan
 * @created: 10-29-2020
 */
public class RoomModel extends PerfectModel {
  protected int numOfRemainingWalls;

  /**
   * Constructor of RoomMaze and all members are inherit from PerfectMaze.
   *
   * @param row row number of the maze
   * @param column column number of the maze
   * @param randomSeed Random() seed for setting golds and thieves
   * @param numOfRemainingWalls the number of remaining walls
   */
  protected RoomModel(int row, int column, int randomSeed, Difficulty difficulty, int numOfPlayer, int numOfRemainingWalls) {
    super(row, column, randomSeed, difficulty, numOfPlayer);
    if (numOfRemainingWalls >= numOfWalls - size + 1 || numOfRemainingWalls < 0) {
      throw new IllegalArgumentException("Incorrect number of remaining walls.");
    }
    this.numOfRemainingWalls = numOfRemainingWalls;
  }

  @Override
  public boolean buildMaze() {
    return buildRoomMaze(randomSeed, randomSeed + 300);
  }

  /**
   * Helper function of buildMaze().
   *
   * @param seed1 random seed for build perfect maze
   * @param seed2 random seed for creating additional connections between random adjacent cells.
   * @return
   */
  protected boolean buildRoomMaze(int seed1, int seed2) {
    if (buildPerfectMaze(seed1)) {
      int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
      Random rand = new Random();
      rand.setSeed(seed2);
      while (numOfConnections < numOfWalls - numOfRemainingWalls) {
        int curr = rand.nextInt(size);
        int x = curr / column;
        int y = curr % column;
        int k = rand.nextInt(4);
        int neighborX = x + directions[k][0];
        int neighborY = y + directions[k][1];
        int neighbor = neighborX * column + neighborY;
        if (neighborX >= 0 && neighborX < row && neighborY >= 0 && neighborY < column
            && !connections.get(curr).contains(neighbor)) {
          connections.get(curr).add(neighbor);
          connections.get(neighbor).add(curr);
          numOfConnections++;
        }
      }
      return numOfConnections == numOfWalls - numOfRemainingWalls;
    }
    return false;
  }
}
