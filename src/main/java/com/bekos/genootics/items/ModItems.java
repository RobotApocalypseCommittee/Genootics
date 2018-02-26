package com.bekos.genootics.items;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    private static ItemSyringe itemSyringe = new ItemSyringe();
    private static ItemManual itemManual = new ItemManual();

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                itemSyringe,
                itemManual
        );
    }

    public static void registerModels() {
        itemSyringe.registerItemModel();
        itemManual.registerItemModel();
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
