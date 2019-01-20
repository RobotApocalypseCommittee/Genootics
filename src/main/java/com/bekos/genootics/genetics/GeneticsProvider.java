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

package com.bekos.genootics.genetics;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class GeneticsProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IGenetics.class)
    public static final Capability<IGenetics> GENETICS_CAPABILITY = null;

    private final IGenetics instance = GENETICS_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == GENETICS_CAPABILITY;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == GENETICS_CAPABILITY ? GENETICS_CAPABILITY.cast(this.instance) : null;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public NBTBase serializeNBT() {
        return GENETICS_CAPABILITY.getStorage().writeNBT(GENETICS_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        GENETICS_CAPABILITY.getStorage().readNBT(GENETICS_CAPABILITY, this.instance, null, nbt);
    }
}
