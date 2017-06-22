package com.johnuckele.ddr.grader;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileWriter {

  public static void writeSongFeatureRows(String filename, List<Song> songs) throws IOException {
    writeLines(filename, songs);
  }

  public static void writeSongFeatureRows(String filename, Song song) throws IOException {
    writeLines(filename, ImmutableList.of(song));
  }

  private static void writeLines(String filename, List<Song> songs) throws IOException {
    System.out.println("Writing file: filename");
    PrintWriter out = new PrintWriter(filename);

    // Write the header
    out.println(Feature.getHeader());
    System.out.println(Feature.getHeader());

    // Write feature rows
    for (Song song : songs) {
      for (Stepchart stepchart : song.stepcharts) {
        String row = Feature.getRow(song, stepchart);
        out.println(row);
        System.out.println(row);
      }
    }
    out.close();
  }
}
