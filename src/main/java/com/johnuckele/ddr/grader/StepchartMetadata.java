package com.johnuckele.ddr.grader;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.ImmutableList;
import com.johnuckele.ddr.grader.enums.Difficulty;
import com.johnuckele.ddr.grader.enums.PadLayout;

public class StepchartMetadata {

  public final PadLayout padLayout;
  public final Difficulty difficulty;
  public final int rating;
  public final List<Double> grooveRadar;

  public StepchartMetadata(
      PadLayout padLayout, Difficulty difficulty, int rating, double[] grooveRadar) {
    this.padLayout = padLayout;
    this.difficulty = difficulty;
    this.rating = rating;
    this.grooveRadar = ImmutableList.copyOf(ArrayUtils.toObject(grooveRadar));
  }

  public double getStream() {
    return grooveRadar.get(0);
  }

  public double getVoltage() {
    return grooveRadar.get(1);
  }

  public double getAir() {
    return grooveRadar.get(2);
  }

  public double getFreeze() {
    return grooveRadar.get(3);
  }

  public double getChaos() {
    return grooveRadar.get(4);
  }
}
