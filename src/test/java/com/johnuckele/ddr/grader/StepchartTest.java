package com.johnuckele.ddr.grader;

import org.junit.Assert;
import org.junit.Test;

import com.johnuckele.ddr.grader.enums.Difficulty;
import com.johnuckele.ddr.grader.enums.PadLayout;

public class StepchartTest {
  @Test
  public void testJump() {
    StepchartMetadata stepchartMetadata =
        new StepchartMetadata(
            PadLayout.DOUBLE, Difficulty.EASY, 0, new double[] {0.0, 0.0, 0.0, 0.0, 0.0});
    Stepchart s = new Stepchart(stepchartMetadata, 2);
    s.addStep(Arrows.parseArrows("10010000"), 10, 1);
    s.addStep(Arrows.parseArrows("00001001"), 12, 2);
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
    s.addStep(Arrows.parseArrows("1000"), 1, 1);
    Assert.assertEquals(1, s.getArrowCount());
    Assert.assertEquals(1, s.getStepCount());
    Assert.assertEquals(0, s.getJumpCount());
  }
}
