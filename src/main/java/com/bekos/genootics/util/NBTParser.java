package com.bekos.genootics.util;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.*;

public abstract class NBTParser {

    public static Map<String, Double> convertNBTToMap(NBTTagList tagList, String keyKey, String valueKey) {
        Map<String, Double> geneMap = new HashMap<>();

        for (NBTBase aTagList : tagList) {
            NBTTagCompound geneCompound = (NBTTagCompound) aTagList;
            geneMap.put(geneCompound.getString(keyKey), geneCompound.getDouble(valueKey));
        }

        return geneMap;
    }

    public static Map<String, Double> convertNBTToMap(NBTTagList tagList) {
        return convertNBTToMap(tagList, "Gene", "Value");
    }

    public static NBTTagList convertMapToNBT(Map<String, Double> mapIn, String stringKey, String doubleKey) {
        NBTTagList tagList = new NBTTagList();

        for (Map.Entry<String, Double> entry : mapIn.entrySet()) {
            String name = entry.getKey();
            Double value = entry.getValue();

            NBTTagCompound geneCompound = new NBTTagCompound();
            geneCompound.setString(stringKey, name);
            geneCompound.setDouble(doubleKey, value);

            tagList.appendTag(geneCompound);
        }

        return tagList;
    }

    public static NBTTagList convertMapToNBT(Map<String, Double> mapIn) {
        return convertMapToNBT(mapIn, "Gene", "Value");
    }

    public static NBTTagList convertMapListToNBT(List<Map<String, Double>> listIn, String stringKey, String doubleKey) {
        NBTTagList tagList = new NBTTagList();
        for (Map<String, Double> map : listIn) {
            tagList.appendTag(convertMapToNBT(map, stringKey, doubleKey));
        }

        return tagList;
    }

    public static NBTTagList convertMapListToNBT(List<Map<String, Double>> listIn) {
        return convertMapListToNBT(listIn, "Gene", "Value");
    }

    public static List<Map<String, Double>> convertNBTToMapList(NBTTagList tagList, String keyKey, String valueKey) {
        List<Map<String, Double>> returnList = new ArrayList<>();

        for (NBTBase aTagList : tagList) {
            NBTTagList geneList = (NBTTagList) aTagList;
            returnList.add(convertNBTToMap(geneList, keyKey, valueKey));
        }

        return returnList;
    }

    public static List<Map<String, Double>> convertNBTToMapList(NBTTagList tagList) {
        return convertNBTToMapList(tagList, "Gene", "Value");
    }
}
