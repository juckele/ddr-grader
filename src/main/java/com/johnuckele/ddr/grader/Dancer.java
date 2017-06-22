package com.johnuckele.ddr.grader;

public class Dancer {
  public void dance(Metadata metadata, Stepchart stepchart) {
    System.out.println("We can dance if you want to!");
    System.out.println("  Dancing to " + metadata.name);
    System.out.println(
        "  Dancing on "
            + stepchart.stepchartMetadata.difficulty.name
            + ", "
            + stepchart.stepchartMetadata.padLayout.name);
  }
}
