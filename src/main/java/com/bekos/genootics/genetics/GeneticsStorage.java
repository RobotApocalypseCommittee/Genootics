package com.bekos.genootics.genetics;

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

        NBTTagList geneList = new NBTTagList();

        if (instance.isGM()) {
            for (Map.Entry<String, Double> entry : instance.getAllGenes().entrySet()) {
                String gene = entry.getKey();
                Double value = entry.getValue();
                NBTTagCompound geneCompound = new NBTTagCompound();

                geneCompound.setString("Gene", gene);
                geneCompound.setDouble("Value", value);

                geneList.appendTag(geneCompound);
            }

            compound.setTag("Genes", geneList);
        }

        return compound;
    }

    @Override

    public void readNBT(Capability<IGenetics> capability, IGenetics instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound compound = (NBTTagCompound) nbt;

        if(!compound.getBoolean("IsGM")) {
            instance.setGM(false);
        } else {
            instance.setGM(true);
            Map<String, Double> geneMap = new HashMap<>();

            Iterator<NBTBase> iterator = compound.getTagList("Genes", Constants.NBT.TAG_COMPOUND).iterator();

            while (iterator.hasNext()) {
                NBTTagCompound geneCompound = (NBTTagCompound) iterator.next();
                geneMap.put(geneCompound.getString("Gene"), geneCompound.getDouble("Value"));
            }

            instance.setGenes(geneMap);
        }
    }

}
