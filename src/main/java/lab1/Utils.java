package lab1;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Utils {
  public static Path readFilePath() {
    Scanner scanner = new Scanner(System.in);
    boolean fileExists = true;
    boolean isDirectory = false;
    boolean repeat;
    Path path;


    do {
      repeat = false;
      if (!fileExists) System.out.println("Файла с введённым адресом не существует. Повторите ещё раз.");
      if (isDirectory) System.out.println("Необходимо ввести путь к файлу, а не к директории");

      do {
        System.out.print("Введите путь файла: ");
      } while (!scanner.hasNextLine());

      path = Paths.get(scanner.nextLine().trim());

      isDirectory = Files.isDirectory(path);
      fileExists = Files.exists(path);

      if (!fileExists) {
        repeat = true;
      } else {
        if (isDirectory) {
          repeat = true;
        }
      }

      System.out.println();
    } while (repeat);

    return path;
  }

}
