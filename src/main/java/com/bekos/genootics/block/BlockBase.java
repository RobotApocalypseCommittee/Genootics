package com.bekos.genootics.block;

import com.bekos.genootics.GenooticsMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block {
    protected String name;

    public BlockBase(Material material, String name) {
        super(material);

        this.name = name;

        setUnlocalizedName(GenooticsMod.MODID + '.' + name);
        setRegistryName(name);
        setCreativeTab(GenooticsMod.creativeTab);
    }

    public BlockBase(Material material, String name, float hardness, float resistance) {
        this(material, name);

        setHardness(hardness);
        setResistance(resistance);
    }

    public void registerItemModel(Item itemBlock) {
        GenooticsMod.proxy.registerItemRenderer(itemBlock, 0, name);
    }

    public Item createItemBlock() {
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }

    @Override
    public BlockBase setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }
}
