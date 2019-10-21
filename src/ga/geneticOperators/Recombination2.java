package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

public class Recombination2<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {
    private int[] child1, child2, segment1, segment2;

    private int cut1;

    public Recombination2(double probability) {
        super(probability);
    }

    @Override
    public void recombine(I ind1, I ind2) {
        child1 = new int[ind1.getNumGenes()];
        child2 = new int[ind2.getNumGenes()];
        cut1 = GeneticAlgorithm.random.nextInt(ind1.getNumGenes());

        create_Segments(cut1, ind1, ind2);
        crossOver(child1, ind1);
        crossOver(child2, ind2);

        for (int i = 0; i < ind1.getNumGenes(); i++) {
            ind1.setGene(i, child1[i]);
            ind2.setGene(i, child2[i]);
        }
    }

    private boolean check_forDuplicates(int[] offspring, int indexOfElement) {
        for (int index = 0; index < offspring.length; index++) {
            if ((offspring[index] == offspring[indexOfElement]) &&
                    (indexOfElement != index)) {
                return true;
            }
        }
        return false;
    }

    // If Element is Duplicated, replace it by using its mapping //
    private void sort_Duplicates(int[] offspring, int indexOfElement) {
        for (int index = 0; index < segment1.length; index++) {
            if (segment1[index] == offspring[indexOfElement]) {
                offspring[indexOfElement] = segment2[index];
            } else if (segment2[index] == offspring[indexOfElement]) {
                offspring[indexOfElement] = segment1[index];
            }
        }
    }

    private void create_Segments(int cutPoint1, I ind1, I ind2) {
        int capacity_ofSegments = ind1.getNumGenes() - cutPoint1 + 1;
        segment1 = new int[capacity_ofSegments];
        segment2 = new int[capacity_ofSegments];
        int segment1and2Index = 0;
        for (int index = cutPoint1; index < ind1.getNumGenes(); index++) {
            int x = ind1.getGene(index);
            int y = ind2.getGene(index);
            segment1[segment1and2Index] = x;
            segment2[segment1and2Index] = y;
            segment1and2Index++;
        }
    }

    private void insert_Segments(int[] offspring, int[] segment) {
        int segmentIndex = 0;
        for (int index = 0; index < offspring.length; index++) {
            if (index >= cut1) {
                offspring[index] = segment[segmentIndex];
                segmentIndex++;
            }
        }
    }

    // offspring2 gets segment 1, offspring1 gets segment2 //
    public void crossOver(int[] offspring, I ind) {
        if (offspring == child1) {
            int[] segment = segment2;
            insert_Segments(offspring, segment);
        } else if (offspring == child2) {
            int[] segment = segment1;
            insert_Segments(offspring, segment);
        }

        for (int index = 0; index < offspring.length; index++) {
            if (index < cut1) {
                offspring[index] = ind.getGene(index);
            }
        }

        for (int index = 0; index < offspring.length; index++) {
            if (index < cut1) {
                while (check_forDuplicates(offspring, index)) {
                    sort_Duplicates(offspring, index);
                }
            }
        }
    }

    @Override
    public String toString(){
        return "One Cut Recombination (ONECUT)";
    }
}