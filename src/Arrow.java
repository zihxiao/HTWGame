/**
 * Arrow class.
 *
 * @author: Zihan
 * @created: 12-18-2020
 */
public class Arrow {
  private Direction direction;

  public Arrow(Direction direction) {
    this.direction = direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public Direction getDirection() {
    return direction;
  }
}
