package catchBox;

import agentSearch.Action;
import agentSearch.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;

public class CatchState extends State implements Cloneable {
    public static Cell GOAL_POSITION;
    protected int[][] matrix; //Matrix lida
    private int lineCatch;
    private int columnCatch;
    private int steps; // regista os passos dados
    private int numBoxes;
    private int lineDoor;
    private int columnDoor;


    public CatchState(int[][] matrix) {
        this.matrix = new int[matrix.length][matrix.length];
        this.steps = 0;
        this.numBoxes = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];
                if (this.matrix[i][j] == Properties.CATCH) {
                    lineCatch = i;
                    columnCatch = j;
                }
                if(this.matrix[i][j] == Properties.DOOR){
                    lineDoor = i;
                    columnDoor = j;
                }
            }
        }
    }


    public CatchState(int[][] matrix, int lineCatch, int columnCatch) {
        //done usado para clonar este nao faz o if todas as vezes por isso e mais optimizado para usar no clone()
        this.matrix = new int[matrix.length][matrix.length];
        this.steps = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }
        this.lineCatch = lineCatch;
        this.columnCatch = columnCatch;
    }

    public void executeAction(Action action) {
        action.execute(this);
        steps++;
        fireUpdatedEnvironment();
    }

    public boolean canMoveUp() {
        //nos consideramos que o catch pode passar pela porta
        return lineCatch != 0 && matrix[lineCatch-1][columnCatch] != Properties.WALL;
    }

    public boolean canMoveRight() {
        //nos consideramos que o catch pode passar pela porta
        return columnCatch != matrix.length - 1 && matrix[lineCatch][columnCatch+1] != Properties.WALL;
    }

    public boolean canMoveDown() {
        //nos consideramos que o catch pode passar pela porta
        return lineCatch != matrix.length - 1 && matrix[lineCatch+1][columnCatch] != Properties.WALL;
    }

    public boolean canMoveLeft() {
        //nos consideramos que o catch pode passar pela porta
        return columnCatch != 0 && matrix[lineCatch][columnCatch-1] != Properties.WALL;
    }

    public void moveUp() {
        if(matrix[lineCatch - 1][columnCatch] == Properties.BOX){
            numBoxes++;
        }

        //este if e caso ele passe em cima da porta para nao a apagar
        if(lineCatch == lineDoor && columnCatch == columnDoor){
            matrix[lineCatch][columnCatch] = Properties.DOOR;
        }else{
            matrix[lineCatch][columnCatch] = Properties.EMPTY;
        }

        matrix[--lineCatch][columnCatch] = Properties.CATCH;

    }

    public void moveRight() {
        if(matrix[lineCatch][columnCatch + 1] == Properties.BOX){
            numBoxes++;
        }

        //este if e caso ele passe em cima da porta para nao a apagar
        if(lineCatch == lineDoor && columnCatch == columnDoor){
            matrix[lineCatch][columnCatch] = Properties.DOOR;
        }else{
            matrix[lineCatch][columnCatch] = Properties.EMPTY;
        }

        matrix[lineCatch][++columnCatch] = Properties.CATCH;

    }

    public void moveDown() {
        if(matrix[lineCatch + 1][columnCatch] == Properties.BOX){
            numBoxes++;
        }

        //este if e caso ele passe em cima da porta para nao a apagar
        if(lineCatch == lineDoor && columnCatch == columnDoor){
            matrix[lineCatch][columnCatch] = Properties.DOOR;
        }else{
            matrix[lineCatch][columnCatch] = Properties.EMPTY;
        }

        matrix[++lineCatch][columnCatch] = Properties.CATCH;

    }

    public void moveLeft() {
        if(matrix[lineCatch][columnCatch - 1] == Properties.BOX){
            numBoxes++;
        }

        //este if e caso ele passe em cima da porta para nao a apagar
        if(lineCatch == lineDoor && columnCatch == columnDoor){
            matrix[lineCatch][columnCatch] = Properties.DOOR;
        }else{
            matrix[lineCatch][columnCatch] = Properties.EMPTY;
        }

        matrix[lineCatch][--columnCatch] = Properties.CATCH;

    }

    public int getNumBox() {
        return numBoxes;
    }

    public void setCellCatch(int line, int column) {
        matrix[lineCatch][columnCatch] = Properties.EMPTY;
        lineCatch = line;
        columnCatch = column;
        matrix[line][column] = Properties.CATCH;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setGoal(int line, int column) {
        GOAL_POSITION = new Cell(line, column);
    }

    public int getSteps() {
        return steps;
    }

    public int getSize() {
        return matrix.length;
    }

    public int computeHeuristic(Cell finalState){
        return abs(finalState.getColumn()-this.columnCatch) + abs(finalState.getLine()-this.lineCatch);
    }

    public Color getCellColor(int line, int column) {
        switch (matrix[line][column]) {
            case Properties.BOX:
                return Properties.COLORBOX;
            case Properties.CATCH:
                return Properties.COLORCATCH;
            case Properties.DOOR:
                return Properties.COLORDOOR;
            case Properties.WALL:
                return Properties.COLORWALL;
            default:
                return Properties.COLOREMPTY;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CatchState)) {
            return false;
        }

        CatchState o = (CatchState) other;
        if (matrix.length != o.matrix.length) {
            return false;
        }

        return Arrays.deepEquals(matrix, o.matrix);
    }

    @Override
    public int hashCode() {
        return 97 * 7 + Arrays.deepHashCode(this.matrix);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            buffer.append('\n');
            for (int j = 0; j < matrix.length; j++) {
                buffer.append(matrix[i][j]);
                buffer.append(' ');
            }
        }
        return buffer.toString();
    }

    @Override
    public CatchState clone() {
        return new CatchState(matrix, lineCatch, columnCatch);
    }

    //Listeners
    private final ArrayList<EnvironmentListener> listeners = new ArrayList<>();

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public synchronized void removeEnvironmentListener(EnvironmentListener l) {
        listeners.remove(l);
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }

    public int getLineCatch() {
        return lineCatch;
    }

    public int getColumnCatch() {
        return columnCatch;
    }
}
