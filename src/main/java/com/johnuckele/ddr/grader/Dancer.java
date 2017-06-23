package com.johnuckele.ddr.grader;

import enums.Position;

public class Dancer {
  private double absoluteRotation = 0;
  private double netRotation = 0;
  private double absoluteDistance = 0;

  // Dancer physical orientation
  private Position leftFoot = Position.PAD_ONE_LEFT;
  private Position rightFoot = Position.PAD_ONE_RIGHT;
  private double currentHeading = Position.getHeading(leftFoot, rightFoot);
  private boolean lastFootIsLeft = true;
  private double currentX = 1;
  private double currentY = 1;

  public void dance(Metadata metadata, Stepchart stepchart) {
    System.out.println("We can dance if you want to!");
    System.out.println("  Dancing to " + metadata.name);
    System.out.println(
        "  Dancing on "
            + stepchart.stepchartMetadata.difficulty.name
            + ", "
            + stepchart.stepchartMetadata.padLayout.name);
    for (int i = 0; i < stepchart.getDataLength(); i++) {
      Arrows arrows = stepchart.getBasicArrows(i);
      if (arrows != Arrows.EMPTY) {
        updateFootPositions(arrows);
        updateHeading();
        updatePosition();
      }
    }
  }

  private void updateFootPositions(Arrows arrows) {
    if (arrows.arrowCount == 2
        && arrows.positions.contains(leftFoot)
        && arrows.positions.contains(rightFoot)) {
      // Jump in place
    } else if (arrows.arrowCount == 2
        && (arrows.positions.contains(leftFoot) || arrows.positions.contains(rightFoot))) {
      // Jump with one foot in place
      if (arrows.positions.contains(leftFoot)) {
        rightFoot = arrows.getJumpArrowsMinus(leftFoot);
        lastFootIsLeft = true;
      } else if (arrows.positions.contains(rightFoot)) {
        leftFoot = arrows.getJumpArrowsMinus(rightFoot);
        lastFootIsLeft = false;
      }
    } else if (arrows.arrowCount == 2) {
      // Jump, complete move
      // TODO: THIS IS HARD, ignore for now
    } else if (arrows.arrowCount == 1
        && (arrows.positions.contains(leftFoot) || arrows.positions.contains(rightFoot))) {
      // Step in place
      if (arrows.positions.contains(leftFoot)) {
        lastFootIsLeft = true;
      } else if (arrows.positions.contains(rightFoot)) {
        lastFootIsLeft = false;
      }
    } else if (arrows.arrowCount == 1) {
      // Step, move a foot
      if (lastFootIsLeft) {
        rightFoot = arrows.getStepPosition();
        lastFootIsLeft = false;
      } else {
        leftFoot = arrows.getStepPosition();
        lastFootIsLeft = true;
      }
    }
  }

  private void updateHeading() {
    double newHeading = Position.getHeading(leftFoot, rightFoot);
    double deltaHeading = newHeading - currentHeading;
    if (deltaHeading > Math.PI) {
      deltaHeading -= Math.PI * 2;
    } else if (deltaHeading < -Math.PI) {
      deltaHeading += Math.PI * 2;
    }
    absoluteRotation += Math.abs(deltaHeading);
    netRotation += deltaHeading;
    currentHeading = newHeading;
  }

  private void updatePosition() {
    double newX = Position.getAverageX(leftFoot, rightFoot);
    double newY = Position.getAverageY(leftFoot, rightFoot);
    double deltaDistance = Math.sqrt((newX - currentX)*(newX - currentX) +(newY - currentY)*(newY - currentY));
    absoluteDistance += deltaDistance;
  }
  public double getAbsoluteRotation() {
    return absoluteRotation;
  }

  public double getNetRotation() {
    return netRotation;
  }

  public double getAbsoluteDistance() {
    return absoluteDistance;
  }
}
