package com.bekos.genootics.machines;

import com.bekos.genootics.machines.client.GuiExtractor;
import com.bekos.genootics.machines.common.ContainerExtractor;
import com.bekos.genootics.tile.TileExtractor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiProxy implements IGuiHandler {

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileExtractor) {
            return new ContainerExtractor(player.inventory, (TileExtractor) te);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileExtractor) {
            TileExtractor containerTileEntity = (TileExtractor) te;
            return new GuiExtractor(containerTileEntity, new ContainerExtractor(player.inventory, containerTileEntity));
        }
        return null;
    }
}
