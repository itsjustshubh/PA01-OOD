package cs3500.pa01;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

/**
 * A visitor for traversing a directory tree and collecting files of a specific type.
 */
public class FileTreeWalkerVisitor implements FileVisitor<Path> {

  /**
   * The root directory to start the traversal from.
   */
  public static Path directory;

  /**
   * The file type to filter the collected files.
   */
  public static String fileType;

  /**
   * A collection of files matching the specified file type.
   */
  public static ArrayList<File> files = new ArrayList<>();

  /**
   * Constructs a FileTreeWalkerVisitor with the given directory and file type.
   *
   * @param directory the root directory to start the traversal from
   * @param fileType  the file type to filter the collected files
   */
   public FileTreeWalkerVisitor(Path directory, String fileType) {
    this.directory = directory;
    this.fileType = fileType;
  }

  /**
   * Returns a list of files matching the specified file type.
   *
   * @return a list of files
   * @throws IOException if an I/O error occurs during the traversal
   */
  public ArrayList<File> getList() throws IOException {
    Files.walkFileTree(this.directory, this);
    return this.files;
  }

  /**
   * Visits a file and adds it to the collection if it matches the specified file type.
   *
   * @param file the file to visit
   * @param attr the file's basic attributes
   * @return the result of the file visit
   */
  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
    if (file.getFileName().toString().endsWith(this.fileType) &&
        !this.files.contains(file)) {
      this.files.add(file.toFile());
    }
    return CONTINUE;
  }

  /**
   * Performs any necessary operations after visiting a directory.
   *
   * @param dir  the directory being visited
   * @param exec the I/O exception that occurred during the visit, or null if no error occurred
   * @return the result of the post-visit
   */
  @Override
  public FileVisitResult postVisitDirectory(Path dir, IOException exec) {
    return CONTINUE;
  }

  /**
   * Performs any necessary operations before visiting a directory.
   *
   * @param dir   the directory to visit
   * @param attrs the directory's basic attributes
   * @return the result of the pre-visit
   * @throws IOException if an I/O error occurs during the pre-visit
   */
  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
    return CONTINUE;
  }

  /**
   * Handles a failed file visit.
   *
   * @param file the file that failed to be visited
   * @param exc  the exception that occurred during the visit
   * @throws IOException if an I/O error occurs during the pre-visit
   */
  @Override
  public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
    throw exc;
  }
}