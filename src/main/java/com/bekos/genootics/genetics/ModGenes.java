package com.bekos.genootics.genetics;

import com.bekos.genootics.genetics.entityhandler.EntityHandlerGeneric;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModGenes {
    private static Gene geneHealth = new Gene("health") {
        @Override
        public NBTTagCompound onAppliedAnimalSpawned(NBTTagCompound priorCompound, Double value, Double dominance) {
            return EntityHandlerGeneric.changeEntityHealth(value, dominance, priorCompound);
        }
    };
    private static Gene geneMovementSpeed = new Gene("movementSpeed") {
        @Override
        public NBTTagCompound onAppliedAnimalSpawned(NBTTagCompound priorCompound, Double value, Double dominance) {
            return EntityHandlerGeneric.changeEntityMovementSpeed(value, dominance, priorCompound);
        }
    };
    public static void register(IForgeRegistry<Gene> registry) {
        registry.registerAll(
                geneHealth,
                geneMovementSpeed
        );
    }
    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Gene> event) {
            ModGenes.register(event.getRegistry());
        }

    }

}
