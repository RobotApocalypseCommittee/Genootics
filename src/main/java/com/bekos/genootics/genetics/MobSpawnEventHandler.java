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

            GeneticsBase entityGenetics = (GeneticsBase) entity.getCapability(GeneticsProvider.GENETICS_CAPABILITY, null);

            if (!entityGenetics.isGM()) {
                return;
            }

            Double health = entityGenetics.getGeneValue("Health");
            Double speed = entityGenetics.getGeneValue("MovementSpeed");

            if (health == null) {
                health = 5d;
            }
            if (speed == null) {
                speed = 5d;
            }

            NBTTagCompound compound = new NBTTagCompound();
            entity.writeEntityToNBT(compound);

            //Makes them have 5 health and sets their movement speed to a VERY large number (hehe)
            //This is not how it will stay, but at least for now it's a calm day

            compound.setDouble("Health", health);

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
            newCompound.setDouble("Base", speed);
            newCompound.setString("Name", "generic.movementSpeed");

            attributes.appendTag(newCompound);

            /*NBTTagList attributes = new NBTTagList();
            NBTTagCompound newCompound = new NBTTagCompound();
            newCompound.setDouble("Base", speed);
            newCompound.setString("Name", "generic.movementSpeed");
            attributes.appendTag(newCompound); TODO Is this a viable replacement?*/

            compound.setTag("Attributes", attributes);

            entity.readEntityFromNBT(compound);
        }
    }

    public static void handleGeneric(EntityLiving entity) {
        GeneticsBase entityGenetics = (GeneticsBase) entity.getCapability(GeneticsProvider.GENETICS_CAPABILITY, null);

        NBTTagCompound compound = new NBTTagCompound();
        entity.writeEntityToNBT(compound);
    }
}
