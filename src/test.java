/**
 * @author: Zihan
 * @created: 12-18-2020
 */
public class test {
  public static void main(String[] args) {
    IModel maze = new RoomModel(5, 5, 100, Difficulty.MEDIUM, 1,11);
    maze.buildMaze();
    System.out.println(maze.toString());
  }

}
