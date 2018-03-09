package com.bekos.genootics.genetics;

import java.util.List;
import java.util.Map;

public interface IGenetics {
    boolean isGM();

    boolean hasBeenEdited();

    boolean hasGene(String gene);
    Double getGeneValue(String gene);

    Double getLeftGeneValue(String gene);
    Double getRightGeneValue(String gene);

    List<Map<String, Double>> getAllGenes();
    List<Map<String, Double>> getAllGenesDominances();

    Map<String, Double> getLeftGenes();
    Map<String, Double> getRightGenes();

    Map<String, Double> getExpressedGenes();

    Map<String, Double> getLeftGenesDominance();
    Map<String, Double> getRightGenesDominance();

    Map<String, Double> getExpressedGenesDominance();

    void setGenes(Map<String, Double> genes, Map<String, Double> geneDominance, GeneticsSide side);
    void setAllGenes(List<Map<String, Double>> genes, List<Map<String, Double>> geneDominance);

    void setGM(boolean isGM);

    void setHasBeenEdited(boolean hasBeenEdited);

    void updateExpressed();
}
