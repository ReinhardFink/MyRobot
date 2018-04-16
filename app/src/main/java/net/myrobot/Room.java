package net.myrobot;

class Room {

    private static final int indexCharFree = 0;
    private static final int indexCharWall = 1;
    private final char[] charsField;

    private final char[][] grid;

    public Room(int rows, int cols, char[] charsField) {

        this.charsField = charsField.clone();

        grid = new char[rows][cols];

        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                grid[row][col] = charsField[indexCharFree];

        for (int row = 0; row < rows; row++)
            grid[row][0] = grid[row][cols - 1] = charsField[indexCharWall];

        for (int col = 0; col < cols; col++)
            grid[0][col] = grid[rows - 1][col] = charsField[indexCharWall];
    }

    public String toString() {
        char[] chars = new char[grid.length * (grid[0].length + 1)];
        int i = 0;
        //noinspection ForLoopReplaceableByForEach
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++)
                chars[i++] = grid[row][col];
            chars[i++] = '\n';
        }
        return new String(chars);
    }

    public char getCharAt(int row, int col) {
        return grid[row][col];
    }

    public void setCharAt(int row, int col, char c) {
        grid[row][col] = c;
    }

    public char getFree() {
        return charsField[indexCharFree];
    }
}
