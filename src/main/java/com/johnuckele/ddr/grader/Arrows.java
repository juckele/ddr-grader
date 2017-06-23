package com.johnuckele.ddr.grader;

import com.google.common.collect.Sets;
import enums.Position;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Arrows {

  private static final Map<String, Arrows> parseMap = new HashMap<>();

  public static final Arrows EMPTY= getEmptyInstance();

  public final String parseString;
  public final int arrowCount;
  public final boolean isJump;
  public final Set<Position> positions;

  private static Arrows getEmptyInstance() {
    Arrows empty = new Arrows("");
//    if( EMPTY == null) {
//      EMPTY = ;
      parseMap.put("0000", empty);
      parseMap.put("00000000", empty);
  //  }
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
      throw new IllegalStateException(
          "Can't find the step for " + positions.size() + " arrows");
    }
    for (Position position : positions) {
        return position;
    }
    throw new IllegalStateException("This really can't happen");
  }

  public static Arrows parseArrows(String sourceString) {
    if (!parseMap.containsKey(sourceString)) {
      parseMap.put(sourceString, new Arrows(sourceString));
    }
    return parseMap.get(sourceString);
    
  }
}
