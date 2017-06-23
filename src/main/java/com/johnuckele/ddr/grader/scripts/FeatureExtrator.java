package com.johnuckele.ddr.grader.scripts;

import com.johnuckele.ddr.grader.FileParser;
import com.johnuckele.ddr.grader.FileWriter;
import com.johnuckele.ddr.grader.Song;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FeatureExtrator {

  public static List<Song> parseSongs(File inputFile) throws IOException {
    List<Song> songs = new LinkedList<>();
    // Directory, recur
    if (inputFile.isDirectory()) {
      System.out.println("Exploring directory " + inputFile.getPath());
      for (File subfile : inputFile.listFiles()) {
        songs.addAll(parseSongs(subfile));
      }
    }
    // File, with .sm suffix, parse the file
    else if (inputFile.exists() && inputFile.getName().endsWith(".sm")) {
      System.out.println("Parsing file " + inputFile.getPath());
      Song song = FileParser.parseFile(inputFile.getPath());
      songs.add(song);
    }
    // Anything else, don't parse
    else {
      System.out.println("Not parsing file " + inputFile.getPath());
    }
    return songs;
  }

  public static void main(String[] args) {
    try {
      // Process user input
      args = new String[] {"/tmp/cs.sm", "/tmp/ddr.csv"};
      String inputFile;
      String outputFile;
      if (args.length == 2) {
        inputFile = args[0];
        outputFile = args[1];
      } else {
        Scanner s = new Scanner(System.in);
        System.out.print("Please enter input file path: ");
        inputFile = s.nextLine();
        System.out.print("Please enter output file path: ");
        outputFile = s.nextLine();
        s.close();
      }

      // Parse songs
      List<Song> songs = parseSongs(new File(inputFile));

      // Write output file
      FileWriter.writeSongFeatureRows(outputFile, songs);
    } catch (Exception e) {
      System.err.println("Unexpected error, halting program.");
      e.printStackTrace();
    }
  }
}
