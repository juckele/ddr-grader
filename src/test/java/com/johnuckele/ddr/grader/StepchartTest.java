package com.johnuckele.ddr.grader;

import org.junit.Assert;
import org.junit.Test;

public class StepchartTest {

  @Test
  public void testJump() {
    Stepchart s = new Stepchart(2);
    s.addStep(9, 10, 1);
    s.addStep(9, 12, 2);
    Assert.assertEquals(4, s.getArrowCount());
    Assert.assertEquals(2, s.getStepCount());
    Assert.assertEquals(2, s.getJumpCount());
  }

  @Test
  public void testStep() {
    Stepchart s = new Stepchart(1);
    s.addStep(1, 1, 1);
    Assert.assertEquals(1, s.getArrowCount());
    Assert.assertEquals(1, s.getStepCount());
    Assert.assertEquals(0, s.getJumpCount());
  }
}

