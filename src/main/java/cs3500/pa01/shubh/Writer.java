package cs3500.pa01.shubh;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a writer that extracts headings and phrases from files and
 * provides summaries.
 */
public class Writer {
  List<File> files;

  /**
   * Constructs a Writer object with the given list of files.
   *
   * @param files the list of files to process
   */
  public Writer(List<File> files) {
    this.files = files;
  }

  /**
   * Summarizes the headings and phrases in the files.
   *
   * @return a list of important phrases from the files
   * @throws IOException if an error occurs while reading the files
   */
  public List<String> summarize() throws IOException {
    ArrayList<String> usedHeadings = new ArrayList<>();
    List<String> importantPhrases = new ArrayList<>();

    for (File file : files) {
      List<String> lines = Files.readAllLines(file.toPath());
      String currentHeading = "";

      for (String line : lines) {
        if (line.startsWith("#")) {
          currentHeading = line;
        }

        if (line.contains("[[")) {
          int startIndex = line.indexOf("[[");
          int endIndex = line.indexOf("]]");

          while (startIndex >= 0 && endIndex > startIndex) {
            String phrase = line.substring(startIndex + 2, endIndex);
            if (usedHeadings.contains(currentHeading)) {
              importantPhrases.add("- " + phrase);
            } else if (usedHeadings.isEmpty()) {
              importantPhrases.add(currentHeading + "\n- " + phrase);
              usedHeadings.add(currentHeading);
            } else {
              importantPhrases.add("\n" + currentHeading + "\n- " + phrase);
              usedHeadings.add(currentHeading);
            }
            startIndex = line.indexOf("[[", endIndex);
            endIndex = line.indexOf("]]", endIndex + 2);
          }
        }
      }
    }

    return importantPhrases;
  }

  /**
   * Writes the summarized content to a file at the specified path.
   *
   * @param fullPath the full path of the file to write
   * @throws IOException if an error occurs while writing the file
   */
  public void writeFile(String fullPath) throws IOException {
    List<String> summaryLines = summarize();
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath))) {
      for (String line : summaryLines) {
        writer.write(line);
        writer.newLine();
      }
      System.out.println("File created successfully!");
    } catch (IOException e) {
      throw new IOException("An error occurred while creating the file.");
    }
  }
}