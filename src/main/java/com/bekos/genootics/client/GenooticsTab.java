package com.bekos.genootics.client;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.item.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class GenooticsTab extends CreativeTabs {

    public GenooticsTab() {
        super(GenooticsMod.MODID);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.itemSyringe);
    }
}
