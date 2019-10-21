package catchBox;

import ga.IntVectorIndividual;

import javax.lang.model.element.VariableElement;
import java.util.Hashtable;
import java.util.LinkedList;

public class CatchIndividual extends IntVectorIndividual<CatchProblemForGA, CatchIndividual> {
    private Hashtable<String, Pair> pairs;

    public CatchIndividual(CatchProblemForGA problem, int size) {
        super(problem, size);
    }

    public CatchIndividual(CatchIndividual original) {
        super(original);
    }

    @Override
    public double computeFitness() {
        fitness = 0;
        LinkedList<Cell> cellsBoxes = problem.getCellsBoxes();
        pairs = problem.getPairsHash();
        Cell cellCatch = problem.getCellCatch();
        Cell door = problem.getDoor();


        //calcular a distancia do carro a 1ª caixa
        fitness += computeFitness(cellCatch, cellsBoxes.get(genome[0]-1));

        //calcular as distancias entre as caixas
        for (int i = 0; i < genome.length-1; i++){
            Cell cell1 = cellsBoxes.get(genome[i]-1); //-1 pq as caixas tao -1 ex: caixa 3 = caixa na posicao 2 no vetor
            Cell cell2 = cellsBoxes.get(genome[i+1]-1);
            fitness += computeFitness(cell1, cell2);
        }

        //calcular a distancia entre a ultima caixa e a porta
        Cell cell1 = cellsBoxes.get(genome[genome.length - 1] - 1);  //-1 pq as caixas tao -1 ex: caixa 3 = caixa na posicao 2 no vetor
        fitness += computeFitness(cell1, door);


        return fitness;
    }

    private double computeFitness(Cell cell1, Cell cell2){
        String key = computeKey(cell1, cell2);
        Pair pair = (Pair) pairs.get(key);
        if(pair == null){
            key = computeKey(cell2, cell1);
            pair = (Pair) pairs.get(key);
        }
        return pair.getValue();
    }


    private String computeKey(Cell cell1, Cell cell2){
        StringBuilder str = new StringBuilder();
        str.append(cell1.getLine());
        str.append("/");
        str.append(cell1.getColumn());
        str.append("#");
        str.append(cell2.getLine());
        str.append("/");
        str.append(cell2.getColumn());
        return str.toString();
    }


    public int[] getGenome() {
        return genome;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("fitness: ");
        sb.append(fitness);
        sb.append("\npath: ");
        for (int i = 0; i <genome.length ; i++) {
            sb.append(genome[i]).append(" ");
        }
        return sb.toString();
    }

    /**
     * @param i
     * @return 1 if this object is BETTER than i, -1 if it is WORST than I and
     * 0, otherwise.
     */
    @Override
    public int compareTo(CatchIndividual i) {
        return (this.fitness == i.getFitness()) ? 0 : (this.fitness < i.getFitness()) ? 1 : -1;
    }

    @Override
    public CatchIndividual clone() {
        return new CatchIndividual(this);

    }
}
