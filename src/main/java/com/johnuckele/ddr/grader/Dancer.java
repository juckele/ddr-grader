package com.johnuckele.ddr.grader;

import enums.Position;
import java.util.LinkedList;
import java.util.List;

public class Dancer {
  private double absoluteRotation = 0;
  private double netRotation = 0;
  private double absoluteDistance = 0;

  private List<Double> jumpsInPlaceTimestamps = new LinkedList<>();
  private List<Double> jumpsHalfInPlaceTimestamps = new LinkedList<>();
  private List<Double> jumpsFullMoveTimestamps = new LinkedList<>();
  private List<Double> stepsRepeatedTimestamps = new LinkedList<>();
  private List<Double> stepsMarchingTimestamps = new LinkedList<>();
  private List<Double> stepsMoveTimestamps = new LinkedList<>();

  private List<Double> jumpsInPlaceBeats = new LinkedList<>();
  private List<Double> jumpsHalfInPlaceBeats = new LinkedList<>();
  private List<Double> jumpsFullMoveBeats = new LinkedList<>();
  private List<Double> stepsRepeatedBeats = new LinkedList<>();
  private List<Double> stepsMarchingBeats = new LinkedList<>();
  private List<Double> stepsMoveBeats = new LinkedList<>();

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
    double lastTimestamp = 0;
    double lastBeat = 0;
    for (int i = 0; i < stepchart.getDataLength(); i++) {
      Arrows arrows = stepchart.getBasicArrows(i);
      double timestamp = stepchart.getTimestamp(i);
      double beat = stepchart.getBeat(i);
      if (arrows != Arrows.EMPTY) {
        updateFootPositions(arrows, timestamp - lastTimestamp, beat - lastBeat);
        updateHeading();
        updatePosition();

        lastTimestamp = timestamp;
        lastBeat = beat;
      }
    }
  }

  private void updateFootPositions(Arrows arrows, double timestamp, double beat) {
    if (arrows.arrowCount == 2
        && arrows.positions.contains(leftFoot)
        && arrows.positions.contains(rightFoot)) {
      // Jump in place
      jumpsInPlaceTimestamps.add(timestamp);
      jumpsInPlaceBeats.add(beat);
    } else if (arrows.arrowCount == 2
        && (arrows.positions.contains(leftFoot) || arrows.positions.contains(rightFoot))) {
      // Jump with one foot in place
      jumpsHalfInPlaceTimestamps.add(timestamp);
      jumpsHalfInPlaceBeats.add(beat);
      if (arrows.positions.contains(leftFoot)) {
        rightFoot = arrows.getJumpArrowsMinus(leftFoot);
        lastFootIsLeft = true;
      } else if (arrows.positions.contains(rightFoot)) {
        leftFoot = arrows.getJumpArrowsMinus(rightFoot);
        lastFootIsLeft = false;
      }
    } else if (arrows.arrowCount == 2) {
      // Jump, complete move
      jumpsFullMoveTimestamps.add(timestamp);
      jumpsFullMoveBeats.add(beat);
      Position arrowOne = arrows.getJumpArrow(false);
      Position arrowTwo = arrows.getJumpArrow(true);
      double distanceLeftToOne = arrowOne.distanceFrom(leftFoot) + arrowTwo.distanceFrom(rightFoot);
      double distanceLeftToTwo = arrowTwo.distanceFrom(leftFoot) + arrowOne.distanceFrom(rightFoot);
      if (distanceLeftToOne == distanceLeftToTwo) {
        double headingLeftToOne = Position.getHeading(arrowOne, arrowTwo);
        double headingLeftToTwo = Position.getHeading(arrowTwo, arrowOne);
        double deltaHeadingLeftToOne = headingLeftToOne - currentHeading;
        double deltaHeadingLeftToTwo = headingLeftToTwo - currentHeading;
        double netRotationLeftToOne = netRotation + deltaHeadingLeftToOne;
        double netRotationLeftToTwo = netRotation + deltaHeadingLeftToTwo;
        if (Math.abs(netRotationLeftToOne) <= Math.abs(netRotationLeftToTwo)) {
          leftFoot = arrowOne;
          rightFoot = arrowTwo;
        } else {
          leftFoot = arrowTwo;
          rightFoot = arrowOne;
        }
      } else if (distanceLeftToOne < distanceLeftToTwo) {
        leftFoot = arrowOne;
        rightFoot = arrowTwo;
      } else {
        leftFoot = arrowTwo;
        rightFoot = arrowOne;
      }
    } else if (arrows.arrowCount == 1
        && ((arrows.positions.contains(leftFoot) && lastFootIsLeft)
            || (arrows.positions.contains(rightFoot) && !lastFootIsLeft))) {
      // Steps repeated
      stepsRepeatedTimestamps.add(timestamp);
      stepsRepeatedBeats.add(beat);
    } else if (arrows.arrowCount == 1
        && (arrows.positions.contains(leftFoot) || arrows.positions.contains(rightFoot))) {
      // Steps marching
      stepsMarchingTimestamps.add(timestamp);
      stepsMarchingBeats.add(beat);
      if (arrows.positions.contains(leftFoot)) {
        lastFootIsLeft = true;
      } else if (arrows.positions.contains(rightFoot)) {
        lastFootIsLeft = false;
      }
    } else if (arrows.arrowCount == 1) {
      // Step, move a foot
      stepsMoveTimestamps.add(timestamp);
      stepsMoveBeats.add(beat);
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
    double deltaDistance =
        Math.sqrt((newX - currentX) * (newX - currentX) + (newY - currentY) * (newY - currentY));
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

  public int getJumpsInPlace() {
    return jumpsInPlaceTimestamps.size();
  }

  public int getJumpsHalfInPlace() {
    return jumpsHalfInPlaceTimestamps.size();
  }

  public int getJumpsFullMove() {
    return jumpsFullMoveTimestamps.size();
  }

  public int getStepsRepeated() {
    return stepsRepeatedTimestamps.size();
  }

  public int getStepsMarching() {
    return stepsMarchingTimestamps.size();
  }

  public int getStepsMove() {
    return stepsMoveTimestamps.size();
  }

  public double getMeanJumpsInPlaceTimestamps() {
    double sum = 0;
    int size = jumpsInPlaceTimestamps.size();
    if (size == 0) {
      return 0;
    }
    for (double d : jumpsInPlaceTimestamps) {
      sum += d;
    }
    return sum / size;
  }

  public double getStdDevJumpsInPlaceTimestamps() {
    int size = jumpsInPlaceTimestamps.size();
    if (size == 0) {
      return 0;
    }
    double mean = getMeanJumpsInPlaceTimestamps();
    double error = 0;
    for (double d : jumpsInPlaceTimestamps) {
      error += (d - mean) * (d - mean);
    }
    return Math.sqrt(error / size);
  }
}
