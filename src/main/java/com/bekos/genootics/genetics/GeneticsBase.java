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
    public Double getGeneValue(String gene) {
        return genes.get(gene); //Tell me how better to do this, please
    }

    @Override
    public Map<String, Double> getAllGenes() {
        return new HashMap<>();
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
