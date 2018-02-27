package com.bekos.genootics.genetics;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.*;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Iterator;

@Mod.EventBusSubscriber
public class MobSpawnEventHandler {

    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinWorldEvent event) {
        if(event.getEntity() instanceof EntityLiving && !(event.getEntity() instanceof EntityPlayer)) {
            EntityLiving entity = (EntityLiving) event.getEntity();

            NBTTagCompound compound = new NBTTagCompound();
            entity.writeEntityToNBT(compound);

            if (!compound.hasKey("GeneticallyModified")) {
                return;
            }

            //Makes them have 5 health and sets their movement speed to a VERY large number (hehe)
            //This is not how it will stay, but at least for now it's a calm day

            compound.setFloat("Health", 5f);

            NBTTagList attributes = compound.getTagList("Attributes", Constants.NBT.TAG_COMPOUND);
            Iterator<NBTBase> iterator = attributes.iterator();

            while (iterator.hasNext()) {
                NBTTagCompound innerCompound = (NBTTagCompound) iterator.next();
                if (innerCompound.getString("Name").equals("generic.movementSpeed")) {
                    iterator.remove();
                    break;
                }
            }

            NBTTagCompound newCompound = new NBTTagCompound();
            newCompound.setDouble("Base", 10d);
            newCompound.setString("Name", "generic.movementSpeed");

            attributes.appendTag(newCompound);

            compound.setTag("Attributes", attributes);

            entity.readEntityFromNBT(compound);
        }
    }
}
