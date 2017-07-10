package com.johnuckele.ddr.grader;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.johnuckele.ddr.grader.enums.Position;

public class Arrows {

  private static final Map<String, Arrows> parseMap = new HashMap<>();

  public static final Arrows EMPTY = getEmptyInstance();

  public final String parseString;
  public final int arrowCount;
  public final boolean isJump;
  public final Set<Position> positions;

  private static Arrows getEmptyInstance() {
    Arrows empty = new Arrows("");
    parseMap.put("0000", empty);
    parseMap.put("00000000", empty);
    return empty;
  }

  private Arrows(String parseString) {
    this.parseString = parseString;

    // Build positions
    Collection<Position> workingPositions = new LinkedList<>();
    for (int arrowIndex = 0; arrowIndex < parseString.length(); arrowIndex++) {
      char arrow = parseString.charAt(arrowIndex);
      if (arrow == '1') {
        workingPositions.add(Position.getPositionAtIndex(arrowIndex));
      }
    }
    this.positions = Sets.immutableEnumSet(workingPositions);

    arrowCount = positions.size();
    isJump = arrowCount > 1 ? true : false;
  }

  public Position getJumpArrowsMinus(Position fixedPosition) {
    if (positions.size() != 2) {
      throw new IllegalStateException(
          "Can't find the other foot of a jump on " + positions.size() + " arrows");
    }
    for (Position position : positions) {
      if (position != fixedPosition) {
        return position;
      }
    }
    throw new IllegalStateException("This really can't happen");
  }

  public Position getStepPosition() {
    if (positions.size() != 1) {
      throw new IllegalStateException("Can't find the step for " + positions.size() + " arrows");
    }
    for (Position position : positions) {
      return position;
    }
    throw new IllegalStateException("This really can't happen");
  }

  public Position getJumpArrow(boolean selectMode) {
    if (positions.size() != 2) {
      throw new IllegalStateException(
          "Can't find the first arrow for " + positions.size() + " arrows");
    }
    int positionIndex = 0;
    Position[] foundPositions = new Position[2];
    for (Position position : positions) {
      foundPositions[positionIndex++] = position;
    }
    boolean findMode = foundPositions[0].compareTo(foundPositions[1]) > 0;
    if(findMode == selectMode) {
      return foundPositions[0];
    }
    else {
      return foundPositions[1];
    }
  }

  public static Arrows parseArrows(String sourceString) {
    if (!parseMap.containsKey(sourceString)) {
      parseMap.put(sourceString, new Arrows(sourceString));
    }
    return parseMap.get(sourceString);
  }
}
