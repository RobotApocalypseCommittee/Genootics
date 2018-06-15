package com.bekos.genootics.genetics;

import com.bekos.genootics.genetics.entityhandler.EntityHandlerGeneric;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.awt.*;

public class ModGenes {
    private static Gene geneHealth = new Gene("health", new Color(255, 0, 0))
            .setAnimalSpawnedCallback(EntityHandlerGeneric::changeEntityHealth);

    private static Gene geneMovementSpeed = new Gene("movementSpeed", new Color(0, 255, 0))
            .setAnimalSpawnedCallback(EntityHandlerGeneric::changeEntityMovementSpeed);

    public static void register(IForgeRegistry<Gene> registry) {
        registry.registerAll(
                geneHealth,
                geneMovementSpeed
        );
        System.out.println(geneHealth.getRegistryName());
    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Gene> event) {
            ModGenes.register(event.getRegistry());
        }

    }

}
