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
