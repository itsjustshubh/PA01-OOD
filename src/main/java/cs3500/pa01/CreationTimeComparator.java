package cs3500.pa01;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Comparator;

class CreationTimeComparator implements Comparator<File> {
  @Override
  public int compare(File file1, File file2) {
    try {
      long creationTime1 = SortFilesByCreationTime.getFileCreationTime(file1.toPath());
      long creationTime2 = SortFilesByCreationTime.getFileCreationTime(file2.toPath());

      return Long.compare(creationTime1, creationTime2);
    } catch (IOException e) {
      throw new RuntimeException("Error comparing file creation times", e);
    }
  }
}
