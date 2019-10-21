package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

public class Mutation3<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public Mutation3(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        int sizeInd = ind.getNumGenes();
        int cut1 = GeneticAlgorithm.random.nextInt(sizeInd);
        int cut2;
        do {
            cut2 = GeneticAlgorithm.random.nextInt(sizeInd);
        }while (cut1==cut2);
        if (cut1 > cut2) {
            int aux = cut1;
            cut1 = cut2;
            cut2 = aux;
        }

        int sizeCuts = cut2 - cut1 + 1;
        int[] genomeValoresEntreCuts = new int[sizeCuts];

        if(sizeInd-sizeCuts == 0){ // se o cut 1 e o cut 2 forem o limit do ind nao vale a pena fazer nada pois nao vai alterar nada
            return;
        }


        //vai do cut1 ao cut2 inclusive e mete os valores no vetor aux
        int aux = 0;
        for(int i = cut1; i <= cut2 ; i++) {
            genomeValoresEntreCuts[aux] = ind.getGene(i);
            aux++;
        }

        aux=0; // retira os valores entre cuts do ind
        for (int i = cut2 + 1; i < sizeInd; i++) {
            ind.setGene(cut1 + aux, ind.getGene(i));
            aux++;
        }

        //cutInsercao = posiçao onde serão inseridos os valores do genome_aux no ind
        //(ind.getNumGenes()-genome_aux.length) para não exceder o tamanho do ind tirando os valores ja "removidos"
        int cutInsercao =  GeneticAlgorithm.random.nextInt(sizeInd-sizeCuts);

        aux = 0; //guarda todos os valores do lado direito do cutInsercao para depois podermos inserilos de novo
        int[] genomeValoresADireitaDoCutInsercao = new int[sizeInd-sizeCuts-cutInsercao];
        for(int i = cutInsercao; i < sizeInd-sizeCuts ; i++) {
            genomeValoresADireitaDoCutInsercao[aux] = ind.getGene(i);
            aux++;
        }

        aux = 0; // adiciona os valores entre os cuts no cutInsercao
        for (int i = cutInsercao; i < sizeCuts + cutInsercao; i++) {
            ind.setGene(i, genomeValoresEntreCuts[aux]);
            aux++;
        }

        aux=0; //adiciona o resto dos valores que foram removidos pela insercao dos cuts na mesma ordem
        for (int i = cutInsercao+ sizeCuts; i < sizeInd; i++) {
            ind.setGene(i, genomeValoresADireitaDoCutInsercao[aux]);
            aux++;
        }
    }

    @Override
    public String toString() {
        return "displacement";
    }
}