/*
 * Genootics Minecraft mod adding genetics to Minecraft
 * Copyright (C) 2019  Robot Apocalypse Committee
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

package com.bekos.genootics.tile.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyHandler extends EnergyStorage implements INBTSerializable<NBTTagCompound> {

    public EnergyHandler(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public void setEnergyStored(int energyStored) {
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
