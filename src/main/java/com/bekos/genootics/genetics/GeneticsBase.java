package com.bekos.genootics.genetics;

import com.bekos.genootics.exception.GeneticsMismatchException;

import java.util.*;

public class GeneticsBase implements IGenetics {
    private boolean isGM = false;
    private boolean hasBeenEdited = false;

    // IMPORTANT: Gene values are to be percentages (perfection) or simple values (like Jump Boost lvl _2.5_) that you know
    // have some mathematical relevance to compute the aspect you want, but not necessarily it exactly

    private Map<String, Double> genesLeft = new HashMap<>();
    private Map<String, Double> genesLeftDominance = new HashMap<>();

    private Map<String, Double> genesRight = new HashMap<>();
    private Map<String, Double> genesRightDominance = new HashMap<>();

    private Map<String, Double> genesExpressed = new HashMap<>();
    private Map<String, Double> genesExpressedDominance = new HashMap<>();

    @Override
    public boolean isGM() {
        return isGM;
    }

    @Override
    public boolean hasBeenEdited() {
        return hasBeenEdited;
    }

    @Override
    public boolean hasGene(String gene) {
        return genesExpressed.containsKey(gene);
    }

    @Override
    public Double getGeneValue(String gene) {
        return genesExpressed.get(gene);
    }

    @Override
    public Double getLeftGeneValue(String gene) {
        return genesLeft.get(gene);
    }

    @Override
    public Double getRightGeneValue(String gene) {
        return genesRight.get(gene);
    }

    @Override
    public List<Map<String, Double>> getAllGenes() {
        List<Map<String, Double>> mapList = new ArrayList<>();
        mapList.add(genesLeft);
        mapList.add(genesRight);
        mapList.add(genesExpressed);
        return mapList;
    }

    @Override
    public List<Map<String, Double>> getAllGenesDominances() {
        List<Map<String, Double>> mapList = new ArrayList<>();
        mapList.add(genesLeftDominance);
        mapList.add(genesRightDominance);
        mapList.add(genesExpressedDominance);
        return mapList;
    }

    @Override
    public Map<String, Double> getLeftGenes() {
        return genesLeft;
    }

    @Override
    public Map<String, Double> getLeftGenesDominance() {
        return genesLeftDominance;
    }

    @Override
    public Map<String, Double> getRightGenes() {
        return genesRight;
    }

    @Override
    public Map<String, Double> getRightGenesDominance() {
        return genesRightDominance;
    }

    @Override
    public Map<String, Double> getExpressedGenes() {
        return genesExpressed;
    }

    @Override
    public Map<String, Double> getExpressedGenesDominance() {
        return genesExpressedDominance;
    }

    @Override
    public void setGenes(Map<String, Double> genes, Map<String, Double> geneDominance, GeneticsSide side) {
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
    public void setAllGenes(List<Map<String, Double>> genes, List<Map<String, Double>> geneDominance) {
        for (int i=0; i < 3; i++) {
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
            List<Map<String, Double>> geneList = computeResultantGenes();
            genesExpressed = geneList.get(0);
            genesExpressedDominance = geneList.get(1);
        }
    }

    private List<Map<String, Double>> computeResultantGenes() {
        /* Long story short, if the gene is shared, it checks the more dominant one
         * and then displays that (after slight modification to its effect and dominance)
         * or if it is unique, it has a 70% chance of being expressed (thematically because
         * the original mob gene has a small dominance anyway) but a much worse degradation
         * than if it is in both sets of genes */

        Random random = new Random();

        Map<String, Double> resultantGenes = new HashMap<>();
        Map<String, Double> resultantGenesDominance = new HashMap<>();

        // Needed for the sole reason that some genes are deleted for it to work faster,
        // but the genes should not _actually_ be deleted
        Map<String, Double> copyOfGenesRight = new HashMap<>(genesRight);

        for (Map.Entry<String, Double> gene : genesLeft.entrySet()) {
            if (genesRight.containsKey(gene.getKey())) {
                if (Objects.equals(genesLeftDominance.get(gene.getKey()), genesRightDominance.get((gene.getKey())))) {
                    if (random.nextDouble() > 0.5) {
                        resultantGenes.put(gene.getKey(), gene.getValue()*(0.9 + random.nextDouble() * (1.1-0.9)));
                        resultantGenesDominance.put(gene.getKey(), genesLeftDominance.get(gene.getKey())*(0.9 + random.nextDouble() * (1.1-0.9)));
                    } else {
                        resultantGenes.put(gene.getKey(), genesRight.get(gene.getKey())*(0.9 + random.nextDouble() * (1.1-0.9)));
                        resultantGenesDominance.put(gene.getKey(), genesRightDominance.get(gene.getKey())*(0.9 + random.nextDouble() * (1.1-0.9)));
                    }
                } else if (genesLeftDominance.get(gene.getKey()) > genesRightDominance.get(gene.getKey())) {
                    resultantGenes.put(gene.getKey(), gene.getValue()*(0.9 + random.nextDouble() * (1.1-0.9)));
                    resultantGenesDominance.put(gene.getKey(), genesLeftDominance.get(gene.getKey())*(0.9 + random.nextDouble() * (1.1-0.9)));
                } else {
                    resultantGenes.put(gene.getKey(), genesRight.get(gene.getKey())*(0.9 + random.nextDouble() * (1.1-0.9)));
                    resultantGenesDominance.put(gene.getKey(), genesRightDominance.get(gene.getKey())*(0.9 + random.nextDouble() * (1.1-0.9)));
                }

                copyOfGenesRight.remove(gene.getKey());
            } else {
                if (random.nextFloat() < 0.7) {
                    resultantGenes.put(gene.getKey(), gene.getValue()*(0.7 + random.nextDouble() * (1.0-0.7)));
                    resultantGenesDominance.put(gene.getKey(), genesLeftDominance.get(gene.getKey())*(0.7 + random.nextDouble() * (1.0-0.7)));
                }
            }
        }

        for (Map.Entry<String, Double> gene : copyOfGenesRight.entrySet()) {
            if (random.nextFloat() < 0.7) {
                resultantGenes.put(gene.getKey(), gene.getValue()*(0.7 + random.nextDouble() * (1.0-0.7)));
                resultantGenesDominance.put(gene.getKey(), genesRightDominance.get(gene.getKey())*(0.7 + random.nextDouble() * (1.0-0.7)));
            }
        }

        List<Map<String, Double>> returnList = new ArrayList<>();
        returnList.add(resultantGenes);
        returnList.add(resultantGenesDominance);

        return returnList;
    }
}
