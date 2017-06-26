package enums;

import com.johnuckele.ddr.grader.Song;
import com.johnuckele.ddr.grader.Stepchart;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum Feature {
  NAME("Name") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      {
        return song.metadata.name;
      }
    }
  },
  TITLE("Title") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return song.metadata.artist;
    }
  },
  PAD_LAYOUT("Pad Layout") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return chart.stepchartMetadata.padLayout.name;
    }
  },
  DIFFICULTY("Difficulty") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return chart.stepchartMetadata.difficulty.name;
    }
  },
  RATING("Rating") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.stepchartMetadata.rating;
    }
  },
  ARROW_COUNT("Arrow Count") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getArrowCount();
    }
  },
  JUMP_COUNT("Jump Count") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getJumpCount();
    }
  },
  STEP_COUNT("Step Count") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getStepCount();
    }
  },
  DURATION("Duration") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + (chart.getLastArrowTimestamp() - chart.getFirstArrowTimestamp());
    }
  },
  STOP_COUNT("Stop Count") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + song.getStopCount();
    }
  },
  MIN_BPM("Min BPM") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + song.getMinBpm();
    }
  },
  MAX_BPM("Max BPM") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + song.getMaxBpm();
    }
  },
  ABSOLUTE_ROTATION("Absolute Rotation") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getAbsoluteRotation();
    }
  },
  NET_ROTATION("Net Rotation") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getNetRotation();
    }
  },
  ABSOLUTE_DISTANCE("Absolute Distance") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getAbsoluteDistance();
    }
  },  
  JUMPS_IN_PLACE("Jumps In Place") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getJumpsInPlace();
    }
  },  
  JUMPS_HALF_IN_PLACE("Jumps Half In Place") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getJumpsHalfInPlace();
    }
  },  
  JUMPS_FULL_MOVE("Jumps Full Move") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getJumpsFullMove();
    }
  },  
  STEPS_IN_PLACE("Steps In Place") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getStepsInPlace();
    }
  },  
  STEPs_MOVE("Steps Move") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getStepsMove();
    }
  },  
  ;

  public final String label;

  Feature(String label) {
    this.label = label;
  }

  public abstract String getValue(Song song, Stepchart chart);

  public static String getHeader() {
    return Arrays.stream(Feature.values()).map(i -> i.label).collect(Collectors.joining(","));
  }

  public static String getRow(Song song, Stepchart stepchart) {
    return Arrays.stream(Feature.values())
        .map(i -> i.getValue(song, stepchart))
        .collect(Collectors.joining(","));
  }
}
