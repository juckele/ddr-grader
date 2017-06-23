package enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

public enum Difficulty {
  BEGINNER("Beginner", new String[] {"soft", "beginner", "practice"}),
  EASY("Easy", new String[] {"easy", "basic", "light"}),
  MEDIUM("Medium", new String[] {"medium", "another", "trick", "standard", "difficult"}),
  HARD("Hard", new String[] {"hard", "maniac", "ssr", "heavy", "expert"}),
  CHALLENGE("Challenge", new String[] {"challenge", "oni", "extra"}),
  EDIT("Edit", new String[] {"edit", ""}),
  ;

  public final String name;
  private final Set<String> aliases;

  private Difficulty(String name, String[] aliases) {
    this.name = name;
    this.aliases = new HashSet<String>(Arrays.asList(aliases));
  }

  public static Difficulty parseDifficulty(String sourceString) {
    String lowercaseString = StringUtils.strip(sourceString.toLowerCase());
    for (Difficulty value : Difficulty.values()) {
      if (value.aliases.contains(lowercaseString)) {
        return value;
      }
    }
    throw new IllegalStateException("Unable to map \"" + sourceString + "\" to a valid difficulty");
  }
}
