package cs3500.pa01;

import static java.lang.System.out;

//import java.io.*;
//import java.util.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Collections;





/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */

  // declaring all the variables
  String currentHeading;
  ArrayList<String> currentLine;

  Map<String, ArrayList> map;
  List<String> lines;
  String directoryPath;

  File outputDirectory;

  public static void main(String[] args) throws IOException {
    // Specifies the fileType to be selected
    String fileType = ".md";
    out.println("The chosen fileType is " + fileType + " files.");

    // The default scanner used
    Scanner scanner = new Scanner(System.in);

    System.out.println("Enter directory to read");
    // Taking in the input for the File Path called and referred to as "directory".
    Path directory = Path.of(scanner.nextLine());

    // Checking if the directory exists
    if (!Files.exists(directory) || !Files.isDirectory(directory)) {
      throw new IOException("Error: The notes root directory does not exist.");
    }

    // Creating the walkFileTree needed to get all relevant files with the specified fileType
    FileTreeWalkerVisitor fileTree = new FileTreeWalkerVisitor(directory, fileType);
//    ArrayList<File> files = fileTree.getList();

    // Sorting the files
//    File[] files = directory.listFiles();
    File[] files = fileTree.getList().toArray(new File[0]);


    System.out.println("Choose how you want the files to be sorted:");
    System.out.println("Enter 1 for filename, Enter 2 for modified");
    int x = scanner.nextInt();

    switch(x) {
      case 1:
//        files = Collections.sort(files);
        files = files;
        break;

      case 2:
        Arrays.sort(files, new Comparator<File>(){
          public int compare(File f1, File f2)
          {
            return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
          }
        });
        break;

      default:

        System.err.println("Wrong Input, by default it will sort by filename");

    }

    String currentHeading = null;
    ArrayList<String> currentLine = new ArrayList<String>();

    Map<String, ArrayList> map = new HashMap<>();
    List<String> lines =null;

    for (File file : files) {

      lines = Files.readAllLines(file.toPath());
      System.out.println(lines);

      for (String line : lines) {

        if (line.startsWith("#")) {
              System.out.println(line);
              currentHeading = line;
              currentLine = new ArrayList<String>();

          }

          if(line.contains("[[") && line.contains("]]")) {
            line = "- " + line.substring(line.indexOf("[") + 2, line.lastIndexOf("]") - 1 );
            System.out.println(line);
            currentLine.add(line);
            map.put(currentHeading, currentLine);
          }
          else if(line.contains("[[") && !line.contains("]]")) {
          line = "- " + line.substring(line.indexOf("[") + 2);
            System.out.println(line);
            currentLine.add(line);
            map.put(currentHeading, currentLine);
          }
          else if(!line.contains("[[") && line.contains("]]")) {
            line = "- " + line.substring(0, line.lastIndexOf("]") - 1 );
            System.out.println(line);
            currentLine.add(line);
            map.put(currentHeading, currentLine);
          }
          else {
            line = null;
            System.out.println(currentLine);
          }
//        System.out.println(currentLine);

        }
    }

    // get sample of the output
      for (String line : map.keySet()) {
        System.out.println(line + ": " + map.get(line));
      }



      // making a directory for output
      System.out.println("Enter a directory path for the output");
      String directoryPath = scanner.next();

      File outputDirectory = new File(directoryPath);

      if (!outputDirectory.exists()) {
        boolean success = outputDirectory.mkdir();
        if (success) {
          System.out.println("Directory created successfully.");
        } else {
          System.err.println("Failed to create directory.");
        }
      } else {
        System.out.println("Directory already exists.");
      }

      // creating new file

    System.out.println("Enter name for the new file");
    String filePath = scanner.next();
      filePath = outputDirectory + "/" + filePath;

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

      for (String i : map.keySet()) {
        writer.newLine();
        writer.write(i);
        writer.newLine();
        System.out.println(i);
        ArrayList<String> specificLine = (map.get(i));
        for( int j = 0; j < specificLine.size(); j++) {
          writer.write(specificLine.get(j));
          System.out.println(specificLine.get(j));

          writer.newLine();
        }
      }
      writer.close();
      System.out.println("Data written to file successfully.");
    } catch (IOException e) {
      System.err.println("An error occurred while writing to the file: " + e.getMessage());
    }

//    // Taking in the input for the Ordering Flag to sort files referred to as "orderingFlag".
//    String orderingFlag = String.valueOf(Path.of(args[1]));
//
//    // Creates the sorter and sorts a new ArrayList of files based on the orderingFlag
//    //Sorter sorter = new Sorter(files);
//    ArrayList<File> sortedFiles = sorter.get(orderingFlag);
//
//    // Taking in the input for the Output Path store the file referred to as "outPath".
//    String outPath = args[2];
//
//    // Creates a writer with the sorted lists
//    Writer writer = new Writer(sortedFiles); // Use sortedFiles instead of files
//
//    // The write file method helps write all the headings and phrases it summarizes
//    writer.writeFile(outPath);
    }

  public void Writer() {


  }
}
