package com.bekos.genootics.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockExtractor extends BlockBase {

    public BlockExtractor() {
        super(Material.ROCK, "machineExtractor");
        setHardness(3f);
        setResistance(5f);
    }

}
