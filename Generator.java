import java.util.*;

import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.util.Scanner;

public class Generator extends Canvas
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
    // Randomly generates a Sudoku from a solution with a difficulty from 0 - 100?

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

  public static int[][] genDiagSym(int[][] solution,int difficulty)
  {
    // Randomly generates a Diagonally Symmetric Sudoku from a solution with a difficulty from 0 - 100?

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

      int diagX = 8 - x;
      int diagY = 8 - y;

      int backup = puzzle[x][y];
      int diagBackup = puzzle[diagX][diagY];

      puzzle[x][y] = 0;
      puzzle[diagX][diagY] = 0;

      if(numSol(copy(puzzle)) != 1)
      {
        trial--;
        puzzle[x][y] = backup;
        puzzle[diagX][diagY] = diagBackup;
      }


    }
    return puzzle;
  }

  public static void main(String[] args)
  {
    JFrame frame = new JFrame("My Drawing");
    Canvas canvas = new Generator();
    canvas.setSize(700,700);
    frame.add(canvas);
    frame.pack();
    frame.setVisible(true);

  }

  public void paint(Graphics g)
  {
    int [][] solution = genSolution();
    int[][] puzz = genDiagSym(solution,15);

    //draws main square
    g.fillRect(47,47,582,582);
    g.setColor(Color.WHITE);
    g.fillRect(50,50,576,576);
    g.setColor(Color.BLACK);

    //draws sectors

    for(int i = 0; i < 576; i +=192)
    {
      for(int j = 0; j < 576; j += 192)
      {
        g.fillRect(48 + i ,48 + j,196,196);
        g.setColor(Color.WHITE);
        g.fillRect(50 + i,50 + j,192,192);
        g.setColor(Color.BLACK);
      }
    }

    //draws horizontals

    for(int i = 50; i < 626; i += 64)
    {
      g.drawLine(50, i +64, 626, i+64);
    }

    //draws verticals
    for(int i = 50; i < 626; i += 64)
    {
      g.drawLine(i +64, 50, i+64,626);
    }

    g.setColor(Color.BLACK);
    g.setFont(new Font("Ariel", Font.PLAIN, 48));

    int startPosX = 68;
    int startPosY = 100;

    for(int i = 0; i < 9; i++)
    {
      for(int j = 0; j < 9; j++)
      {
        if(puzz[i][j] != 0)
        {
          g.drawString(Integer.toString(puzz[i][j]),68 + (i*64),100 + (j*64));
        }

      }
    }

    Scanner sc = new Scanner(System.in);
    char next = sc.next().charAt(0); 

    for(int i = 0; i < 9; i++)
    {
      for(int j = 0; j < 9; j++)
      {
        if(puzz[i][j] == 0)
        {
          g.setColor(Color.RED);
          g.drawString(Integer.toString(solution[i][j]),68 + (i*64),100 + (j*64));
        }

      }
    }

  }

}
