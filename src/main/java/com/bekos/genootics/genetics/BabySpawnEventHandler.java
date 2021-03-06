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

        List<Map<Gene, Double>> fatherGamete = getRandomGenes(fatherGenetics);
        List<Map<Gene, Double>> motherGamete = getRandomGenes(motherGenetics);

        childGenetics.setGenes(fatherGamete.get(0), fatherGamete.get(1), GeneticsSide.LEFT);
        childGenetics.setGenes(motherGamete.get(0), motherGamete.get(1), GeneticsSide.RIGHT);

        // Generates the real genes from the parent ones
        childGenetics.updateExpressed();

        event.setChild(baseChild);
    }

    @SuppressWarnings("unused")
    private static List<Map<Gene, Double>> getRandomGenes(IGenetics parentGenetics) {
        // Basically chooses left or right for each, or if there is no equivalent on the other side,
        // right/left or nothing
        List<Map<Gene, Double>> returnGeneList = new ArrayList<>();

        Random random = new Random();

        Map<Gene, Double> parentLeftGenes = parentGenetics.getGenes(GeneticsSide.LEFT);
        Map<Gene, Double> parentRightGenes = parentGenetics.getGenes(GeneticsSide.RIGHT);

        Map<Gene, Double> parentLeftGenesDom = parentGenetics.getGenesDominance(GeneticsSide.LEFT);
        Map<Gene, Double> parentRightGenesDom = parentGenetics.getGenesDominance(GeneticsSide.RIGHT);

        if (parentLeftGenes == parentRightGenes && parentLeftGenesDom == parentRightGenesDom) {
            returnGeneList.add(parentLeftGenes);
            returnGeneList.add(parentLeftGenesDom);
            return returnGeneList;
        }

        Map<Gene, Double> gameteGenes = new HashMap<>();
        Map<Gene, Double> gameteGenesDom = new HashMap<>();

        for (Map.Entry<Gene, Double> gene : parentLeftGenes.entrySet()) {
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

        for (Map.Entry<Gene, Double> gene : parentRightGenes.entrySet()) {
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
