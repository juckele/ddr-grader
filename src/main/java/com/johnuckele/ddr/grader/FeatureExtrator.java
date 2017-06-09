package com.johnuckele.ddr.grader;

import java.util.Scanner;

public class FeatureExtrator {
  public static void main(String[] args) {
    try {
      // args = new String[] {"/tmp/cs.sm", "/tmp/cs.csv"};
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
      Song song = FileParser.parseFile(inputFile);
      FileWriter.writeSongFeatureRows(outputFile, song);
    } catch (Exception e) {
      System.err.println("Unexpected error, halting program.");
      e.printStackTrace();
    }
  }
}
