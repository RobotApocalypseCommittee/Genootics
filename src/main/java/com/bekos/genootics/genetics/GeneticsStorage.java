package com.bekos.genootics.genetics;

import com.bekos.genootics.util.NBTParser;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GeneticsStorage implements Capability.IStorage<IGenetics> {

    @Override
    public NBTBase writeNBT(Capability<IGenetics> capability, IGenetics instance, EnumFacing side) {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setBoolean("IsGM", instance.isGM());

        NBTTagList geneList = NBTParser.convertMapToNBT(instance.getAllGenes());
        compound.setTag("Genes", geneList);

        return compound;
    }

    @Override
    public void readNBT(Capability<IGenetics> capability, IGenetics instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound compound = (NBTTagCompound) nbt;

        if(!compound.getBoolean("IsGM")) {
            instance.setGM(false);
        } else {
            instance.setGM(true);
            instance.setGenes(NBTParser.convertNBTToMap(compound.getTagList("Genes", Constants.NBT.TAG_COMPOUND)));
        }
    }

}
