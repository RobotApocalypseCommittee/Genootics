package com.bekos.genootics.items;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    private static ItemSyringe itemSyringe = new ItemSyringe();

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                itemSyringe
        );
    }

    public static void registerModels() {
        itemSyringe.registerItemModel();
    }
}
