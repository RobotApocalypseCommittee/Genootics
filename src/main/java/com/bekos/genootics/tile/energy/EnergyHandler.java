package com.bekos.genootics.tile.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyHandler extends EnergyStorage implements INBTSerializable<NBTTagCompound> {

    public EnergyHandler(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public void setEnergyStored(int energyStored) {
        System.out.println("Set energy");
        energy = Math.min(energyStored, capacity);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("capacity", capacity);
        tag.setInteger("maxReceive", maxReceive);
        tag.setInteger("maxExtract", maxExtract);
        tag.setInteger("energy", energy);
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        capacity = nbt.getInteger("capacity");
        maxExtract = nbt.getInteger("maxExtract");
        maxReceive = nbt.getInteger("maxReceive");
        energy = nbt.getInteger("energy");
    }
}
