package cs3500.pa01.shubh;

import static java.util.Comparator.comparing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

/**
 * The Sorter class is responsible for sorting an ArrayList of files based on different criteria.
 */
public class Sorter {
  ArrayList<File> inputFiles;

  /**
   * Constructs a Sorter object with the specified ArrayList of files.
   *
   * @param inputFiles the ArrayList of files to be sorted
   */
  Sorter(ArrayList<File> inputFiles) {
    this.inputFiles = inputFiles;
  }

  /**
   * Sorts the input files based on the specified ordering flag.
   *
   * @param orderingFlag the flag indicating the desired ordering (e.g., "filename", "created", "modified")
   * @return the sorted ArrayList of files
   * @throws IllegalArgumentException if the ordering flag is invalid
   */
  public ArrayList<File> get(String orderingFlag) throws IllegalArgumentException {
    switch (orderingFlag) {
      case "filename" -> this.inputFiles.sort(comparing(File::getName));
      case "created"  -> this.inputFiles.sort(comparing(this::getFileCreationTime));
      case "modified" -> this.inputFiles.sort(comparing(File::lastModified));
      default -> throw new IllegalArgumentException("Error: This Ordering Flag is not allowed");
    }
    return this.inputFiles;
  }

  /**
   * Retrieves the creation time of the specified file.
   *
   * @param file the file to retrieve the creation time from
   * @return the creation time of the file in milliseconds
   */
  public long getFileCreationTime(File file) {
    try {
      Path filePath = file.toPath();
      BasicFileAttributes attributes = Files.readAttributes(filePath, BasicFileAttributes.class);
      return attributes.creationTime().toMillis();
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }
}