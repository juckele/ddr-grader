package com.johnuckele.ddr.grader;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {
  @Test
  public void testBeatToTimeSlowTempo() {
    Map<Double, Double> beatsToBpms = ImmutableMap.of(0.0, 60.0);
    Map<Double, Double> beatsToStops = ImmutableMap.of();
    Assert.assertEquals(0, Utils.beatToTime(0, beatsToBpms, beatsToStops), 0);
    Assert.assertEquals(7.5, Utils.beatToTime(7.5, beatsToBpms, beatsToStops), 0);
    Assert.assertEquals(10, Utils.beatToTime(10, beatsToBpms, beatsToStops), 0);
  }

  @Test
  public void testBeatToTimeFastTempo() {
    Map<Double, Double> beatsToBpms = ImmutableMap.of(0.0, 300.0);
    Map<Double, Double> beatsToStops = ImmutableMap.of();
    Assert.assertEquals(6, Utils.beatToTime(30, beatsToBpms, beatsToStops), 0);
    Assert.assertEquals(15, Utils.beatToTime(75, beatsToBpms, beatsToStops), 0);
  }

  @Test
  public void testBeatToWithTempoChange() {
    Map<Double, Double> beatsToBpms = ImmutableMap.of(0.0, 120.0, 30.0, 240.0);
    Map<Double, Double> beatsToStops = ImmutableMap.of();
    Assert.assertEquals(7.5, Utils.beatToTime(15, beatsToBpms, beatsToStops), 0);
    Assert.assertEquals(15, Utils.beatToTime(30, beatsToBpms, beatsToStops), 0);
    Assert.assertEquals(22.5, Utils.beatToTime(60, beatsToBpms, beatsToStops), 0);
  }

  @Test
  public void testBeatToTimeWithStop() {
    Map<Double, Double> beatsToBpms = ImmutableMap.of(0.0, 120.0);
    Map<Double, Double> beatsToStops = ImmutableMap.of(20.0, 1.0, 40.0, 2.0);
    Assert.assertEquals(10, Utils.beatToTime(20, beatsToBpms, beatsToStops), 0);
    Assert.assertEquals(21, Utils.beatToTime(40, beatsToBpms, beatsToStops), 0);
    Assert.assertEquals(23.5, Utils.beatToTime(41, beatsToBpms, beatsToStops), 0);
  }

  @Test
  public void testBeatToTimeWithMidBeatTempoChange() {
    Map<Double, Double> beatsToBpms = ImmutableMap.of(0.0, 15.0, 7.5, 30.0, 22.5, 60.0);
    Map<Double, Double> beatsToStops = ImmutableMap.of();
    Assert.assertEquals(1, Utils.beatToTime(0.25, beatsToBpms, beatsToStops), 0);
    Assert.assertEquals(28, Utils.beatToTime(7, beatsToBpms, beatsToStops), 0);
    Assert.assertEquals(60, Utils.beatToTime(22.5, beatsToBpms, beatsToStops), 0);
    Assert.assertEquals(90, Utils.beatToTime(52.5, beatsToBpms, beatsToStops), 0);
    Assert.assertEquals(100, Utils.beatToTime(62.5, beatsToBpms, beatsToStops), 0);
  }

  @Test
  public void testBeatToStopsAndTempoChanges() {
    Map<Double, Double> beatsToBpms = ImmutableMap.of(0.0, 200.0, 20.0, 100.0, 60.0, 200.0);
    Map<Double, Double> beatsToStops = ImmutableMap.of(20.0, 0.5, 40.0, 0.5, 60.0, 0.5);
    Assert.assertEquals(6, Utils.beatToTime(20, beatsToBpms, beatsToStops), 0);
    Assert.assertEquals(12.5, Utils.beatToTime(30, beatsToBpms, beatsToStops), 0);
    Assert.assertEquals(18.5, Utils.beatToTime(40, beatsToBpms, beatsToStops), 0);
    Assert.assertEquals(31, Utils.beatToTime(60, beatsToBpms, beatsToStops), 0);
    Assert.assertEquals(37.5, Utils.beatToTime(80, beatsToBpms, beatsToStops), 0);
  }
}
