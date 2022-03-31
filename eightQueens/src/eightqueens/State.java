
package eightqueens;

/**
 *
 * @author Marcel Colon
 * @Date: 7/12/2021
 * @Version: 2.3
 */
public class State {
    //Variables
    private int[][] board;
    private int heuristic;
    
    //Constuctor
    /**
     * @Description: Object that takes a 2d board array and integer representing heuristic value
     * @param board 2d Array simulating a board
     * @param heuristic Integer representing amount of conflicts
     */
    public State(int[][] board, int heuristic){
        this.board = deepCopyBoard(board);
        this.heuristic = heuristic;
        
    }

    /**
     * @Description: Method to retrieve the board of the object
     * @return 2d Integer Array
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * @Description: Method to retrieve object heuristic
     * @return Integer representing amount of conflicts
     */
    public int getHeuristic() {
        return heuristic;
    }

    /**
     * @Description: Method to objects board
     * @param board 2d Integer Array representing a board
     */
    public void setBoard(int[][] board) {
        this.board = board;
    }

    /**
     * @Description: Method to set the heuristic value of the object
     * @param heuristic Integer representing the amount of conflicts
     */
    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }
    
    /**
     * @Description: Method that allows the cloning of a board to create a new location in memory
     * @param board 2d Integer Array simulating a board
     * @return 2d Integer Array representing a board
     */
    private int[][] deepCopyBoard(int[][] board){
        int[][] copyBoard = new int[8][8];
        for (int j = 0; j < 8; ++j){
            for (int k = 0; k < 8; ++k){
                copyBoard[j][k] = board[j][k];
            }
        }
        
        return copyBoard;
    }
    
    /**
     * @Description: Method to print the objects board to console
     */
    public void printBoard() {
        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {
                System.out.print(this.board[j][k] +"   ");
            }
            System.out.println("");
        }
    }//End printBoard Method
    
    /**
     * @Description: Method to return a cloned board
     * @return A 2d Integer Array
     */
    public int[][] cloneBoard(){
        return this.deepCopyBoard(this.board);
    }
}
