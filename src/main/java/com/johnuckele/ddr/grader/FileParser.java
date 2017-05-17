package com.johnuckele.ddr.grader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileParser {

  public static String[][] splitSections(String[] lines) {
    // Count how many step charts are in the file, plus one for the header
    int sections = 1;
    for(String line:lines) {
      if(line.startsWith("#NOTES:")) {
        sections += 1;
      }
    }
    String[][] sectionLines = new String[sections][];

    // Split the headers and step charts into separate arrays
    int sectionStart = 0;
    int sectionEnd = 0;
    int lineIndex = 0;
    int sectionIndex = 0;
    for(String line:lines) {
      if(line.startsWith("#NOTES:")) {
        sectionEnd = lineIndex;
        sectionLines[sectionIndex] = Arrays.copyOfRange(lines, sectionStart, sectionEnd);
        sectionStart = sectionEnd;
        sectionIndex++;
      }
      lineIndex++;
    }

    return sectionLines;
  }

  public static Song parseLines(String[] lines) {
    Metadata metadata = parseMetadata(lines);
    Song song = new Song(metadata);
    return song;
  }

  public static Metadata parseMetadata(String[] lines) {
    Metadata metadata = new Metadata();
    for(String line : lines) {
      if(line.startsWith("#TITLE:")) {
        metadata.name = line.substring(7, line.length()-1);
      }
      else if(line.startsWith("#ARTIST:")) {
        metadata.artist = line.substring(8, line.length()-1);
      }
      else if(line.startsWith("#BPMS:")) {
        // TODO: write this
      }
      else if(line.startsWith("#STOPS:")) {
        // TODO: write this
      }
    }
    return metadata;
  }

  public static String[] readFile(String filename) throws IOException {
    Path filepath = Paths.get(filename);
    List<String> lines = Files.readAllLines(filepath, Charset.forName("UTF_8"));
    return lines.toArray(new String[lines.size()]);
  }
  
  public static Song parseFile(String filename) throws IOException {
    return parseLines(readFile(filename));
  }
}

