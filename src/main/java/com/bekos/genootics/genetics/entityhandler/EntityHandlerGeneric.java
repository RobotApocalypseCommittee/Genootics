package com.bekos.genootics.genetics.entityhandler;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.Iterator;

public abstract class EntityHandlerGeneric {

    public static NBTTagCompound changeEntityHealth(Double value, Double dominance, NBTTagCompound priorCompound) {
        // THIS IS WHERE GENE DESCRIPTIONS SHOULD GO. PAR EXEMPLE:
        // Sets the heath of the entity at spawn (is equivalent to MaxHealth, but only sets it higher
        // than the base, whereas the health itself can be lower than the default.
        // "value" is a multiplier of health (and max health)

        priorCompound.setDouble("Health", priorCompound.getDouble("Health")*value);

        NBTTagList attributes = priorCompound.getTagList("Attributes", Constants.NBT.TAG_COMPOUND);
        Iterator<NBTBase> iterator = attributes.iterator();

        boolean update = true;
        while (iterator.hasNext()) {
            NBTTagCompound compound = (NBTTagCompound) iterator.next();

            if (compound.getString("Name").equals("generic.maxHealth")) {
                if (update = compound.getDouble("Base") < value*priorCompound.getDouble("Health")) {
                    iterator.remove();
                }
                break;
            }
        }

        if (update) {
            NBTTagCompound newCompound = new NBTTagCompound();
            newCompound.setString("Name", "generic.maxHealth");
            newCompound.setDouble("Base", priorCompound.getDouble("Health"));

            attributes.appendTag(newCompound);

            priorCompound.setTag("Attributes", attributes);
        }

        return priorCompound;
    }

    public static NBTTagCompound changeEntityMovementSpeed(Double value, Double dominance, NBTTagCompound priorCompound) {
        // Sets the movement speed of an entity. This is a MULTIPLIER of the base

        NBTTagList attributes = priorCompound.getTagList("Attributes", Constants.NBT.TAG_COMPOUND);
        Iterator<NBTBase> iterator = attributes.iterator();

        Double priorValue = 2.0;
        while (iterator.hasNext()) {
            NBTTagCompound compound = (NBTTagCompound) iterator.next();

            if (compound.getString("Name").equals("generic.movementSpeed")) {
                iterator.remove();
                priorValue = compound.getDouble("Base");
                break;
            }
        }

        NBTTagCompound newCompound = new NBTTagCompound();
        newCompound.setString("Name", "generic.movementSpeed");
        newCompound.setDouble("Base", value*priorValue);

        attributes.appendTag(newCompound);

        priorCompound.setTag("Attributes", attributes);

        return priorCompound;
    }
}
