package com.bekos.genootics.item;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    public static ItemSyringe itemSyringe = new ItemSyringe();

    private static ItemManual itemManual = new ItemManual();
    private static ItemBase itemPetriWooden = new ItemBase("itemPetriWooden");

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                itemSyringe,
                itemManual,
                itemPetriWooden
        );
    }

    public static void registerModels() {
        itemSyringe.registerItemModel();
        itemManual.registerItemModel();
        itemPetriWooden.registerItemModel();
    }

    //Item registerer
    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ModItems.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerItems(ModelRegistryEvent event) {
            ModItems.registerModels();
        }
    }
}