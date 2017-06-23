package com.johnuckele.ddr.grader;

public class Stepchart {
  // Variables for tracking features of a song
  // The number of arrows in a song, which is a measure of how many literal arrows the song has.
  // Usually steps + jumps, unless there are 3 foot jumps or something else funny going on.
  private int arrowCount = 0;
  private int jumpCount = 0;
  private int stepCount = 0;
  private double firstStepTimestamp = Double.MAX_VALUE;
  private double lastStepTimestamp = 0;

  // Variables to store the step chart
  public final StepchartMetadata stepchartMetadata;
  private int stepHead = 0;
  private final int dataLength;
  private Arrows[] basicArrows;
  private double[] timestamps;
  private double[] beats;

  // Dancer, to dance the stepchart
  private Dancer dancer;

  public Stepchart(StepchartMetadata stepchartMetadata, int maxStepCount) {
    this.stepchartMetadata = stepchartMetadata;
    // Create storage structures
    dataLength = maxStepCount;
    basicArrows = new Arrows[dataLength];
    timestamps = new double[dataLength];
    beats = new double[dataLength];
  }

  public void dance(Metadata metadata) {
    this.dancer = new Dancer();
    dancer.dance(metadata, this);
  }

  public void addStep(Arrows arrows, double timestamp, double beat) {
    // Add the step
    basicArrows[stepHead] = arrows;
    timestamps[stepHead] = timestamp;
    beats[stepHead] = beat;
    stepHead++;

    // Process arrows for arrow count / jumps / steps
    if (arrows.arrowCount > 0) {
      if (arrows.arrowCount > 1) {
        jumpCount++;
      }
      arrowCount += arrows.arrowCount;
      stepCount++;
    }

    // Update first / last step timestamps
    if (arrows.arrowCount > 0) {
      if (timestamp < firstStepTimestamp) {
        firstStepTimestamp = timestamp;
      }
      if (timestamp > lastStepTimestamp) {
        lastStepTimestamp = timestamp;
      }
    }
  }

  public Arrows getBasicArrows(int index) {
    return basicArrows[index];
  }

  public int getDataLength() {
    return dataLength;
  }

  public int getArrowCount() {
    return arrowCount;
  }

  public int getJumpCount() {
    return jumpCount;
  }

  public int getStepCount() {
    return stepCount;
  }

  public double getFirstArrowTimestamp() {
    return firstStepTimestamp;
  }

  public double getLastArrowTimestamp() {
    return lastStepTimestamp;
  }

  public Dancer getDancer() {
    return dancer;
  }
}
