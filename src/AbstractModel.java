import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * The Abstract class implements the Maze interface
 * which implements most of methods except buildMaze().
 *
 * @author: Zihan
 * @created: 10-26-2020
 */
abstract class AbstractModel implements IModel {
  protected int row;
  protected int column;
  protected int size;
  protected int start;
  protected int randomSeed;
  protected String[][] maze;
  protected List<Set<Integer>> connections;
  protected int numOfWalls;
  protected Player player;
  protected List<Integer> history;
  protected int[] father;
  protected int numOfConnections;
  protected int perfectMazeMaxNumOfConnections;
  //
  protected String message;
  protected int numOfArrows;
  protected int wumpusLoc;
  protected boolean nearPit;
  protected boolean nearWumpus;
  protected Set<Integer> pitSet;
  protected Set<Integer> batSet;
  protected boolean gameEnd;

  /**
   * The constructor of AbstractMaze with the maze size, start point, end point, Player and
   * a Random() seed used to generate pseudo-random numbers
   * to reproduce the same "unique" mazes each time.
   *  @param row row number of the maze
   * @param column column number of the maze
   * @param randomSeed Random() seed for setting golds and thieves
   */
  protected AbstractModel(int row, int column, int randomSeed, Difficulty difficulty, int numOfPlayer) {
    if (row < 0 || column < 0 || start >= row * column || start < 0) {
      throw new IllegalArgumentException("Invalid input parameters");
    }
    this.row = row;
    this.column = column;
    this.randomSeed = randomSeed;
    this.size = row * column;
    this.pitSet = new HashSet<>();
    this.wumpusLoc = -1;
    this.batSet = new HashSet<>();
    this.start = 0;
    int numOfPits, numOfBats;
    if (difficulty.equals(Difficulty.EASY)) {
      numOfPits = (int) (size * 0.1);
      numOfBats = (int) (size * 0.1);
      this.numOfArrows = size / 2;
    } else if (difficulty.equals(Difficulty.MEDIUM)) {
      numOfPits = (int) (size * 0.2);
      numOfBats = (int) (size * 0.2);
      this.numOfArrows = size / 5;
    } else {
      numOfPits = (int) (size * 0.3);
      numOfBats = (int) (size * 0.3);
      this.numOfArrows = size / 10;
    }
    System.out.println(numOfBats);
    this.maze = createMaze(row, column, row * column, randomSeed, numOfPits, numOfBats);
    this.numOfWalls = (row - 1) * column + (column - 1) * row;
    this.player = new Player("p1", start);
    this.connections = createConnections(row * column);
    this.history = new ArrayList<>(Arrays.asList(start));
    this.father = new int[size];
    this.numOfConnections = 0;
    this.perfectMazeMaxNumOfConnections = numOfWalls - (numOfWalls - size + 1);
    // htw related variables
    this.message = "initial message.";
    this.nearPit = false;
    this.nearWumpus = false;
    this.gameEnd = false;
  }

  /**
   * Initialize the cells of the maze with golds and thieves.
   * @param row row number of the maze
   * @param column column number of the maze
   * @param size number of the cells in the maze which equals to row times column
   * @param randomSeed Random() seed for setting golds and thieves
   * @return 2d-int array of maze cells
   */
  private String[][] createMaze(int row, int column, int size, int randomSeed, int numOfPits, int numOfBats) {
    String[][] m = new String[row][column];
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < column; j++) {
        m[i][j] = "";
      }
    }
    Random rand = new Random();
    rand.setSeed(randomSeed);
    Set<Integer> pitPos = new HashSet<>();
    while (pitPos.size() < numOfPits) {
      int n = rand.nextInt(size);
      if (!pitPos.contains(n) && n != start) {
        pitPos.add(n);
        int r = n / column;
        int c = n % column;
        m[r][c] = "p";
      }
    }
    pitSet = pitPos;
    Set<Integer> batPos = new HashSet<>();
    while (batPos.size() < numOfBats) {
      int k = rand.nextInt(size);
      if (!batPos.contains(k) && k != start) {
        batPos.add(k);
        int r = k / column;
        int c = k % column;
        m[r][c] = "b";
      }
    }
    batSet = batPos;
    boolean setWumpus = false;
    while (!setWumpus) {
      int i = rand.nextInt(size);
      if (!batPos.contains(i) && !pitPos.contains(i)) {
        setWumpus = true;
        wumpusLoc = i;
        int r = i / column;
        int c = i % column;
        m[r][c] = "w";
      }
    }
    // set start position
    int startR = start / column;
    int startC = start % column;
    m[startR][startC] = "*";
    return m;
  }

  /**
   * Initialize the connections of cells.
   *
   * @param size the size of the connections which equals to the number of cells
   * @return the list of set
   */
  private List<Set<Integer>> createConnections(int size) {
    List<Set<Integer>> connections = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      connections.add(new HashSet<>());
    }
    return connections;
  }

  /**
   * Helper function to build the perfect maze.
   *
   * @param seed Random() seed for selecting random cells to do union-find
   * @return true: build successfully; false: build unsuccessfully
   */
  protected boolean buildPerfectMaze(int seed) {
    for (int i = 0; i < size; i++) {
      father[i] = i;
    }
    int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    Random rand = new Random();
    rand.setSeed(seed);
    while (numOfConnections < perfectMazeMaxNumOfConnections) {
      int curr = rand.nextInt(size);
      int x = curr / column;
      int y = curr % column;
      int k = rand.nextInt(4);
      int neighborX = x + directions[k][0];
      int neighborY = y + directions[k][1];
      if (neighborX >= 0 && neighborX < row && neighborY >= 0 && neighborY < column) {
        int neighbor = neighborX * column + neighborY;
        union(curr, neighbor);
      }
    }
    for (int i = 0; i < size; i++) {
      father[i] = find(i);
    }
    for (int i = 1; i < size; i++) {
      if (father[i] != father[i - 1]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Helper function for Union-Find which find the father of the cell.
   *
   * @param x the index of the cell
   * @return the "top" father of the cell
   */
  private int find(int x) {
    if (father[x] != x) {
      father[x] = find(father[x]);
      return father[x];
    } else {
      return x;
    }
  }

  /**
   * Helper function for Union-Find which combine cell x and cell y into a same set
   * and add connections between x and y if x and y are not in the same set.
   *
   * @param x the index of the cell
   * @param y the index of the cell
   */
  private void union(int x, int y) {
    int p1 = find(x);
    int p2 = find(y);
    if (p1 != p2) {
      if (p1 < p2) {
        father[p2] = p1;
      } else {
        father[p1] = p2;
      }
      connections.get(x).add(y);
      connections.get(y).add(x);
      numOfConnections++;
    }
  }

  private void checkNearByPits() {
    int curr = player.getLoc();
    for (Integer i : pitSet) {
      if (connections.get(curr).contains(i)) {
        message += "You feel a draft!\n";
      }
    }
  }

  private void checkNearByWumpus() {
    int curr = player.getLoc();
    if (connections.get(curr).contains(wumpusLoc)) {
      message += "You smell a Wumpus!\n";
    }
  }

  private void caughtByBat() {
    message += "You were picked up by superbats!\n";
    int next = new Random().nextInt(size);
    takeAction(next);
  }

  private void fallInPit() {
    message += "Defeat! You fail into a pit.\n";
    gameEnd = true;
  }

  private void eatenByWumpus() {
    message += "Defeat! You were eaten by the Wumpus.\n";
    gameEnd = true;
  }


  /**
   * Make any action when player move into a new cave.
   *
   * @param loc the index of the new cell
   */
  private void takeAction(int loc) {
    if (loc == wumpusLoc) {
      eatenByWumpus();
    } else if (pitSet.contains(loc)) {
      if (!batSet.contains(loc)) {
        fallInPit();
      } else {
        boolean pickUpByBat = Math.random() > 0.5;
        if (pickUpByBat) {
          caughtByBat();
        } else {
          fallInPit();
        }
      }
    } else if (batSet.contains(loc)) {
      boolean pickUpByBat = Math.random() > 0.5;
      if (pickUpByBat) {
        caughtByBat();
      } else {
        message += ("Now you are in cave " + loc + ".\n");
        int curr = player.getLoc();
        int x = curr / column, y = curr % column;
        maze[x][y] = maze[x][y].substring(0, maze[x][y].length()-1);
        player.setLoc(loc);
        x = loc / column;
        y = loc % column;
        maze[x][y] += "*";
        history.add(loc);
        checkNearByPits();
        checkNearByWumpus();
      }
    } else {
      message += ("Now you are in cave " + loc + ".\n");
      int curr = player.getLoc();
      int x = curr / column, y = curr % column;
      maze[x][y] = maze[x][y].substring(0, maze[x][y].length()-1);
      player.setLoc(loc);
      x = loc / column;
      y = loc % column;
      maze[x][y] += "*";
      history.add(loc);
      checkNearByPits();
      checkNearByWumpus();
    }
  }

  @Override
  public void shoot(Direction direction, int distance) {
    message = " ";
    if (numOfArrows < 0) {
      message += "Defeat, you run out of arrows.\n";
      gameEnd = true;
      return;
    }
    numOfArrows--;
    int in = player.getLoc();
    int curr = player.getLoc();
    int out = player.getLoc();
    Arrow arrow = new Arrow(direction);
    while (distance > 0) {
      Set<Integer> neighbors = connections.get(curr);
      int next;
      if (arrow.getDirection().equals(Direction.EAST)) {
        if ((curr + 1) % column == 0) {
          next = curr - (column - 1);
        } else {
          next = curr + 1;
        }
        if (neighbors.contains(next)) {
          out = next;
        }
      } else if (arrow.getDirection().equals(Direction.WEST)) {
        if (curr % column == 0) {
          next = curr + (column - 1);
        } else {
          next = curr - 1;
        }
        if (neighbors.contains(next)) {
          out = next;
        }
      } else if (arrow.getDirection().equals(Direction.NORTH)) {
        next = curr - column;
        if (next < 0) {
          next += size;
        }
        if (neighbors.contains(next)) {
          out = next;
        }
      } else if (arrow.getDirection().equals(Direction.SOUTH)) {
        next = curr + column;
        if (next >= size) {
          next -= size;
        }
        if (neighbors.contains(next)) {
          out = next;
        }
      }
      if (out == curr) {
        if (neighbors.size() > 2) {
          message += ("Arrow is stuck in the cave " + curr + ".\n");
        } else {
          for (Integer i : neighbors) {
            if (i == in) {
              continue;
            } else {
              out = i;
              if (curr + 1 == out || curr - (column - 1) == out) {
                arrow.setDirection(Direction.EAST);
              } else if (curr - 1 == out || curr + column - 1 == out) {
                arrow.setDirection(Direction.WEST);
              } else if (curr - column == out || curr - column + size == out) {
                arrow.setDirection(Direction.NORTH);
              } else if (curr + column == out || curr + column - size == out) {
                arrow.setDirection(Direction.SOUTH);
              }
            }
          }
        }
      }
      in = curr;
      curr = out;
      distance--;
    }
    if (curr == wumpusLoc) {
      message += "Victory! You beat the Wumpus!\n";
      gameEnd = true;
    } else {
      if (numOfArrows == 0) {
        message += "Defeat, you run out of arrows.\n";
        gameEnd = true;
      } else {
        message += ("You didn't shoot the Wumpus. You have " + numOfArrows + " arrow(s) left.\n");
        gameEnd = false;
      }
    }
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public boolean gameEnd() {
    return gameEnd;
  }

  private String nextPossibleMove(int loc) {
    String mesg = "Tunnels lead to the ";
    Set<Integer> neighbors = connections.get(loc);
    for (Integer neighbor : neighbors) {
      if (loc + 1 == neighbor || loc  - (column - 1) == neighbor) {
        mesg += "E, ";
      } else if (loc - 1 == neighbor || loc + column - 1 == neighbor) {
        mesg += "W, ";
      } else if (loc - column == neighbor || loc - column + size == neighbor) {
        mesg += "N, ";
      } else if (loc + column == neighbor || loc + column - size == neighbor) {
        mesg += "S, ";
      }
    }
    return mesg.trim() + "\n";
  }

//  private String printConnection(int curr) {
//    Set<Integer> s = connections.get(curr);
//    String res = "";
//    for (Integer i : s) {
//      res += (i + " ");
//    }
//    return res;
//  }

  @Override
  public void move(Direction d) {
    message = " ";
    int curr = player.getLoc();
    int next;
    switch (d) {
      case EAST:
        if ((curr + 1) % column == 0) {
          next = curr - (column - 1);
        } else {
        next = curr + 1;
        }
        if (connections.get(curr).contains(next)) {
          takeAction(next);
          if (!gameEnd) {
            message += nextPossibleMove(player.getLoc());
          }
        } else {
          message += "Dead end. Please try to move towards another direction.1\n";
        }
        break;
      case WEST:
        if (curr % column == 0) {
          next = curr + (column - 1);
        } else {
          next = curr - 1;
        }
        if (connections.get(curr).contains(next)) {
          takeAction(next);
          if (!gameEnd) {
            message += nextPossibleMove(player.getLoc());
          }
        } else {
          message += "Dead end. Please try to move towards another direction.2\n";
        }
        break;
      case NORTH:
        next = curr - column;
        if (next < 0) {
          next += size;
        }
        if (connections.get(curr).contains(next)) {
          takeAction(next);
          if (!gameEnd) {
            message += nextPossibleMove(player.getLoc());
          }
        } else {
          message += "Dead end. Please try to move towards another direction.3\n";
        }
        break;
      case SOUTH:
        next = curr + column;
        if (next >= size) {
          next -= size;
        }
        if (connections.get(curr).contains(next)) {
          takeAction(next);
          if (!gameEnd) {
            message += nextPossibleMove(player.getLoc());
          }
        } else {
          message += "Dead end. Please try to move towards another direction.4\n";
        }
        break;
      default:
        message += "Incorrect Input!\n";
    }
  }

  /**
   * Helper function of getHistory() which converts List of String to a String.
   *
   * @param path the list of Integer and the Integer represents the index of the visited cell.
   * @return String
   */
  private String getPathString(List<Integer> path) {
    StringBuilder sb = new StringBuilder();
    for (Integer h : path) {
      sb.append("[" + h / column + ", " + h % column + "] -> ");
    }
    sb.delete(sb.lastIndexOf(" -> "), sb.length());
    return sb.toString();
  }

  @Override
  public String getHistory() {
    return getPathString(history);
  }

  /**
   * Print 2d String array to a String. Used to print out the maze.
   *
   * @param matrix 2d String array of maze including cells and walls
   * @return String of maze
   */
  private String print2D(String[][] matrix) {
    String str = "";
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        str += ((matrix[i][j]) + "\t");
      }
      str += "\n";
    }
    return str;
  }

  @Override
  public String[][] getMaze() {
    String[][] matrix = new String[2 * row + 1][2 * column + 1];
    for (int i = 0; i < 2 * row + 1; i++) {
      if (i % 2 == 0) {
        for (int j = 0 ; j < 2 * column + 1; j++) {
          matrix[i][j] = "-";
        }
      } else {
        for (int j = 0; j < 2 * column + 1; j++) {
          if (j % 2 == 1) {
            matrix[i][j] = maze[i / 2][j / 2] == null ? " " : maze[i / 2][j / 2];
          } else {
            matrix[i][j] = "|";
          }
        }
      }
    }
    for (int i = 0; i < size; i++) {
      Set<Integer> cell = connections.get(i);
      int x = i / column;
      int y = i % column;
      for (Integer neighbor : cell) {
        if (neighbor - i == 1) {
          if (matrix[2 * x + 1][2 * y + 2].equals("|")) {
            matrix[2 * x + 1][2 * y + 2] = " ";
          }
        } else if (neighbor - i == -1) {
          if (matrix[2 * x + 1][2 * y].equals("|")) {
            matrix[2 * x + 1][2 * y] = " ";
          }
        } else if (neighbor - i == column) {
          if (matrix[2 * x + 2][2 * y + 1].equals("-")) {
            matrix[2 * x + 2][2 * y + 1] = " ";
          }
        } else if (neighbor - i == -1 * column) {
          if (matrix[2 * x][2 * y + 1].equals("-")) {
            matrix[2 * x][2 * y + 1] = " ";
          }
        } else if (neighbor - i == column - 1 || i - neighbor == column - 1) {
          if (matrix[2 * x + 1][0].equals("|")) {
            matrix[2 * x + 1][0] = " ";
          }
          if (matrix[2 * x + 1][2 * column].equals("|")) {
            matrix[2 * x + 1][2 * column] = " ";
          }
        } else if (neighbor - i == (row - 1) * column || i - neighbor == (row - 1) * column) {
          if (matrix[0][2 * y + 1].equals("|")) {
            matrix[0][2 * y + 1] = " ";
          }
          if (matrix[2 * row][2 * y + 1].equals("|")) {
            matrix[2 * row][2 * y + 1] = " ";
          }
        }
      }
    }
    return matrix;
  }

  @Override
  public String toString() {
    return print2D(this.getMaze());
  }
}
