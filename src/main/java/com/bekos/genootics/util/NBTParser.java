package com.bekos.genootics.util;

import com.bekos.genootics.genetics.Gene;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.*;

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
