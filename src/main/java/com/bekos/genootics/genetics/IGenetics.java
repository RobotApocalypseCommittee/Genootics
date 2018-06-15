package com.bekos.genootics.genetics;

import java.util.List;
import java.util.Map;

public interface IGenetics {
    boolean isGM();

    boolean hasBeenEdited();

    boolean hasGene(Gene gene, GeneticsSide side);
    Double getGeneValue(Gene gene, GeneticsSide side);

    List<Map<Gene, Double>> getAllGenes();
    List<Map<Gene, Double>> getAllGenesDominances();

    Map<Gene, Double> getGenes(GeneticsSide side);

    Map<Gene, Double> getGenesDominance(GeneticsSide side);

    void setGenes(Map<Gene, Double> genes, Map<Gene, Double> geneDominance, GeneticsSide side);
    void setAllGenes(List<Map<Gene, Double>> genes, List<Map<Gene, Double>> geneDominance);

    void setGM(boolean isGM);

    void setHasBeenEdited(boolean hasBeenEdited);

    void updateExpressed();
}
