import static java.awt.event.KeyEvent.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

/**
 * Controller Implementation.
 *
 * @author: Zihan
 * @created: 12-03-2020
 */
public class Controller implements IController, ActionListener, KeyListener {
  private IModel model;
  private IView view;

  private Timer timer;

  private boolean isCommand;
  private String commandHelper;
  private Direction arrowDirection;
  private boolean move;
  private boolean shoot;

  /**
   * Constructor.
   */
  public Controller(IModel model, IView view) {
    this.model = model;
    this.view = view;
    this.isCommand = true;
    this.arrowDirection = Direction.SOUTH;
    this.move = false;
    this.shoot = false;
    this.commandHelper = " ";

    view.setKeyListener(this);
    timer = new Timer(100, this);
  }

  @Override
  public void go() {
    timer.start();
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    int keycode = e.getKeyCode();
    switch (keycode) {
      case VK_ENTER:
        isCommand = true;
        commandHelper = "Move or Shoot? Press M(move) or A(shoot).";
        break;
      case VK_M:
        isCommand = true;
        move = true;
        shoot = false;
        commandHelper = "Move to UP, DOWN, LEFT or RIGHT? Press any one of the key.";
        break;
      case VK_A:
        isCommand = true;
        move = false;
        shoot = true;
        commandHelper = "Shoot direction? Press any one of the letter from E, S, W and N.";
        break;
      case VK_LEFT:
        if (!move) {
          break;
        }
        isCommand = false;
        model.move(Direction.WEST);
        break;
      case VK_RIGHT:
        if (!move) {
          break;
        }
        isCommand = false;
        model.move(Direction.EAST);
        break;
      case VK_UP:
        if (!move) {
          break;
        }
        isCommand = false;
        model.move(Direction.NORTH);
        break;
      case VK_DOWN:
        if (!move) {
          break;
        }
        isCommand = false;
        model.move(Direction.SOUTH);
        break;
      case VK_E:
        if (!shoot) {
          break;
        }
        isCommand = true;
        arrowDirection = Direction.EAST;
        commandHelper = "Shoot direction is EAST. Shoot distance? Press any number from 1 - 5.";
        break;
      case VK_W:
        if (!shoot) {
          break;
        }
        isCommand = true;
        arrowDirection = Direction.WEST;
        commandHelper = "Shoot direction is WEST. Shoot distance? Press any number from 1 - 5.";
        break;
      case VK_N:
        if (!shoot) {
          break;
        }
        isCommand = true;
        arrowDirection = Direction.NORTH;
        commandHelper = "Shoot direction is NORTH. Shoot distance? Press any number from 1 - 5.";
        break;
      case VK_S:
        if (!shoot) {
          break;
        }
        isCommand = true;
        arrowDirection = Direction.SOUTH;
        commandHelper = "Shoot direction is SOUTH. Shoot distance? Press any number from 1 - 5.";
        break;
      case VK_1:
        if (!shoot) {
          break;
        }
        isCommand = false;
        model.shoot(arrowDirection, 1);
        break;
      case VK_2:
        if (!shoot) {
          break;
        }
        isCommand = false;
        model.shoot(arrowDirection, 2);
        break;
      case VK_3:
        if (!shoot) {
          break;
        }
        isCommand = false;
        model.shoot(arrowDirection, 3);
        break;
      case VK_4:
        if (!shoot) {
          break;
        }
        isCommand = false;
        model.shoot(arrowDirection, 4);
        break;
      case VK_5:
        if (!shoot) {
          break;
        }
        isCommand = false;
        model.shoot(arrowDirection, 5);
        break;
      default:
        break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (model.getMessage() == null && commandHelper == null) {
      return;
    }
    view.paint(model.gameEnd(), isCommand, model.getMaze(), model.getMessage(), commandHelper);
  }
}
