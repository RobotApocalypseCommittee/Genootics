package com.bekos.genootics.genetics;

import net.minecraft.entity.EntityAgeable;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Mod.EventBusSubscriber
public class BabySpawnEventHandler {

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

        Map<String, Double> mapChildGenes = shuffleGenes(fatherGenetics, motherGenetics).getAllGenes();

        IGenetics childGenetics = baseChild.getCapability(GeneticsProvider.GENETICS_CAPABILITY, null);
        childGenetics.setGM(true);
        childGenetics.setGenes(mapChildGenes);

        event.setChild(baseChild);
    }

    private static IGenetics shuffleGenes(IGenetics fatherGenetics, IGenetics motherGenetics) {
        /* 70% is both the retention chance and the retention modifier, this can be changed in config, or even augmented based on genes */
        Random random = new Random();

        IGenetics childGenetics = new GeneticsBase();

        Map<String, Double> childGenes = new HashMap<>();

        Map<String, Double> fatherGenes = fatherGenetics.getAllGenes();
        Map<String, Double> motherGenes = motherGenetics.getAllGenes();

        System.out.println(fatherGenes);
        System.out.println(motherGenes);

        for (Map.Entry<String, Double> gene : fatherGenes.entrySet()) {
            if (motherGenes.containsKey(gene.getKey())) {
                childGenes.put(gene.getKey(), (gene.getValue()+motherGenes.get(gene.getKey()))*0.7);
                motherGenes.remove(gene.getKey());
            } else {
                if (random.nextFloat() < 0.7) {
                    childGenes.put(gene.getKey(), gene.getValue() * 0.7);
                }
            }
        }

        for (Map.Entry<String, Double> gene : motherGenes.entrySet()) {
            if (random.nextFloat() < 0.7) {
                childGenes.put(gene.getKey(), gene.getValue() * 0.7);
            }
        }

        System.out.println(childGenes);

        childGenetics.setGenes(childGenes);

        return childGenetics;
    }
}
