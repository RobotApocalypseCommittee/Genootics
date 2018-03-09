package com.bekos.genootics.genetics;

import com.bekos.genootics.util.NBTParser;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;

import java.util.List;
import java.util.Map;

public class GeneticsStorage implements Capability.IStorage<IGenetics> {

    @Override
    public NBTBase writeNBT(Capability<IGenetics> capability, IGenetics instance, EnumFacing side) {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setBoolean("IsGM", instance.isGM());

        compound.setBoolean("HasBeenEdited", instance.hasBeenEdited());

        if (!instance.isGM()) {
            return compound;
        }

        NBTTagList allGenes = NBTParser.convertMapListToNBT(instance.getAllGenes());
        compound.setTag("AllGenes", allGenes);

        NBTTagList allGenesDom = NBTParser.convertMapListToNBT(instance.getAllGenesDominances());
        compound.setTag("AllGenesDom", allGenesDom);

        return compound;
    }

    @Override
    public void readNBT(Capability<IGenetics> capability, IGenetics instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound compound = (NBTTagCompound) nbt;

        if(!compound.getBoolean("IsGM")) {
            instance.setGM(false);
        } else {
            instance.setGM(true);

            instance.setHasBeenEdited(compound.getBoolean("HasBeenEdited"));

            List<Map<String, Double>> allGenes = NBTParser.convertNBTToMapList(compound.getTagList("AllGenes", Constants.NBT.TAG_LIST));
            List<Map<String, Double>> allGenesDom = NBTParser.convertNBTToMapList(compound.getTagList("AllGenesDom", Constants.NBT.TAG_LIST));

            System.out.println(allGenes);

            instance.setAllGenes(allGenes, allGenesDom);
        }
    }

}
