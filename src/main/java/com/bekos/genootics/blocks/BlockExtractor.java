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

public class BlockExtractor extends BlockMachine<TileExtractor> {

    public BlockExtractor() {
        super("machineExtractor");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote){
            TextComponentString component = new TextComponentString("HELLO");
            player.sendStatusMessage(component, false);
        }
        System.out.print("OK");
        return true;
    }

    @Override
    public Class<TileExtractor> getTileEntityClass() {
        return TileExtractor.class;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileExtractor();
    }

    @javax.annotation.Nullable
    @Override
    public TileExtractor createTileEntity(World world, IBlockState state) {
        return new TileExtractor();
    }
}
