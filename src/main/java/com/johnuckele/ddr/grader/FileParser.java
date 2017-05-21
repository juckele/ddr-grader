package com.johnuckele.ddr.grader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class FileParser {
  private static final int MINIBEATS_PER_BEAT = 48;
  
  public static Song parseFile(String filename) throws IOException {
    return parseLines(readFile(filename));
  }

  public static Song parseLines(String[] lines) {
    Metadata metadata = parseMetadata(lines);
    Song song = new Song(metadata);
    return song;
  }

  public static Stepchart parseStepchart(String[] lines) {
    // Create a stepchart with the correct arraysize
    int eventCount = 0;
    for(String line : lines) {
      if(StringUtils.isNumeric(line) && (line.length() == 4 || line.length() == 8)) {
        eventCount++;
      }
    }
    Stepchart chart = new Stepchart(eventCount);

    int minibeat = 0; // A minibeat is 1/48th of a beat, which
    // is the smallest quantized time that is available in Stepmania. An integer
    // is used here instead of a floating point to ensure that time math has as few
    // rounding errors as possible
    List<String> measure = new LinkedList<>();
    for(String line: lines) {
      // Add all lines that are just numbers to the measure
      if(StringUtils.isNumeric(line) && (line.length() == 4 || line.length() == 8)) {
        measure.add(line);
      }
      // we've hit the end of a measure
      else {
        // process the measure
        for(String arrowLine : measure) {
          minibeat += MINIBEATS_PER_BEAT/(measure.size()/4);
          int arrows = 0;
          for(int arrowIndex = 0; arrowIndex<arrowLine.length(); arrowIndex++) {
            char arrow = arrowLine.charAt(arrowIndex);
            if(arrow == '1') {
              arrows += 1 << arrowIndex;
            }
          }
          chart.addStep(arrows, minibeat/(double)MINIBEATS_PER_BEAT, minibeat/(double)MINIBEATS_PER_BEAT);
        }
        // flush the measure
        measure.clear();
      }
    }
    
    return chart;
  }
  public static Metadata parseMetadata(String[] lines) {
    Metadata metadata = new Metadata();
    for (String line : lines) {
      if (line.startsWith("#TITLE:")) {
        metadata.name = line.substring(7, line.length() - 1);
      } else if (line.startsWith("#ARTIST:")) {
        metadata.artist = line.substring(8, line.length() - 1);
      } else if (line.startsWith("#BPMS:")) {
        String bpms = line.substring(6, line.length() - 1);
        String[] bpmPairs = bpms.split(",");
        Map<Double, Double> beatToBpmMap = new HashMap<>();
        for(String bpmPair :bpmPairs) {
          String[] chunks = bpmPair.split("=");
          beatToBpmMap.put(Double.parseDouble(chunks[0]), Double.parseDouble(chunks[1]));
        }
        metadata.beatToBpms = beatToBpmMap;
      } else if (line.startsWith("#STOPS:")) {
        String stops = line.substring(7, line.length() - 1);
        String[] stopPairs = stops.split(",");
        Map<Double, Double> beatToStopMap = new HashMap<>();
        for(String stopPair :stopPairs) {
          String[] chunks = stopPair.split("=");
          beatToStopMap.put(Double.parseDouble(chunks[0]), Double.parseDouble(chunks[1]));
        }
        metadata.beatToStops = beatToStopMap;
      }
    }
    return metadata;
  }

  public static String[] readFile(String filename) throws IOException {
    Path filepath = Paths.get(filename);
    List<String> lines = Files.readAllLines(filepath, Charset.forName("UTF_8"));
    return lines.toArray(new String[lines.size()]);
  }

  public static String[][] splitSections(String[] lines) {
    // Count how many step charts are in the file, plus one for the header
    int sections = 1;
    for (String line : lines) {
      if (line.startsWith("#NOTES:")) {
        sections += 1;
      }
    }
    String[][] sectionLines = new String[sections][];

    // Split the headers and step charts into separate arrays
    int sectionStart = 0;
    int sectionEnd = 0;
    int lineIndex = 0;
    int sectionIndex = 0;
    for (String line : lines) {
      if (line.startsWith("#NOTES:")) {
        sectionEnd = lineIndex;
        sectionLines[sectionIndex] = Arrays.copyOfRange(lines, sectionStart, sectionEnd);
        sectionStart = sectionEnd;
        sectionIndex++;
      }
      lineIndex++;
    }
    sectionEnd = lineIndex;
    sectionLines[sectionIndex] = Arrays.copyOfRange(lines, sectionStart, sectionEnd);

    return sectionLines;
  }
}
