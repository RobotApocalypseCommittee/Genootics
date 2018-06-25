package com.bekos.genootics.block;

import com.bekos.genootics.tile.TileExtractor;
import jline.internal.Nullable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockExtractor extends BlockMachine {

    public static final int GUI_ID = 1;

    public BlockExtractor() {
        super("machineExtractor");
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileExtractor();
    }

    @Override
    public boolean checkTileEntity(TileEntity te) {
        return te instanceof TileExtractor;
    }

}
