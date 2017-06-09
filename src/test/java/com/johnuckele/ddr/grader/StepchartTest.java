package com.johnuckele.ddr.grader;

import org.junit.Assert;
import org.junit.Test;

public class StepchartTest {
  @Test
  public void testJump() {
    StepchartMetadata stepchartMetadata =
        new StepchartMetadata(
            PadLayout.DOUBLE, Difficulty.EASY, 0, new double[] {0.0, 0.0, 0.0, 0.0, 0.0});
    Stepchart s = new Stepchart(stepchartMetadata, 2);
    s.addStep(9, 10, 1);
    s.addStep(9, 12, 2);
    Assert.assertEquals(4, s.getArrowCount());
    Assert.assertEquals(2, s.getStepCount());
    Assert.assertEquals(2, s.getJumpCount());
  }

  @Test
  public void testStep() {
    StepchartMetadata stepchartMetadata =
        new StepchartMetadata(
            PadLayout.SINGLE, Difficulty.EASY, 2, new double[] {0.0, 0.0, 0.0, 0.0, 0.0});
    Stepchart s = new Stepchart(stepchartMetadata, 1);
    s.addStep(1, 1, 1);
    Assert.assertEquals(1, s.getArrowCount());
    Assert.assertEquals(1, s.getStepCount());
    Assert.assertEquals(0, s.getJumpCount());
  }
}
