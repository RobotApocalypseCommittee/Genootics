package com.bekos.genootics.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {

    public static BlockExtractor machineExtractor = new BlockExtractor();
    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(
                machineExtractor
        );
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(
                machineExtractor.createItemBlock()
        );

    }

    public static void registerModels() {
        machineExtractor.registerItemModel(Item.getItemFromBlock(machineExtractor));
    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            ModBlocks.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ModBlocks.registerItemBlocks(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            ModBlocks.registerModels();
        }

    }
}
