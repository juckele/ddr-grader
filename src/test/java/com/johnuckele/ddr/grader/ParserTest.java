package com.johnuckele.ddr.grader;

import org.junit.Assert;
import org.junit.Test;

public class ParserTest {
  @Test
  public void testParsingMetadataBpm() {
    String[] lines = {
      "#BPMS:0.000=302.521,4.000=300.070,290.000=214.286,291.000=145.161,292.000=104.651,293.000=64.286,294.000=300.131;"
    };
    Metadata metadata = FileParser.parseMetadata(lines);
    Assert.assertEquals(7, metadata.beatToBpms.size());
    Assert.assertEquals(302.521, metadata.beatToBpms.get(0.0), 0.001);
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
    Assert.assertEquals(1, metadata.beatToStops.get(3.5), 0.001);
    Assert.assertEquals(0.25, metadata.beatToStops.get(7.0), 0.001);
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
    String[] lines = {
      "NOTES:",
      "dance-single:" /*dance-double*/,
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
    Stepchart stepchart = FileParser.parseStepchart(lines);
    Assert.assertEquals(2, stepchart.getArrowCount());
  }

  @Test
  public void testSplitSections() {
    String[] lines = {"#TITLE:title;", "#ARTIST:artist;", "#NOTES:", "//SOME COMEENT", "#NOTES:"};
    String[][] sections = FileParser.splitSections(lines);
    Assert.assertEquals(3, sections.length);
    Assert.assertEquals(2, sections[1].length);
    Assert.assertEquals(1, sections[2].length);
  }
}
