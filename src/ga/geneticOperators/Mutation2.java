package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

public class Mutation2<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public Mutation2(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        int cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        int cut2;
        do {
            cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        }while (cut1==cut2);
        if (cut1 > cut2) {
            int aux = cut1;
            cut1 = cut2;
            cut2 = aux;
        }

        int tamanho = cut2 - cut1 + 1;
        int[] genome_entre_cuts = new int[tamanho];
        int increment = 0;

        //guarda os genes que estão entre o cut1 e cut2
        for(int i = cut2; i >= cut1 ; i--) {
            genome_entre_cuts[increment] = ind.getGene(i);
            increment++;
        }

        increment=0;//reset ao increment

        int new_seed = ind.getNumGenes() - tamanho; // tamanho
        if(new_seed == 0){//se new_seed for igual a zero sai inverte o que está no ind tudo e sai
            for (int i = 0; i < ind.getNumGenes(); i++) {
                ind.setGene(i,genome_entre_cuts[i]);
            }
            return;
        }

        int[] genome_fora_cuts = new int[new_seed];

        //guarda os genes que estão fora do cut1 e cut2
        for (int i = 0; i < ind.getNumGenes(); i++) {
            if(i<cut1 || i>cut2){
                genome_fora_cuts[increment] = ind.getGene(i);
                increment++;
            }
        }

        int new_random =  GeneticAlgorithm.random.nextInt(new_seed);// novo valor para colocar os valores invertidos

        int inc_fora = 0;// incremento para o genome_fora_cuts
        int inc_entre = 0;// incremento para o genome_entre_cuts

        //redefenir inteiramente o ind
        for(int i = 0; i < ind.getNumGenes(); i++) {
            if(i>=new_random && i<new_random+tamanho){
                ind.setGene(i,genome_entre_cuts[inc_entre]);
                inc_entre++;
            }else{
                ind.setGene(i,genome_fora_cuts[inc_fora]);
                inc_fora++;
            }
        }
    }

    @Override
    public String toString() {
        return "inversion";
    }
}