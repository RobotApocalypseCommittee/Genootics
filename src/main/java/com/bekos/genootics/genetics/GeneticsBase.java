package com.bekos.genootics.genetics;

import java.util.*;

public class GeneticsBase implements IGenetics {
    private boolean isGM = false;

    private Map<String, Double> genes = new HashMap<>();

    @Override
    public boolean isGM() {
        return isGM;
    }

    @Override
    public boolean hasGene(String gene) {
        return genes.containsKey(gene);
    }

    @Override
    public Double getGeneValue(String gene) {
        return genes.get(gene);
    }

    @Override
    public Map<String, Double> getAllGenes() {
        return genes;
    }

    @Override
    public void setGenes(Map<String, Double> genes) {
        this.genes = genes;
    }

    @Override
    public void setGM(boolean isGM) {
        this.isGM = isGM;
    }
}
