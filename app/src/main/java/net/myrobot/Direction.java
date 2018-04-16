package net.myrobot;

@SuppressWarnings("unused")
public class Direction implements Cloneable {

    private static final Direction RIGHT = new Direction(0,1);
    private static final Direction UP = new Direction(-1,0);
    private static final Direction LEFT = new Direction(0,-1);
    private static final Direction DOWN = new Direction(1,0);

    public int deltaRow;
    public int deltaCol;

    private Direction(int deltaRow, int deltaCol) {
        this.deltaRow = deltaRow;
        this.deltaCol = deltaCol;
    }

    private boolean equals(Direction d) {
        return d.deltaRow == deltaRow && d.deltaCol == deltaCol;
    }

    public static Direction getRIGHT() {
        Direction direction = null;
        try {
            direction = (Direction)RIGHT.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return direction;
    }

    public static Direction getUP() {
        Direction direction = null;
        try {
            direction = (Direction)UP.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return direction;
    }

    public static Direction getLEFT() {
        Direction direction = null;
        try {
            direction = (Direction)LEFT.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return direction;
    }

    public static Direction getDOWN() {
        Direction direction = null;
        try {
            direction = (Direction)DOWN.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return direction;
    }

    /*
     * turn*()
     * (x_new/y_new) = [(cos(90)/sin(90))//(-sin(90)/cos(90)] (x/y)
     * cos(90) = 0; sin(90) = 1
     */
    public void turnLeft() {
        int dx = -deltaCol;
        int dy = deltaRow;
        deltaRow = dx;
        deltaCol = dy;
    }

    public void turnRight() {
        int dx = deltaCol;
        int dy = -deltaRow;
        deltaRow = dx;
        deltaCol = dy;
    }

    public boolean isRight() {
        return equals(RIGHT);
    }

    public boolean isUP() {
        return equals(UP);
    }

    public boolean isLeft() {
        return equals(LEFT);
    }

    public boolean isDown() {
        return equals(DOWN);
    }
}
