package com.bekos.genootics.tile;

import net.minecraft.nbt.NBTTagCompound;

public class TileExtractor extends TileMachine {
    // Number of itemstacks stored.
    public static final int SIZE = 4;

    public TileExtractor() {
        super(SIZE, 10000, 20);
    }


}
