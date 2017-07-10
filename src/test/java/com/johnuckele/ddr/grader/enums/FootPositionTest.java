package com.johnuckele.ddr.grader.enums;

import org.junit.Assert;
import org.junit.Test;

public class FootPositionTest {
  @Test
  public void testGetHeadingNorth() {
    double north = Position.getHeading(Position.PAD_ONE_LEFT, Position.PAD_ONE_RIGHT);
    Assert.assertEquals(0, north, 0);
  }

  @Test
  public void testGetHeadingEast() {
    double east = Position.getHeading(Position.PAD_ONE_TOP, Position.PAD_ONE_BOTTOM);
    Assert.assertEquals(Math.PI / 2, east, 0);
  }

  @Test
  public void testGetHeadingSouth() {
    double south = Position.getHeading(Position.PAD_ONE_RIGHT, Position.PAD_ONE_LEFT);
    Assert.assertEquals(Math.PI, south, 0);
  }

  @Test
  public void testGetHeadingWest() {
    double west = Position.getHeading(Position.PAD_ONE_BOTTOM, Position.PAD_ONE_TOP);
    Assert.assertEquals(Math.PI * 3 / 2, west, 0);
  }

  @Test
  public void testGetHeadingSecondaryDirections() {
    double northEast = Position.getHeading(Position.PAD_ONE_TOP, Position.PAD_ONE_RIGHT);
    Assert.assertEquals(Math.PI * 1 / 4, northEast, 0);
    double northWest = Position.getHeading(Position.PAD_ONE_LEFT, Position.PAD_ONE_TOP);
    Assert.assertEquals(Math.PI * 7 / 4, northWest, 0);
    double southEast = Position.getHeading(Position.PAD_ONE_RIGHT, Position.PAD_ONE_BOTTOM);
    Assert.assertEquals(Math.PI * 3 / 4, southEast, 0);
    double southWest = Position.getHeading(Position.PAD_ONE_BOTTOM, Position.PAD_ONE_LEFT);
    Assert.assertEquals(Math.PI * 5 / 4, southWest, 0);
  }
}
