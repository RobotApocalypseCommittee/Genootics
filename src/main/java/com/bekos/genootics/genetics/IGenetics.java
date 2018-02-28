package com.bekos.genootics.genetics;

import java.util.Map;

public interface IGenetics {
    boolean isGM();
    Double getGeneValue(String gene);

    Map<String, Double> getAllGenes();

    void setGenes(Map<String, Double> genes);
    void setGM(boolean isGM);
}
