package com.johnuckele.ddr.grader;

import java.util.LinkedList;
import java.util.List;

public class Song {
  public final Metadata metadata;
  public final List<Stepchart> stepcharts = new LinkedList<>();

  public Song(Metadata metadata) {
    this.metadata = metadata;
  }

  public void dance() {
    for (Stepchart stepchart : stepcharts) {
      stepchart.dance(metadata);
    }
  }

  public void addStepchart(Stepchart stepchart) {
    stepcharts.add(stepchart);
  }

  public int getStopCount() {
    return metadata.beatToStops.size();
  }

  public double getMinBpm() {
    double minBpm = Double.MAX_VALUE;
    for (double bpm : metadata.beatToBpms.values()) {
      if (bpm < minBpm) {
        minBpm = bpm;
      }
    }
    return minBpm;
  }

  public double getMaxBpm() {
    double maxBpm = Double.MIN_VALUE;
    for (double bpm : metadata.beatToBpms.values()) {
      if (bpm > maxBpm) {
        maxBpm = bpm;
      }
    }
    return maxBpm;
  }
}
