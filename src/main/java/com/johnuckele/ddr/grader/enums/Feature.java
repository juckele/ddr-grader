package com.johnuckele.ddr.grader.enums;

import com.johnuckele.ddr.grader.Song;
import com.johnuckele.ddr.grader.Stepchart;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum Feature {
  // Stepchart ID
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

  // Foot rating
  RATING("Rating") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.stepchartMetadata.rating;
    }
  },

  // Basic arrow counting
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

  // Timing features
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

  // Dancer physics simulation features
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

  // Step counts, by motion type as determined by virtual dancer
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
  STEPS_REPEATED("Steps Repeated") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getStepsRepeated();
    }
  },
  STEPS_MARCHING("Steps Marching") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getStepsMarching();
    }
  },
  STEPs_MOVE("Steps Move") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getStepsMove();
    }
  },

  // Jumps In Place Timing Features
  MEAN_JUMPS_IN_PLACE_SEC("Mean Seconds Before: Jumps In Place") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getMeanJumpsInPlaceTimestamps();
    }
  },
  STDDEV_JUMPS_IN_PLACE_SEC("StdDev Seconds Before: Jumps In Place") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getStdDevJumpsInPlaceTimestamps();
    }
  },
  MEAN_JUMPS_IN_PLACE_BEAT("Mean Beats Before: Jumps In Place") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getMeanJumpsInPlaceBeats();
    }
  },
  STDDEV_JUMPS_IN_PLACE_BEAT("StdDev Beats Before: Jumps In Place") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getStdDevJumpsInPlaceBeats();
    }
  },

  // Jumps Half In Place Timing Features
  MEAN_JUMPS_HALF_IN_PLACE_SEC("Mean Seconds Before: Jumps Half In Place") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getMeanJumpsHalfInPlaceTimestamps();
    }
  },
  STDDEV_JUMPS_HALF_IN_PLACE_SEC("StdDev Seconds Before: Jumps Half In Place") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getStdDevJumpsHalfInPlaceTimestamps();
    }
  },
  MEAN_JUMPS_HALF_IN_PLACE_BEAT("Mean Beats Before: Jumps Half In Place") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getMeanJumpsHalfInPlaceBeats();
    }
  },
  STDDEV_JUMPS_HALF_IN_PLACE_BEAT("StdDev Beats Before: Jumps Half In Place") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getStdDevJumpsHalfInPlaceBeats();
    }
  },

  // Jumps Full Move Timing Features
  MEAN_JUMPS_FULL_MOVE_SEC("Mean Seconds Before: Jumps Full Move") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getMeanJumpsFullMoveTimestamps();
    }
  },
  STDDEV_JUMPS_FULL_MOVE_SEC("StdDev Seconds Before: Jumps Full Move") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getStdDevJumpsFullMoveTimestamps();
    }
  },
  MEAN_JUMPS_FULL_MOVE_BEAT("Mean Beats Before: Jumps Full Move") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getMeanJumpsFullMoveBeats();
    }
  },
  STDDEV_JUMPS_FULL_MOVE_BEAT("StdDev Beats Before: Jumps Full Move") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getStdDevJumpsFullMoveBeats();
    }
  },

  // Steps Repeated Timing Features
  MEAN_STEPS_REPEATED_SEC("Mean Seconds Before: Steps Repeated") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getMeanStepsRepeatedTimestamps();
    }
  },
  STDDEV_STEPS_REPEATED_SEC("StdDev Seconds Before: Steps Repeated") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getStdDevStepsRepeatedTimestamps();
    }
  },
  MEAN_STEPS_REPEATED_BEAT("Mean Beats Before: Steps Repeated") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getMeanStepsRepeatedBeats();
    }
  },
  STDDEV_STEPS_REPEATED_BEAT("StdDev Beats Before: Steps Repeated") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getStdDevStepsRepeatedBeats();
    }
  },

  // Steps Marching Timing Features
  MEAN_STEPS_MARCHING_SEC("Mean Seconds Before: Steps Marching") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getMeanStepsMarchingTimestamps();
    }
  },
  STDDEV_STEPS_MARCHING_SEC("StdDev Seconds Before: Steps Marching") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getStdDevStepsMarchingTimestamps();
    }
  },
  MEAN_STEPS_MARCHING_BEAT("Mean Beats Before: Steps Marching") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getMeanStepsMarchingBeats();
    }
  },
  STDDEV_STEPS_MARCHING_BEAT("StdDev Beats Before: Steps Marching") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getStdDevStepsMarchingBeats();
    }
  },

  // Steps Move Timing Features
  MEAN_STEPS_MOVE_SEC("Mean Seconds Before: Steps Move") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getMeanStepsMoveTimestamps();
    }
  },
  STDDEV_STEPS_MOVE_SEC("StdDev Seconds Before: Steps Move") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getStdDevStepsMoveTimestamps();
    }
  },
  MEAN_STEPS_MOVE_BEAT("Mean Beats Before: Steps Move") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getMeanStepsMoveBeats();
    }
  },
  STDDEV_STEPS_MOVE_BEAT("StdDev Beats Before: Steps Move") {
    @Override
    public String getValue(Song song, Stepchart chart) {
      return "" + chart.getDancer().getStdDevStepsMoveBeats();
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
