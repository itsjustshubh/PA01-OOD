package cs3500.pa01.sumedh;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

class LastModifiedComparator implements Comparator<File> {
  @Override
  public int compare(File file1, File file2) {
    return Long.compare(file1.lastModified(), file2.lastModified());
  }
}






