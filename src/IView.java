import java.awt.event.KeyListener;

/**
 * @author: Zihan
 * @created: 12-18-2020
 */
public interface IView {
  /**
   * Paint the UI.
   *
   * @param isGameOver
   * @param isInputCommand
   * @param maze
   * @param message
   * @param commandHelper
   */
  void paint(boolean isGameOver, boolean isInputCommand, String[][] maze, String message, String commandHelper);

  /**
   * Set key listener.
   *
   * @param listener
   */
  void setKeyListener(KeyListener listener);
}
