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

package com.bekos.genootics.util;

import com.bekos.genootics.genetics.Gene;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class NBTParser {

    public static Gene getGeneFromString(String key) {
        IForgeRegistry<Gene> registry = GameRegistry.findRegistry(Gene.class);
        return registry.getValue(new ResourceLocation(key));
    }

    public static String getKeyForGene(Gene gene) {
        return gene.getRegistryName().toString();
    }

    public static Map<Gene, Double> convertNBTToMap(NBTTagList tagList, String keyKey, String valueKey) {
        Map<Gene, Double> geneMap = new HashMap<>();

        for (NBTBase aTagList : tagList) {
            NBTTagCompound geneCompound = (NBTTagCompound) aTagList;
            geneMap.put(getGeneFromString(geneCompound.getString(keyKey)), geneCompound.getDouble(valueKey));
        }

        return geneMap;
    }

    public static Map<Gene, Double> convertNBTToMap(NBTTagList tagList) {
        return convertNBTToMap(tagList, "Gene", "Value");
    }

    public static NBTTagList convertMapToNBT(Map<Gene, Double> mapIn, String stringKey, String doubleKey) {
        NBTTagList tagList = new NBTTagList();

        for (Map.Entry<Gene, Double> entry : mapIn.entrySet()) {
            String name = getKeyForGene(entry.getKey());
            Double value = entry.getValue();

            NBTTagCompound geneCompound = new NBTTagCompound();
            geneCompound.setString(stringKey, name);
            geneCompound.setDouble(doubleKey, value);

            tagList.appendTag(geneCompound);
        }

        return tagList;
    }

    public static NBTTagList convertMapToNBT(Map<Gene, Double> mapIn) {
        return convertMapToNBT(mapIn, "Gene", "Value");
    }

    public static NBTTagList convertMapListToNBT(List<Map<Gene, Double>> listIn, String stringKey, String doubleKey) {
        NBTTagList tagList = new NBTTagList();
        for (Map<Gene, Double> map : listIn) {
            tagList.appendTag(convertMapToNBT(map, stringKey, doubleKey));
        }

        return tagList;
    }

    public static NBTTagList convertMapListToNBT(List<Map<Gene, Double>> listIn) {
        return convertMapListToNBT(listIn, "Gene", "Value");
    }

    public static List<Map<Gene, Double>> convertNBTToMapList(NBTTagList tagList, String keyKey, String valueKey) {
        List<Map<Gene, Double>> returnList = new ArrayList<>();

        for (NBTBase aTagList : tagList) {
            NBTTagList geneList = (NBTTagList) aTagList;
            returnList.add(convertNBTToMap(geneList, keyKey, valueKey));
        }

        return returnList;
    }

    public static List<Map<Gene, Double>> convertNBTToMapList(NBTTagList tagList) {
        return convertNBTToMapList(tagList, "Gene", "Value");
    }
}
