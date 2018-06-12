package com.bekos.genootics.genetics;

import net.minecraft.entity.EntityAgeable;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;

@Mod.EventBusSubscriber
public class BabySpawnEventHandler {

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void onBabyBorn(BabyEntitySpawnEvent event) {
        System.out.println("BABY BORN!");
        EntityAgeable father = (EntityAgeable) event.getParentA();
        EntityAgeable mother = (EntityAgeable) event.getParentB();

        IGenetics fatherGenetics = father.getCapability(GeneticsProvider.GENETICS_CAPABILITY, null);
        IGenetics motherGenetics = mother.getCapability(GeneticsProvider.GENETICS_CAPABILITY, null);

        if (!fatherGenetics.isGM() && !motherGenetics.isGM()) {
            return;
        }

        EntityAgeable baseChild = father.createChild(mother);

        IGenetics childGenetics = baseChild.getCapability(GeneticsProvider.GENETICS_CAPABILITY, null);
        childGenetics.setGM(true);

        List<Map<String, Double>> fatherGamete = getRandomGenes(fatherGenetics);
        List<Map<String, Double>> motherGamete = getRandomGenes(motherGenetics);

        childGenetics.setGenes(fatherGamete.get(0), fatherGamete.get(1), GeneticsSide.LEFT);
        childGenetics.setGenes(motherGamete.get(0), motherGamete.get(1), GeneticsSide.RIGHT);

        // Generates the real genes from the parent ones
        childGenetics.updateExpressed();

        event.setChild(baseChild);
    }

    @SuppressWarnings("unused")
    private static List<Map<String, Double>> getRandomGenes(IGenetics parentGenetics) {
        // Basically chooses left or right for each, or if there is no equivalent on the other side,
        // right/left or nothing
        List<Map<String, Double>> returnGeneList = new ArrayList<>();

        Random random = new Random();

        Map<String, Double> parentLeftGenes = parentGenetics.getGenes(GeneticsSide.LEFT);
        Map<String, Double> parentRightGenes = parentGenetics.getGenes(GeneticsSide.RIGHT);

        Map<String, Double> parentLeftGenesDom = parentGenetics.getGenesDominance(GeneticsSide.LEFT);
        Map<String, Double> parentRightGenesDom = parentGenetics.getGenesDominance(GeneticsSide.RIGHT);

        if (parentLeftGenes == parentRightGenes && parentLeftGenesDom == parentRightGenesDom) {
            returnGeneList.add(parentLeftGenes);
            returnGeneList.add(parentLeftGenesDom);
            return returnGeneList;
        }

        Map<String, Double> gameteGenes = new HashMap<>();
        Map<String, Double> gameteGenesDom = new HashMap<>();

        for (Map.Entry<String, Double> gene : parentLeftGenes.entrySet()) {
            if (parentRightGenes.containsKey(gene.getKey())) {
                if (random.nextDouble() > 0.5) {
                    gameteGenes.put(gene.getKey(), gene.getValue());
                    gameteGenesDom.put(gene.getKey(), parentLeftGenesDom.get(gene.getKey()));
                } else {
                    gameteGenes.put(gene.getKey(), parentRightGenes.get(gene.getKey()));
                    gameteGenesDom.put(gene.getKey(), parentRightGenesDom.get(gene.getKey()));
                    parentRightGenes.remove(gene.getKey());
                }
            } else {
                if (random.nextDouble() > 0.5) {
                    gameteGenes.put(gene.getKey(), gene.getValue());
                    gameteGenesDom.put(gene.getKey(), parentLeftGenesDom.get(gene.getKey()));
                }
            }
        }

        for (Map.Entry<String, Double> gene : parentRightGenes.entrySet()) {
            if (random.nextDouble() > 0.5) {
                gameteGenes.put(gene.getKey(), gene.getValue());
                gameteGenesDom.put(gene.getKey(), parentRightGenesDom.get(gene.getKey()));
            }
        }

        returnGeneList.add(gameteGenes);
        returnGeneList.add(gameteGenesDom);

        return returnGeneList;
    }
}
