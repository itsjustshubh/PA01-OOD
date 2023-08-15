package cs3500.pa01.shubh;

import cs3500.pa01.shubh.FileTreeWalkerVisitor;
import cs3500.pa01.shubh.Sorter;
import cs3500.pa01.shubh.Writer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The main entry point of the program.
 */
public class Driver {
  /**
   * The main method that executes the program.
   *
   * @param args The command line arguments (not used in this program).
   * @throws IOException If an I/O error occurs while performing file operations.
   */
  public static void main(String[] args) throws IOException {
    // program args: pa01 filename output/output.md
    // Specifies the fileType to be selected
    String fileType = ".md";
    System.out.println("The chosen fileType is " + fileType + " files.");

    // The default scanner used
    Scanner scanner = new Scanner(System.in);

    // Taking in the input for the File Path called and referred to as "directory".
    Path directory = Path.of(args[0]);

    // Checking if the directory exists
    if (!Files.exists(directory) || !Files.isDirectory(directory)) {
      throw new IOException("Error: The notes root directory does not exist.");
    }

    // Creating the walkFileTree needed to get all relevant files with the specified fileType
    FileTreeWalkerVisitor fileTree = new FileTreeWalkerVisitor(directory, fileType);
    ArrayList<File> files = fileTree.getList();

    // Taking in the input for the Ordering Flag to sort files referred to as "orderingFlag".
    String orderingFlag = String.valueOf(Path.of(args[1]));

    // Creates the sorter and sorts a new ArrayList of files based on the orderingFlag
    Sorter sorter = new Sorter(files);
    ArrayList<File> sortedFiles = sorter.get(orderingFlag);

    // Taking in the input for the Output Path store the file referred to as "outPath".
    String outPath = args[2];

    // Creates a writer with the sorted lists
    Writer writer = new Writer(sortedFiles); // Use sortedFiles instead of files

    // The write file method helps write all the headings and phrases it summarizes
    writer.writeFile(outPath);
  }
}