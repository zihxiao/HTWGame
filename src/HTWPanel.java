import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author: Zihan
 * @created: 12-18-2020
 */
public class HTWPanel extends JPanel {
  private String[][] maze;
  private BufferedImage horizontal;
  private BufferedImage vertical;
  private BufferedImage pit;
  private BufferedImage bat;
  private BufferedImage wumpus;
  private BufferedImage player;
  private boolean isGameOver;
//  private boolean beforeStart;
  private String message;
  private boolean isInputCommand;
  private String commandHelperMesg;

  static final int WINDOW_WIDTH = 1200;
  static final int GAME_HEIGHT = 700;
  static final int MESSAGE_HEIGHT = 500;

  /**
   * Panel Constructor.
   */
  public HTWPanel() {
    try {
      horizontal = ImageIO.read(new File("./image/horizontal.png"));
      vertical = ImageIO.read(new File("./image/vertical.png"));
      pit = ImageIO.read(new File("./image/slime-pit.png"));
      bat = ImageIO.read(new File("./image/superbat.png"));
      wumpus = ImageIO.read(new File("./image/wumpus.png"));
      player = ImageIO.read(new File("./image/player.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.setFocusable(true);
  }

  /**
   * Paint UI.
   *
   * @param isGameOver
   * @param isInputCommand
   * @param maze
   * @param message
   * @param commandHelperMesg
   */
  public void paint(boolean isGameOver, boolean isInputCommand, String[][] maze, String message, String commandHelperMesg) {
    this.isGameOver = isGameOver;
//    this.beforeStart = beforeStart;
    this.maze = maze;
    this.message = message;
    this.commandHelperMesg = commandHelperMesg;
    this.isInputCommand = isInputCommand;
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (isGameOver) {
      g.drawString(message, WINDOW_WIDTH/2, WINDOW_WIDTH/2);
    } else if (isInputCommand) {
      gameDeploy(g, commandHelperMesg);
    } else {
      gameDeploy(g, message);
    }
  }

  private void gameDeploy(Graphics g, String message) {
    int row = maze.length, col = maze[0].length;
    int length = Math.min(GAME_HEIGHT / row, WINDOW_WIDTH / col);
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        if (maze[i][j].equals("-")) {
          int r = i * length, c = j * length;
          g.drawImage(horizontal, c, r, this);
        } else if (maze[i][j].equals("|")) {
          int r = i * length, c = j * length;
          g.drawImage(vertical, c, r, this);
        } else if (maze[i][j].equals("p")) {
          int r = i * length, c = j * length;
          g.drawImage(pit, c, r, this);
        } else if (maze[i][j].equals("w")) {
          int r = i * length, c = j * length;
          g.drawImage(wumpus, c, r, this);
        } else if (maze[i][j].equals("b")) {
          int r = i * length, c = j * length;
          g.drawImage(bat, c, r, this);
        } else if (maze[i][j].endsWith("*")) {
          int r = i * length, c = j * length;
          g.drawImage(player, c , r, this);
        }
      }
    }
    g.drawString(message, 0, WINDOW_WIDTH - MESSAGE_HEIGHT);
  }


}
