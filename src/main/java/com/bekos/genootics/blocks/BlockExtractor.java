package com.bekos.genootics.blocks;

import com.bekos.genootics.tile.TileExtractor;
import jline.internal.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.io.Console;

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
