package com.johnuckele.ddr.grader;


import org.junit.Assert;
import org.junit.Test;

public class ParserTest {
  @Test
  public void testParsingMetadata() {
    String[] lines = {"#TITLE:CANDY☆;", "#ARTIST:Luv UNLIMITED;"};
    Metadata metadata = FileParser.parseMetadata(lines);
    Assert.assertEquals("CANDY☆", metadata.name);
    Assert.assertEquals("Luv UNLIMITED", metadata.artist);
  }
}

