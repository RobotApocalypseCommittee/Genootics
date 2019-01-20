/*
 * Genootics Minecraft mod adding genetics to Minecraft
 * Copyright (C) 2019  Robot Apocalypse Committee
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

import com.bekos.genootics.exception.GeneticsMismatchException;

import java.util.*;

public class GeneticsBase implements IGenetics {
    private boolean isGM = false;
    private boolean hasBeenEdited = false;

    // IMPORTANT: Gene values are to be percentages (perfection) or simple values (like Jump Boost lvl _2.5_) that you know
    // have some mathematical relevance to compute the aspect you want, but not necessarily it exactly

    private Map<Gene, Double> genesLeft = new HashMap<>();
    private Map<Gene, Double> genesLeftDominance = new HashMap<>();

    private Map<Gene, Double> genesRight = new HashMap<>();
    private Map<Gene, Double> genesRightDominance = new HashMap<>();

    private Map<Gene, Double> genesExpressed = new HashMap<>();
    private Map<Gene, Double> genesExpressedDominance = new HashMap<>();

    @Override
    public boolean isGM() {
        return isGM;
    }

    @Override
    public boolean hasBeenEdited() {
        return hasBeenEdited;
    }

    @Override
    public boolean hasGene(Gene gene, GeneticsSide side) {
        switch (side) {
            case LEFT:
                return genesLeft.containsKey(gene);
            case RIGHT:
                return genesRight.containsKey(gene);
            default:
                return genesExpressed.containsKey(gene);
        }
    }

    @Override
    public Double getGeneValue(Gene gene, GeneticsSide side) {
        switch (side) {
            case LEFT:
                return genesLeft.get(gene);
            case RIGHT:
                return genesRight.get(gene);
            default:
                return genesExpressed.get(gene);
        }
    }

    @Override
    public List<Map<Gene, Double>> getAllGenes() {
        return getMaps(genesLeft, genesRight, genesExpressed);
    }

    private List<Map<Gene, Double>> getMaps(Map<Gene, Double> genesLeft, Map<Gene, Double> genesRight, Map<Gene, Double> genesExpressed) {
        List<Map<Gene, Double>> mapList = new ArrayList<>();
        mapList.add(genesLeft);
        mapList.add(genesRight);
        mapList.add(genesExpressed);
        return mapList;
    }

    @Override
    public List<Map<Gene, Double>> getAllGenesDominances() {
        return getMaps(genesLeftDominance, genesRightDominance, genesExpressedDominance);
    }

    @Override
    public Map<Gene, Double> getGenes(GeneticsSide side) {
        return getGeneStringDoubleMap(side, genesLeft, genesRight, genesExpressed);
    }

    private Map<Gene, Double> getGeneStringDoubleMap(GeneticsSide side, Map<Gene, Double> genesLeft, Map<Gene, Double> genesRight, Map<Gene, Double> genesExpressed) {
        switch (side) {
            case LEFT:
                return genesLeft;
            case RIGHT:
                return genesRight;
            default:
                return genesExpressed;
        }
    }

    @Override
    public Map<Gene, Double> getGenesDominance(GeneticsSide side) {
        return getGeneStringDoubleMap(side, genesLeftDominance, genesRightDominance, genesExpressedDominance);
    }

    @Override
    public void setGenes(Map<Gene, Double> genes, Map<Gene, Double> geneDominance, GeneticsSide side) {
        if (!(genes.keySet().equals(geneDominance.keySet()))) {
            throw new GeneticsMismatchException("Gene and Gene Dominance maps must refer to the same genes (the same keys)");
        }

        switch (side) {
            case RIGHT:
                genesRight = genes;
                genesRightDominance = geneDominance;
                break;
            case LEFT:
                genesLeft = genes;
                genesLeftDominance = geneDominance;
                break;
            default:
                genesExpressed = genes;
                genesExpressedDominance = geneDominance;
                break;
        }
    }

    @Override
    public void setAllGenes(List<Map<Gene, Double>> genes, List<Map<Gene, Double>> geneDominance) {
        for (int i = 0; i < 3; i++) {
            if (!(genes.get(i).keySet().equals(geneDominance.get(i).keySet()))) {
                throw new GeneticsMismatchException("Gene and Gene Dominance maps must refer to the same genes (the same keys)");
            }
        }

        genesLeft = genes.get(0);
        genesRight = genes.get(1);
        genesExpressed = genes.get(2);

        genesLeftDominance = geneDominance.get(0);
        genesRightDominance = geneDominance.get(1);
        genesExpressedDominance = geneDominance.get(2);
    }

    @Override
    public void setGM(boolean isGM) {
        this.isGM = isGM;
    }

    @Override
    public void setHasBeenEdited(boolean hasBeenEdited) {
        this.hasBeenEdited = hasBeenEdited;
    }

    @Override
    public void updateExpressed() {
        if (genesRight == genesLeft && genesRightDominance == genesLeftDominance) {
            genesExpressed = genesLeft;
            genesExpressedDominance = genesLeftDominance;
        } else {
            List<Map<Gene, Double>> geneList = computeResultantGenes();
            genesExpressed = geneList.get(0);
            genesExpressedDominance = geneList.get(1);
        }
    }

    private List<Map<Gene, Double>> computeResultantGenes() {
        /* Long story short, if the gene is shared, it checks the more dominant one
         * and then displays that (after slight modification to its effect and dominance)
         * or if it is unique, it has a 70% chance of being expressed (thematically because
         * the original mob gene has a small dominance anyway) but a much worse degradation
         * than if it is in both sets of genes */

        Random random = new Random();

        Map<Gene, Double> resultantGenes = new HashMap<>();
        Map<Gene, Double> resultantGenesDominance = new HashMap<>();

        // Needed for the sole reason that some genes are deleted for it to work faster,
        // but the genes should not _actually_ be deleted
        Map<Gene, Double> copyOfGenesRight = new HashMap<>(genesRight);

        for (Map.Entry<Gene, Double> gene : genesLeft.entrySet()) {
            if (genesRight.containsKey(gene.getKey())) {
                if (Objects.equals(genesLeftDominance.get(gene.getKey()), genesRightDominance.get((gene.getKey())))) {
                    if (random.nextDouble() > 0.5) {
                        resultantGenes.put(gene.getKey(), gene.getValue() * (0.9 + random.nextDouble() * (1.1 - 0.9)));
                        resultantGenesDominance.put(gene.getKey(), genesLeftDominance.get(gene.getKey()) * (0.9 + random.nextDouble() * (1.1 - 0.9)));
                    } else {
                        resultantGenes.put(gene.getKey(), genesRight.get(gene.getKey()) * (0.9 + random.nextDouble() * (1.1 - 0.9)));
                        resultantGenesDominance.put(gene.getKey(), genesRightDominance.get(gene.getKey()) * (0.9 + random.nextDouble() * (1.1 - 0.9)));
                    }
                } else if (genesLeftDominance.get(gene.getKey()) > genesRightDominance.get(gene.getKey())) {
                    resultantGenes.put(gene.getKey(), gene.getValue() * (0.9 + random.nextDouble() * (1.1 - 0.9)));
                    resultantGenesDominance.put(gene.getKey(), genesLeftDominance.get(gene.getKey()) * (0.9 + random.nextDouble() * (1.1 - 0.9)));
                } else {
                    resultantGenes.put(gene.getKey(), genesRight.get(gene.getKey()) * (0.9 + random.nextDouble() * (1.1 - 0.9)));
                    resultantGenesDominance.put(gene.getKey(), genesRightDominance.get(gene.getKey()) * (0.9 + random.nextDouble() * (1.1 - 0.9)));
                }

                copyOfGenesRight.remove(gene.getKey());
            } else {
                if (random.nextFloat() < 0.7) {
                    resultantGenes.put(gene.getKey(), gene.getValue() * (0.7 + random.nextDouble() * (1.0 - 0.7)));
                    resultantGenesDominance.put(gene.getKey(), genesLeftDominance.get(gene.getKey()) * (0.7 + random.nextDouble() * (1.0 - 0.7)));
                }
            }
        }

        for (Map.Entry<Gene, Double> gene : copyOfGenesRight.entrySet()) {
            if (random.nextFloat() < 0.7) {
                resultantGenes.put(gene.getKey(), gene.getValue() * (0.7 + random.nextDouble() * (1.0 - 0.7)));
                resultantGenesDominance.put(gene.getKey(), genesRightDominance.get(gene.getKey()) * (0.7 + random.nextDouble() * (1.0 - 0.7)));
            }
        }

        List<Map<Gene, Double>> returnList = new ArrayList<>();
        returnList.add(resultantGenes);
        returnList.add(resultantGenesDominance);

        return returnList;
    }
}
