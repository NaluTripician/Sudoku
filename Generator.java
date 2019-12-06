import java.util.*;

public class Generator
{
  public static boolean isSolved(int[][] puzzle)
  {
    boolean solved = true;
    for(int r = 0; r < 9; r++)
    {
      for(int c = 0; c < 9; c ++)
      {
        if(puzzle[r][c] == 0)
        {
          return false;
        }
        else
        {
          solved = solved && isValid(puzzle,r,c,puzzle[r][c]);
        }
      }
    }

    return solved;
  }

  public static boolean solve(int[][] puzzle)
  {
    int rowTest = -1;
    int colTest = -1;

    boolean done = true;

    //find first unmarked cell
    for(int r = 0; r < 9; r++)
    {
      for(int c = 0; c < 9; c++)
      {
        if(puzzle[r][c] == 0)
        {
          rowTest = r;
          colTest = c;

          done = false;
          break;
        }
      }
      if(!done)
      {
        break;
      }
    }

    if(done)
    {
      return true;
    }

    for(int num = 1; num<=9; num++)
    {
      if(canPlace(puzzle,rowTest,colTest,num))
      {

        puzzle[rowTest][colTest] = num;

        if(solve(puzzle))
        {
          return true;
        }
        else
        {
            //value does not work
            puzzle[rowTest][colTest] = 0;
        }
      }
    }


    return false;

  }

  public static boolean isValid(int[][] puzzle, int row, int col, int num)
  {
    //check row
    for(int c = 0; c<9; c++)
    {
      if(puzzle[row][c] == num && (c != col))
      {
        return false;
      }
    }

    //check column
    for(int r = 0; r<9; r++)
    {
      if(puzzle[r][col] == num && (r != row))
      {
        return false;
      }
    }

    //check box
    int boxRow = row - row%3;
    int boxCol = col - col%3;

    for(int r = boxRow; r < (boxRow +3); r++)
    {
      for(int c = boxCol; c < (boxCol +3); c ++)
      {
        if(puzzle[r][c] == num && ((r != row) && (c != col)))
        {

          return false;
        }
      }
    }

    return true;
  }
  //tests to see if you can place a number a given spot in the Sudoku
  public static boolean canPlace(int[][] puzzle, int row, int col, int num)
  {
    //check row
    for(int c = 0; c<9; c++)
    {
      if(puzzle[row][c] == num)
      {
        return false;
      }
    }

    //check column
    for(int r = 0; r<9; r++)
    {
      if(puzzle[r][col] == num)
      {
        return false;
      }
    }

    //check box
    int boxRow = row - row%3;
    int boxCol = col - col%3;

    for(int r = boxRow; r < (boxRow +3); r++)
    {
      for(int c = boxCol; c < (boxCol +3); c ++)
      {
        if(puzzle[r][c] == num)
        {
          return false;
        }
      }
    }

    return true;
  }

  public static void print(int[][] puzzle)
  {
    for(int r = 0; r < 9; r++)
    {
      for(int c = 0; c < 9; c++)
      {
        System.out.print( Integer.toString(puzzle[r][c]) + " ");
      }
      System.out.println(" ");
    }
  }

  public static int[][] genSolution()
  {
    Random rando = new Random();
    int[][] solution = new int[9][9];

    for(int r = 0; r < 9; r++)
    {
      for(int c = 0; c < 9; c++)
      {
        solution[r][c] = 0;
      }
    }

    genHelper(solution,rando);

    return solution;
  }

  public static boolean genHelper(int[][] puzzle, Random rando)
  {
    int rowTest = -1;
    int colTest = -1;

    boolean done = true;

    //find first unmarked cell
    for(int r = 0; r < 9; r++)
    {
      for(int c = 0; c < 9; c++)
      {
        if(puzzle[r][c] == 0)
        {
          rowTest = r;
          colTest = c;

          done = false;
          break;
        }
      }
      if(!done)
      {
        break;
      }
    }

    if(done)
    {
      return true;
    }

    int[] numbers = {1,2,3,4,5,6,7,8,9};
    int i = rando.nextInt(9);

    int num = numbers[i];


    for(int j = 1; j<=9; j++)
    {
      if(canPlace(puzzle,rowTest,colTest,num))
      {
        puzzle[rowTest][colTest] = num;

        if(genHelper(puzzle,rando))
        {
          return true;
        }
        else
        {
            //value does not work
            puzzle[rowTest][colTest] = 0;
        }
      }

      if(i<8)
      {
        i++;
      }
      else
      {
        i = 0;
      }

      num = numbers[i];
    }

    return false;
  }

  public static int[][] copy(int[][] puzzle)
  {
    int[][] copy = new int [9][9];

    for(int r = 0; r < 9; r++)
    {
      for(int c = 0; c < 9; c++)
      {
        copy[r][c] = puzzle[r][c];
      }
    }

    return copy;
  }

  public static int numSol(int[][] puzzle)
  {
    /*
    returns the number of solutions to a Sudoku
    If already solved will return 1
    */

    int counter = 0;

    int[][] tester = new int[9][9];

    boolean done = false;

    if(isSolved(puzzle))
    {
      return 1;
    }

    int row = -1;
    int col = -1;

    //find first unmarked cell
    for(int r = 0; r < 9; r++)
    {
      for(int c = 0; c < 9; c++)
      {
        if(puzzle[r][c] == 0)
        {
          row = r;
          col = c;
          done = true;
        }
        if(done){break;}
      }
      if(done){break;}
    }

    for(int num = 1; num<=9; num++)
    {
      if(canPlace(puzzle,row,col,num))
      {
        tester = copy(puzzle);

        tester[row][col] = num;
        puzzle[row][col] = num;

        if(solve(tester))
        {
          done = true;
          counter+= numSol(puzzle);
        }

        puzzle[row][col] = 0;

      }
    }

    return counter;

  }

  public static int[][] genSudoku(int[][] solution,int difficulty)
  {
    /* Naieve implemtation (will change in the future to include patterns)
    randomly generates a Sudoku from a solution with a difficulty from 0 - 4
    0| Very Easy  (36-40 removed)
    1| Easy       (41-45 removed)
    2| Medium     (46-50 removed)
    3| Hard       (51-57 removed)
    4| Very Hard  (58-64 removed)
    */

    Random rando = new Random();
    int trial;

    int [][] puzzle = copy(solution);

    trial = difficulty + 5;

    while(trial > 0)
    {
      int x = rando.nextInt(9);
      int y = rando.nextInt(9);

      while(puzzle[x][y] == 0)
      {
        x = rando.nextInt(9);
        y = rando.nextInt(9);
      }

      int backup = puzzle[x][y];

      puzzle[x][y] = 0;

      if(numSol(copy(puzzle)) != 1)
      {
        trial--;
        puzzle[x][y] = backup;
      }


    }
    return puzzle;
  }

  public static void main(String[] args)
  {
    int [][] guy = genSolution();
    print(guy);
    System.out.println("___");
    int[][] puzz = genSudoku(guy,50);
    print(puzz);
  }


}
