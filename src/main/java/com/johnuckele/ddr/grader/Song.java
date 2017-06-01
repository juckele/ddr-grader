package com.johnuckele.ddr.grader;

import java.util.LinkedList;
import java.util.List;

public class Song {
  public final Metadata metadata;
  public final List<Stepchart> stepcharts = new LinkedList<>();

  public Song(Metadata metadata) {
    this.metadata = metadata;
  }

  public void addStepchart(Stepchart stepchart) {
    stepcharts.add(stepchart);
  }
}
