package com.bekos.genootics.items;

import com.bekos.genootics.GenooticsMod;
import com.bekos.genootics.client.GenooticsTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemBase extends Item {
    String name;

    public ItemBase(String name) {
        this.name = name;
        setUnlocalizedName(GenooticsMod.MODID + '.' + name);
        setRegistryName(name);
        setCreativeTab(GenooticsMod.creativeTab);
    }

    public void registerItemModel() {
        GenooticsMod.proxy.registerItemRenderer(this, 0, name);
    }

    @Override
    public ItemBase setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }


    protected NBTTagCompound getTagCompoundSafe(ItemStack stack) {
        NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
            stack.setTagCompound(tagCompound);
        }
        return tagCompound;
    }
}
