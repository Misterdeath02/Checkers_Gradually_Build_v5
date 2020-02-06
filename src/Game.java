import java.awt.*;

public class Game {
  /*
      These are constant values that you can access in any
      class file. If you try to access one of these constants
      outside of the Game class, then you will need to include
      "Game." before the variable reference. For example, to
      access PLAYER1_CHECKER_COLOR in the checker class, you would
      need to type this:
          Game.PLAYER1_CHECKER_COLOR
      If you only typed this in the Checkers class...
          PLAYER1_CHECKER_COLOR
      ...then Java would not know what you meant and throw an error.
  */

    public static final int BOARD_NUM_OF_ROWS = 8;
    public static final int BOARD_NUM_OF_COLS = 8;
    public static final double WIDTH_OF_SPACE = 1.0;
    public static final double HEIGHT_OF_SPACE = 1.0;
    public static final double HALF_WIDTH_OF_SPACE = WIDTH_OF_SPACE/2.0;
    public static final double HALF_HEIGHT_OF_SPACE = HEIGHT_OF_SPACE/2.0;
    public static final Color PRIMARY_SPACE_COLOR = StdDraw.WHITE;
    public static final Color SECONDARY_SPACE_COLOR = StdDraw.BOOK_LIGHT_BLUE;
    public static final Color PLAYER1_CHECKER_COLOR = StdDraw.RED;
    public static final Color PLAYER2_CHECKER_COLOR = StdDraw.BLACK;

    private BoardSpace[][] boardSpaces;
    private boolean isTurnForPlayer1;

    public Game(){
        boardSpaces = new BoardSpace[BOARD_NUM_OF_ROWS][BOARD_NUM_OF_COLS];
        isTurnForPlayer1 = true;

        //create a new BoardSpace object, and store it in each spot of the grid
        for(int row = 0; row < BOARD_NUM_OF_ROWS; row++){
            for(int col = 0; col < BOARD_NUM_OF_COLS; col++){
                boolean isPrimaryColor = (row % 2) == (col % 2); //if both the row and col index values are even or both odd, then the board space should be
                boardSpaces[row][col] = new BoardSpace(isPrimaryColor);
            }
        }

        //Place Checkers in starting positions
        boolean checkerPlacedBelongsToPlayer1 = true;
        for(int row = 0; row < BOARD_NUM_OF_ROWS; row++){
            if(row==3){
                row = row+2; //skip middle two rows when placing checkers
                checkerPlacedBelongsToPlayer1 = false;
            }
            for(int col = 0; col < BOARD_NUM_OF_COLS; col=col+2){
                if(row%2==1 && col==0){ //on rows with an odd index, checkers need offset by 1 to the right
                    col++;
                }
                boardSpaces[row][col].placeChecker(new Checker(checkerPlacedBelongsToPlayer1));
            }
        }
        updateCheckersWithMovementOptions();
    }

    public void switchToNextPlayerTurn(){
        isTurnForPlayer1 = !isTurnForPlayer1;
    }

    //For each checker on the board, reset its movement options
    public void resetAllCheckerMovementOptions(){
        for(int row = 0; row < BOARD_NUM_OF_ROWS; row++) {
            for (int col = 0; col < BOARD_NUM_OF_COLS; col++) {
                Checker checker = boardSpaces[row][col].getChecker();
                if(checker != null){
                    checker.resetMovementOptions();
                }
            }
        }
    }

    public void resetAllSpaceMarkings(){
        for(int row = 0; row < BOARD_NUM_OF_ROWS; row++) {
            for (int col = 0; col < BOARD_NUM_OF_COLS; col++) {
                boardSpaces[row][col].resetMarkings();
            }
        }
    }

    /*
    Questions to make sure you know the answers to:
        (a) Do we really need to clear out all the checker movement options and then remark them all again every single turn? If not, why do you think I took the approach I did?
        (b) How does the method resetAllCheckerMovementOptions() work?
        (c) How does the method switchToNextPlayerTurn() work? Does the game currently care whose turn it is?
        (d) Why do we call resetAllCheckerMovementOptions(), updateCheckersWithMovementOptions(), and switchToNextPlayerTurn()
        ONLY when a destination space is clicked?
     */
    public void setSpaceSelected(int rowClicked, int colClicked, int prevRowClicked, int prevColClicked){
        BoardSpace spaceClicked = boardSpaces[rowClicked][colClicked];
        //If the user clicks on a space highlighted in yellow, then we know we need to move a checker. Otherwise, we know it is still the same player's turn and do what appears after this if statement.
        if(spaceClicked.getIsDestinationOption()){
            BoardSpace spacePrevClicked = boardSpaces[prevRowClicked][prevColClicked];
            Checker checker = spacePrevClicked.pickUpChecker();
            spaceClicked.placeChecker(checker);
            /*
                A checker changing positions on the board is significant for a few reasons:
                (1) It indicates that it is the next player's turn. (Don't worry about double jumping for now.)

                (2) A checker moving to a new space doesn't auto-magically reset the state of all board spaces.

                (3) A checker moving to a new space doesn't auto-magically reset the state of that checker.

                (4) If we reset all checkers to their default states, then all checkers will be marked as false in terms
                of being able to slide/jump (see the private instance variables for our checkers if you are confused). If we don't
                update the checkers again with where they can move based on where they are at on the board, then all our
                checkers will be permanently stuck.

                (5) If we do not take care of both (2) and (3) above, then this will cause major issues in some
                situations-- namely, it will cause out of bounds errors and allow checkers to devour one another. Not good.

             */
            resetAllSpaceMarkings(); //takes care of (2)
            resetAllCheckerMovementOptions(); //takes care of (3)
            updateCheckersWithMovementOptions(); //takes care of (4)
            switchToNextPlayerTurn(); //takes care of (1)
            return;
            /*
                Regarding the above line: setSpaceSelected (the method we are in right now) is a void method. This means
                we cannot return anything. However, you can still have a completely blank return statement like this; what
                this does is immediately stop running setSpaceSelected right here.
             */
        }
        resetAllSpaceMarkings();
        spaceClicked.setIsSelected(true);
        Checker clickedChecker = spaceClicked.getChecker();
        if(clickedChecker!=null && clickedChecker.canSlide()){
            if(clickedChecker.getCanSlideUpLeft()){
                BoardSpace destSpace = boardSpaces[rowClicked+1][colClicked-1];
                destSpace.markDestinationOption();
            }
            if(clickedChecker.getCanSlideUpRight()){
                BoardSpace destSpace = boardSpaces[rowClicked+1][colClicked+1];
                destSpace.markDestinationOption();
            }
            if(clickedChecker.getCanSlideDownLeft()){
                BoardSpace destSpace = boardSpaces[rowClicked-1][colClicked-1];
                destSpace.markDestinationOption();
            }
            if(clickedChecker.getCanSlideDownRight()){
                BoardSpace destSpace = boardSpaces[rowClicked-1][colClicked+1];
                destSpace.markDestinationOption();
            }
        }


    }

    public void updateCheckersWithMovementOptions(){
        updateCheckersWithPossibleJumps();
        updateCheckersWithPossibleSlides();
    }

    public boolean doesCheckerExistWithPossibleJump(){
        //nonsense
        return false;
    }

    public boolean doesCheckerExistWithPossibleSlide(){
        //nonsense
        return false;
    }

    public void updateCheckersWithPossibleJumps(){
        //up left
        for(int row = 0; row < BOARD_NUM_OF_ROWS; row++) {
            for (int col = 0; col < BOARD_NUM_OF_COLS; col++) {
                Checker checker = boardSpaces[row][col].getChecker();
                if(checker != null){
                    int midSpaceRow = row + 1;
                    int midSpaceCol = col - 1;
                    int landSpaceRow = row + 2;
                    int landSpaceCol = col - 2;
                    if(isSpaceOnBoard(landSpaceRow,landSpaceCol)){
                        BoardSpace midSpace = boardSpaces[midSpaceRow][midSpaceCol];
                        BoardSpace landSpace = boardSpaces[landSpaceRow][landSpaceCol];
                        Checker midChecker = midSpace.getChecker();
                        if(midChecker != null && (midChecker.getBelongsToPlayer1() != checker.getBelongsToPlayer1())){
                            if(landSpace.getChecker() == null){
                                //Then we can jump!!
                                checker.setCanJumpUpLeft(true);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean canCheckerJumpRelDir(Checker checker, int startRow, int startCol, int rowDir, int colDir){
        //nonsense
        return false;
    }

    public void updateCheckersWithPossibleSlides(){
        for(int row = 0; row < BOARD_NUM_OF_ROWS; row++){
            for(int col = 0; col < BOARD_NUM_OF_COLS; col++){
                Checker checker = boardSpaces[row][col].getChecker();
                if(checker != null){
                    boolean canSlideUpLeft = canCheckerSlideRelDir(checker, row, col, 1, -1);
                    boolean canSlideUpRight = canCheckerSlideRelDir(checker, row, col, 1, 1);
                    boolean canSlideDownLeft = canCheckerSlideRelDir(checker, row, col, -1, -1);
                    boolean canSlideDownRight = canCheckerSlideRelDir(checker, row, col, -1, 1);
                    checker.setPossibleSlideDirections(canSlideUpLeft, canSlideUpRight, canSlideDownLeft, canSlideDownRight);
                }
            }
        }
    }

    public boolean canCheckerSlideRelDir(Checker checker, int startRow, int startCol, int rowDir, int colDir){
        if(checker != null){
            int destRow = startRow+rowDir;
            int destCol = startCol+colDir;
            if(isSpaceOnBoard(destRow, destCol)){
                if(boardSpaces[destRow][destCol].getChecker() == null){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSpaceOnBoard(int rowNum, int colNum){
        return (rowNum >= 0 && rowNum < Game.BOARD_NUM_OF_ROWS && colNum >= 0 && colNum < Game.BOARD_NUM_OF_COLS);
    }

    public void draw(){
        for(int row = 0; row < Game.BOARD_NUM_OF_ROWS; row++){
            for(int col = 0; col < Game.BOARD_NUM_OF_COLS; col++ ){
                boardSpaces[row][col].draw(row, col);
            }
        }
    }

}
