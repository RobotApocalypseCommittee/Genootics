package com.bekos.genootics.genetics;

import java.util.List;
import java.util.Map;

public interface IGenetics {
    boolean isGM();

    boolean hasBeenEdited();

    boolean hasGene(String gene, GeneticsSide side);
    Double getGeneValue(String gene, GeneticsSide side);

    List<Map<String, Double>> getAllGenes();
    List<Map<String, Double>> getAllGenesDominances();

    Map<String, Double> getGenes(GeneticsSide side);

    Map<String, Double> getGenesDominance(GeneticsSide side);

    void setGenes(Map<String, Double> genes, Map<String, Double> geneDominance, GeneticsSide side);
    void setAllGenes(List<Map<String, Double>> genes, List<Map<String, Double>> geneDominance);

    void setGM(boolean isGM);

    void setHasBeenEdited(boolean hasBeenEdited);

    void updateExpressed();
}
