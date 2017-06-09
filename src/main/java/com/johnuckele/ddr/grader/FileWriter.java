package com.johnuckele.ddr.grader;

import java.io.IOException;
import java.io.PrintWriter;

public class FileWriter {

  public static void writeSongFeatureRows(String filename, Song song) throws IOException {
    writeLines(filename, generateFeatureRows(song));
  }

  private static void writeLines(String filename, String[] lines) throws IOException {
    System.out.println("Writing file: filename");
    PrintWriter out = new PrintWriter(filename);
    for (String line : lines) {
      out.println(line);
      System.out.println(line);
    }
    out.close();
  }

  private static String[] generateFeatureRows(Song song) {
    String[] lines = new String[song.stepcharts.size() + 1];

    lines[0] = Feature.getHeader();
    for (int i = 0; i < song.stepcharts.size(); i++) {
      lines[i + 1] = Feature.getRow(song, song.stepcharts.get(i));
    }
    return lines;
  }
}
