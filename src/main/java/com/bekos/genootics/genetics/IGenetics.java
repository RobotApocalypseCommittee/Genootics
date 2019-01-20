/*
 * Genootics Minecraft mod adding genetics to Minecraft
 * Copyright (C) 2018  Robot Apocalypse Committee
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
