package com.bekos.genootics.genetics;

import com.bekos.genootics.genetics.entityhandler.EntityHandlerGeneric;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.*;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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

            if (entity.getCapability(GeneticsProvider.GENETICS_CAPABILITY, null).hasBeenEdited()) {
                return;
            }

            NBTTagCompound compound = handleGeneSwitchboard(entity);

            entity.getCapability(GeneticsProvider.GENETICS_CAPABILITY, null).setHasBeenEdited(true);

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

            System.out.println(compound);

            entity.readEntityFromNBT(compound);
        }
    }

    private static NBTTagCompound handleGeneSwitchboard(EntityLivingBase entity) {
        NBTTagCompound compound = new NBTTagCompound();
        entity.writeEntityToNBT(compound);

        IGenetics entityGenetics = entity.getCapability(GeneticsProvider.GENETICS_CAPABILITY, null);

        System.out.println(entityGenetics.getAllGenes());

        Map<String, Double> expressedGenes = entityGenetics.getGenes(GeneticsSide.EXPRESSED);
        Map<String, Double> expressedGenesDom = entityGenetics.getGenesDominance(GeneticsSide.EXPRESSED);

        for (Map.Entry<String, Double> gene : expressedGenes.entrySet()) {
            compound = chooseGeneFunction(gene.getKey(), gene.getValue(), expressedGenesDom.get(gene.getKey()), compound);
        }

        return compound;
    }

    private static NBTTagCompound chooseGeneFunction(String gene, Double value, Double dominance, NBTTagCompound priorCompound) {
        NBTTagCompound compound = new NBTTagCompound();

        switch (gene) {
            case "Health": compound = EntityHandlerGeneric.changeEntityHealth(value, dominance, priorCompound); break;
            case "MovementSpeed": compound = EntityHandlerGeneric.changeEntityMovementSpeed(value, dominance, priorCompound); break;
        }

        return compound;
    }
}
