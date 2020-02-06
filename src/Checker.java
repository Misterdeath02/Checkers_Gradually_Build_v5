import java.awt.*;

public class Checker {
    private boolean belongsToPlayer1;
    private Color color;

    private boolean canJumpUpLeft;
    private boolean canJumpUpRight;
    private boolean canJumpDownLeft;
    private boolean canJumpDownRight;
    private boolean canSlideUpLeft;
    private boolean canSlideUpRight;
    private boolean canSlideDownLeft;
    private boolean canSlideDownRight;

    public Checker(boolean belongsToPlayer1){
        this.belongsToPlayer1 = belongsToPlayer1;
        color = belongsToPlayer1 ? Game.PLAYER1_CHECKER_COLOR : Game.PLAYER2_CHECKER_COLOR;
        resetMovementOptions();
    }

    public boolean canJump(){
        //nonsense
        return false;
    }

    public boolean canSlide(){
        if(canSlideUpRight || canSlideUpLeft || canSlideDownRight || canSlideDownLeft){
            return true;
        }
        return false;
    }

    public void resetMovementOptions(){
        canJumpUpLeft = false;
        canJumpUpRight = false;
        canJumpDownLeft = false;
        canJumpDownRight = false;
        canSlideUpLeft = false;
        canSlideUpRight = false;
        canSlideDownLeft = false;
        canSlideDownRight = false;
    }


    public void setCanJumpUpLeft(boolean canJumpUpLeft){
        this.canJumpUpLeft = canJumpUpLeft;
    }

    public void setPossibleJumpDirections(boolean canJumpUpLeft, boolean canJumpUpRight, boolean canJumpDownLeft, boolean canJumpDownRight){
        //nonsense
    }

    public void setPossibleSlideDirections(boolean canSlideUpLeft, boolean canSlideUpRight, boolean canSlideDownLeft, boolean canSlideDownRight){
        this.canSlideUpLeft = canSlideUpLeft;
        this.canSlideUpRight = canSlideUpRight;
        this.canSlideDownLeft = canSlideDownLeft;
        this.canSlideDownRight = canSlideDownRight;
    }

    public boolean getBelongsToPlayer1() {
        return belongsToPlayer1;
    }

    public boolean getCanJumpUpLeft() {
        return canJumpUpLeft;
    }

    public boolean getCanJumpUpRight() {
        return canJumpUpRight;
    }

    public boolean getCanJumpDownLeft() {
        return canJumpDownLeft;
    }

    public boolean getCanJumpDownRight() {
        return canJumpDownRight;
    }

    public boolean getCanSlideUpLeft() {
        return canSlideUpLeft;
    }

    public boolean getCanSlideUpRight() {
        return canSlideUpRight;
    }

    public boolean getCanSlideDownLeft() {
        return canSlideDownLeft;
    }

    public boolean getCanSlideDownRight() {
        return canSlideDownRight;
    }

    public void draw(int rowIndex, int colIndex){
        if(canJump() || canSlide()){
            StdDraw.setPenColor(StdDraw.YELLOW);
            StdDraw.filledCircle(colIndex+0.5, rowIndex+0.5, 0.50);
        }

        StdDraw.setPenColor(color);
        StdDraw.filledCircle(colIndex+0.5, rowIndex+0.5, 0.37);
    }


}
