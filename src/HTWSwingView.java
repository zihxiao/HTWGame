import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.KeyListener;

/**
 * IView implementation extends JFrame.
 *
 * @author: Zihan
 * @created: 12-19-2020
 */
public class HTWSwingView extends JFrame implements IView{
  private HTWPanel htwPanel;

  public HTWSwingView(String title) {
    super(title);

    htwPanel = new HTWPanel();
    this.add(htwPanel);

    htwPanel.setPreferredSize(new Dimension(htwPanel.WINDOW_WIDTH, htwPanel.WINDOW_WIDTH));


    this.pack();
    this.setVisible(true);
  }

  @Override
  public void paint(boolean isGameOver, boolean isInputCommand, String[][] maze, String message, String commandHelper) {
    htwPanel.paint(isGameOver, isInputCommand, maze, message, commandHelper);
  }

  @Override
  public void setKeyListener(KeyListener listener) {
    this.addKeyListener(listener);
    this.setFocusable(true);
  }
}
