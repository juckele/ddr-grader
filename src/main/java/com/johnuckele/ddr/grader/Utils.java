package com.johnuckele.ddr.grader;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Utils {
  public static double beatToTime(double beat, Map<Double, Double> beatToBpms, Map<Double, Double> beatToStops) {
    // Build data structures, this is slow, if parsing songs is slow, cache this
    Set<Double> beatEvents = new HashSet<>();
    beatEvents.addAll(beatToBpms.keySet());
    beatEvents.addAll(beatToStops.keySet());
    List<Double> orderedBeatEvents = new LinkedList<>(beatEvents);
    orderedBeatEvents.add(beat);
    Collections.sort(orderedBeatEvents);
    
    // Track current state
    double currentBPM = beatToBpms.get(orderedBeatEvents.get(0));
    double currentBeat = 0.0;
    double totalTime = 0.0;

    // Replay to the current beat
    for(Double eventBeat : orderedBeatEvents) {

      if(beat == eventBeat) {
        totalTime += (eventBeat-currentBeat) * 60/currentBPM;
        return totalTime;
      }

      if(beatToBpms.containsKey(eventBeat)) {
        totalTime += (eventBeat-currentBeat) * 60/currentBPM;
        currentBPM = beatToBpms.get(eventBeat);
        currentBeat = eventBeat;
      }

      if(beatToStops.containsKey(eventBeat)) {
        totalTime += beatToStops.get(eventBeat);
      }
    }
    throw new IllegalStateException("Not sure what happened...");
  }
}
