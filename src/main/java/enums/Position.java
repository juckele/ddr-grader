package enums;

public enum Position {
  PAD_ONE_LEFT(0, 1, 0),
  PAD_ONE_RIGHT(2, 1, 1),
  PAD_ONE_TOP(1, 2, 2),
  PAD_ONE_BOTTOM(1, 0, 3),
  PAD_TWO_LEFT(3, 1, 4),
  PAD_TWO_RIGHT(5, 1, 5),
  PAD_TWO_TOP(4, 2, 6),
  PAD_TWO_BOTTOM(4, 0, 7),
  ;

  // As measured from the bottom left of player 1's pad, distances are in blocks
  private final double x, y;
  public final int ddrArrowIndex;

  Position(int x, int y, int ddrArrowIndex) {
    this.x = x;
    this.y = y;
    this.ddrArrowIndex = ddrArrowIndex;
  }

  public static double getAverageX(Position leftFoot, Position rightFoot) {
    return (leftFoot.x + rightFoot.x) / 2;
  }

  public static double getAverageY(Position leftFoot, Position rightFoot) {
    return (leftFoot.y + rightFoot.y) / 2;
  }

  public double distanceFrom(Position that) {
    return Math.sqrt((this.x - that.x)*(this.x - that.x) +(this.y - that.y)*(this.x - that.y));
  }

  public static double getXSpan(Position leftFoot, Position rightFoot) {
    return Math.abs(leftFoot.x - rightFoot.x);
  }

  public static double getYSpan(Position leftFoot, Position rightFoot) {
    return Math.abs(leftFoot.y - rightFoot.y);
  }

  public static double getHeading(Position leftFoot, Position rightFoot) {
    if (leftFoot == rightFoot) {
      throw new IllegalStateException("Can't determine heading when feet are matched");
    }
    double heading = Math.atan2(rightFoot.x - leftFoot.x, rightFoot.y - leftFoot.y) - Math.PI / 2;
    if (heading < 0) {
      heading += Math.PI * 2;
    } else if (heading > Math.PI * 2) {
      heading -= Math.PI * 2;
    }
    return heading;
  }

  public static Position getPositionAtIndex(int arrowIndex) {
    for (Position value : Position.values()) {
      if (value.ddrArrowIndex == arrowIndex) {
        return value;
      }
    }
    throw new IllegalStateException("Unable to map \"" + arrowIndex + "\" to a valid Position");
  }

}
