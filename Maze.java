import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Maze {
  private char[][]maze;
  private boolean animate;//false by default

  /*Constructor loads a maze text file, and sets animate to false by default.

    1. The file contains a rectangular ascii maze, made with the following 4 characters:
    '#' - Walls - locations that cannot be moved onto
    ' ' - Empty Space - locations that can be moved onto
    'E' - the location of the goal (exactly 1 per file)

    'S' - the location of the start(exactly 1 per file)

    2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!


    3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then:

       throw a FileNotFoundException or IllegalStateException

  */
  public Maze(String file) throws FileNotFoundException {
    File mazeFile = new File(file);
    //find rows and columns
    Scanner mazeLines1 = new Scanner(mazeFile);
    int rows=0;
    int cols=0;
    while (mazeLines1.hasNextLine()) {
      rows++;
      cols=mazeLines1.nextLine().length();
    }
    maze=new char[rows][cols];
    //create array
    Scanner mazeLines2 = new Scanner(mazeFile);
    int i=0;
    while (mazeLines2.hasNextLine()) {
      String line = mazeLines2.nextLine();
      for (int j=0;j<line.length();j++) {
        maze[i][j] = line.charAt(j);
      }
      i++;
    }
  }

  private void wait(int millis){
     try {
         Thread.sleep(millis);
     }
     catch (InterruptedException e) {
     }
 }


  public void setAnimate(boolean b){
      animate = b;
  }


  public void clearTerminal(){
      //erase terminal, go to top left of screen.
      System.out.println("\033[2J\033[1;1H");
  }

  /*Return the string that represents the maze.

   It should look like the text file with some characters replaced.

  */
  public String toString(){
    String ans="";
    for (int i=0;i<maze.length;i++) {
      for (int j=0;j<maze[i].length;j++) {
        ans+=maze[i][j];
      }
      ans+="\n";
    }
    return ans;
  }

  private boolean move(int row, int col) {
    if (row>maze.length || row<0
    || col>maze[0].length || col<0) return false;
    if (maze[row][col]=='#' || maze[row][col]=='.') return false;
    return true;
  }

  /*Wrapper Solve Function returns the helper function

    Note the helper function has the same name, but different parameters.
    Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.

  */
  public int solve(){
          int sRow=0;int sCol=0;
          //find the location of the S.
          for (int i=0;i<maze.length;i++) {
            for (int j=0;j<maze[i].length;j++) {
              if (maze[i][j]=='S') {
                sRow=i;sCol=j;
              }
            }
          }
          //erase the S
          maze[sRow][sCol]=' ';
          //and start solving at the location of the s.
          return solve(sRow,sCol);
  }

  /*
    Recursive Solve function:

    A solved maze has a path marked with '@' from S to E.

    Returns the number of @ symbols from S to E when the maze is solved,
    Returns -1 when the maze has no solution.


    Postcondition:

      The S is replaced with '@' but the 'E' is not.

      All visited spots that were not part of the solution are changed to '.'

      All visited spots that are part of the solution are changed to '@'
  */
  private int solve(int row, int col){ //you can add more parameters since this is private
      //automatic animation! You are welcome.
      if(animate){
          clearTerminal();
          System.out.println(this);
          wait(20);
      }
      //COMPLETE SOLVE
      int spots = 0;
      int[] moves = {1,0,-1,0,0,1,0,-1};
      for (int i=0;i<moves.length;i+=2) {
        int nextRow=row+moves[i];
        int nextCol=col+moves[i+1];
        if (move(nextRow,nextCol)) {
          maze[row][col]='@';
          if (maze[nextRow][nextCol]=='E') {
            return 1;
          }
          if (maze[nextRow][nextCol]!='@') {
            spots=solve(nextRow,nextCol);
            if (spots!=0) return spots+1;
          }
        }
      }
      maze[row][col]='.';
      return 0;
  }

}
