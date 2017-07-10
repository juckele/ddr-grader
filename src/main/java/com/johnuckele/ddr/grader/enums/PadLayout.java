package com.johnuckele.ddr.grader.enums;

public enum PadLayout {
  SINGLE(4, "dance-single"),
  SOLO(6, "dance-solo"),
  DOUBLE(8, "dance-double"),
  COUPLE(8, "dance-couple"),
  ;

  public final int arrowCount;
  public final String name;

  private PadLayout(int arrowCount, String name) {
    this.arrowCount = arrowCount;
    this.name = name;
  }

  public static PadLayout parsePadLayout(String sourceString) {
    String lowercaseString = sourceString.toLowerCase();
    for (PadLayout value : PadLayout.values()) {
      if (lowercaseString.contains(value.name)) {
        return value;
      }
    }
    throw new IllegalStateException("Unable to map \"" + sourceString + "\" to a valid pad layout");
  }
}
