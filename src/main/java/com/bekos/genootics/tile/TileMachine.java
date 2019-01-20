/*
 * Genootics Minecraft mod adding genetics to Minecraft
 * Copyright (C) 2018  Robot Apocalypse Committee
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.bekos.genootics.tile;

import com.bekos.genootics.tile.energy.EnergyHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;


public abstract class TileMachine extends TileItemDataSync implements ITickable {
    protected int ticksRemaining;
    private int energyPerTick;
    private EnergyHandler energyStorage;

    public TileMachine(int itemSize, int capacity, int maxTransfer, int energyPerTick) {
        super(itemSize);
        this.energyPerTick = energyPerTick;
        ticksRemaining = 0;
        this.energyStorage = new EnergyHandler(capacity, maxTransfer);
    }

    public int getTicksRemaining() {
        return ticksRemaining;
    }

    public boolean canWork() {
        return this.energyStorage.extractEnergy(energyPerTick, true) == energyPerTick;
    }

    public void doWork() {
        this.energyStorage.extractEnergy(energyPerTick, false);
    }


    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("energy")) {
            energyStorage.deserializeNBT((NBTTagCompound) compound.getTag("energy"));
        }
        if (compound.hasKey("ticksRemaining")) {
            this.ticksRemaining = compound.getInteger("ticksRemaining");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("energy", energyStorage.serializeNBT());
        compound.setInteger("ticksRemaining", this.ticksRemaining);
        return compound;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return (T) energyStorage;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void setField(byte id, short value) {
        switch (id) {
            case 0:
                energyStorage.setEnergyStored((int) value);
                break;
            case 1:
                ticksRemaining = (int) value;
                break;
        }
    }

    @Override
    public short getField(byte id) {
        switch (id) {
            case 0:
                return (short) energyStorage.getEnergyStored();
            case 1:
                return (short) ticksRemaining;
            default:
                return -1;
        }
    }

    @Override
    public int getFieldsLength() {
        return 2;
    }

}
