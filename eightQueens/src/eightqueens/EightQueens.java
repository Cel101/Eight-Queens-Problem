/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eightqueens;

import java.util.Random;
import java.util.ArrayList;

/**
 *
 * @author Marcel Colon
 * @Date: 7/21/2021
 * @Version: 3.0
 */
public class EightQueens {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Variables
        int[][] testBoard = new int[8][8];
        testBoard = randomBoard();
        int heuristicTest = 0;
        ArrayList<State> tempList = new ArrayList<State>();

        //Intialize Section
        heuristicTest = calcHeuristic(testBoard);
        State test = new State(testBoard, heuristicTest);

        //Program Start
        tempList = lowerH(test);
        recursion(test, 0, 0);

    }

    /**
     * @Description: Method used in testing to print boards made in main
     * @param board 2d Integer Array
     */
    public static void printBoard(int[][] board) {
        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {
                System.out.print(board[j][k] + "   ");
            }
            System.out.println("");
        }
        System.out.println("");
    }//End printBoard Method

    /**
     * @Description: Method that creates a board with a queen placed randomly in
     * reach column
     * @return A 2d Integer Array
     */
    public static int[][] randomBoard() {
        int[][] board = new int[8][8];
        Random rand = new Random();
        for (int j = 0; j < 8; j++) {
            board[rand.nextInt(8)][j] = 1;

        }
        return board;
    }

    /**
     * @Description: Method to calculate the number of direct and in-direct
     * conflicts between queens on a board
     * @param board A 2d Integer Array
     * @return A integer representing the number of queen conflicts on the board
     */
    public static int calcHeuristic(int[][] board) {
        int heuristic = 0;
        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {
                if (board[j][k] == 1) {
                    int temp = k;
                    for (int i = k + 1; i < 8; i++) {
                        if (board[j][i] == 1) {
                            heuristic++;
                        }
                    }
                    //Find queens down column
                    for (int i = j + 1; i < 8; i++) {
                        if (board[i][k] == 1) {
                            heuristic++;
                        }
                    }
                    //Find diagonel queens Bottom Right
                    for (int i = j + 1; i < 8 && temp + 1 < 8; i++) {
                        if (board[i][temp + 1] == 1) {
                            heuristic++;

                        }
                        temp++;
                    }
                    temp = k;
                    for (int i = j + 1; i < 8 && temp - 1 >= 0; i++) {
                        if (board[i][temp - 1] == 1) {
                            heuristic++;
                        }
                        temp--;
                    }
                    temp = k;
                }//End if (All the Work)
            }//End column loop 'k'
        }//End row loop 'j'
        return heuristic;
    }//End calcHeuristic method

    /**
     * @Description: Method that takes a state object and moves it's queens down the columns then creates a new state. The states are stored in an array
     * @param current State object of current state to be worked with
     * @return A State Object array of all the various position changes of the queens down their column 
     */
    public static ArrayList<State> lowerH(State current) {
        int[][] tempBoard = current.cloneBoard();

        int tempHeuristic = -1;
        State tempState;
        ArrayList<State> tempArray = new ArrayList();
        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {
                //If we find a queen
                if (current.getBoard()[j][k] == 1) {
                    //Store index position
                    int tempJ = j;
                    int tempK = k;
                    //Remove queen from index
                    tempBoard[j][k] = 0;
                    //Start a loop of length 8
                    for (int i = 0; i < 8; i++) {
                        //Insert queen
                        tempBoard[i][k] = 1;
                        //Calculate Heuristic of new state
                        tempHeuristic = calcHeuristic(tempBoard);
                        //Create a new state
                        tempState = new State(tempBoard, calcHeuristic(tempBoard));
                        //Store it in array list
                        tempArray.add(tempState);
                        //Then remove queen we just placed
                        tempBoard[i][k] = 0;
                    }//LOOP
                    //Put the queen back once we have finished moving
                    tempBoard[tempJ][tempK] = 1;
                }//If queen found
            }//k loop
        }//j loop
        return tempArray;
    }//End lowerH method

    /**
     * @Description: A method that takes a State Object array and the heuristic value of the current state. It calculates how many states in the array that have a lower
     * heuristic than the current state.
     * @param list A ArrayList of state objects
     * @param current The current State object
     * @return A Integer value of the number of states with a lower heuristic than current
     */
    public static int lowerNeighbors(ArrayList<State> list, State current) {
        int tally = 0;
        for (int i = 0; i < list.size(); i++) {
            if (calcHeuristic(list.get(i).getBoard()) < current.getHeuristic()) {
                tally++;
            }
        }
        return tally;
    }

    /**
     * @Description: A method that returns the state with the lowest heuristic
     * @param list Array list of State objects
     * @param current State object of the current state
     * @return State object with the lowest heuristic
     */
    public static State applyLower(ArrayList<State> list, State current) {
        State lowest = current;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getHeuristic() < current.getHeuristic()) {
                lowest = list.get(i);
            }
        }
        return lowest;
    }

    /**
     * @Description: Method that performs a recursive loop until a state with a zero heuristic is found
     * @param current State of object of current state
     * @param restarts Integer that increments as restarts occur
     * @param changes Integer that increments as changes to current state occur
     */
    public static void recursion(State current, int restarts, int changes) {
        if (current.getHeuristic() == 0) {
            current.printBoard();
            System.out.println("SOLUTION FOUND!");
            System.out.println("State Changes: " + changes);
            System.out.println("Restarts: " + restarts);
        } else if (lowerNeighbors(lowerH(current), current) == 0) {
            System.out.println("We need to restart");
            restarts++;
            current.setBoard(randomBoard());
            recursion(current, restarts, changes);
        } else {
            System.out.println("Current h: " + current.getHeuristic());
            System.out.println("Current State");
            current.printBoard();
            System.out.println("Neighbors found with lower h: " + lowerNeighbors(lowerH(current), current));
            System.out.println("Setting new current state");
            changes++;
            recursion(applyLower(lowerH(current), current), restarts, changes);
        }
    }
}
