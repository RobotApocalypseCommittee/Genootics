package com.bekos.genootics.blocks;

import net.minecraft.block.material.Material;

public class BlockExtractor extends BlockBase {

    public BlockExtractor() {
        super(Material.ROCK, "machineExtractor");
        setHardness(3f);
        setResistance(5f);
    }

}
