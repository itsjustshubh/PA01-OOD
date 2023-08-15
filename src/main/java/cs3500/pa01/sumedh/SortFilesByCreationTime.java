package cs3500.pa01.sumedh;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

public class SortFilesByCreationTime {
  public static void main(String[] args) {
    String directoryPath = "your_directory_path_here";

    try {
      Path directory = Paths.get(directoryPath);
      File[] files = getFilesSortedByCreationTime(directory);

      for (File file : files) {
        System.out.println(file.getName() + " - Creation Time: " + getFileCreationTime(file.toPath()));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static File[] getFilesSortedByCreationTime(Path directory) throws IOException {
    File[] files = directory.toFile().listFiles();

    if (files != null) {
      Arrays.sort(files, new CreationTimeComparator());
    }

    return files;
  }

  public static long getFileCreationTime(Path filePath) throws IOException {
    BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
    return attrs.creationTime().toMillis();
  }
}

