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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;




/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) throws IOException {
    // Specifies the fileType to be selected
    String fileType = ".md";
    out.println("The chosen fileType is " + fileType + " files.");


//    int ch;
//    FileReader file1=null;
//    FileReader file2=null;
//
//    try
//    {
//      file1 = new FileReader("files/Vectors.md");
//      file2 = new FileReader("files/Arrays.md");
//    }
//    catch (FileNotFoundException fe)
//    {
//      System.out.println("File not found");
//    }
//
//    // read from FileReader till the end of file
//    while ((ch=file1.read())!=-1){
//      System.out.print((char)ch);
//      if(ch == '[') {
//        System.out.print("");
//      }
//
//    }
//    while ((ch=file2.read())!=-1){
//      System.out.print((char)ch);
//      if(ch == '#'){
//        System.out.println("??");
//      }
//    }
//
//    // close the file
//    file1.close();
//    file2.close();


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
    ArrayList<File> files = fileTree.getList();
    
    String currentHeading = null;
    String currentLine = null;

    Map<String, String  > map = new HashMap<>();

    for (File file : files) {

      List<String> lines = Files.readAllLines(file.toPath());
      System.out.println(lines);

      for (String line : lines) {

        if(line.startsWith("#")) {
          String newline = line;
          System.out.println(line);
          currentHeading = line;

        }
        if(line.startsWith("- [[") && line.endsWith("]]")) {
          line = line.substring(4,line.length()-3);
          line = "- " + line;
          System.out.println(line);

          currentLine = line;
        }
        if(line.startsWith("- [[") && line.endsWith(".")) {
          line = line.substring(4,line.lastIndexOf("]")-2);
          line = "- " + line;
          System.out.println(line);

          currentLine = line;
        }
        if(line.startsWith("[[") && line.endsWith("]]")) {
          line = line.substring(2,line.lastIndexOf("]")-2);
          line = "- " + line;
          System.out.println(line);

          currentLine = line;
        }
      }


      map.put(currentHeading, currentLine);


      for (String line: map.keySet()) {
        System.out.println( line + ": " + map.get(line));
      }

      System.out.println("Enter a directory path for the output");
      String directoryPath = scanner.next();

      File newDirectory = new File(directoryPath);
        if (!newDirectory.exists()) {
          boolean success = newDirectory.mkdirs();
          if (success) {
            System.out.println("Directory created successfully.");
          } else {
            System.err.println("Failed to create directory.");
            return;
          }
        }

      System.out.println("Enter a file name");
      String fileName = scanner.next();

      File newFile = new File(newDirectory, fileName);

      try {
        boolean fileCreated = file.createNewFile();
        if (fileCreated) {
          System.out.println("File created successfully.");
        } else {
          System.err.println("File already exists or failed to create.");
        }
      } catch (IOException e) {
        System.err.println("An error occurred while creating the file: " + e.getMessage());
      }


      for (String line: map.keySet()) {
        System.out.println( line + ": " + map.get(line));
      }

      try (FileWriter writer = new FileWriter(file)) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
          writer.write(entry.getKey() + "=" + entry.getValue() + System.lineSeparator());
        }
        for (String line: map.keySet()) {
          System.out.println( line + ": " + map.get(line));
        }
        System.out.println("Data written to file successfully.");
      } catch (IOException e) {
        System.err.println("An error occurred while writing to the file: " + e.getMessage());
      }

      BufferedWriter bf = null;

      try {
        bf = new BufferedWriter(new FileWriter(newFile));

        for (String line: map.keySet()) {
          System.out.println( line + ": " + map.get(line));
        }

        for (String line : map.keySet()) {
          bf.write(line + ": " + map.get(line));
          bf.newLine();
        }

//        bf.flush();
      }
      catch (IOException e) {
        throw new IOException("Error: ");
      }
      bf.close();



// ---------------------------------------------------------------


//      File newFile = new File("C:/Users/sumed/IdeaProjects/PA01-OOD/output/CombinedFile.md");
//      FileOutputStream fos = new FileOutputStream(newFile);
//      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

//      File newFile = new File(directoryPath, fileName);

//      BufferedWriter bf = null;
//
//      try {
//        bf = new BufferedWriter(new FileWriter(newFile));
//        for (String line: map.keySet()) {
//          System.out.println( line + ": " + map.get(line));
//        }
//        for (String line: map.keySet()) {
//          bf.write( line + ": " + map.get(line));
//        }



//        bf.flush();
//      }
//      catch (IOException e) {
//        throw new IOException("Error: ");
//      }
//
//      bf.close();







//      finally {
//
//        try {
//
//          // always close the writer
//          bf.close();
//        }
//        catch (Exception e) {
//        }
//      }
    }




    for(int i=0; i<5; i++ ) {
      out.print("Hi");
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

  public static void Reader() throws IOException {

  }
  public static void Writer() throws IOException {

  }


}