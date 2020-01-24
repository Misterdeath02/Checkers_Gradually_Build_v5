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
        //nonsense
    }

    public void resetAllCheckerMovementOptions(){
        //nonsense
    }

    public void resetAllSpaceMarkings(){
        //nonsense
    }

    public void setSpaceSelected(int rowClicked, int colClicked, int prevRowClicked, int prevColClicked){
        BoardSpace spaceClicked = boardSpaces[rowClicked][colClicked];
        spaceClicked.setIsSelected(true);
        Checker clickedChecker = spaceClicked.getChecker();
        if(clickedChecker.canSlide()){
            BoardSpace destSpace = boardSpaces[rowClicked+1][colClicked+1];
            destSpace.markDestinationOption();
        }
        if(spaceClicked.getIsDestinationOption()){

        }
    }

    public void updateCheckersWithMovementOptions(){
        updateCheckersWithPossibleJumps();
        updateCheckersWithPossibleSlides();
        //nonsense
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
        //nonsense
    }

    public boolean canCheckerJumpRelDir(Checker checker, int startRow, int startCol, int rowDir, int colDir){
        //nonsense
        return false;
    }

    public void updateCheckersWithPossibleSlides(){
        //Checks which checkers could slide UpRight
        for(int row = 0; row < BOARD_NUM_OF_ROWS; row++){
            for(int col = 0; col < BOARD_NUM_OF_COLS; col++){
                Checker checker = boardSpaces[row][col].getChecker();
                if(checker != null){
                    int destRow = row+1;
                    int destCol = col+1;
                    if(isSpaceOnBoard(destRow, destCol)){
                        if(boardSpaces[destRow][destCol].getChecker() == null){
                            checker.setCanSlideUpRight(true);
                        }
                    }
                }
            }
        }
    }

    public boolean canCheckerSlideRelDir(Checker checker, int startRow, int startCol, int rowDir, int colDir){
        //nonsense
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
