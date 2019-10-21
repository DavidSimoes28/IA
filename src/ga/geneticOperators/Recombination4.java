package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

public class Recombination4<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {
    private int[] child1, child2, cuts, segment1, segment2;

    public Recombination4(double probability) {
        super(probability);
    }

    @Override
    public void recombine(I ind1, I ind2) {
        int cut, sizeCuts;
        child1 = new int[ind1.getNumGenes()];
        child2 = new int[ind2.getNumGenes()];
        int sizeInd = ind1.getNumGenes();

        do{
            sizeCuts = GeneticAlgorithm.random.nextInt(sizeInd);
        }while(sizeCuts == 0);
        if (sizeCuts >= sizeInd - 1){
            I aux = ind1;
            ind1 = ind2;
            ind2 = aux;
            return;
        }
        cuts = new int[sizeCuts]; //random de numero de cuts a fazer



        for (int i = 0; i < cuts.length; i++) {
            do{
                cut = GeneticAlgorithm.random.nextInt(sizeInd);
            }while(contains(cuts, cut)); //faz com que o cut seja unique nos valores ja no array
            cuts[i] = cut;
        }

        for (int i: cuts) {
            child1[i] = ind2.getGene(i);
            child2[i] = ind1.getGene(i);
        }


        int aux = 0;
        for (int i = 0; i < sizeInd; i++) {
            if(child1[i] == 0){ //esta condicao e valida para o child 1 e 2 pois tem o mesmo tamnho e mesmos cuts
                for (int j = 0; j < sizeInd; j++) {
                    if(!contains(child1, ind1.getGene(j)) && aux != 1){
                        child1[i] = ind1.getGene(j);
                        if(aux == 2){ //este if server para sair caso ja tenha inserido na child1 e child2
                            break;
                        }
                        aux=1;
                    }
                    if(!contains(child2, ind2.getGene(j)) && aux != 2){
                        child2[i] = ind2.getGene(j);
                        if(aux == 1){ //este if server para sair caso ja tenha inserido na child1 e child2
                            break;
                        }
                        aux=2;
                    }
                }
                aux = 0;
            }
        }

        for (int i = 0; i < sizeInd; i++) {
            ind1.setGene(i, child1[i]);
            ind2.setGene(i, child2[i]);
        }
    }


    public boolean contains(int[] array, int value){
        for (int val: array) {
            if(val == value){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        return "Position Based Crossover (POS)";
    }
}
