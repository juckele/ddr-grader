package com.johnuckele.ddr.grader;

import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Test;

public class ParserTest {
  @Test
  public void testParseLinesForEmptyLines() {
    String[] lines = {};
    Song song = FileParser.parseLines(lines);
    Assert.assertNull(song.metadata.name);
    Assert.assertNull(song.metadata.artist);
    Assert.assertEquals(0, song.stepcharts.size());
  }

  @Test
  public void testParseLinesForCompleteSong() {
    String[] lines = {
      "#TITLE:TEST SONG;",
      "#ARTIST:An Artist;",
      "#BPMS:0.000=15.000;",
      "#STOPS:60.000=1.0,120.000=1.0;",
      "#NOTES:",
      "dance-single:",
      ":",
      ":",
      ":",
      ",,,,:",
      "0000",
      "0000",
      "1001",
      "0000",
      ",",
      "1001",
      "0110",
      "0011",
      "0110",
      ",",
      "1100",
      "1010",
      "0011",
      "1001",
      ",",
      "0000",
      "1000",
      "0100",
      "0010",
      "0001",
      "0100",
      "0010",
      "1000",
      "0100",
      "0010",
      "0001",
      "0100",
      "0010",
      "1001",
      "0000",
      "0000",
      ";"
    };
    Song song = FileParser.parseLines(lines);
    Assert.assertEquals("TEST SONG", song.metadata.name);
    Assert.assertEquals("An Artist", song.metadata.artist);
    Assert.assertEquals(1, song.stepcharts.size());
    Assert.assertEquals(8, song.stepcharts.get(0).getFirstArrowTimestamp(), 0.0);
    Assert.assertEquals(61, song.stepcharts.get(0).getLastArrowTimestamp(), 0.0);
    Assert.assertEquals(32, song.stepcharts.get(0).getArrowCount(), 0.0);
    Assert.assertEquals(10, song.stepcharts.get(0).getJumpCount(), 0.0);
    Assert.assertEquals(22, song.stepcharts.get(0).getStepCount(), 0.0);
  }

  @Test
  public void testParsingMetadataBpm() {
    String[] lines = {
      "#BPMS:0.000=302.521,4.000=300.070,290.000=214.286,291.000=145.161,292.000=104.651,293.000=64.286,294.000=300.131;"
    };
    Metadata metadata = FileParser.parseMetadata(lines);
    Assert.assertEquals(7, metadata.beatToBpms.size());
    Assert.assertEquals(302.521, metadata.beatToBpms.get(0.0), 0.0);
    Assert.assertEquals(300.070, metadata.beatToBpms.get(4.0), 0.001);
    Assert.assertEquals(214.286, metadata.beatToBpms.get(290.0), 0.001);
    Assert.assertEquals(145.161, metadata.beatToBpms.get(291.0), 0.001);
    Assert.assertEquals(104.651, metadata.beatToBpms.get(292.0), 0.001);
    Assert.assertEquals(64.286, metadata.beatToBpms.get(293.0), 0.001);
    Assert.assertEquals(300.131, metadata.beatToBpms.get(294.0), 0.001);
  }

  @Test
  public void testParsingMetadataStops() {
    String[] lines = {"#STOPS:3.500=1.0,7=0.25;"};
    Metadata metadata = FileParser.parseMetadata(lines);
    Assert.assertEquals(2, metadata.beatToStops.size());
    Assert.assertEquals(1, metadata.beatToStops.get(3.5), 0.0);
    Assert.assertEquals(0.25, metadata.beatToStops.get(7.0), 0.0);
  }
  
  @Test
  public void testParsingMetadataTitleAndArtist() {
    String[] lines = {"#TITLE:CANDY☆;", "#ARTIST:Luv UNLIMITED;"};
    Metadata metadata = FileParser.parseMetadata(lines);
    Assert.assertEquals("CANDY☆", metadata.name);
    Assert.assertEquals("Luv UNLIMITED", metadata.artist);
  }

  @Test
  public void testParsingStepChart() {
    Metadata metadata = new Metadata();
    metadata.beatToBpms = ImmutableMap.of(0.0, 120.0);
    metadata.beatToStops = ImmutableMap.of();
    String[] lines = {
      "#NOTES:",
      "dance-single:",
      ":",
      "Beginner:",
      "5:",
      "0.134,0.174,0.011,0.000,0.000:",
      "0000",
      "0000",
      "0000",
      "0000",
      ",",
      "0000",
      "0000",
      "0100",
      "0010",
      ";"
    };
    Stepchart stepchart = FileParser.parseStepchart(metadata, lines);
    Assert.assertEquals(2, stepchart.getArrowCount());
    Assert.assertEquals(PadLayout.SINGLE, stepchart.stepchartMetadata.padLayout);
    Assert.assertEquals(3, stepchart.getFirstArrowTimestamp(), 0.0);
    Assert.assertEquals(3.5, stepchart.getLastArrowTimestamp(), 0.0);
  }

  @Test
  public void testParsingStepChartForDoubles() {
    Metadata metadata = new Metadata();
    metadata.beatToBpms = ImmutableMap.of(0.0, 120.0);
    metadata.beatToStops = ImmutableMap.of();
    String[] lines = {"#NOTES:", "dance-double:", ":", "Expert:", "13:", "0.0,0.0,0.0,0.0,0.0:", ";"};
    Stepchart stepchart = FileParser.parseStepchart(metadata, lines);
    Assert.assertEquals(PadLayout.DOUBLE, stepchart.stepchartMetadata.padLayout);
  }

  @Test
  public void testSplitSections() {
    String[] lines = {"#TITLE:title;", "#ARTIST:artist;", "#NOTES:", "//SOME COMEENT", "#NOTES:"};
    String[][] sections = FileParser.splitSections(lines);
    Assert.assertEquals(3, sections.length);
    Assert.assertEquals(2, sections[1].length);
    Assert.assertEquals(1, sections[2].length);
  }

  @Test
  public void testParsingStepMetadata() {
    String[] lines = {
      "#NOTES:",
      "dance-single:",
      "Description that we discard:",
      "Beginner:",
      "5:",
      "0.134,0.174,0.011,0.001,0.000:",
      "Note data goes here, but it being bad shouldn't hard metadata parsing",
      ";"
    };
    StepchartMetadata metadata = FileParser.parseStepchartMetadata(lines);
    Assert.assertEquals(PadLayout.SINGLE, metadata.padLayout);
    Assert.assertEquals(Difficulty.BEGINNER, metadata.difficulty);
    Assert.assertEquals(5, metadata.rating);
    Assert.assertEquals(0.134, metadata.getStream(), 0.0);
    Assert.assertEquals(0.174, metadata.getVoltage(), 0.0);
    Assert.assertEquals(0.011, metadata.getAir(), 0.0);
    Assert.assertEquals(0.001, metadata.getFreeze(), 0.0);
    Assert.assertEquals(0.000, metadata.getChaos(), 0.0);
  }
}
