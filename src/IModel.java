/**
 * The interface of Maze.
 *
 * @author: Zihan
 * @created: 10-26-2020
 */
public interface IModel {

  /**
   * Build the cells, walls of the maze and set the golds and thieves. Use Union-Find to
   * remove walls/add connections which guarantee that the player can reach any of the cell.
   *
   * @return true: build successfully; false: build unsuccessfully
   */
  boolean buildMaze();

  /**
   * Get the movement history of the player.
   *
   * @return The history in String
   */
  String getHistory();

  /**
   * The player move to the next cell and the movement direction is d.
   *
   * @param d the direction of the movement, it can be either EAST, WEST, NORTH, SOUTH
   * @return true: if the player make a move; false: if there is a wall blocking the movement
   */
  void move(Direction d);

  /**
   * Print the maze in string including cells and walls.
   *
   * @return the maze
   */
  String toString();

  /**
   * Get the player's current state.
   *
   * @return current state
   */
  String getMessage();


  /**
   * Make a shot.
   *
   * @param numOfCaves the distance of the shoot
   */
  void shoot(Direction d, int numOfCaves);

  /**
   * Check if the game is end.
   *
   * @return
   */
  boolean gameEnd();

  /**
   * Get maze in matrix format.
   *
   * @return
   */
  String[][] getMaze();

}
