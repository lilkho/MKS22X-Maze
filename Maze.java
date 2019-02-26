import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Maze {
  public Maze(String file) throws FileNotFoundException {
    File mazeFile = new File(file);
    Scanner maze = new Scanner(mazeFile);
    while (maze.hasNextLine()) {
      System.out.println(maze.nextLine());
    }
  }

  public static void main(String[] args) {
    try {
      Maze maze = new Maze("Maze1.txt");
    } catch(FileNotFoundException f) {
      System.out.println("File doesn't exist");
    }
  }
}
