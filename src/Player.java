/**
 * Player Object in the Maze.
 *
 * @author: Zihan
 * @created: 10-27-2020
 */
public class Player {
  private String name;
  private int gold;
  private int start;
  private int loc;

  /**
   * Constructor of Player with name of the string and the index of start cell.
   *
   * @param name name of the player
   * @param startLoc index of the player's start cell
   */
  public Player(String name, int startLoc) {
    this.name = name;
    this.gold = 0;
    this.start = startLoc;
    this.loc = startLoc;
  }

  /**
   * Get the name of the Player.
   *
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * Get the current amount of gold of the Player.
   *
   * @return the current amount of gold
   */
  public int getGold() {
    return gold;
  }

  /**
   * Set the Player's gold into a new value.
   *
   * @param gold new gold value
   */
  public void setGold(int gold) {
    this.gold = gold;
  }

  /**
   * Get the current location of the Player.
   *
   * @return the current index of cell where the Player is
   */
  public int getLoc() {
    return loc;
  }

  public int getStart() {
    return start;
  }

  /**
   * Set the Player a new location.
   *
   * @param loc the index of the new location
   */
  public void setLoc(int loc) {
    this.loc = loc;
  }
}
