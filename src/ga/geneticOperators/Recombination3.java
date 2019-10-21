package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

public class Recombination3<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {
    private int[] ind1_aux,ind2_aux;
    private int cut1,cut2;

    public Recombination3(double probability) {
        super(probability);
    }

    @Override
    public void recombine(I ind1, I ind2) {
        ind1_aux = new int[ind1.getNumGenes()];
        ind2_aux = new int[ind2.getNumGenes()];

        cut1 = GeneticAlgorithm.random.nextInt(ind1.getNumGenes());
        do {
            cut2 = GeneticAlgorithm.random.nextInt(ind2.getNumGenes());
        }while (cut1==cut2);
        if (cut1 > cut2) {
            int aux = cut1;
            cut1 = cut2;
            cut2 = aux;
        }

        if(cut2-cut1 >= ind1.getNumGenes()-2){// pequena otimizacao
            return;
        }

        for (int i = cut1; i <= cut2; i++) {
            ind1_aux[i] = ind1.getGene(i);
            ind2_aux[i] = ind2.getGene(i);
        }

        int[] aux1 = new int[ind1.getNumGenes() - (cut2 - cut1)-1];
        int[] aux2 = new int[ind1.getNumGenes() - (cut2 - cut1)-1];

        int j = 0;
        for (int i = cut2 + 1; i < ind1.getNumGenes(); i++) {
            if(!contains(ind2_aux, ind1.getGene(i))){
                aux2[j] = ind1.getGene(i);
                j++;
            }
        }
        for (int i = 0; i <= cut2; i++) {
            if(!contains(ind2_aux, ind1.getGene(i))){
                aux2[j] = ind1.getGene(i);
                j++;
            }
        }

        j = 0;
        for (int i = cut2 + 1; i < ind2.getNumGenes(); i++) {
            if(!contains(ind1_aux, ind2.getGene(i))){
                aux1[j] = ind2.getGene(i);;
                j++;
            }
        }
        for (int i = 0; i <= cut2; i++) {
            if(!contains(ind1_aux, ind2.getGene(i))){
                aux1[j] = ind2.getGene(i);;
                j++;
            }
        }

        j = 0;
        for (int i = cut2+1; i < ind1.getNumGenes(); i++) {
            ind1_aux[i] = aux1[j];
            ind2_aux[i] = aux2[j];
            j++;
        }
        for (int i = 0; i < cut1; i++) {
            ind1_aux[i] = aux1[j];
            ind2_aux[i] = aux2[j];
            j++;
        }


        for (int i = 0; i < ind1.getNumGenes(); i++) {
            ind1.setGene(i, ind1_aux[i]);
            ind2.setGene(i, ind2_aux[i]);
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
        return "Order Crossover (OX1)";
    }
}