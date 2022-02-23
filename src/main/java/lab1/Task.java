package lab1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Task {
  public static void execute(Path path) {
    try (Stream<String> stream = Files.lines(path)) {
      StringBuilder stringBuilder = new StringBuilder();
      stream.forEach(line -> {
        stringBuilder.append(line.length() > 0 ? modifyLine(line) : "").append("\n");
      });

      Path result = Paths.get(path.toAbsolutePath().getParent().toString(), "result.txt");

      Files.deleteIfExists(result);
      Files.createFile(result);
      Files.write(result, stringBuilder.toString().getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String modifyLine(String line) {
    boolean firstWordExists = false;
    boolean secondWordExists = false;
    boolean firstWordStarted = false;
    boolean secondWordStarted = false;

    StringBuilder firstWord = new StringBuilder();
    StringBuilder secondWord = new StringBuilder();

    int firstWordPos = 0;
    int secondWordPos = 0;

    for (char c : line.toCharArray()) {
      if (Character.isAlphabetic(c)) {

        if (!firstWordStarted) {
          firstWordStarted = true;
        }

        firstWord.append(c);

      } else {
        if (firstWordStarted) {
          firstWordExists = true;
          break;

        }
        firstWordPos++;

      }
    }

    for (char c : new StringBuilder(line).reverse().toString().toCharArray()) {
      if (Character.isAlphabetic(c)) {
        if (!secondWordStarted) {
          secondWordStarted = true;
        }

        secondWord.append(c);

      } else {
        if (secondWordStarted) {
          secondWordExists = true;
          break;
        }

        secondWordPos++;

      }
    }

    return getResultString(
        line, firstWordExists, secondWordExists, firstWord, secondWord, firstWordPos, secondWordPos
    );
  }

  private static String getResultString(String line,
                                        boolean firstWordExists,
                                        boolean secondWordExists,
                                        StringBuilder firstWord,
                                        StringBuilder secondWord,
                                        int firstWordPos,
                                        int secondWordPos) {
    StringBuilder result = new StringBuilder();

    if (firstWordExists && secondWordExists) {
      if (!firstWord.toString().equals(secondWord.toString())) {
        result.append(line, 0, firstWordPos);
        result.append(secondWord.reverse());
        result.append(line, firstWordPos + firstWord.length(), line.length() - secondWordPos - secondWord.length());
        result.append(firstWord);
        result.append(line, line.length() - secondWordPos - 1, line.length());

        return result.toString();
      } else {
        return line;
      }
    } else {
      return line;
    }
  }
}