import java.util.Scanner;

/**
 * @author: Zihan
 * @created: 12-03-2020
 */
public class Driver {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Choose the game size:\nRow:");
    Integer row = Integer.parseInt(scanner.nextLine());
    System.out.println("Column:");
    Integer col = Integer.parseInt(scanner.nextLine());
    System.out.println("Number of remaining walls:");
    Integer numOfRemainingWall = Integer.parseInt(scanner.nextLine());
    System.out.println("Difficulty? 1: Easy, 2: Medium or 3: Hard. Input any number from 1 - 3:");
    Integer d = Integer.parseInt(scanner.nextLine());
    Difficulty difficulty;
    if (d == 1) {
      difficulty = Difficulty.EASY;
    } else if (d == 2) {
      difficulty = Difficulty.MEDIUM;
    } else {
      difficulty = Difficulty.HARD;
    }
    IModel model = new RoomModel(row, col, 100, difficulty,
        1, numOfRemainingWall);
    model.buildMaze();
    System.out.println(model.toString());
//    System.out.println(model.getMessage());
    System.out.println("---------Now start playing! Start by press Enter.----------");
    IView view = new HTWSwingView("HTW Game");
    IController game = new Controller(model, view);
    game.go();
  }
}
