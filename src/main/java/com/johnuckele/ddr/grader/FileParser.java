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

  public static String[] readFile(String filename) throws IOException {
    Path filepath = Paths.get(filename);
    List<String> lines = Files.readAllLines(filepath, Charset.forName("UTF-8"));
    return lines.toArray(new String[lines.size()]);
  }

  public static Song parseLines(String[] lines) {
    String[][] lineSections = splitSections(lines);
    Metadata metadata = parseMetadata(lineSections[0]);
    Song song = new Song(metadata);
    for (int i = 1; i < lineSections.length; i++) {
      song.addStepchart(parseStepchart(metadata, lineSections[i]));
    }
    return song;
  }

  public static String[][] splitSections(String[] lines) {
    // Count how many step charts are in the file, plus one for the header
    int sections = 1;
    for (String line : lines) {
      if (line.startsWith("#NOTES")) {
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
      if (line.startsWith("#NOTES")) {
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

  public static Metadata parseMetadata(String[] lines) {
    System.out.println("Parsing Metadata");
    Metadata metadata = new Metadata();
    for (String line : lines) {
      line = StringUtils.trim(line);
      if (line.contains("#TITLE:")) {
        metadata.name = line.substring(line.indexOf("#TITLE:") + 7, line.length() - 1);
      } else if (line.contains("#ARTIST:")) {
        metadata.artist = line.substring(line.indexOf("#ARTIST:") + 8, line.length() - 1);
      } else if (line.startsWith("#BPMS:")) {
        String bpms = line.substring(6, line.length() - 1);
        String[] bpmPairs = bpms.split(",");
        Map<Double, Double> beatToBpmMap = new HashMap<>();
        for (String bpmPair : bpmPairs) {
          String[] chunks = bpmPair.split("=");
          beatToBpmMap.put(Double.parseDouble(chunks[0]), Double.parseDouble(chunks[1]));
        }
        metadata.beatToBpms = beatToBpmMap;
      } else if (line.startsWith("#STOPS:")) {
        String stops = line.substring(7, line.length() - 1);
        String[] stopPairs = stops.split(",");
        Map<Double, Double> beatToStopMap = new HashMap<>();
        if (stopPairs.length > 1 || StringUtils.isNotEmpty(stopPairs[0])) {
          for (String stopPair : stopPairs) {
            String[] chunks = stopPair.split("=");
            beatToStopMap.put(Double.parseDouble(chunks[0]), Double.parseDouble(chunks[1]));
          }
        }
        metadata.beatToStops = beatToStopMap;
      } else {
        System.out.println("Ignoring metadata line: " + line);
      }
    }
    return metadata;
  }

  public static Stepchart parseStepchart(Metadata metadata, String[] lines) {
    // Parse stepchart metadata
    StepchartMetadata stepchartMetadata = parseStepchartMetadata(lines);

    // Create a stepchart with the correct arraysize
    int eventCount = 0;
    for (String line : lines) {
      if (StringUtils.isNumeric(line) && (line.length() == 4 || line.length() == 8)) {
        eventCount++;
      }
    }
    Stepchart chart = new Stepchart(stepchartMetadata, eventCount);

    int minibeat = 0; // A minibeat is 1/48th of a beat, which
    // is the smallest quantized time that is available in Stepmania. An integer
    // is used here instead of a floating point to ensure that time math has as few
    // rounding errors as possible
    List<String> measure = new LinkedList<>();
    for (String line : lines) {
      // Add all lines that are just numbers to the measure
      if (StringUtils.isNumeric(line) && (line.length() == 4 || line.length() == 8)) {
        measure.add(line);
      }
      // we've hit the end of a measure
      else {
        // process the measure
        for (String arrowLine : measure) {
          int arrows = 0;
          for (int arrowIndex = 0; arrowIndex < arrowLine.length(); arrowIndex++) {
            char arrow = arrowLine.charAt(arrowIndex);
            if (arrow == '1') {
              arrows += 1 << arrowIndex;
            }
          }
          double beatAtStep = minibeat / (double) MINIBEATS_PER_BEAT;
          double timeAtStep =
              Utils.beatToTime(beatAtStep, metadata.beatToBpms, metadata.beatToStops);
          /*          System.out.println(
          "Parsing line: "
              + arrowLine
              + " @ beatAtStep "
              + beatAtStep
              + " / @ timeAtStep "
              + timeAtStep);*/
          chart.addStep(arrows, timeAtStep, beatAtStep);
          minibeat += MINIBEATS_PER_BEAT / (measure.size() / 4);
        }
        // flush the measure
        measure.clear();
      }
    }

    return chart;
  }

  public static StepchartMetadata parseStepchartMetadata(String[] lines) {
    String[] stepchartParts = String.join("", lines).split(":");
    PadLayout padLayout = PadLayout.parsePadLayout(stepchartParts[1]);
    Difficulty difficulty = Difficulty.parseDifficulty(stepchartParts[3]);
    int rating =
        StringUtils.isEmpty(stepchartParts[4])
            ? 0
            : Integer.parseInt(StringUtils.strip(stepchartParts[4]));
    double[] grooveRadar = new double[5];
    int grooveRadarIndex = 0;
    for (String grooveRadarElement : stepchartParts[5].split(",")) {
      grooveRadar[grooveRadarIndex++] = Double.parseDouble(grooveRadarElement);
    }
    StepchartMetadata stepchartMetadata =
        new StepchartMetadata(padLayout, difficulty, rating, grooveRadar);
    return stepchartMetadata;
  }
}
