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

        if (!compound.getBoolean("IsGM")) {
            instance.setGM(false);
        } else {
            instance.setGM(true);

            instance.setHasBeenEdited(compound.getBoolean("HasBeenEdited"));

            List<Map<Gene, Double>> allGenes = NBTParser.convertNBTToMapList(compound.getTagList("AllGenes", Constants.NBT.TAG_LIST));
            List<Map<Gene, Double>> allGenesDom = NBTParser.convertNBTToMapList(compound.getTagList("AllGenesDom", Constants.NBT.TAG_LIST));

            System.out.println(allGenes);

            instance.setAllGenes(allGenes, allGenesDom);
        }
    }

}
