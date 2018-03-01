package com.bekos.genootics.genetics;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.*;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber
public class MobSpawnEventHandler {

    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinWorldEvent event) {
        if(event.getEntity() instanceof EntityLiving && !(event.getEntity() instanceof EntityPlayer)) {
            EntityLiving entity = (EntityLiving) event.getEntity();

            if (!entity.getCapability(GeneticsProvider.GENETICS_CAPABILITY, null).isGM()) {
                return;
            }

            NBTTagCompound compound = handleNBTGenes(entity);

            /*GeneticsBase entityGenetics = (GeneticsBase) entity.getCapability(GeneticsProvider.GENETICS_CAPABILITY, null);

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
            attributes.appendTag(newCompound); TODO Is this a viable replacement?/

            compound.setTag("Attributes", attributes);*/

            entity.readEntityFromNBT(compound);
        }
    }

    private static NBTTagCompound handleNBTGenes(EntityLiving entity) {
        IGenetics entityGenetics = entity.getCapability(GeneticsProvider.GENETICS_CAPABILITY, null);

        NBTTagCompound compound = new NBTTagCompound();
        entity.writeEntityToNBT(compound);

        NBTTagList attributes = compound.getTagList("Attributes", Constants.NBT.TAG_COMPOUND);

        attributes = cleanAttributes(attributes, entityGenetics);

        attributes = updateAttributes(attributes, entityGenetics);

        compound = updateNonAttributeGenes(compound, entityGenetics);

        compound.setTag("Attributes", attributes);

        System.out.println(compound);

        return compound;
    }

    private static NBTTagList cleanAttributes(NBTTagList attributes, IGenetics entityGenetics) {
        List<String> nbtAttributes = new ArrayList<>();

        for (String gene : entityGenetics.getAllGenes().keySet()) { //ALL ATTRIBUTE GENES ARE THE SNAKE CASE VERSION (minus "generic.")
            String attributeName = "generic." + Character.toLowerCase(gene.charAt(0)) + gene.substring(1);
            if (GeneticsProvider.GENERIC_ATTRIBUTE_GENES.contains(attributeName)) {
                nbtAttributes.add(attributeName);
            }
        }

        Iterator<NBTBase> iterator = attributes.iterator();

        while (iterator.hasNext()) {
            if (nbtAttributes.contains(((NBTTagCompound) iterator.next()).getString("Name"))) {
                iterator.remove();
            }
        }

        return attributes;
    }

    private static NBTTagList updateAttributes(NBTTagList attributes, IGenetics entityGenetics) {
        for (Map.Entry<String, Double> gene : entityGenetics.getAllGenes().entrySet()) {
            String geneName = "generic." + Character.toLowerCase(gene.getKey().charAt(0)) + gene.getKey().substring(1);

            if (!GeneticsProvider.GENERIC_ATTRIBUTE_GENES.contains(geneName)) {
                continue;
            }

            Double geneValue = gene.getValue();

            NBTTagCompound geneCompound = new NBTTagCompound();
            geneCompound.setString("Name", geneName);
            geneCompound.setDouble("Base", geneValue);
            attributes.appendTag(geneCompound);
        }

        return attributes;
    }

    private static NBTTagCompound updateNonAttributeGenes(NBTTagCompound compound, IGenetics entityGenetics) {
        Map<String, Double> geneList = entityGenetics.getAllGenes();

        for (Map.Entry<String, Double> gene : geneList.entrySet()) { //ALL ATTRIBUTES ARE THE EXACT COPY OF THE NBT
            if (compound.hasKey(gene.getKey())) {
                compound.setDouble(gene.getKey(), gene.getValue());
            }
        }

        return compound;
    }
}
