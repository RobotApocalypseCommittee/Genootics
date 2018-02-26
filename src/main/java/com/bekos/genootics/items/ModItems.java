package com.bekos.genootics.items;

import net.minecraft.item.Item;
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

}
