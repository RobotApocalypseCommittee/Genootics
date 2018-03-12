package com.bekos.genootics.tile;

import com.bekos.genootics.tile.energy.EnergyHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public abstract class TileMachine extends TileEntity implements ITickable {
    // Number of itemstacks stored.
    public static int SIZE;
    private int energyPerTick;
    public int ticksRemaining;

    private EnergyHandler energyStorage;
    protected ItemStackHandler itemStackHandler;

    public TileMachine(int itemSize, int capacity, int maxTransfer, int energyPerTick){
        super();
        this.energyPerTick = energyPerTick;
        ticksRemaining = 0;
        this.energyStorage = new EnergyHandler(capacity, maxTransfer);
        this.SIZE = itemSize;
        // This item handler will hold our nine inventory slots
        this.itemStackHandler = new ItemStackHandler(this.SIZE) {
            @Override
            protected void onContentsChanged(int slot) {
                // We need to tell the tile entity that something has changed so
                // that the chest contents is persisted
                markDirty();
            }
        };
    }

    public boolean canWork() {
        boolean x = this.energyStorage.extractEnergy(energyPerTick, true) == energyPerTick;
        return this.energyStorage.extractEnergy(energyPerTick, true) == energyPerTick;
    }

    public void doWork() {
        this.energyStorage.extractEnergy(energyPerTick, false);
    }



    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) {
            itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        }
        if (compound.hasKey("energy")){
            energyStorage.deserializeNBT((NBTTagCompound) compound.getTag("energy"));
        }
        if (compound.hasKey("ticksRemaining")) {
            this.ticksRemaining = compound.getInteger("ticksRemaining");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", itemStackHandler.serializeNBT());
        compound.setTag("energy", energyStorage.serializeNBT());
        compound.setInteger("ticksRemaining", this.ticksRemaining);
        return compound;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
        }
        if (capability == CapabilityEnergy.ENERGY) {
            return (T) energyStorage;
        }
        return super.getCapability(capability, facing);
    }

}
