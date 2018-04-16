package net.myrobot;

class Robot {

    private static final int indexCharRight = 0;
    private static final int indexCharUp = 1;
    private static final int indexCharLeft = 2;
    private static final int indexCharDown = 3;

    private final char[] charsField;

    private int row;
    private int col;

    private final Direction direction;

    private final Room room;

    public Robot(int row, int col, Direction direction, Room room, char[] charsField) {

        this.charsField = charsField.clone();

        this.row = row;
        this.col = col;
        this.direction = direction;
        this.room = room;
        this.room.setCharAt(row, col, getDirectionChar());
    }

    public void act() {
        if (free()) move();
        else turnRight();
    }

    private boolean free() {
        int tempRow = row + direction.deltaRow;
        int tempCol = col + direction.deltaCol;
        return room.getCharAt(tempRow, tempCol) == room.getFree();
    }

    private void move(){
        if (!free()) return;
        this.room.setCharAt(row, col, room.getFree());
        row += direction.deltaRow;
        col += direction.deltaCol;
        this.room.setCharAt(row, col, getDirectionChar());
    }

    @SuppressWarnings("unused")
    private void turnLeft() {
        direction.turnLeft();
        this.room.setCharAt(row, col, getDirectionChar());
    }

    private void turnRight() {
        direction.turnRight();
        this.room.setCharAt(row, col, getDirectionChar());
    }

    private char getDirectionChar() {
        char headsTo;
        if (direction.isRight()) headsTo = charsField[indexCharRight];
        else if (direction.isUP()) headsTo = charsField[indexCharUp];
        else if (direction.isLeft()) headsTo = charsField[indexCharLeft];
        else if (direction.isDown()) headsTo = charsField[indexCharDown];
        else headsTo = '?';
        return headsTo;
    }
}
