package com.bekos.genootics.util;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NBTParser {

    public static Map<String, Double> convertNBTToMap(NBTTagList tagList, String keyKey, String valueKey) {
        Map<String, Double> geneMap = new HashMap<>();

        Iterator<NBTBase> iterator = tagList.iterator();

        while (iterator.hasNext()) {
            NBTTagCompound geneCompound = (NBTTagCompound) iterator.next();
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
}
