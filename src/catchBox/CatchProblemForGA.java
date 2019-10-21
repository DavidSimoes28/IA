package catchBox;

import ga.Problem;

import java.util.Hashtable;
import java.util.LinkedList;

//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class CatchProblemForGA implements Problem<CatchIndividual> {
    private LinkedList<Cell> cellsBoxes; //tem as posicoes das caixas
    private LinkedList<Pair> pairs; //pares tipo carro a caixa 1, carro a caixa 2, ... , caixa 1 a caixa 2, caixa 1 a caixa 3, ... (tambem tem o value da distencai entre os pares)
    private Hashtable<String, Pair> pairsHash; //pares tipo carro a caixa 1, carro a caixa 2, ... , caixa 1 a caixa 2, caixa 1 a caixa 3, ... (tambem tem o value da distencai entre os pares)
    private Cell cellCatch; // posicao do catch
    private Cell door; // posicao da door

    public CatchProblemForGA( LinkedList<Cell> cellsBoxes, LinkedList<Pair> pairs, Cell cellCatch, Cell door) {
        this.cellsBoxes = cellsBoxes;
        this.pairs = pairs;
        makeHashTable();
        this.cellCatch = cellCatch;
        this.door = door;
    }

    @Override
    public CatchIndividual getNewIndividual() {
        return new CatchIndividual(this,cellsBoxes.size());
    }

    private void makeHashTable(){
        this.pairsHash = new Hashtable<>();
        StringBuilder str;
        String key;
        for (Pair p : getPairs()) {
            str = new StringBuilder();
            str.append(p.getCell1().getLine());
            str.append("/");
            str.append(p.getCell1().getColumn());
            str.append("#");
            str.append(p.getCell2().getLine());
            str.append("/");
            str.append(p.getCell2().getColumn());
            key = str.toString();
            pairsHash.put(key, p);
        }
    }

    @Override
    public String toString() {
        return "CatchProblemForGA";
    }

    public LinkedList<Cell> getCellsBoxes() {
        return cellsBoxes;
    }

    public LinkedList<Pair> getPairs() {
        return pairs;
    }

    public Cell getCellCatch() {
        return cellCatch;
    }

    public Cell getDoor() {
        return door;
    }

    public Hashtable<String, Pair> getPairsHash() {
        return pairsHash;
    }
}
